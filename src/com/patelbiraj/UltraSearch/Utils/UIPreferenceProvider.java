package com.patelbiraj.UltraSearch.Utils;

import com.patelbiraj.UltraSearch.R;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class UIPreferenceProvider {

	Context mContext = null;

	private final String TAG = "UIPreferenceProvider";

	public UIPreferenceProvider(Context context) {
		mContext = context;
	}

	/**
	 * Gets the pictures button icon.
	 * 
	 * @param isChecked
	 *            the is checked
	 * @return the pictures button icon
	 */
	public Drawable getPicturesButtonIcon(boolean isChecked) {
		LogUtils.i(TAG, "getPicturesButtonIcon " + isChecked);
		if (isChecked) {
			return mContext.getResources().getDrawable(
					R.drawable.ic_bua_browse_list_gallery_48x48);
		} else {
			return mContext.getResources().getDrawable(R.drawable.cancelled);
		}
	}

	/**
	 * Gets the music button icon.
	 * 
	 * @param isChecked
	 *            the is checked
	 * @return the music button icon
	 */
	public Drawable getMusicButtonIcon(boolean isChecked) {
		LogUtils.i(TAG, "getMusicButtonIcon " + isChecked);
		if (isChecked) {
			return mContext.getResources().getDrawable(
					R.drawable.ic_bua_browse_list_music_48x48);
		} else {
			return mContext.getResources().getDrawable(R.drawable.cancelled);
		}
	}

	/**
	 * Gets the video button icon.
	 * 
	 * @param isChecked
	 *            the is checked
	 * @return the video button icon
	 */
	public Drawable getVideoButtonIcon(boolean isChecked) {
		LogUtils.i(TAG, "getVideoButtonIcon " + isChecked);
		if (isChecked) {
			return mContext.getResources().getDrawable(
					R.drawable.ic_bua_browse_list_mov_48x48);
		} else {
			return mContext.getResources().getDrawable(R.drawable.cancelled);
		}
	}

	/**
	 * Gets the docs button icon.
	 * 
	 * @param isChecked
	 *            the is checked
	 * @return the docs button icon
	 */
	public Drawable getDocsButtonIcon(boolean isChecked) {
		LogUtils.i(TAG, "getDocsButtonIcon " + isChecked);
		if (isChecked) {
			return mContext.getResources().getDrawable(R.drawable.doc);
		} else {
			return mContext.getResources().getDrawable(R.drawable.cancelled);
		}
	}

	/**
	 * Gets the user choice button icon.
	 * 
	 * @param isChecked
	 *            the is checked
	 * @return the user choice button icon
	 */
	public Drawable getUserChoiceButtonIcon(boolean isChecked) {
		LogUtils.i(TAG, "getUserChoiceButtonIcon " + isChecked);
		if (isChecked) {
			return mContext.getResources().getDrawable(R.drawable.user_choice);
		} else {
			return mContext.getResources().getDrawable(R.drawable.cancelled);
		}
	}
	
	public Drawable getListBackGroundIcon(){
		return null;
	}

}
