package com.patelbiraj.ultrasearch.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.patelbiraj.ultrasearch.utils.Constants.FileFormatConstants.*;

import com.patelbiraj.ultrasearch.R;
import com.patelbiraj.ultrasearch.pojo.HeaderInformationHolder;
import com.patelbiraj.ultrasearch.pojo.InformationHolder;
import com.patelbiraj.ultrasearch.pojo.OfflineFileInformationHolder;
import com.patelbiraj.ultrasearch.utils.LogUtils;
import com.patelbiraj.ultrasearch.utils.UtilityClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * The Class ViewHolder is a helper class used by CustomAdapter class it holds
 * references to TextView ,checkBox, file/folder icons for a single row.
 */
class ViewHolder {

	/** . The file name */
	TextView rowTextViewFileName = null;

	/** The icon. */
	ImageView rowFileIcon = null;

}

public class CustomAdapter extends ArrayAdapter<InformationHolder> {

	/** The TAG. */
	private final String TAG = "CustomAdapter";

	/** The application context. */
	private Context mContext = null;

	/**
	 * The view holder object which holds reference to single row icon and check
	 * box.
	 */
	private ViewHolder mViewHolder = null;

	/**
	 * The information list which contains information like file name and file
	 * location for offline tab further this object will contain data of online
	 * storage path etc.
	 */
	private ArrayList<InformationHolder> mInformationList = null;

	/** The inflater. */
	private LayoutInflater mInflater;

	/**
	 * Instantiates a new custom view.
	 * 
	 * @param context
	 *            the context
	 * @param dataSource
	 *            the data source
	 * @param mcblist
	 *            the mcblist
	 * @param iconOnClickListener
	 * @param mIsChecked
	 *            the m is checked
	 */
	public CustomAdapter(final Context context,
			final ArrayList<InformationHolder> informationList,
			final OnCheckedChangeListener mcblist,
			OnClickListener iconOnClickListener,
			final HashMap<String, Boolean> mIsChecked) {
		super(context, R.layout.customview, informationList);
		mInformationList = informationList;
		mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * returns inflated view to calling activity
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		if (null == parent) {
			return null;
		}
		LogUtils.i(TAG, "enter in getView Method");
		InformationHolder information = mInformationList.get(position);
		if (null != information
				&& information instanceof HeaderInformationHolder) {
			convertView = mInflater
					.inflate(R.layout.list_header, parent, false);
			TextView textView = (TextView) convertView
					.findViewById(R.id.list_header_title);
			textView.setText(((HeaderInformationHolder) information)
					.getItemName());
			return convertView;
		}
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.customview, parent, false);
			mViewHolder = new ViewHolder();
			convertView.setTag(mViewHolder);
		} else {
			if (mInformationList.get(position) instanceof OfflineFileInformationHolder
					&& !(convertView instanceof RelativeLayout)) {
				convertView = mInflater.inflate(R.layout.customview, parent,
						false);
			}
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		if (null != mViewHolder) {
			mViewHolder.rowTextViewFileName = (TextView) convertView
					.findViewById(R.id.file_name);
			mViewHolder.rowFileIcon = (ImageView) convertView
					.findViewById(R.id.file_icon);
			mViewHolder.rowFileIcon.setTag(position);
			mViewHolder.rowTextViewFileName.setTag(position);
		}
		setViewValues(position, convertView);
		LogUtils.i(TAG, "exit from getView Method");
		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#getViewTypeCount()
	 */
	@Override
	public int getViewTypeCount() {
		for (InformationHolder info : mInformationList) {
			if (info instanceof HeaderInformationHolder) {
				return 2;
			}
		}
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		int type = 1;

		// check if position inside this section
		if (mInformationList.get(position) instanceof HeaderInformationHolder)
			return 0;
		return type;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		return (getItemViewType(position) != 0);
	}

	/**
	 * gets count from data source to set adapter size.
	 * 
	 * @see android.widget.ArrayAdapter#getCount()
	 * 
	 */
	@Override
	public int getCount() {
		return mInformationList.size();
	}

	/**
	 * Sets the icons for the files and folders .This method also manages check
	 * box preferences.
	 * 
	 * @param position
	 *            the new image view
	 * @param view
	 */
	private void setViewValues(final int position, View view) {
		LogUtils.i(TAG, "Enter in setImageView Method of CustomView in "
				+ "EventHandler " + position);
		if (null != view && null != mInformationList && null != mViewHolder
				&& position < mInformationList.size()) {

			File file = null;
			Object dataAtPosition = mInformationList.get(position);
			if (null != dataAtPosition
					&& dataAtPosition instanceof OfflineFileInformationHolder) {
				file = ((OfflineFileInformationHolder) dataAtPosition)
						.getFileObject();
			}
			if (null != file && file.isFile()) {
				String mFileName = file.toString();
				String itemExtension;
				try {
					itemExtension = "."
							+ mFileName
									.substring(mFileName.lastIndexOf(".") + 1);
				} catch (Exception e) {
					itemExtension = "";
				}
				switch (UtilityClass.getFileTypeFromExtention(itemExtension)) {
				case TYPE_PDF:
					mViewHolder.rowFileIcon.setImageResource(R.drawable.pdf);
					break;
				case TYPE_ZIP:
					mViewHolder.rowFileIcon.setImageResource(R.drawable.zip_2);
					break;
				case TYPE_PICTURE:
					mViewHolder.rowFileIcon
							.setImageResource(R.drawable.ic_bua_browse_list_gallery_48x48);
					break;
				case TYPE_MUSIC:
					mViewHolder.rowFileIcon
							.setImageResource(R.drawable.ic_bua_browse_list_music_48x48);
					break;
				case TYPE_VIDEO:
					mViewHolder.rowFileIcon
							.setImageResource(R.drawable.ic_bua_browse_list_mov_48x48);
					break;
				case TYPE_DOC:
					mViewHolder.rowFileIcon.setImageResource(R.drawable.doc);
					break;
				case TYPE_XLS:
					mViewHolder.rowFileIcon.setImageResource(R.drawable.xls);
					break;
				case TYPE_PPT:
					mViewHolder.rowFileIcon.setImageResource(R.drawable.ppt);
					break;
				case TYPE_APK:
					mViewHolder.rowFileIcon
							.setImageResource(R.drawable.apk_file);
					break;
				case TYPE_TEXT:
					mViewHolder.rowFileIcon.setImageResource(R.drawable.text);
					break;
				case TYPE_UNKNOWN:
				default:
					mViewHolder.rowFileIcon
							.setImageResource(R.drawable.unknown_file);
					break;
				}

			} else if (null != file && file.isDirectory()) {
				mViewHolder.rowFileIcon
						.setImageResource(R.drawable.ic_bua_browse_list_folder_48x48);

			}

			mViewHolder.rowTextViewFileName.setText(file.getName());

		}
		LogUtils.i(TAG, "End of setImageView Method of CustomView ");
	}

	/**
	 * Used to reset new InformationList objects arraylist
	 * 
	 * @param informationList
	 *            the new information list
	 */
	public void setInformationList(ArrayList<InformationHolder> informationList) {
		if (null != informationList) {
			mInformationList = informationList;
		}
	}

}
