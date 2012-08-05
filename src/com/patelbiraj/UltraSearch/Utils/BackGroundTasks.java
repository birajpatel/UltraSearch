package com.patelbiraj.UltraSearch.Utils;

import java.util.ArrayList;

import android.os.AsyncTask;

public class BackGroundTasks extends AsyncTask<Object, Void, Object> {

	/**
	 * Instantiates a new back ground tasks.
	 *
	 * @param mBackGroundTasksCallbacks the m back ground tasks callbacks
	 */
	public BackGroundTasks(BackGroundTasksCallbacks mBackGroundTasksCallbacks) {
		super();
		this.mBackGroundTasksCallbacks = mBackGroundTasksCallbacks;
	}

	/**
	 * The back ground tasks callbacks will notify client of this back ground
	 * task , about task status.
	 */
	BackGroundTasksCallbacks mBackGroundTasksCallbacks;

	private String TAG = "BackGroundTasks";

	public interface BackGroundTasksCallbacks {

		/**
		 * Work started.
		 */
		void workStarted();

		/**
		 * Work finished.
		 * 
		 * @param result
		 *            the result
		 */
		void workFinished(Object result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		LogUtils.i(TAG, "onPreExecute");
		if (null != mBackGroundTasksCallbacks) {
			mBackGroundTasksCallbacks.workStarted();
		} else {
			LogUtils.i(TAG, "mBackGroundTasksCallbacks is null in onPreExecute");
		}
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		LogUtils.i(TAG, "onPostExecute");
		if (null != mBackGroundTasksCallbacks) {
			mBackGroundTasksCallbacks.workFinished(result);
		} else {
			LogUtils.i(TAG,
					"mBackGroundTasksCallbacks is null in onPostExecute");
		}

	}

}
