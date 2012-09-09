package com.patelbiraj.UltraSearch.UI;

import com.patelbiraj.UltraSearch.R;
import com.patelbiraj.UltraSearch.BackGroundTasks.BackGroundTask;
import com.patelbiraj.UltraSearch.BackGroundTasks.BackGroundTask.BackGroundCallback;
import com.patelbiraj.UltraSearch.Manager.TaskManager;
import com.patelbiraj.UltraSearch.Manager.TaskManager.UICallBack;
import com.patelbiraj.UltraSearch.Utils.LogUtils;
import static com.patelbiraj.UltraSearch.Utils.Constants.ButtonConstants.*;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.ToggleButton;

// TODO: Auto-generated Javadoc
/**
 * The Class UltraListViewActivity.
 */
public class UltraListViewActivity extends ListActivity implements UICallBack,
		OnClickListener {

	/** The tag. */
	private final String TAG = "UltraListViewActivity";

	/** The bottom search selection button. */
	private CustomImageButton mPictureChoiceButton = null;

	/** The m music choice button. */
	private CustomImageButton mMusicChoiceButton = null;

	/** The m video choice button. */
	private CustomImageButton mVideoChoiceButton = null;

	/** The m document choice button. */
	private CustomImageButton mDocumentChoiceButton = null;

	/** The m user defined choice button. */
	private CustomImageButton mUserDefinedChoiceButton = null;

	/** The setting button to launch setting activity. */
	private ImageButton mSettingButton = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ultra_list_view_activity);
		setReferenceToUiComponents();
		setOnClickListeners();
		updateBottomBarValues();
	}

	/**
	 * update users last selections on create.
	 */
	private void updateBottomBarValues() {
		if (null != mPictureChoiceButton) {
			mPictureChoiceButton.setChecked(true);
		}
		if (null != mMusicChoiceButton) {
			mMusicChoiceButton.setChecked(false);
		}
		if (null != mVideoChoiceButton) {
			mVideoChoiceButton.setChecked(true);
		}
		if (null != mDocumentChoiceButton) {
			mDocumentChoiceButton.setChecked(false);
		}
		if (null != mUserDefinedChoiceButton) {
			mUserDefinedChoiceButton.setChecked(true);
		}
	}

	/**
	 * Sets the on click listeners.
	 */
	private void setOnClickListeners() {
		if (null != mSettingButton) {
			mSettingButton.setOnClickListener(this);
		}
	}

	/**
	 * Sets the reference to UI components.
	 */
	private void setReferenceToUiComponents() {
		mPictureChoiceButton = new CustomImageButton(
				(ImageButton) findViewById(R.id.choice_pictures),
				BUTTON_PICTURES, getApplicationContext());
		mMusicChoiceButton = new CustomImageButton(
				(ImageButton) findViewById(R.id.choice_music), BUTTON_MUSIC,
				getApplicationContext());
		mVideoChoiceButton = new CustomImageButton(
				(ImageButton) findViewById(R.id.choice_videos), BUTTON_VIDEOS,
				getApplicationContext());
		mDocumentChoiceButton = new CustomImageButton(
				(ImageButton) findViewById(R.id.choice_documents),
				BUTTON_DOCUMENTS, getApplicationContext());
		mUserDefinedChoiceButton = new CustomImageButton(
				(ImageButton) findViewById(R.id.choice_user),
				BUTTON_USER_CHOICE, getApplicationContext());
		mSettingButton = (ImageButton) findViewById(R.id.launch_setting);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void taskStarted(BackGroundTask task) {
		LogUtils.i(TAG, "taskStarted");
	}

	@Override
	public void taskRunning(BackGroundTask task) {
		LogUtils.i(TAG, "taskRunning");
	}

	@Override
	public void taskComplete(BackGroundTask task, Object result) {
		LogUtils.i(TAG, "taskComplete");
	}

	@Override
	public void taskCancelled(BackGroundTask task) {
		LogUtils.i(TAG, "taskCancelled");
	}

	@Override
	public void onClick(View view) {
		if (null == view) {
			return;
		}
		LogUtils.i(TAG, "onClicked");
		switch (view.getId()) {
		case R.id.launch_setting:
			launchSettingScreen();
			break;
		default:
			break;
		}
	}

	/**
	 * Launch setting screen.
	 */
	private void launchSettingScreen() {
		LogUtils.i(TAG, "launchSettingScreen");
	}

}
