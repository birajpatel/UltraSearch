package com.patelbiraj.ultrasearch.ui;

import com.patelbiraj.ultrasearch.utils.LogUtils;
import com.patelbiraj.ultrasearch.utils.UIPreferenceProvider;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Checkable;
import android.widget.ImageButton;
import static com.patelbiraj.ultrasearch.utils.Constants.ButtonConstants.*;

public class CustomImageButton implements Checkable, OnClickListener {

	private boolean isChecked = false;

	private ImageButton imageButton = null;

	private int buttonId = -1;

	private final String TAG = "CustomImageButton";

	private Context mContext = null;

	private UIPreferenceProvider mUIPreferenceProvider;

	public CustomImageButton(ImageButton imageButton, int id, Context context) {
		this.imageButton = imageButton;
		this.buttonId = id;
		this.mContext = context;
		mUIPreferenceProvider = new UIPreferenceProvider(mContext);
		if (null != imageButton) {
			imageButton.setOnClickListener(this);
		}
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		isChecked = checked;
		setDrawable();
	}

	@Override
	public void toggle() {
		isChecked = !isChecked;
		setDrawable();
	}

	private void setDrawable() {
		if (buttonId == -1) {
			LogUtils.i(TAG, "button id is = -1 returning in setDrawable");
			return;
		}
		if (null == imageButton) {
			LogUtils.i(TAG, "button is null in setDrawable button returning");
			return;
		}
		if (null == mUIPreferenceProvider) {
			mUIPreferenceProvider = new UIPreferenceProvider(mContext);
		}
		LogUtils.i(TAG, "setDrawable");
		switch (buttonId) {
		case BUTTON_PICTURES:
			imageButton.setImageDrawable(mUIPreferenceProvider
					.getPicturesButtonIcon(isChecked()));
			break;
		case BUTTON_MUSIC:
			imageButton.setImageDrawable(mUIPreferenceProvider
					.getMusicButtonIcon(isChecked()));
			break;
		case BUTTON_VIDEOS:
			imageButton.setImageDrawable(mUIPreferenceProvider
					.getVideoButtonIcon(isChecked()));
			break;
		case BUTTON_DOCUMENTS:
			imageButton.setImageDrawable(mUIPreferenceProvider
					.getDocsButtonIcon(isChecked()));
			break;
		case BUTTON_USER_CHOICE:
			imageButton.setImageDrawable(mUIPreferenceProvider
					.getUserChoiceButtonIcon(isChecked()));
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		LogUtils.i(TAG, "Onclick");
		toggle();
	}
}
