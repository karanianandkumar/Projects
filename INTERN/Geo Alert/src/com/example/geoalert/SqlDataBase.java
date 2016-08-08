package com.example.geoalert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressLint("DefaultLocale")
public class SqlDataBase extends SQLiteOpenHelper {
	public SqlDataBase(Context context) {
		super(context, "ga.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE IF NOT EXISTS Data(_id int(10),num varchar(10),name varchar(20),status varchar(20),time varchar(5));";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE Data IF EXISTS";
		db.execSQL(sql);
		onCreate(db);
	}

	public void insertData(String num, String name, String time, String status) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "INSERT INTO Data VALUES("+(count()+1)+",'"+num+"','"+name+"','"+time+"','"+status+"');";
		//Log.d(num,"inserted");
		db.execSQL(sql);
	}

	public Cursor retriveData() {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM Data";
		return db.rawQuery(sql, null);
		// return c;
	}
	public int count()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM Data";
		Cursor c = db.rawQuery(sql,null);
		int val = c.getCount();
		c.close();
		if(val>0)
			return val;
		else
			return 0;
	}
	public void delete()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "DELETE FROM Data";
		db.execSQL(sql);
	}
}
