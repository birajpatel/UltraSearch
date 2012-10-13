package com.patelbiraj.ultrasearch.contentprovider;

import com.patelbiraj.ultrasearch.utils.Constants;
import com.patelbiraj.ultrasearch.utils.LogUtils;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * This Class SettingsReader is a helper class which interacts between UI and
 * the Content Provider . when application wants to store or retrieve
 * user-preference-data then UI must call methods of this class instead of
 * directly interacting with content provider this will make the interaction
 * more easier for UI side as code will not be copied everywhere
 */
public class SettingsReader {

	/** The tag. */
	private String TAG = SettingsReader.class.getSimpleName();

	/** The m context. */
	private Context mContext = null;

	/** The m instance. */
	private static SettingsReader mInstance;

	/**
	 * we will make this reader class as singleton to be able to used from
	 * different places via single instance. we will insert some default values
	 * when this constructor is called. even though if user pauses app and
	 * "clears data", due to android design "clear data" will also kill the app,
	 * when user re-opens this app default values will be inserted again.
	 * 
	 * @param context
	 *            the context
	 */
	private SettingsReader(Context context) {
		LogUtils.i(TAG, "SettingsReader contructor");
		mContext = context;
		checkAndInsertDefaultEntries();
	}

	/**
	 * Gets the singleton.
	 * 
	 * @param context
	 *            the context
	 * @return the singleton
	 */
	public synchronized static SettingsReader getSingleton(Context context) {
		if (null == context) {
			return null;
		}

		if ((null == mInstance)) {
			mInstance = new SettingsReader(context);
		}
		return mInstance;
	}

	/**
	 * Check and insert default entries.
	 */
	private void checkAndInsertDefaultEntries() {
		insertDefaultMediaFormats();
	}

	/**
	 * Insert default media formats.
	 */
	private void insertDefaultMediaFormats() {
		if (isTableEmpty(Constants.ContentProviderConstants.TableUris.MEDIA_FORMAT_CONTENT_URI)) {
			LogUtils.i(TAG,
					"MEDIA_FORMAT table is empty inserting default media formats");
			insertMediaValues(Constants.FileFormatConstants.MUSIC_FORMATS);
			insertMediaValues(Constants.FileFormatConstants.PIX_FORMATS);
			insertMediaValues(Constants.FileFormatConstants.VIDEO_FORMATS);
			insertMediaValues(Constants.FileFormatConstants.DOC_FORMATS);
		} else {
			LogUtils.i(TAG, "MEDIA_FORMAT table is not empty");
		}
	}

	/**
	 * This function will insert media values into media format table with
	 * default value = CHECKED for passed array formats.
	 * 
	 * @param array
	 *            the array
	 */
	private void insertMediaValues(String[] array) {
		if (null == array) {
			LogUtils.i(TAG, "array is null in insertMediaValues");
			return;
		}
		ContentValues contentValue = null;
		for (String ext : array) {
			if (null == ext || ext.isEmpty()) {
				continue;
			}
			contentValue = new ContentValues();
			contentValue
					.put(Constants.ContentProviderConstants.FormatTableConstants.COLUMN_FORMAT_NAME,
							ext);
			contentValue
					.put(Constants.ContentProviderConstants.FormatTableConstants.COLUMN_FORMAT_SELECTION,
							Constants.ContentProviderConstants.CHECKED);
			mContext.getContentResolver()
					.insert(Constants.ContentProviderConstants.TableUris.MEDIA_FORMAT_CONTENT_URI,
							contentValue);
		}
	}

	/**
	 * Checks if is table empty by given URI.
	 * 
	 * @param tableUri
	 *            the table uri
	 * @return true, if is table empty
	 */
	private boolean isTableEmpty(Uri tableUri) {
		if (null == mContext) {
			LogUtils.e(TAG, "mContext object is null in isTableEmpty method ");
			return false;
		}
		ContentResolver contentResolver = mContext.getContentResolver();
		if (null == contentResolver) {
			LogUtils.e(TAG,
					"contentResolver object is null in isTableEmpty method ");
			return false;
		}
		Cursor mCursorObj = contentResolver.query(tableUri, null, null, null,
				null);
		if (null != mCursorObj && mCursorObj.getCount() == 0) {
			// zero rows.
			return true;
		}
		return false;
	}
}
