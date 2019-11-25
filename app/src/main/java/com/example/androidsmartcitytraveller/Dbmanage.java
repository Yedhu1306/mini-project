package com.example.androidsmartcitytraveller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Dbmanage extends SQLiteOpenHelper {

    private static final int DB_Version=1;
    private static final String DB_name="travel";
    private static final String DB_Col2="Latitude";
    private static final String DB_Col3="Longitude";
    private static final String DB_Col1="PlaceName";
    private static final String DB_Table="placestable";
    private static final String DB_Table2="placestable2";
    private static final String DB_Table3="placestable3";
    private static final String DB_Table4="final_placestable";

    String query= String.format("CREATE TABLE %s(%s TEXT UNIQUE,%s TEXT NOT NULL UNIQUE,%s TEXT NOT NULL UNIQUE);",DB_Table,DB_Col1,DB_Col2,DB_Col3);
    String query2= String.format("CREATE TABLE %s(%s TEXT UNIQUE,%s TEXT NOT NULL UNIQUE,%s TEXT NOT NULL UNIQUE);",DB_Table2,DB_Col1,DB_Col2,DB_Col3);
    String query3= String.format("CREATE TABLE %s(%s TEXT UNIQUE,%s TEXT NOT NULL UNIQUE,%s TEXT NOT NULL UNIQUE);",DB_Table3,DB_Col1,DB_Col2,DB_Col3);
    String query4= String.format("CREATE TABLE %s(%s TEXT UNIQUE,%s TEXT NOT NULL UNIQUE,%s TEXT NOT NULL UNIQUE);",DB_Table4,DB_Col1,DB_Col2,DB_Col3);


    //  Context mcontext;





    public Dbmanage(Context mcontext){

        super(mcontext,DB_name,null,DB_Version);


    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);
        sqLiteDatabase.execSQL(query3);
        sqLiteDatabase.execSQL(query4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query= String.format("DROP TABLE IF EXISTS "+DB_Table);
        String query2= String.format("DROP TABLE IF EXISTS "+DB_Table2);
        String query3= String.format("DROP TABLE IF EXISTS "+DB_Table3);
        String query4= String.format("DROP TABLE IF EXISTS "+DB_Table4);
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);

    }

    public void addtask(String placename, String lat, String lng){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Dbmanage.DB_Col1,placename);
        values.put(Dbmanage.DB_Col2,lat);
        values.put(Dbmanage.DB_Col3,lng);
        db.insertWithOnConflict(DB_Table,null,values, SQLiteDatabase.CONFLICT_REPLACE);
     //   db.close();

    }

    public void addtask2(String placename, String lat, String lng){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Dbmanage.DB_Col1,placename);
        values.put(Dbmanage.DB_Col2,lat);
        values.put(Dbmanage.DB_Col3,lng);
        db.insertWithOnConflict(DB_Table2,null,values, SQLiteDatabase.CONFLICT_REPLACE);
       // db.close();

    }
    public void addtask3(String placename, String lat, String lng){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Dbmanage.DB_Col1,placename);
        values.put(Dbmanage.DB_Col2,lat);
        values.put(Dbmanage.DB_Col3,lng);
        db.insertWithOnConflict(DB_Table3,null,values, SQLiteDatabase.CONFLICT_REPLACE);
        //db.close();

    }
    public void addtask4(String placename, String lat, String lng){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Dbmanage.DB_Col1,placename);
        values.put(Dbmanage.DB_Col2,lat);
        values.put(Dbmanage.DB_Col3,lng);
        db.insertWithOnConflict(DB_Table4,null,values, SQLiteDatabase.CONFLICT_REPLACE);
        //db.close();

    }


    public List<CustomList> getallTask(String table){
        SQLiteDatabase db=this.getWritableDatabase();
        List<CustomList> list=new ArrayList<>();
        Cursor cursor=db.query(table,new String[]{DB_Col1,DB_Col2,DB_Col3},null,null,null,null,null);
        while(cursor.moveToNext()){
            int pos=cursor.getColumnIndex(DB_Col1);
            int pos2=cursor.getColumnIndex(DB_Col2);
            int pos3=cursor.getColumnIndex(DB_Col3);
            list.add(new CustomList(cursor.getString(pos),cursor.getString(pos2),cursor.getString(pos3)));
        }
        cursor.close();
        //db.close();
        return list;
    }

    public void deletetask(){

        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(DB_Table,null,null);
        db.execSQL("DELETE FROM "+DB_Table);
        db.delete(DB_Table2,null,null);
        db.execSQL("DELETE FROM "+DB_Table2);
        db.delete(DB_Table3,null,null);
        db.execSQL("DELETE FROM "+DB_Table3);

        if( getallTask("final_placestable")!=null)
        {
            db.delete(DB_Table4,null,null);
            db.execSQL("DELETE FROM "+DB_Table4);


        }

       // db.close();

    }
}

