package com.patelbiraj.ultrasearch.contentprovider;

import java.util.*;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.patelbiraj.ultrasearch.utils.LogUtils;
import static com.patelbiraj.ultrasearch.utils.Constants.ContentProviderConstants.*;
import static com.patelbiraj.ultrasearch.utils.Constants.ContentProviderConstants.FormatTableConstants.*;
import static com.patelbiraj.ultrasearch.utils.Constants.ContentProviderConstants.TableUris.*;

/**
 * The Class UltraSearchContentProvider is as class to provide user-preferences
 * to outside applications.This content provider is implemented as this is most
 * efficient way to store and retrieve users data in persistent storage like
 * SQLite database
 * 
 * 
 */
public class UltraSearchContentProvider extends ContentProvider {

	/** The Sqlite helper to save preference data . */
	private SQLHelper dbHelper = null;

	private static HashMap<String, String> MediaFormatsProjectionMap = null;

	/** The Constant mUriMatcher. */
	private static UriMatcher mUriMatcher = null;

	private final String TAG = "UltraSearchContentProvider";

	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		// Media formats table.
		// User selected format table.
		mUriMatcher.addURI(AUTHORITY, MEDIA_FORMAT_PREF_TABLE_NAME,
				MEDIA_FORMAT_TABLE_CODE);
		mUriMatcher.addURI(AUTHORITY, USER_PREF_TABLE_NAME,
				USER_PREF_TABLE_CODE);
		MediaFormatsProjectionMap = new HashMap<String, String>();
		MediaFormatsProjectionMap.put(COLUMN_FORMAT_NAME, COLUMN_FORMAT_NAME);
		MediaFormatsProjectionMap.put(COLUMN_FORMAT_SELECTION,
				COLUMN_FORMAT_SELECTION);
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
		if (null == uri || null == mUriMatcher) {
			LogUtils.e(TAG, "uri is null? " + (null == uri)
					+ "uri matcher is null?" + (null == mUriMatcher)
					+ " selection is null? " + (null == where)
					+ " SelectionArgs is null? " + (null == whereArgs));
			return 0;
		}
		SQLiteDatabase db = null;
		if (null == dbHelper) {
			dbHelper = new SQLHelper(getContext());
		}
		db = dbHelper.getWritableDatabase();
		int count = 0;
		if (null != db && db.isOpen()) {
			try {
				switch (mUriMatcher.match(uri)) {

				case MEDIA_FORMAT_TABLE_CODE:
					LogUtils.i(TAG, "deleting + MEDIA_FORMAT_TABLE_CODE");
					db.beginTransaction();
					count = db.delete(MEDIA_FORMAT_PREF_TABLE_NAME, where,
							whereArgs);
					db.setTransactionSuccessful();
					db.endTransaction();
					LogUtils.i(TAG, "Deletd rows " + count);
					break;
				case USER_PREF_TABLE_CODE:
					LogUtils.i(TAG, "deleting + USER_PREF_TABLE_CODE");
					db.beginTransaction();
					count = db.delete(USER_PREF_TABLE_NAME, where, whereArgs);
					db.setTransactionSuccessful();
					db.endTransaction();
					LogUtils.i(TAG, "Deletd rows " + count);
					break;
				default:
					throw new IllegalArgumentException("Unknown URI " + uri);
				}
				getContext().getContentResolver().notifyChange(uri, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			if (null == db) {
				LogUtils.e(TAG, "db is null");
			} else {
				LogUtils.e(TAG, "db is not open");
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
		if (null == uri || null == mUriMatcher) {
			LogUtils.e(TAG, "uri is null? " + (null == uri)
					+ "uri matcher is null?" + (null == mUriMatcher)
					+ " in getType method");
			return null;
		}
		switch (mUriMatcher.match(uri)) {
		case MEDIA_FORMAT_TABLE_CODE:
		case USER_PREF_TABLE_CODE:
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
		if (null == uri || null == mUriMatcher) {
			LogUtils.e(TAG, "uri is null? " + (null == uri)
					+ "uri matcher is null?" + (null == mUriMatcher)
					+ " in insert method");
			return null;
		}
		ContentValues values;
		if (null != initialValues) {
			values = new ContentValues(initialValues);
		} else {
			LogUtils.e(TAG, "content values null");
			values = new ContentValues();
		}
		SQLiteDatabase db = null;
		if (null == dbHelper) {
			dbHelper = new SQLHelper(getContext());
		}
		db = dbHelper.getWritableDatabase();
		long rowId = 0;
		if (null != db && db.isOpen()) {
			try {
				LogUtils.i(TAG, "values  " + values.valueSet());
				switch (mUriMatcher.match(uri)) {
				case MEDIA_FORMAT_TABLE_CODE:
					LogUtils.i(TAG, "inserting MEDIA_FORMAT_TABLE_CODE");
					db.beginTransaction();
					rowId = db.insert(MEDIA_FORMAT_PREF_TABLE_NAME, null,
							values);
					db.setTransactionSuccessful();
					db.endTransaction();
					LogUtils.i(TAG, "inserted rows: " + rowId);
					if (rowId > 0) {
						Uri notifyUri = ContentUris.withAppendedId(
								MEDIA_FORMAT_CONTENT_URI, rowId);
						getContext().getContentResolver().notifyChange(
								notifyUri, null);
						return notifyUri;
					}
					break;
				case USER_PREF_TABLE_CODE:
					LogUtils.i(TAG, "inserting USER_PREF_TABLE_CODE");
					db.beginTransaction();
					rowId = db.insert(USER_PREF_TABLE_NAME, null, values);
					db.setTransactionSuccessful();
					db.endTransaction();
					LogUtils.i(TAG, "inserted rows: " + rowId);
					if (rowId > 0) {
						Uri notifyUri = ContentUris.withAppendedId(
								USER_PREF_CONTENT_URI, rowId);
						getContext().getContentResolver().notifyChange(
								notifyUri, null);
						return notifyUri;
					}
					break;
				default:
					throw new IllegalArgumentException("Unknown URI " + uri);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (null == db) {
				LogUtils.e(TAG, "db is null");
			} else {
				LogUtils.e(TAG, "db is not open");
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
		dbHelper = new SQLHelper(getContext());
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
		if (null == uri || null == mUriMatcher) {
			LogUtils.e(TAG, "uri is null? " + (null == uri)
					+ " uri matcher is null? " + (null == mUriMatcher)
					+ " in query method");
			return null;
		}
		SQLiteQueryBuilder mSQLiteQueryBuilderObj = new SQLiteQueryBuilder();
		switch (mUriMatcher.match(uri)) {
		case MEDIA_FORMAT_TABLE_CODE:
			mSQLiteQueryBuilderObj.setTables(MEDIA_FORMAT_PREF_TABLE_NAME);
			mSQLiteQueryBuilderObj.setProjectionMap(MediaFormatsProjectionMap);
			break;
		case USER_PREF_TABLE_CODE:
			mSQLiteQueryBuilderObj.setTables(USER_PREF_TABLE_NAME);
			mSQLiteQueryBuilderObj.setProjectionMap(MediaFormatsProjectionMap);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		SQLiteDatabase mSQLiteDataBaseObj = null;
		if (null == dbHelper) {
			dbHelper = new SQLHelper(getContext());
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
				// calling api to get data.
			}
			if (mCursorObj == null) {
				LogUtils.i(TAG, "cursor is null true");
			}
			return mCursorObj;
		} else {
			LogUtils.e(TAG, "mSQLiteDataBaseObj is null? "
					+ (null == mSQLiteDataBaseObj)
					+ " mSQLiteQueryBuilderObj is null? "
					+ (null == mSQLiteQueryBuilderObj)
					+ " mSQLiteDataBaseObj is open? ");
			if (null != mSQLiteDataBaseObj) {
				LogUtils.e(TAG, "mSQLiteDataBaseObj.isOpen "
						+ mSQLiteDataBaseObj.isOpen());
			}
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
		if (null == uri || null == mUriMatcher) {
			LogUtils.e(TAG, "uri is null? " + (null == uri)
					+ " uri matcher is null? " + (null == mUriMatcher)
					+ " in update method ");
			return 0;
		}
		SQLiteDatabase db = null;
		if (null == dbHelper) {
			dbHelper = new SQLHelper(getContext());
		}
		db = dbHelper.getWritableDatabase();
		int count = 0;
		if (null != db && db.isOpen()) {
			try {
				switch (mUriMatcher.match(uri)) {
				case MEDIA_FORMAT_TABLE_CODE:
					LogUtils.i(TAG, "updating MEDIA_FORMAT_TABLE_CODE");
					db.beginTransaction();
					count = db.update(MEDIA_FORMAT_PREF_TABLE_NAME, values,
							where, whereArgs);
					db.setTransactionSuccessful();
					db.endTransaction();
					break;
				case USER_PREF_TABLE_CODE:
					LogUtils.i(TAG, "updating USER_PREF_TABLE_CODE");
					db.beginTransaction();
					count = db.update(USER_PREF_TABLE_NAME, values, where,
							whereArgs);
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
			if (null == db) {
				LogUtils.e(TAG, "db is null");
			} else {
				LogUtils.e(TAG, "db is not open");
			}
		}
		return count;
	}

}
