package com.patelbiraj.ultrasearch.ContentProvider;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.am.bua.utils.LogUtils;
import com.am.bua.R;
import com.am.bua.data.BackupStateData;
import com.am.bua.utils.LogUtils;
import com.glue.manager.Utils;
import com.glue.manager.profile.DownloadProfile;
import com.glue.manager.profile.Profile;
import com.glue.manager.profile.UploadProfile;

import static com.am.bua.utils.Constants.ContentProviderConstants.BackupTableConstants.*;
import static com.am.bua.utils.Constants.ContentProviderConstants.FileOperationInfoTableConstants.*;
import static com.am.bua.utils.Constants.ContentProviderConstants.*;
import static com.am.bua.utils.Constants.*;


/**
 * This Class UserPreferencesController is a helper class which interacts
 * between UI and the Content Provider . when application wants to store or
 * retrieve user-preference-data then UI must call methods of this class instead
 * of directly interacting with content provider
 */
public class UserPreferencesController {

	/** The BUA content URI to query the BUA content provider. */
	// private Uri BUA_URI = BuaContentProvider.getBuaPrefCONTENT_URI();

	/** The application context. */
	private Context mContext = null;

	/** The TAG. */
	private String TAG = "UserPreferencesController";

	/**
	 * Instantiates a new content provider helper.
	 * 
	 * @param context
	 *            the context
	 */
	public UserPreferencesController(Context context) {
		if (null != context) {
			mContext = context;
		}
		checkIfRowIsPresentElseInsertFirstRow();
	}

	/**
	 * return a boolean value if item is checked or not internally we store
	 * value as integer as SQLite does not support boolean variable.
	 * 
	 * @param columnName
	 *            the column name
	 * @return true, if is contact checked
	 */
	public boolean isItemChecked(String columnName) {
		checkIfRowIsPresentElseInsertFirstRow();
		Cursor tempCursor = null;
		if (null != columnName) {
			tempCursor = returnCursorAsPerColumnName(columnName,
					BuaContentProvider.getBuaPrefCONTENT_URI(), null);
		} else {
			LogUtils.e(TAG, "Column name is null in isItemChecked");
		}
		boolean bReturnFlag = getBooleanValueFromIntFromCursor(tempCursor,
				columnName);

		if (null != tempCursor && !tempCursor.isClosed()) {
			tempCursor.close();
			tempCursor = null;
		}
		return bReturnFlag;
	}

	/**
	 * return integer preference value from database and return to caller.
	 * 
	 * @param columnName
	 *            the column name
	 * @return the update schedule
	 */
	public int getItemPreferenceValue(String columnName) {
		checkIfRowIsPresentElseInsertFirstRow();
		Cursor tempCursor = null;
		if (null != columnName) {
			tempCursor = returnCursorAsPerColumnName(columnName,
					BuaContentProvider.getBuaPrefCONTENT_URI(), null);
		} else {
			LogUtils.e(TAG, "Column name is null in getItemPreferenceValue");
		}
		int tempInt = getIntValueFromCursor(tempCursor, columnName);
		if (null != tempCursor && !tempCursor.isClosed()) {
			tempCursor.close();
			tempCursor = null;
		}
		return tempInt;
	}

	/**
	 * return string preference value from database and return to caller.
	 * 
	 * @param columnName
	 *            the column name
	 * @return the update schedule
	 */
	public String getItemTextValue(String columnName) {
		checkIfRowIsPresentElseInsertFirstRow();
		Cursor tempCursor = null;
		if (null != columnName) {
			tempCursor = returnCursorAsPerColumnName(columnName,
					BuaContentProvider.getBuaPrefCONTENT_URI(), null);
		} else {
			LogUtils.e(TAG, "Column name is null in getItemTextValue");
		}
		String tempString = getStringValueFromCursor(tempCursor, columnName);
		if (null != tempCursor && !tempCursor.isClosed()) {
			tempCursor.close();
			tempCursor = null;
		}
		return tempString;
	}

	/**
	 * Gets the string value from cursor in this method we pass cursor which is
	 * retrieved from database and we return string value stored for passed
	 * column name.
	 * 
	 * @param mCursor
	 *            the m cursor
	 * @param columnName
	 *            the column name
	 * @return the string value from cursor
	 */
	private String getStringValueFromCursor(Cursor mCursor, String columnName) {
		String temp = null;
		if (null != columnName) {
			if (columnName == COLUMN_EMAIL_ADDRESS) {
				temp = "";
			}
			try {
				if (null != mCursor && mCursor.getCount() > NO_ROWS) {
					mCursor.moveToFirst();
					temp = mCursor
							.getString(mCursor.getColumnIndex(columnName));
				} else {
					LogUtils.e(TAG, "mCursor is null ? " + (mCursor == null)
							+ "mCursor getCount "
							+ "in getStringValueFromCursor method");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != mCursor && !mCursor.isClosed()) {
					mCursor.close();
					mCursor = null;
				} else {
					LogUtils.e(TAG, "mCursor is null ? " + (mCursor == null)
							+ "mCursor isClosed " + mCursor.isClosed()
							+ "in getStringValueFromCursor method");
				}
			}
		} else {
			LogUtils.e(TAG, "Column name is null in getStringValueFromCursor");
		}
		return temp;
	}

	/**
	 * Gets the integer value from cursor in this method we pass cursor which is
	 * retrieved from database and we return integer value stored for passed
	 * column name.
	 * 
	 * @param mCursor
	 *            the m cursor
	 * @param columnName
	 *            the column name
	 * @return the int value from cursor
	 */
	private int getIntValueFromCursor(Cursor mCursor, String columnName) {
		int temp = -1;
		try {
			if (null != columnName) {
				if (columnName == COLUMN_UPDATE_SCHEDULE) {
					temp = UPDATE_SCHEDULE_DEFAULT_VALUE;
				} else if (columnName == COLUMN_USE_NETWORK) {
					temp = USE_NETWORKS_DEFAULT_VALUE;
				}
				if (null != mCursor && mCursor.getCount() > NO_ROWS) {
					mCursor.moveToFirst();
					temp = mCursor.getInt(mCursor.getColumnIndex(columnName));
				} else {
					LogUtils.e(TAG, "mCursor is null ? " + (mCursor == null)
							+ "mCursor getCount "
							+ "in getIntValueFromCursor method");
				}
			} else {
				LogUtils.e(TAG, "Column name is null in getIntValueFromCursor");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != mCursor && !mCursor.isClosed()) {
				mCursor.close();
				mCursor = null;
			}
		}
		return temp;
	}

	/**
	 * Queries the content provider and return cursor as per column name .
	 * 
	 * @param columnName
	 *            the column name
	 * @param uri
	 *            the uri
	 * @param where
	 *            the where
	 * @return the cursor
	 */
	private Cursor returnCursorAsPerColumnName(String columnName, Uri uri,
			String where) {
		String[] projection = null;
		if (null != columnName) {
			projection = new String[] { columnName };
		} else {
			LogUtils.e(TAG,
					"Column name is null in returnCursorAsPerColumnName");
		}
		if (null != projection && null != mContext) {
			return mContext.getContentResolver().query(uri, projection, where,
					null, null);
		} else {
			LogUtils.e(TAG, "projection object is null? "
					+ (null == projection) + "context is null? "
					+ (null == mContext));
			return null;
		}
	}
	


	/**
	 * Gets the boolean value as integer from cursor we store users check box
	 * preferences in SQLite database but since the SQLite database can not
	 * store boolean value we need to use integer flag to store in database. in
	 * this method we read the integer value from cursor and return boolean
	 * value as per integer value.
	 * 
	 * @param mCursor
	 *            the m cursor
	 * @param columnName
	 *            the column name
	 * @return the boolean value as int from cursor
	 */
	private boolean getBooleanValueFromIntFromCursor(Cursor mCursor,
			String columnName) {
		if (null != columnName) {
			int temp = UNCHECKED;
			try {
				if (null != mCursor && mCursor.getCount() > NO_ROWS) {
					mCursor.moveToFirst();
					temp = mCursor.getInt(mCursor.getColumnIndex(columnName));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != mCursor && !mCursor.isClosed()) {
					mCursor.close();
					mCursor = null;
				}
			}
			if (temp == CHECKED) {
				return true;
			}
		} else {
			LogUtils.e(TAG,
					"Column name is null in getBooleanValueFromIntFromCursor");
		}
		return false;
	}

	/**
	 * Sets the item checked state by given column name.
	 * 
	 * @param isChecked
	 *            the new contact checked
	 * @param columnName
	 *            the column name
	 */
	public void setItemChecked(boolean isChecked, String columnName) {
		checkIfRowIsPresentElseInsertFirstRow();
		updateDataBase(columnName, isChecked,
				BuaContentProvider.getBuaPrefCONTENT_URI());

	}

	/**
	 * Sets the item string value of given column name.
	 * 
	 * @param value
	 *            the value
	 * @param columnName
	 *            the column name
	 */
	public void setItemStringValue(String value, String columnName) {
		checkIfRowIsPresentElseInsertFirstRow();
		updateDataBase(columnName, value,
				BuaContentProvider.getBuaPrefCONTENT_URI());
	}

	/**
	 * Sets int item preference value in the database.
	 * 
	 * @param preference
	 *            the new update schedule
	 * @param columnName
	 *            the column name
	 */
	public void setItemPreferenceValue(int preference, String columnName) {
		checkIfRowIsPresentElseInsertFirstRow();
		updateDataBase(columnName, preference,
				BuaContentProvider.getBuaPrefCONTENT_URI(), null);
	}

	/**
	 * Update data base to store text value of a column.
	 * 
	 * @param columnName
	 *            the column name
	 * @param value
	 *            the value
	 * @param uri
	 *            the uri
	 */
	private void updateDataBase(String columnName, String value, Uri uri) {
		if (null != columnName && null != value && null != mContext) {
			ContentValues mContentValues = new ContentValues();
			mContentValues.put(columnName, value);
			int total = mContext.getContentResolver().update(uri,
					mContentValues, null, null);
			LogUtils.i(TAG, "total updated : " + total);
		} else {
			LogUtils.e(TAG, "columnName object is null? "
					+ (null == columnName) + "value is null? "
					+ (null == value) + " in updateDataBase method");
		}

	}

	/**
	 * Update database as per column name.
	 * 
	 * @param columnName
	 *            the column name
	 * @param isChecked
	 *            the is checked
	 * @param uri
	 *            the uri
	 */
	private void updateDataBase(String columnName, boolean isChecked, Uri uri) {
		if (null != columnName && null != mContext) {
			ContentValues mContentValues = new ContentValues();
			mContentValues.put(columnName, isChecked);
			mContext.getContentResolver().update(uri, mContentValues, null,
					null);
		} else {
			LogUtils.e(TAG, "columnName object is null? "
					+ (null == columnName) + "in update database method ");
		}
	}

	/**
	 * Update data base as per column name.
	 * 
	 * @param columnName
	 *            the column name
	 * @param value
	 *            the value
	 * @param uri
	 *            the uri
	 */
	private void updateDataBase(String columnName, int value, Uri uri,
			String where) {
		if (null != columnName && null != mContext) {
			ContentValues mContentValues = new ContentValues();
			mContentValues.put(columnName, value);
			mContext.getContentResolver().update(uri, mContentValues, where,
					null);
		} else {
			LogUtils.e(TAG, "columnName object is null? "
					+ (null == columnName) + "in update database method ");
		}

	}

	/**
	 * Checks if row is present else insert first row.
	 */
	private void checkIfRowIsPresentElseInsertFirstRow() {
		if (null == mContext) {
			LogUtils.e(TAG,
					"mContext object is null in checkIfRowIsPresentElseInsertFirstRow method ");
			return;
		}
		ContentResolver contentResolver = mContext.getContentResolver();
		if (null == contentResolver) {
			LogUtils.e(
					TAG,
					"contentResolver object is null in checkIfRowIsPresentElseInsertFirstRow method ");
			return;
		}
		Cursor mCursorObj = contentResolver.query(
				BuaContentProvider.getBuaPrefCONTENT_URI(), null, null, null,
				null);
		try {
			if (null != mCursorObj && mCursorObj.getCount() == NO_ROWS) {
				LogUtils.i(TAG, "Adding first row.....");
				ContentValues mContentValues = new ContentValues();
				mContentValues.put(INDEX, FIRST_ROW);
				mContentValues.put(COLUMN_CONTACTS, UNCHECKED);
				mContentValues.put(COLUMN_BOTH_NETWORK_DONT_REMIND, UNCHECKED);
				mContentValues.put(COLUMN_WIFI_DONT_REMIND, UNCHECKED);
				mContentValues.put(COLUMN_TEXT_MESSAGES, CHECKED);
				mContentValues.put(COLUMN_MEDIA_MESSAGES, CHECKED);
				mContentValues.put(COLUMN_CALL_HISTORY, CHECKED);
				mContentValues.put(COLUMN_BOTH_NETWORK, DEFAULT_VALUE);
				mContentValues.put(COLUMN_SUBSCRIPTION_STATE,
						SUBSCRIPTION_STATE_UNSUBSCRIBED);
				mContentValues.put(COLUMN_MUSIC, CHECKED);
				mContentValues.put(COLUMN_PICTURES, CHECKED);
				mContentValues.put(COLUMN_VIDEOS, CHECKED);
				mContentValues.put(COLUMN_DOCUMENTS, CHECKED);
				mContentValues.put(COLUMN_UPDATE_SCHEDULE,
						UPDATE_SCHEDULE_DEFAULT_VALUE);
				mContentValues.put(COLUMN_USE_NETWORK,
						USE_NETWORKS_DEFAULT_VALUE);
				// for requirement 2.2.2.2.4 pg 21
				mContentValues.put(COLUMN_RESTORE_PENDING,
						RESTORE_PENDING_DEFAULT_VALUE);
				// for requirement 2.2.2.2.2 pg 18
				mContentValues.put(COLUMN_REMIND_WAITING_WIFI,
						WIFI_BACKUP_REMIND_AGAIN_DEFAULT_VALUE);
				// for requirement 2.2.2.2.2 pg 18
				mContentValues.put(COLUMN_FAILED_ATTEMPTS,
						FAILED_ATTEMPTS_DEFAULT_VALUE);
				mContentValues.put(COLUMN_REMIND_THRESHOLD_BACKUP,
						THRESHOLD_BACKUP_REMIND_AGAIN_DEFAULT_VALUE);
				mContentValues
						.put(COLUMN_EMAIL_ADDRESS, mContext
								.getString(R.string.email_id_is_not_configured));
				mContentValues.put(COLUMN_NOTIFICATION_PREF, CHECKED);
				mContentValues.put(COLUMN_LASTBACKUP_DATE, "");
				mContentValues.put(COLUMN_LASTBACKUP_STATE, BACKUP_NO_STATE);
				mContentValues.put(COLUMN_DEFAULT_STORAGE_LOCATION,
						STORAGE_PREFERENCE_DEFAULT_VALUE);
				mContentValues.put(COLUMN_NEXT_BACKUP_TIME,
						NEXT_BACKUP_TIME_DEFAULT_VALUE); // After Restart alarm
															// reset issue.
				mContext.getContentResolver().insert(
						BuaContentProvider.getBuaPrefCONTENT_URI(),
						mContentValues);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != mCursorObj && !mCursorObj.isClosed()) {
				mCursorObj.close();
				mCursorObj = null;
			}
		}
	}

	// ################################################################################################
	// for requirement 2.2.2.2.4 additional requirement point 6 pg 23

	/**
	 * Gets the number of rows present in particular table.
	 * 
	 * @param uri
	 *            the uri
	 * @return the number of rows
	 */
	public int getNumberOfRows(Uri uri) {

		if (null == mContext || null == uri) {
			LogUtils.e(TAG,
					"mContext or uri object is null in getNumberOfRows method returning 0");
			return 0;
		}
		ContentResolver contentResolver = mContext.getContentResolver();
		if (null == contentResolver) {
			LogUtils.e(TAG,
					"contentResolver object is null in getNumberOfRows method returning 0");
			return 0;
		}
		Cursor mCursorObj = contentResolver.query(uri, null, null, null, null);
		try {
			if (null == mCursorObj || mCursorObj.getCount() == NO_ROWS) {
				return 0;
			} else {
				return mCursorObj.getCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != mCursorObj && !mCursorObj.isClosed()) {
				mCursorObj.close();
				mCursorObj = null;
			}
		}

		return 0;
	}
	
	public ArrayList<BackupStateData> getBackupStateDataList() {
		ArrayList<BackupStateData> returnList = new ArrayList<BackupStateData>();
		Uri uri = BuaContentProvider.getBackupStateCONTENT_URI();
		Cursor cursor = mContext.getContentResolver().query(uri, null, null,
				null, null);
		BackupStateData backupStateData = null;
		if(null != cursor) {
			while(cursor.moveToNext()) {
				backupStateData = new BackupStateData();
				backupStateData.setFileNameWithPath(cursor.getString(cursor.getColumnIndex(COLUMN_FILE_PATH)));
				backupStateData.setFileBackupState(cursor.getInt(cursor.getColumnIndex(COLUMN_FILE_BACKUP_STATE)));
				returnList.add(backupStateData);
			}
		}
		if (null != cursor && !cursor.isClosed()) {
			cursor.close();
			cursor = null;
		}
		return returnList;
	}

	/**
	 * Read by row number.
	 * 
	 * @param rowNumber
	 *            the row number
	 * @return the backup state data
	 */
	public BackupStateData readBackupStateByRowNumber(int rowNumber) {
		BackupStateData backUpdata = new BackupStateData();
		backUpdata.setRowNumber(rowNumber);
		backUpdata.setFileBackupState(getBackupStateFromBackupTable(rowNumber));
		backUpdata.setFileNameWithPath(getFileNameFromBackupTable(rowNumber));
		return backUpdata;
	}

	/**
	 * Update backup state by file path.
	 * 
	 * @param newBackupData
	 *            the backup data
	 */
	public void updateBackupStateByFilePath(BackupStateData newBackupData) {
		if(null == newBackupData){
			LogUtils.i(TAG,"new back up data is null returning");
			return;
		}
		String fileNAme = newBackupData.getFileNameWithPath();
		if (null == fileNAme) {
			fileNAme = "";
		}
		int backupState = newBackupData.getFileBackupState();
		if (backupState == -1) {
			backupState = FILE_BACKUP_CANCELLED;
		}
		String where = COLUMN_FILE_PATH + " = '" + fileNAme + "'";
		updateDataBase(COLUMN_FILE_BACKUP_STATE, backupState,
				BuaContentProvider.getBackupStateCONTENT_URI(), where);
	}

	/**
	 * Drop all rows from backup table.
	 */
	public void dropAllRowsFromTable(Uri uri) {
		if (null == uri) {
			LogUtils.i(TAG, "uri is null returning");
			return;
		}
		if (getNumberOfRows(uri) > 0) {
			LogUtils.i(TAG, "deleting rows");
			mContext.getContentResolver().delete(uri, null, null);
			return;
		}
		LogUtils.i(TAG, "could not delete rows");
	}

	/**
	 * Insert at last position.
	 * 
	 * @param backupData
	 *            the backup data
	 */
	public void insertAtLastPosition(BackupStateData backupData) {
		if(null == backupData){
			LogUtils.i(TAG,"back up data is null returning");
			return;
		}
		String fileNAme = backupData.getFileNameWithPath();
		if (null == fileNAme) {
			fileNAme = "";
		}
		LogUtils.i(TAG,"FilePath To insert : "+ fileNAme);
		LogUtils.i(TAG,"file state is : "+ backupData.getFileBackupState());
		int backupState = backupData.getFileBackupState();
		if (backupState == -1) {
			backupState = FILE_BACKUP_CANCELLED;
		}
		ContentValues contentValues = new ContentValues();

		contentValues.put(COLUMN_FILE_PATH, fileNAme);
		contentValues.put(COLUMN_FILE_BACKUP_STATE, backupState);
		mContext.getContentResolver().insert(
				BuaContentProvider.getBackupStateCONTENT_URI(), contentValues);
	}

	/**
	 * Insert at last position.
	 * 
	 * @param backupData
	 *            the backup data
	 */
	public void insertFileInfo(Profile profile) {
		if(null == profile){
			LogUtils.i(TAG,"profile is null returning");
			return;
		}
		int operationType =0;
		String fileName = null;
		String remotePath = null;
		String localPath = null;
		if(profile instanceof UploadProfile) {
			operationType = OPERATION_UPLOAD;
			fileName = Utils.getFileNameFromPath(((UploadProfile)profile).getLocalPath());
			remotePath = ((UploadProfile)profile).getPathOnServer();
			localPath =   ((UploadProfile)profile).getLocalPath().replace(fileName, "").replaceAll("/"+"/", "/");
		}
		else if(profile instanceof DownloadProfile) {
			operationType = OPERATION_DOWNLOAD;
			fileName = ((DownloadProfile)profile).getFileName();
			LogUtils.i(TAG, "Remote Path-"+((DownloadProfile)profile).getRemotePath());
			remotePath = Utils.getRemotePath(((DownloadProfile)profile).getRemotePath(),fileName);
			localPath =   ((DownloadProfile)profile).getLocalPath();
			
		}
		ContentValues contentValues = new ContentValues();
		LogUtils.i("FileOperationInfo", "************************");
		LogUtils.i("FileName:", fileName+" ");
		LogUtils.i("LocalPath:", localPath+" ");
		LogUtils.i("RemotePath:", remotePath+" ");
		LogUtils.i("FileOperationType:", operationType+" ");
		LogUtils.i("FileOperationInfo", "************************");
		contentValues.put(COLUMN_FILE_NAME, fileName);
		contentValues.put(COLUMN_LOCAL_PATH, localPath);
		contentValues.put(COLUMN_REMOTE_PATH, remotePath);
		contentValues.put(COLUMN_OPERATION_TYPE, operationType);
		mContext.getContentResolver().insert(
				BuaContentProvider.getFileOperationCONTENT_URI(), contentValues);
	}

	/**
	 * delete file from DB
	 * 
	 * @param fileName
	 *            file to be removed
	 */
	public void deleteFileInfo(String fileName) {
		LogUtils.d(TAG, "deleteFileInfo+++++++++++++++++++++++++++++++");
		if(null == fileName){
			LogUtils.i(TAG,"fileName is null returning");
			LogUtils.d(TAG, "deleteFileInfo-------------------------------");
			return;
		}
		LogUtils.i("FileOperationInfo", "************************");
		LogUtils.i("FileName:", fileName+" ");
		LogUtils.i("FileOperationInfo", "************************");
		mContext.getContentResolver().delete(
				BuaContentProvider.getFileOperationCONTENT_URI(), COLUMN_FILE_NAME+"=?", new String[] {fileName});
		LogUtils.d(TAG, "deleteFileInfo-------------------------------");
	}
	
	
	/**
	 * delete file from DB
	 * 
	 * @param fileName
	 *            file to be removed
	 */
	public Cursor getFileInfo(String fileName) {
		LogUtils.d(TAG, "deleteFileInfo+++++++++++++++++++++++++++++++");
		if(null == fileName){
			LogUtils.i(TAG,"fileName is null returning");
			LogUtils.d(TAG, "deleteFileInfo-------------------------------");
			return null;
		}
		LogUtils.i("FileOperationInfo", "************************");
		LogUtils.i("FileName:", fileName+" ");
		LogUtils.i("FileOperationInfo", "************************");
		Cursor c= mContext.getContentResolver().query(BuaContentProvider.getFileOperationCONTENT_URI(), 
				null,  COLUMN_FILE_NAME+"=?", new String[] {fileName},null);
		while(c.moveToNext()) {
			LogUtils.i(TAG, "File Name-"+c.getString(c.getColumnIndex(COLUMN_FILE_NAME)));
			LogUtils.i(TAG, "local path-"+c.getString(c.getColumnIndex(COLUMN_LOCAL_PATH)));
			LogUtils.i(TAG, "File Name-"+c.getString(c.getColumnIndex(COLUMN_REMOTE_PATH)));
		}
		return c;
	}
	

	/**
	 * Gets the file name from backup table.
	 * 
	 * @param rowNumber
	 *            the row number
	 * @return the file name from backup table
	 */
	private String getFileNameFromBackupTable(int rowNumber) {
		String columnName = COLUMN_FILE_PATH;
		Cursor tempCursor = null;
		String where = INDEX + " = " + (rowNumber + 1);
		tempCursor = returnCursorAsPerColumnName(columnName,
				BuaContentProvider.getBackupStateCONTENT_URI(), where);
		String fileName = getStringValueFromCursor(tempCursor, columnName);
		LogUtils.i(TAG,"File name in userpref controoler is "+fileName );
		if (null != tempCursor && !tempCursor.isClosed()) {
			tempCursor.close();
			tempCursor = null;
		}
		if (null == fileName) {
			return "";
		}
		return fileName;
	}

	/**
	 * return integer preference value from database and return to caller.
	 * 
	 * @param rowNumber
	 *            the row number
	 * @return the update schedule
	 */
	private int getBackupStateFromBackupTable(int rowNumber) {
		String columnName = COLUMN_FILE_BACKUP_STATE;
		Cursor tempCursor = null;
		String where = INDEX + " = " + (rowNumber + 1);
		tempCursor = returnCursorAsPerColumnName(columnName,
				BuaContentProvider.getBackupStateCONTENT_URI(), where);
		int tempInt = getIntValueFromCursor(tempCursor, columnName);
		if (null != tempCursor && !tempCursor.isClosed()) {
			tempCursor.close();
			tempCursor = null;
		}
		if (tempInt == -1) {
			return FILE_BACKUP_CANCELLED;
		}
		return tempInt;
	}

	// for requirement 2.2.2.2.4 additional requirement point 6 pg 23
	// #############################################################################################

}
