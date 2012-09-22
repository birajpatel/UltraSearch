package com.patelbiraj.ultrasearch.backgroundtasks;

import android.os.AsyncTask;

public interface BackGroundTask {
	
	public interface BackGroundCallback{

		public abstract void taskStarted(BackGroundTask task);
		
		public abstract void taskRunning(BackGroundTask task);
		
		public abstract void taskComplete(BackGroundTask task, Object result);
		
		public abstract void taskCancelled(BackGroundTask task);
		
	}
	
	public abstract void cancelTask();

}
