package com.cornu.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {

	public final static int VERSION=1;
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);	
	}
	public DBHelper(Context context, String name,int version) {
		super(context, name, null, version);	
	}
	public DBHelper(Context context, String name) {
		super(context, name, null, VERSION);	
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("create db");
		db.execSQL("create table items (id INTEGER PRIMARY KEY,type nvarchar(10),subject nvarchar(10)," +
				"book nvarchar(40),page int,ordernum int,description nvarchar(50)," +
				"state int)");
		db.execSQL("create table types(id INTEGER PRIMARY KEY,typename nvarchar(10) )");
		db.execSQL("create table subjects(id INTEGER PRIMARY KEY,subjectname nvarchar(10) )");
		db.execSQL("create table books(id INTEGER PRIMARY KEY,bookname nvarchar(40),subjectname nvarchar(10) )");
		
		db.execSQL("insert into types(typename) values ('疑')");
		db.execSQL("insert into types(typename) values ('记')");
		
		db.execSQL("insert into subjects(subjectname) values ('数学')");
		db.execSQL("insert into subjects(subjectname) values ('计算机')");
		db.execSQL("insert into subjects(subjectname) values ('英语')");
		db.execSQL("insert into subjects(subjectname) values ('政治')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
}
