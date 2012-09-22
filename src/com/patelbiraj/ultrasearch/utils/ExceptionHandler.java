package com.patelbiraj.ultrasearch.utils;


public class ExceptionHandler {
	
	/** The tag. */
	private String TAG = "ExceptionHandler";
	
	/** The exception thrown by application. */
	Exception exceptionThrown; 
	
	/**
	 * Instantiates a new exception handler.
	 *
	 * @param exception the exception
	 */
	public ExceptionHandler(Exception exception){
		exceptionThrown = exception;
		handleException();
	}

	/**
	 * Handles exception.
	 */
	private void handleException() {
		LogUtils.i(TAG,"inside handleException");
		
	}
}
