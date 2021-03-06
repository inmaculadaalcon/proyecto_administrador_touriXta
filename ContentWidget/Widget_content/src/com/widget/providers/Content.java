package com.widget.providers;

import java.util.List;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class Content extends ContentProvider
{
	public static final String AUTHORITY = "com.widget.providers.content";
	public static final String Datos = "Datos";
	public static final int ALLDatos = 1;
	public static final int SINGLEDatos = 2;
	public static final int Dato = 3;
	public static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static
	{
		matcher.addURI(AUTHORITY, null, ALLDatos);
		matcher.addURI(AUTHORITY, Datos, Dato);
		matcher.addURI(AUTHORITY, "#", SINGLEDatos);
	}
	
	DatosSQLiteHelper db;
	private List<String> Listsegments;
	public static final Uri CONTENT_URI = Uri.parse("content://datos");
	
	public int delete(Uri uri, String where, String[] args) 
	{
		int match = matcher.match(uri);
		if(match == 1)
		{
			SQLiteDatabase database = db.getWritableDatabase();
			return database.delete(db.DatosTable, where, args);
		}else
			return 0;
	}
	
	public String getType(Uri uri)
	{
		int match = matcher.match(uri);
		if(match == 2)
		{
			return "com.widget.Dato";
		}else
		{
			return "com.widget.Datos";
		}
	}

	public Uri insert(Uri uri, ContentValues values) 
	{
		int match = matcher.match(uri);
		long newID = 0;
		if(match != 1)
		{
			throw new IllegalArgumentException("Wrong Uri"+ uri.toString());
		}
		if(values != null)
		{
			newID = db.getWritableDatabase().insert(DatosSQLiteHelper.DatosTable, DatosSQLiteHelper.colNombre, values);
			return Uri.withAppendedPath(uri, String.valueOf(newID));
		}else
		{
			return null;
		}
	
	}
	public boolean onCreate() 
	{
		db = new DatosSQLiteHelper(this.getContext());
		if(db == null)
		{
			return false;
		}else
		{
			return true;
		}
		
	}
	

	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) 
	{
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(DatosSQLiteHelper.viewDatos);
		String order = null;
		Cursor result = null;
		if(sortOrder != null)
		{
			order = sortOrder;
		}
		int match = matcher.match(uri);
		switch(match)
		{
		case ALLDatos:
				result = builder.query(db.getWritableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
				break;
		case SINGLEDatos:
			List<String> listSegments = uri.getPathSegments();
			String DatoID = listSegments.get(0);
			result = db.getDatoByID(DatoID);
			break;
			
		case Dato:
			result = builder.query(db.getWritableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
			break;
		}
		return result;
	}

	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) 
	{
		int match = matcher.match(uri);
		int rows = 0;
		if(match == 2)
		{
			if(values!= null)
			{
				Listsegments = uri.getPathSegments();
				String codigo = Listsegments.get(0);
				rows = db.getWritableDatabase().update(DatosSQLiteHelper.DatosTable, values, DatosSQLiteHelper.colCodigo+ "=?",new String[]{codigo});
			}
		}
		return rows;
	}
}