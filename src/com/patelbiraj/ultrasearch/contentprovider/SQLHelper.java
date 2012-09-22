package com.patelbiraj.ultrasearch.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.patelbiraj.ultrasearch.utils.Constants.ContentProviderConstants.*;
import static com.patelbiraj.ultrasearch.utils.Constants.ContentProviderConstants.FormatTableConstants.*;

/**
 * The Class SQLHelper used to store content providers data into SQLite
 * database, this class extends SQL helper which gives interface to create and
 * update database definition.
 */
public class SQLHelper extends SQLiteOpenHelper {

	private static String TAG = "SQLHelper";

	@Override
	public synchronized void close() {
		super.close();
	}

	/**
	 * Instantiates a new content provider SQL helper.
	 * 
	 * @param context
	 *            the context
	 */
	public SQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * on create of content provider SQL helper. this is used to create a table
	 * which holds users preferences.
	 * 
	 * @param db
	 *            the db
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		// Media File Format preference table, this will create entry for all
		// the media table and their checked/unchecked values.
		db.execSQL("CREATE TABLE " + MEDIA_FORMAT_PREF_TABLE_NAME + " ("
				+ INDEX + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_FORMAT_NAME + " TEXT," + COLUMN_FORMAT_SELECTION
				+ " INTEGER" + ");");

		// User selection File Format preference table, this will create entry
		// for all the user selected formats table and their checked/unchecked
		// values.
		db.execSQL("CREATE TABLE " + USER_PREF_TABLE_NAME + " (" + INDEX
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FORMAT_NAME
				+ " TEXT," + COLUMN_FORMAT_SELECTION + " INTEGER" + ");");

	}

	/**
	 * on Upgrade of content provider SQL helper.
	 * 
	 * @param arg0
	 *            the arg0
	 * @param arg1
	 *            the arg1
	 * @param arg2
	 *            the arg2
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase,
	 *      int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
