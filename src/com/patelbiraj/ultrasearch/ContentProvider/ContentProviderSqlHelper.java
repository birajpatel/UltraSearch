package com.patelbiraj.ultrasearch.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * The Class ContentProviderSqlHelper used to store content providers data into
 * SQLite database.
 */
public class ContentProviderSqlHelper extends SQLiteOpenHelper {

	private static String TAG = "ContentProviderSqlHelper";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#close()
	 */
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
	public ContentProviderSqlHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		LogUtils.e(TAG,
				"created DB Helper ---------------------------------->>>>>>>>>>>>>>>>>>>");
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
		db.execSQL("CREATE TABLE " + BUA_TABLE_NAME + " (" + INDEX
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CONTACTS
				+ " INTEGER ," + COLUMN_WIFI_DONT_REMIND + " INTEGER ,"
				+ COLUMN_TEXT_MESSAGES + " INTEGER ," + COLUMN_MEDIA_MESSAGES
				+ " INTEGER ," + COLUMN_CALL_HISTORY + " INTEGER ,"
				+ COLUMN_BOTH_NETWORK + " INTEGER ,"
				+ COLUMN_SUBSCRIPTION_STATE + " INTEGER ,"
				+ COLUMN_BOTH_NETWORK_DONT_REMIND + " INTEGER ," + COLUMN_MUSIC
				+ " INTEGER," + COLUMN_PICTURES + " INTEGER," + COLUMN_VIDEOS
				+ " INTEGER," + COLUMN_DOCUMENTS + " INTEGER,"
				+ COLUMN_UPDATE_SCHEDULE + " INTEGER," + COLUMN_USE_NETWORK
				+ " INTEGER," + COLUMN_EMAIL_ADDRESS + " TEXT,"
				+ COLUMN_LASTBACKUP_DATE + " TEXT," + COLUMN_LASTBACKUP_STATE
				+ " INTEGER," + COLUMN_NOTIFICATION_PREF + " INTEGER,"
				+ COLUMN_NEXT_BACKUP_TIME + " TEXT,"
				// for requirement 2.2.2.2.4 pg 21
				+ COLUMN_RESTORE_PENDING + " INTEGER,"
				// for requirement 2.2.2.2.2 pg 18
				+ COLUMN_REMIND_WAITING_WIFI + " INTEGER,"
				// for requirement 2.2.2.2.2 pg 18
				+ COLUMN_FAILED_ATTEMPTS + " INTEGER,"
				// After Restart alarm reset issue.
				+ COLUMN_REMIND_THRESHOLD_BACKUP + " INTEGER,"
				+ COLUMN_DEFAULT_STORAGE_LOCATION + " TEXT" + ");");

		// for requirement 2.2.2.2.4 additional requirement point 6 pg 23
		db.execSQL("CREATE TABLE " + BACKUP_STATE_TABLE_NAME + " (" + INDEX
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_FILE_PATH + " TEXT,"
				+ COLUMN_FILE_BACKUP_STATE + " INTEGER" + ");");
		
		/*Vaibhav 30July*/
		db.execSQL("CREATE TABLE " + FILE_OPERATION_INFO_TABLE_NAME + " (" + INDEX
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_FILE_NAME + " TEXT,"
				+ COLUMN_LOCAL_PATH + " TEXT,"
				+ COLUMN_REMOTE_PATH + " TEXT,"
				+ COLUMN_OPERATION_TYPE + " INTEGER" + ");");
		
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
