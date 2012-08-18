package com.patelbiraj.UltraSearch.UI;

import com.patelbiraj.UltraSearch.BackGroundTasks.BackGroundTask;
import com.patelbiraj.UltraSearch.BackGroundTasks.BackGroundTask.BackGroundCallback;
import com.patelbiraj.UltraSearch.Manager.TaskManager;
import com.patelbiraj.UltraSearch.Manager.TaskManager.UICallBack;

import android.app.ListActivity;

public class UltraListViewActivity extends ListActivity implements UICallBack {


	@Override
	public void taskStarted(BackGroundTask task) {
	}

	@Override
	public void taskRunning(BackGroundTask task) {
	}

	@Override
	public void taskComplete(BackGroundTask task, Object result) {
	}

	@Override
	public void taskCancelled(BackGroundTask task) {
	}
}
