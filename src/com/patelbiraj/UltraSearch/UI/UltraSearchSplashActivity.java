package com.patelbiraj.UltraSearch.UI;

import static com.patelbiraj.UltraSearch.Utils.Constants.*;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.patelbiraj.UltraSearch.R;
import com.patelbiraj.UltraSearch.Utils.LogUtils;

public class UltraSearchSplashActivity extends Activity implements
		MediaScannerConnectionClient {

	private String TAG = "UltraSearchSplashActivity";

	/** The media scanner connection object used to scan the file system. */
	private MediaScannerConnection mMediaScannerConnectionObj = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ultra_search_splash);
		prepareMediaScanner();
		startMediaScanner();
	}

	/**
	 * Prepare media scanner object before starting scanning.
	 */
	private void prepareMediaScanner() {
		sendBroadcast(new Intent(
				Intent.ACTION_MEDIA_MOUNTED,
				Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		mMediaScannerConnectionObj = new MediaScannerConnection(this, this);
	}

	/**
	 * This will start media scanner every time this application is launched so
	 * Media Store will have the latest entry of file system.
	 */
	private void startMediaScanner() {
		if (null != mMediaScannerConnectionObj) {
			mMediaScannerConnectionObj.connect();
		}
	}

	/**
	 * this will finish splash activity and will launch up the search activity.
	 */
	private void finishSplashActivity() {
		Intent intent = new Intent(this, UltraListViewActivity.class);
		startActivity(intent);
		UltraSearchSplashActivity.this.finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			LogUtils.i(TAG, "Activity Destroying");
			if (null != mMediaScannerConnectionObj
					&& mMediaScannerConnectionObj.isConnected()) {
				mMediaScannerConnectionObj.disconnect();
			}
		}
	}

	// Media Scanner Methods >>>>>>>>>>>>>>>>>>>>>>>>
	@Override
	public void onMediaScannerConnected() {
		Log.i(TAG, "onMediaScannerConnected>>>>>>>>>>>>>>>>>>");
		mMediaScannerConnectionObj.scanFile(MNT_FOLDER, null);

	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		Log.i(TAG, "onScanCompleted<<<<<<<<<<<<<<<");
		if (null != mMediaScannerConnectionObj
				&& mMediaScannerConnectionObj.isConnected()) {
			mMediaScannerConnectionObj.disconnect();
		}
		finishSplashActivity();
	}

	// Media Scanner Methods <<<<<<<<<<<<<<<<<<<<<<<<

}
