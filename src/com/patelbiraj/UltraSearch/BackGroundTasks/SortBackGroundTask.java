package com.patelbiraj.UltraSearch.BackGroundTasks;

import java.util.ArrayList;
import android.os.AsyncTask;
import com.patelbiraj.UltraSearch.POJO.InformationHolder;

public class SortBackGroundTask
		extends
		AsyncTask<ArrayList<InformationHolder>, Void, ArrayList<InformationHolder>>
		implements BackGroundTask {

	/** The call back to respond to various task events to update UI. */
	BackGroundCallback mCallBack;

	/**
	 * Instantiates a new search back ground task.
	 * 
	 * @param callback
	 *            the callback
	 */
	public SortBackGroundTask(BackGroundCallback callback) {
		mCallBack = callback;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (null != mCallBack) {
			mCallBack.taskStarted(this);
		}
	}

	@Override
	protected ArrayList<InformationHolder> doInBackground(
			ArrayList<InformationHolder>... params) {
		if (null != mCallBack) {
			mCallBack.taskRunning(this);
		}
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<InformationHolder> result) {
		super.onPostExecute(result);
		if (null != mCallBack) {
			mCallBack.taskComplete(this, result);
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (null != mCallBack) {
			mCallBack.taskCancelled(this);
		}
	}

	@Override
	public void cancelTask() {
		this.cancel(true);
	}
}
