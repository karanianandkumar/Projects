package com.example.callblock;

import java.util.Calendar;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

	public DataBase(Context context) {
		super(context, "CB.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE IF NOT EXISTS Numbers(Number int(10) UNIQUE,_id INTEGER PRIMARY KEY);";
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int prevv, int upgradev) {
		String sql = "DROP TABLE IF EXISTS Numbers;";
		db.execSQL(sql);
		onCreate(db);
	}

	public Cursor numscursor() {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM Numbers";
		Cursor c = db.rawQuery(sql, null);
		return c;
	}

	public boolean exists(String phnnum) {
		Cursor c = numscursor();
		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				if (phnnum.equals("" + c.getLong(c.getColumnIndex("Number")))) {
					return true;
				}
			} while (c.moveToNext());
		}
		return false;
	}

	public void insert(long phnnum) {
		SQLiteDatabase db = this.getReadableDatabase();
		SQLiteDatabase db1 = this.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Numbers", null);
		int n = 0;
		if (c.getCount() > 0) {
			c.moveToLast();
			n = c.getInt(c.getColumnIndex("_id"));
		}
		n++;
		String sql = String.format("INSERT INTO Numbers VALUES(%d,%d);",
				phnnum, n);
		db1.execSQL(sql);
	}

	public void delete(long phnnum) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = String.format("DELETE FROM Numbers where Number=%d;",
				phnnum);
		db.execSQL(sql);
	}

	public void logTable(String phnnum) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = String
				.format("CREATE TABLE IF NOT EXISTS '%s'(Time VARCHAR,_id INTEGER PRIMARY KEY);",
						phnnum);
		db.execSQL(sql);
	}

	public void insertLog(String phnnum) {
		SQLiteDatabase db = this.getWritableDatabase();
		SQLiteDatabase db1 = this.getReadableDatabase();
		int n = 0;
		Cursor cursor = db1.rawQuery(
				String.format("SELECT * FROM '%s'", phnnum), null);
		if (cursor.getCount() > 0) {
			cursor.moveToLast();
			n = cursor.getInt(cursor.getColumnIndex("_id"));
		}
		n++;
		Calendar cal = Calendar.getInstance();
		String time = String.format("Missed Call AT -> %d:%d:%d",
				cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND));
		String sql = String.format("INSERT INTO '%s' VALUES('%s',%d);", phnnum,
				time, n);
		db.execSQL(sql);

	}

	public Cursor showLog(String phnnum) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = String.format("SELECT * FROM '%s'", phnnum);
		Cursor c = db.rawQuery(sql, null);
		return c;
	}

	public void delLog(String phnnum) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = String.format("DROP TABLE IF EXISTS '%s';", phnnum);
		db.execSQL(sql);
	}

	public void droptb() {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "DROP TABLE Numbers;";
		db.execSQL(sql);
	}
}
