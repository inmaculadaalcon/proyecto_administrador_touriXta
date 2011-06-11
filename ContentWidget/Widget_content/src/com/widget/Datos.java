package com.widget;

import com.widget.providers.DatosSQLiteHelper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class Datos extends Activity
{
	 DatosSQLiteHelper usdbh;
	public static final SQLiteDatabase db = null;
	public void onCreate(Bundle savedInstanceState) 
	{
       super.onCreate(savedInstanceState);
	  //Creación De la base de datos
    DatosSQLiteHelper usdbh = new DatosSQLiteHelper(this);
    SQLiteDatabase db = usdbh.getWritableDatabase();
    
    	if(db != null)
    	{
    		String[] tipos = {"Monumento00","Estadio","Escuela"};
    		for(int i = 0; i< 3;i++)
    		{
    			int codigo = i;
    			db.execSQL("INSERT INTO Tipos(codigo,tipo)"+"VALUES("+codigo+",'"+tipos[i]+"')");
    		}
    		//cerramos la base de datos
    	db.close();
    }
    
	}
}
