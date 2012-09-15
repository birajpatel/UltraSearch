package com.patelbiraj.ultrasearch.Utils;

import android.util.Log;

/**
 * The Class LogUtils used for logging.
 */
public class LogUtils {

	private static final String TAG = "LogUtils";

	/** The logging flag to enable/disable logging. */
	private static boolean isLogging = true;

	/**
	 * Checks if is logging.
	 * 
	 * @return true, if is logging
	 */
	public static boolean isLogging() {
		return isLogging;
	}

	/**
	 * Sets the logging.
	 * 
	 * @param isLoggingSet
	 *            the new logging
	 */
	public static void setLogging(Boolean isLoggingSet) {
		if (isLoggingSet != null) {
			isLogging = isLoggingSet;
		} else {
			LogUtils.i("LogUtils",
					"In setLogging function isLoggingSet is null");
		}
	}

	/**
	 * Send a DEBUG log message.
	 * 
	 * @param tag
	 *            the tag
	 * @param msg
	 *            the message
	 */
	public static void d(String tag, String msg) {
		if (tag != null && msg != null) {
			if (isLogging())
				Log.d("UltraSearch_log_" + tag, msg);
		} else {
			LogUtils.i(TAG,
					"In LogUtils function (Debug) TAG or message is null");
		}
	}

	/**
	 * Send an INFO log message.
	 * 
	 * @param tag
	 *            the tag
	 * @param msg
	 *            the message
	 */
	public static void i(String tag, String msg) {
		if (tag != null && msg != null) {
			if (isLogging())
				Log.i("UltraSearch_log_" + tag, msg);
		} else {
			LogUtils.i(TAG,
					"In LogUtils function (Info) TAG or message is null");
		}
	}

	/**
	 * Send a WARN log message.
	 * 
	 * @param tag
	 *            the tag
	 * @param msg
	 *            the message
	 */
	public static void w(String tag, String msg) {
		if (tag != null && msg != null) {
			if (isLogging())
				Log.w("UltraSearch_log_" + tag, msg);
		} else {
			LogUtils.i(TAG,
					"In LogUtils function (Warn) TAG or message is null");
		}
	}

	/**
	 * Send a ERROR log message.
	 * 
	 * @param tag
	 *            the tag
	 * @param msg
	 *            the message
	 */
	public static void e(String tag, String msg) {
		if (tag != null && msg != null) {
			if (isLogging())
				Log.e("UltraSearch_log_" + tag, msg);
		} else {
			LogUtils.i(TAG,
					"In LogUtils function (Error) TAG or message is null");
		}
	}

}