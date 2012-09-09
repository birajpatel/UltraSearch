package com.patelbiraj.UltraSearch.BackGroundTasks;

import static com.patelbiraj.UltraSearch.Utils.Constants.MNT_FOLDER;

import com.patelbiraj.UltraSearch.BackGroundTasks.FileSystemRefresher.FileScannerCallbacks;
import com.patelbiraj.UltraSearch.Utils.LogUtils;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * The Class FileSystemRefresher.
 * 
 * @author biraj
 */
public class FileSystemRefresher implements MediaScannerConnectionClient {

	private final String TAG = "FileSystemRefresher";

	/** The media scanner connection object used to scan the file system. */
	private MediaScannerConnection mMediaScannerConnectionObj = null;

	/** The callback to get scanner progress. */
	private FileScannerCallbacks mCallback = null;

	/** The context. */
	private Context mContext = null;

	/**
	 * Instantiates a new file system refresher.
	 *
	 * @param callback the callback
	 * @param context the context
	 */
	public FileSystemRefresher(FileScannerCallbacks callback, Context context) {
		mCallback = callback;
		mContext = context;
	}

	/**
	 * Start scanner.
	 */
	public void startScanner() {
		prepareMediaScanner();
		if (null != mMediaScannerConnectionObj) {
			mMediaScannerConnectionObj.connect();
		}
	}



	/**
	 * Disconnect the scanner.
	 */
	public void disconnect() {
		if (null != mMediaScannerConnectionObj
				&& mMediaScannerConnectionObj.isConnected()) {
			LogUtils.i(TAG, "disconnect requested");
			mMediaScannerConnectionObj.disconnect();
			mMediaScannerConnectionObj = null;
			mCallback = null;
		}
	}

	/**
	 * Prepare media scanner object before starting scanning.
	 */
	private void prepareMediaScanner() {
		if (null != mContext) {
			mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
					.parse("file://"
							+ Environment.getExternalStorageDirectory())));
		}
		mMediaScannerConnectionObj = new MediaScannerConnection(mContext, this);
	}

	/**
	 * The Interface FileScannerCallbacks,this will be used to notify the
	 * scanner progress.
	 */
	public interface FileScannerCallbacks {

		/**
		 * On media scanner connected.
		 */
		public void onMediaScannerConnected();

		/**
		 * On scan completed.
		 * 
		 * @param path
		 *            the path
		 * @param uri
		 *            the uri
		 */
		public void onScanCompleted(String path, Uri uri);

	}

	// Media Scanner Methods >>>>>>>>>>>>>>>>>>>>>>>>
	@Override
	public void onMediaScannerConnected() {
		Log.i(TAG, "onMediaScannerConnected>>>>>>>>>>>>>>>>>>");
		if (null != mCallback) {
			mCallback.onMediaScannerConnected();
		} else {
			Log.i(TAG, "callback null");
		}
		mMediaScannerConnectionObj.scanFile(MNT_FOLDER, null);

	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		Log.i(TAG, "onScanCompleted<<<<<<<<<<<<<<<");
		if (null != mCallback) {
			mCallback.onScanCompleted(path, uri);
		} else {
			Log.i(TAG, "callback null");
		}
		if (null != mMediaScannerConnectionObj
				&& mMediaScannerConnectionObj.isConnected()) {
			mMediaScannerConnectionObj.disconnect();
		}
	}

	// Media Scanner Methods <<<<<<<<<<<<<<<<<<<<<<<<

}
