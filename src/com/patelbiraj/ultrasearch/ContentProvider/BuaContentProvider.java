package com.patelbiraj.ultrasearch.ContentProvider;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.patelbiraj.ultrasearch.Utils.LogUtils;

import static com.patelbiraj.ultrasearch.Utils.Constants.ContentProviderConstants.*;
import static com.patelbiraj.ultrasearch.Utils.Constants.ContentProviderConstants.BackupTableConstants.*;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class BuaContentProvider is as class to provide user-preferences to
 * outside applications.This content provider is implemented as this is most
 * efficient way to store and retrieve users data in persistent storage like
 * SQLite database
 * 
 * Preferences like which data is to be synced to BUA+ server. Auto Backup
 * timing preference. Network usage preference.
 * 
 */
public class BuaContentProvider extends ContentProvider {

	/** The Sqlite helper to save preference data . */
	private ContentProviderSqlHelper dbHelper = null;

	/** The Bua projection map. */
	private static HashMap<String, String> BuaPreferencesProjectionMap = null;

	/** The Bua projection map. */
	private static HashMap<String, String> BackupStatesProjectionMap = null;

	/** The Constant sUriMatcher. */
	private static UriMatcher sUriMatcher = null;

	/** The Constant BUA_PREFERENCES_CONTENT_URI. */
	private static Uri BUA_PREFERENCES_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + BUA_TABLE_NAME);

	private static Uri BACKUP_STATE_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + BACKUP_STATE_TABLE_NAME);

	private static Uri FILE_OPERATION_INFO_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + FILE_OPERATION_INFO_TABLE_NAME);

	
	/** The Bua projection map. */
	private static HashMap<String, String> sFileOperationInfoProjectionMap = null;
	
	/** The Constant TAG. */
	private final String TAG = "BuaContentProvider";

	
	
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, BUA_TABLE_NAME, BUA_SETTINGS);
		BuaPreferencesProjectionMap = new HashMap<String, String>();
		BuaPreferencesProjectionMap.put(INDEX, INDEX);
		BuaPreferencesProjectionMap.put(COLUMN_CONTACTS, COLUMN_CONTACTS);
		BuaPreferencesProjectionMap.put(COLUMN_BOTH_NETWORK_DONT_REMIND,
				COLUMN_BOTH_NETWORK_DONT_REMIND);
		BuaPreferencesProjectionMap.put(COLUMN_WIFI_DONT_REMIND,
				COLUMN_WIFI_DONT_REMIND);
		BuaPreferencesProjectionMap.put(COLUMN_TEXT_MESSAGES,
				COLUMN_TEXT_MESSAGES);
		BuaPreferencesProjectionMap.put(COLUMN_MEDIA_MESSAGES,
				COLUMN_MEDIA_MESSAGES);
		BuaPreferencesProjectionMap.put(COLUMN_LASTBACKUP_DATE,
				COLUMN_LASTBACKUP_DATE);
		BuaPreferencesProjectionMap.put(COLUMN_LASTBACKUP_STATE,
				COLUMN_LASTBACKUP_STATE);
		BuaPreferencesProjectionMap.put(COLUMN_CALL_HISTORY,
				COLUMN_CALL_HISTORY);
		BuaPreferencesProjectionMap.put(COLUMN_BOTH_NETWORK,
				COLUMN_BOTH_NETWORK);
		BuaPreferencesProjectionMap.put(COLUMN_SUBSCRIPTION_STATE,
				COLUMN_SUBSCRIPTION_STATE);
		// for requirement 2.2.2.2.4 pg 21
		BuaPreferencesProjectionMap.put(COLUMN_RESTORE_PENDING,
				COLUMN_RESTORE_PENDING);
		// for requirement 2.2.2.2.2 pg 18
		BuaPreferencesProjectionMap.put(COLUMN_FAILED_ATTEMPTS,
				COLUMN_FAILED_ATTEMPTS);
		// for requirement 2.2.2.2.2 pg 18
		BuaPreferencesProjectionMap.put(COLUMN_REMIND_WAITING_WIFI,
				COLUMN_REMIND_WAITING_WIFI);
		BuaPreferencesProjectionMap.put(COLUMN_REMIND_THRESHOLD_BACKUP,
				COLUMN_REMIND_THRESHOLD_BACKUP);
		BuaPreferencesProjectionMap.put(COLUMN_MUSIC, COLUMN_MUSIC);
		BuaPreferencesProjectionMap.put(COLUMN_PICTURES, COLUMN_PICTURES);
		BuaPreferencesProjectionMap.put(COLUMN_VIDEOS, COLUMN_VIDEOS);
		BuaPreferencesProjectionMap.put(COLUMN_DOCUMENTS, COLUMN_DOCUMENTS);
		BuaPreferencesProjectionMap.put(COLUMN_UPDATE_SCHEDULE,
				COLUMN_UPDATE_SCHEDULE);
		BuaPreferencesProjectionMap.put(COLUMN_USE_NETWORK, COLUMN_USE_NETWORK);
		BuaPreferencesProjectionMap.put(COLUMN_EMAIL_ADDRESS,
				COLUMN_EMAIL_ADDRESS);
		BuaPreferencesProjectionMap.put(COLUMN_NOTIFICATION_PREF,
				COLUMN_NOTIFICATION_PREF);
		BuaPreferencesProjectionMap.put(COLUMN_DEFAULT_STORAGE_LOCATION,
				COLUMN_DEFAULT_STORAGE_LOCATION);
		BuaPreferencesProjectionMap.put(COLUMN_NEXT_BACKUP_TIME,
				COLUMN_NEXT_BACKUP_TIME);

		sUriMatcher.addURI(AUTHORITY, BACKUP_STATE_TABLE_NAME, BACKUP_STATE);
		BackupStatesProjectionMap = new HashMap<String, String>();
		BackupStatesProjectionMap.put(COLUMN_FILE_PATH, COLUMN_FILE_PATH);
		BackupStatesProjectionMap.put(COLUMN_FILE_BACKUP_STATE,
				COLUMN_FILE_BACKUP_STATE);
		
		/*Vaibhav 31July*/
		sUriMatcher.addURI(AUTHORITY, FILE_OPERATION_INFO_TABLE_NAME, FILE_OPERATION_CODE);
		sFileOperationInfoProjectionMap = new HashMap<String, String>();
		sFileOperationInfoProjectionMap.put(COLUMN_FILE_NAME, COLUMN_FILE_NAME);
		sFileOperationInfoProjectionMap.put(COLUMN_LOCAL_PATH,
				COLUMN_LOCAL_PATH);
		sFileOperationInfoProjectionMap.put(COLUMN_REMOTE_PATH,
				COLUMN_REMOTE_PATH);
		sFileOperationInfoProjectionMap.put(COLUMN_OPERATION_TYPE,
				COLUMN_OPERATION_TYPE);
	}

	/**
	 * Delete method implementation of content provider.
	 * 
	 * @param uri
	 *            the uri
	 * @param where
	 *            the where
	 * @param whereArgs
	 *            the where args
	 * @return the int
	 * @see android.content.ContentProvider#delete(android.net.Uri,
	 *      java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		if (null == uri || null == sUriMatcher) {
			LogUtils.e(TAG, "uri is null? " + (null == uri)
					+ "uri matcher is null?" + (null == sUriMatcher)
					+ " selection is null? " + (null == where)
					+ " SelectionArgs is null? " + (null == whereArgs));
			return 0;
		}
		SQLiteDatabase db = null;
		if (null == dbHelper) {
			dbHelper = new ContentProviderSqlHelper(getContext());
		}
		db = dbHelper.getWritableDatabase();
		int count = 0;
		if (null != db && db.isOpen()) {
			try {
				switch (sUriMatcher.match(uri)) {
				case BUA_SETTINGS:
					db.beginTransaction();
					count = db.delete(BUA_TABLE_NAME, where, whereArgs);
					db.setTransactionSuccessful();
					db.endTransaction();
					break;
				case BACKUP_STATE:
					LogUtils.i(TAG, "deleting from db");
					db.beginTransaction();
					count = db
							.delete(BACKUP_STATE_TABLE_NAME, where, whereArgs);
					db.setTransactionSuccessful();
					db.endTransaction();
					LogUtils.i(TAG,"Deletd rows "+count);
					break;
				case FILE_OPERATION_CODE:
					LogUtils.i(TAG, "deleting from db");
					db.beginTransaction();
					count = db
							.delete(FILE_OPERATION_INFO_TABLE_NAME, where, whereArgs);
					db.setTransactionSuccessful();
					db.endTransaction();
					LogUtils.i(TAG,"Deletd rows "+count);
					break;
				default:
					throw new IllegalArgumentException("Unknown URI " + uri);
				}
				getContext().getContentResolver().notifyChange(uri, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			LogUtils.e("BUAContentProvider", "db is null ?" + (null == db));
			if (null != db) {
				LogUtils.e("BUAContentProvider", "db is open ?" + (db.isOpen()));
			}
		}

		return count;
	}

	/**
	 * Get the type method implementation of content provider..
	 * 
	 * @param uri
	 *            the uri
	 * @return the type
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		if (null == uri || null == sUriMatcher) {
			LogUtils.e(TAG, "uri is null? " + (null == uri)
					+ "uri matcher is null?" + (null == sUriMatcher)
					+ " in getType method");
			return null;
		}
		switch (sUriMatcher.match(uri)) {
		case BUA_SETTINGS:
		case BACKUP_STATE:
		/*Vaibhav 30July*/
		case FILE_OPERATION_CODE:
			return CONTENT_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	/**
	 * Insert method implementation of content provider..
	 * 
	 * @param uri
	 *            the uri
	 * @param initialValues
	 *            the initial values
	 * @return the uri
	 * @see android.content.ContentProvider#insert(android.net.Uri,
	 *      android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (null == uri || null == sUriMatcher) {
			LogUtils.e(TAG, "uri is null? " + (null == uri)
					+ "uri matcher is null?" + (null == sUriMatcher)
					+ " in insert method");
			return null;
		}
		ContentValues values;
		if (null != initialValues) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		SQLiteDatabase db = null;
		if (null == dbHelper) {
			dbHelper = new ContentProviderSqlHelper(getContext());
		}
		db = dbHelper.getWritableDatabase();
		long rowId = 0;
		if (null != db && db.isOpen()) {
			try {
				switch (sUriMatcher.match(uri)) {
				case BUA_SETTINGS:
					if (values.containsKey(COLUMN_SUBSCRIPTION_STATE)) {
						if (values.getAsInteger(COLUMN_SUBSCRIPTION_STATE) == SUBSCRIPTION_STATE_SUBSCRIBED) {
							LogUtils.i(TAG,"insert + SUBSCRIPTION_STATE_SUBSCRIBED");
							Utils.resetAlarm(getContext());
						} else if (values
								.getAsInteger(COLUMN_SUBSCRIPTION_STATE) == SUBSCRIPTION_STATE_UNSUBSCRIBED) {
							LogUtils.i(TAG,"insert + SUBSCRIPTION_STATE_UNSUBSCRIBED");
							cancelAlarm(getContext());
						}
					}
					db.beginTransaction();
					LogUtils.i(TAG, "inserting database");
					rowId = db.insert(BUA_TABLE_NAME, null, values);
					db.setTransactionSuccessful();
					db.endTransaction();
					if (rowId > 0) {
						Uri noteUri = ContentUris.withAppendedId(
								BUA_PREFERENCES_CONTENT_URI, rowId);
						getContext().getContentResolver().notifyChange(noteUri, null);
						return noteUri;
					}
					break;
				case BACKUP_STATE :
					db.beginTransaction();
					LogUtils.i(TAG, "inserting database");
					LogUtils.i(TAG, "values  "+ values.valueSet());
					LogUtils.i(TAG, "values Name "+ values.getAsString(COLUMN_FILE_PATH));
					LogUtils.i(TAG, "values value "+ values.getAsString(COLUMN_FILE_BACKUP_STATE));
					rowId = db.insert(BACKUP_STATE_TABLE_NAME, null, values);
					db.setTransactionSuccessful();
					db.endTransaction();
					LogUtils.i(TAG, "inserted rows: " + rowId);
					if (rowId > 0) {
						Uri noteUri = ContentUris.withAppendedId(
								BACKUP_STATE_CONTENT_URI, rowId);
						getContext().getContentResolver().notifyChange(noteUri, null);
						return noteUri;
					}
					break;
					/*Vaibhav 30July*/
				case FILE_OPERATION_CODE :
					db.beginTransaction();
					LogUtils.i(TAG, "inserting database");
					LogUtils.i(TAG, "values  "+ values.valueSet());
					LogUtils.i(TAG, "values Name "+ values.getAsString(COLUMN_FILE_NAME));
					LogUtils.i(TAG, "values local path "+ values.getAsString(COLUMN_LOCAL_PATH));
					LogUtils.i(TAG, "values remote path "+ values.getAsString(COLUMN_REMOTE_PATH));
					LogUtils.i(TAG, "values operation type "+ values.getAsString(COLUMN_OPERATION_TYPE));
					rowId = db.insert(FILE_OPERATION_INFO_TABLE_NAME, null, values);
					db.setTransactionSuccessful();
					db.endTransaction();
					LogUtils.i(TAG, "inserted rows: " + rowId);
					if (rowId > 0) {
						Uri noteUri = ContentUris.withAppendedId(
								FILE_OPERATION_INFO_URI, rowId);
						getContext().getContentResolver().notifyChange(noteUri, null);
						return noteUri;
					}
					break;
				default:
					throw new IllegalArgumentException("Unknown URI " + uri);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	/**
	 * On create method implementation of content provider..
	 * 
	 * @return true, if successful
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		dbHelper = new ContentProviderSqlHelper(getContext());
		return true;
	}

	/**
	 * Query method implementation of content provider..
	 * 
	 * @param uri
	 *            the uri
	 * @param projection
	 *            the projection
	 * @param selection
	 *            the selection
	 * @param selectionArgs
	 *            the selection args
	 * @param sortOrder
	 *            the sort order
	 * @return the cursor
	 * @see android.content.ContentProvider#query(android.net.Uri,
	 *      java.lang.String[], java.lang.String, java.lang.String[],
	 *      java.lang.String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		if (null == uri || null == sUriMatcher) {
			LogUtils.e(TAG, "uri is null? " + (null == uri)
					+ " uri matcher is null? " + (null == sUriMatcher)
					+ " in query method");
			return null;
		}
		SQLiteQueryBuilder mSQLiteQueryBuilderObj = new SQLiteQueryBuilder();
		switch (sUriMatcher.match(uri)) {
		case BUA_SETTINGS:
			mSQLiteQueryBuilderObj.setTables(BUA_TABLE_NAME);
			mSQLiteQueryBuilderObj
					.setProjectionMap(BuaPreferencesProjectionMap);
			break;
		case BACKUP_STATE:
			mSQLiteQueryBuilderObj.setTables(BACKUP_STATE_TABLE_NAME);
			mSQLiteQueryBuilderObj
					.setProjectionMap(BackupStatesProjectionMap);
			break;
			/*Vaibhav 30July*/
		case FILE_OPERATION_CODE:
			mSQLiteQueryBuilderObj.setTables(FILE_OPERATION_INFO_TABLE_NAME);
			mSQLiteQueryBuilderObj
					.setProjectionMap(sFileOperationInfoProjectionMap);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		SQLiteDatabase mSQLiteDataBaseObj = null;
		if (null == dbHelper) {
			dbHelper = new ContentProviderSqlHelper(getContext());
		}
		mSQLiteDataBaseObj = dbHelper.getReadableDatabase();
		if (null != mSQLiteDataBaseObj && null != mSQLiteQueryBuilderObj
				&& mSQLiteDataBaseObj.isOpen()) {
			Cursor mCursorObj = null;
			try {
				mSQLiteDataBaseObj.beginTransaction();
				mCursorObj = mSQLiteQueryBuilderObj.query(mSQLiteDataBaseObj,
						projection, selection, selectionArgs, null, null,
						sortOrder);
				mSQLiteDataBaseObj.setTransactionSuccessful();
				mSQLiteDataBaseObj.endTransaction();
				mCursorObj.setNotificationUri(
						getContext().getContentResolver(), uri);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// here we can not close the cursor because it will be used by
				// client application to retrieve data from .
			}
			if(mCursorObj == null){
				LogUtils.i("BUAContentProvider", "cursor is null true");
			}
			return mCursorObj;
		} else {
			LogUtils.e(TAG,
					"mSQLiteDataBaseObj is null? "
							+ (null == mSQLiteDataBaseObj)
							+ " mSQLiteQueryBuilderObj is null? "
							+ (null == mSQLiteQueryBuilderObj)
							+ " mSQLiteDataBaseObj is open? "
							+ (mSQLiteDataBaseObj.isOpen()));
			LogUtils.i(TAG, "returning null cursor");
			return null;
		}
	}

	/**
	 * Update method implementation of content provider..
	 * 
	 * @param uri
	 *            the uri
	 * @param values
	 *            the values
	 * @param where
	 *            the where
	 * @param whereArgs
	 *            the where args
	 * @return the int
	 * @see android.content.ContentProvider#update(android.net.Uri,
	 *      android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		if (null == uri || null == sUriMatcher) {
			LogUtils.e(TAG, "uri is null? " + (null == uri)
					+ " uri matcher is null? " + (null == sUriMatcher)
					+ " in update method ");
			return 0;
		}
		SQLiteDatabase db = null;
		if (null == dbHelper) {
			dbHelper = new ContentProviderSqlHelper(getContext());
		}
		db = dbHelper.getWritableDatabase();
		int count = 0;
		if (null != db && db.isOpen()) {
			try {
				switch (sUriMatcher.match(uri)) {
				case BUA_SETTINGS:
					if (values.containsKey(COLUMN_SUBSCRIPTION_STATE)) {
						if (values.getAsInteger(COLUMN_SUBSCRIPTION_STATE) == SUBSCRIPTION_STATE_SUBSCRIBED) {
							LogUtils.i(TAG,"update + SUBSCRIPTION_STATE_SUBSCRIBED");
							Utils.resetAlarm(getContext());
						} else if (values
								.getAsInteger(COLUMN_SUBSCRIPTION_STATE) == SUBSCRIPTION_STATE_UNSUBSCRIBED) {
							LogUtils.i(TAG,"update + SUBSCRIPTION_STATE_UNSUBSCRIBED");
							cancelAlarm(getContext());
						}
					}
					db.beginTransaction();
					LogUtils.i(TAG, "updating database");
					count = db.update(BUA_TABLE_NAME, values, where, whereArgs);
					db.setTransactionSuccessful();
					db.endTransaction();
					break;
				case BACKUP_STATE:
					db.beginTransaction();
					LogUtils.i(TAG, "updating database");
					count = db.update(BACKUP_STATE_TABLE_NAME, values, where, whereArgs);
					db.setTransactionSuccessful();
					db.endTransaction();
					break;
				case FILE_OPERATION_CODE :
					db.beginTransaction();
					LogUtils.i(TAG, "updating database");
					count = db.update(FILE_OPERATION_INFO_TABLE_NAME, values, where, whereArgs);
					db.setTransactionSuccessful();
					db.endTransaction();
					break;
				default:
					throw new IllegalArgumentException("Unknown URI " + uri);
				}
				getContext().getContentResolver().notifyChange(uri, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			LogUtils.e("BUAContentProvider", "db is null ?" + (null == db));
			if (null != db) {
				LogUtils.e("BUAContentProvider", "db is open ?" + (db.isOpen()));
			}
		}
		return count;
	}

	/**
	 * returns the content URI.
	 * 
	 * @return the content URI
	 */
	public static Uri getBuaPrefCONTENT_URI() {
		return BUA_PREFERENCES_CONTENT_URI;
	}
	
	
	/**
	 * Gets the backup state content URI
	 *
	 * @return the backup state conten t_ uri
	 */
	public static Uri getBackupStateCONTENT_URI(){
		return BACKUP_STATE_CONTENT_URI;
	}


	/**
	 * returns the content URI.
	 * 
	 * @return the content URI
	 */
	public static Uri getFileOperationCONTENT_URI() {
		return FILE_OPERATION_INFO_URI;
	}
	
	
	
	/**
	 * Cancels alarm.
	 * 
	 * @param context
	 *            the context
	 */
	private void cancelAlarm(Context context) {
		try {
			LogUtils.e(TAG, "removing alarm");
			if (context == null) {
				context = getContext();
			}
			String alarm = Context.ALARM_SERVICE;
			Calendar calendar = Calendar.getInstance();
			AlarmManager alarmManager = (AlarmManager) context
					.getSystemService(alarm);

			Intent intent = new Intent();
			intent.setAction(BACKUP_INTENT_ACTION);
			PendingIntent sender = PendingIntent.getService(
					context.getApplicationContext(), 0, intent, 0);
			alarmManager.cancel(sender);
		} catch (Exception e) {
			LogUtils.e(TAG, "Exception in cancelAlarm !!!");
		}
	}

}
