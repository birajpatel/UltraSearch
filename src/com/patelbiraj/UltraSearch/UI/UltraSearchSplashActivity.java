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
import com.patelbiraj.UltraSearch.BackGroundTasks.FileSystemRefresher;
import com.patelbiraj.UltraSearch.BackGroundTasks.FileSystemRefresher.FileScannerCallbacks;
import com.patelbiraj.UltraSearch.Utils.LogUtils;

public class UltraSearchSplashActivity extends Activity implements
		FileScannerCallbacks {

	private String TAG = "UltraSearchSplashActivity";

	
	private FileSystemRefresher mFileSystemRefresher = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ultra_search_splash);
		mFileSystemRefresher = new FileSystemRefresher(this,
				getApplicationContext());
		mFileSystemRefresher.startScanner();
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
			if (null != mFileSystemRefresher) {
				LogUtils.i(TAG, "disconnecting the refresher");
				mFileSystemRefresher.disconnect();
			}
		}
	}

	@Override
	public void onMediaScannerConnected() {
		LogUtils.i(TAG, "onMediaScannerConnected callback");
	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		LogUtils.i(TAG, "onScanCompleted callback");
		finishSplashActivity();
	}

}
