package com.widget;

import com.widget.providers.DatosSQLiteHelper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class muestra extends Activity
{
	ListView listview ;
	TextView textView ;
	Context context;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.muestra);
		
		textView = (TextView)findViewById(R.id.unodeellos);
		
		
		String	nombre;
		
		
		 Uri allTitles = Uri.parse("content://com.content_provider.Datos");
	        Cursor c = getContentResolver().query(allTitles, null,null,null,null);
	        int nameCol = c.getColumnIndex(DatosSQLiteHelper.colNombre);
	        if(c.moveToFirst())
	        {
	        	do
	        	{
	        		nombre = c.getString(nameCol);
	        		
	        			Toast.makeText(this, c.getString(c.getColumnIndex("nombre")), Toast.LENGTH_LONG).show();
	        	//	Toast.makeText(this, c.getString(c.getColumnIndex(DatosSQLiteHelper.nombre)), Toast.LENGTH_LONG).show();
	        	}while(c.moveToNext());
	        }
	        
	    //    String[] projection = new String{Datos.codigo,Datos.tipo,Datos.nombre,Datos.desc_breve,Datos};
		} 
	
	   

	}
