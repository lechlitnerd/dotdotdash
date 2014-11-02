package com.dotdotdash.manager.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dotdotdash.model.Translation;

public class TranslationDataSource {

	// Database fields
	private SQLiteDatabase mDatabase;
	private MorseSQLiteHelper mDbHelper;
	private String[] mAllColumns = { MorseSQLiteHelper.COLUMN_ID,
			MorseSQLiteHelper.COLUMN_SOURCE, MorseSQLiteHelper.COLUMN_TRANS };

	public TranslationDataSource(Context context) {
		mDbHelper = new MorseSQLiteHelper(context);
	}

	public void open() throws SQLException {
		mDatabase = mDbHelper.getWritableDatabase();
	}

	public void close() {
		mDbHelper.close();
	}

	public Translation createTranslation(String source, String translation) {
		ContentValues values = new ContentValues();
		values.put(MorseSQLiteHelper.COLUMN_SOURCE, source);
		values.put(MorseSQLiteHelper.COLUMN_TRANS, translation);
		long insertId = mDatabase.insert(MorseSQLiteHelper.TABLE_TRANSLATION,
				null, values);
		Cursor cursor = mDatabase.query(MorseSQLiteHelper.TABLE_TRANSLATION,
				mAllColumns, MorseSQLiteHelper.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Translation trans = cursorToComment(cursor);
		cursor.close();

		return trans;
	}

	public void deleteComment(Translation translation) {
		long id = translation.getId();
		Log.d(TranslationDataSource.class.getName(),
				"Translation deleted with id: " + id);
		mDatabase.delete(MorseSQLiteHelper.TABLE_TRANSLATION,
				MorseSQLiteHelper.COLUMN_ID + " = " + id, null);
	}

	public List<Translation> getAllTranslations() {
		List<Translation> translations = new ArrayList<Translation>();

		Cursor cursor = mDatabase.query(MorseSQLiteHelper.TABLE_TRANSLATION,
				mAllColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Translation trans = cursorToComment(cursor);
			translations.add(trans);
			cursor.moveToNext();
		}

		// make sure to close the cursor
		cursor.close();
		return translations;
	}

	private Translation cursorToComment(Cursor cursor) {
		return new Translation(cursor.getLong(0), cursor.getString(1),
				cursor.getString(2));
	}
}
