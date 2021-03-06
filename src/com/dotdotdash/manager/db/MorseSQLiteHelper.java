package com.dotdotdash.manager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MorseSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_TRANSLATION = "translations";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SOURCE = "source";
	public static final String COLUMN_TRANS = "translation";

	private static final String DATABASE_NAME = "translations.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_TRANSLATION + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_SOURCE
			+ " text not null, " + COLUMN_TRANS + " text not null);";

	public MorseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public MorseSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(MorseSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSLATION);
		onCreate(db);
	}

}
