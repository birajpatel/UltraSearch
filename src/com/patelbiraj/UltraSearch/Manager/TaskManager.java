package com.patelbiraj.UltraSearch.Manager;

import android.content.Context;

import com.patelbiraj.UltraSearch.BackGroundTasks.BackGroundTask;
import com.patelbiraj.UltraSearch.BackGroundTasks.BackGroundTask.BackGroundCallback;
import com.patelbiraj.UltraSearch.Utils.LogUtils;

public class TaskManager implements BackGroundCallback {

	/** The ui call back to notify UI only when callback is registered. */
	private UICallBack mUicallBack = null;

	/** The tag. */
	private String TAG = "TaskManager";

	private static TaskManager mTaskManager = null;

	private Context mContext = null;

	public void registerUICallBack(UICallBack uiCallBack) {
		mUicallBack = uiCallBack;
	}

	public void unregisterUICallBack() {
		mUicallBack = null;
	}

	public TaskManager(Context context) {
		mContext = context;
	}

	public synchronized static final TaskManager getSingleton(
			final Context context) {
		if (null == context) {
			return null;
		}
		if (null == mTaskManager) {
			mTaskManager = new TaskManager(context);
		}
		return mTaskManager;
	}

	public interface UICallBack {

		public abstract void taskStarted(BackGroundTask task);

		public abstract void taskRunning(BackGroundTask task);

		public abstract void taskComplete(BackGroundTask task, Object result);

		public abstract void taskCancelled(BackGroundTask task);

	}

	@Override
	public void taskStarted(BackGroundTask task) {
		LogUtils.i(TAG, "taskStarted");
		if (null != mUicallBack) {
			LogUtils.i(TAG, "Callback registered in taskStarted - notifying");
			mUicallBack.taskStarted(task);
		} else {
			LogUtils.i(TAG, "Callback not found in taskStarted");
		}

	}

	@Override
	public void taskRunning(BackGroundTask task) {
		LogUtils.i(TAG, "taskRunning");
		if (null != mUicallBack) {
			LogUtils.i(TAG, "Callback registered in taskRunning - notifying");
			mUicallBack.taskRunning(task);
		} else {
			LogUtils.i(TAG, "Callback not found in taskRunning");
		}

	}

	@Override
	public void taskComplete(BackGroundTask task, Object result) {
		LogUtils.i(TAG, "taskComplete");
		if (null != mUicallBack) {
			LogUtils.i(TAG, "Callback registered in taskComplete - notifying");
			mUicallBack.taskComplete(task, result);
		} else {
			LogUtils.i(TAG, "Callback not found in taskComplete");
		}

	}

	@Override
	public void taskCancelled(BackGroundTask task) {
		LogUtils.i(TAG, "taskCancelled");
		if (null != mUicallBack) {
			LogUtils.i(TAG, "Callback registered in taskCancelled - notifying");
			mUicallBack.taskCancelled(task);
		} else {
			LogUtils.i(TAG, "Callback not found in taskCancelled");
		}

	}

}
