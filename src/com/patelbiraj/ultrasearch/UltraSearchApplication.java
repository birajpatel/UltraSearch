package com.patelbiraj.ultrasearch;

import com.patelbiraj.ultrasearch.contentprovider.SettingsReader;

import android.app.Application;
import android.content.Context;

public class UltraSearchApplication extends Application {

	/**
	 * Gets the context.
	 * 
	 * @return the context
	 */
	public static final Context getContext() {
		return getContext();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// here we will get the instance of SettingReader class this will ensure
		// that constructor is called and,the default values are inserted in
		// case db is empty
		SettingsReader.getSingleton(getApplicationContext());
	}

}
