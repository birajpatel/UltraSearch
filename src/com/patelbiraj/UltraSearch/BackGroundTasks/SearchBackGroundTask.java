package com.patelbiraj.UltraSearch.BackGroundTasks;

import java.util.ArrayList;
import com.patelbiraj.UltraSearch.POJO.InformationHolder;
import com.patelbiraj.UltraSearch.POJO.SearchPreference;
import com.patelbiraj.UltraSearch.Utils.Constants;
import com.patelbiraj.UltraSearch.Utils.LogUtils;
import com.patelbiraj.UltraSearch.Utils.UtilityClass;
import static  com.patelbiraj.UltraSearch.Utils.Constants.FileFormatConstants.*;
import android.R.bool;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

public class SearchBackGroundTask extends
		AsyncTask<SearchPreference, Void, ArrayList<InformationHolder>>
		implements BackGroundTask {

	/** The call back to respond to various task events to update UI. */
	private BackGroundCallback mCallBack;

	private Context mContext;

	private String TAG = "SearchBackGroundTask";

	/**
	 * Instantiates a new search back ground task.
	 * 
	 * @param callback
	 *            the callback
	 */
	public SearchBackGroundTask(BackGroundCallback callback, Context context) {
		mCallBack = callback;
		mContext = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (null != mCallBack) {
			mCallBack.taskStarted(this);
		}
	}

	@Override
	protected ArrayList<InformationHolder> doInBackground(
			SearchPreference... params) {
		if (null != mCallBack) {
			mCallBack.taskRunning(this);
		}
		SearchPreference pref = params[0];
		String searchString = pref.getSearchString();
		boolean isPicture = pref.isPictureMarked();
		boolean isDocument = pref.isDocumentMarked();
		boolean isVideo = pref.isVideoMarked();
		boolean isMusic = pref.isMusicMarked();
		boolean isOther = pref.isOthersMarked();
		if(isPicture && !isCancelled()){
			searchString(searchString,"/mnt/",SEARCH_CATEGORY_PICTURES);
		}
		if(isDocument && !isCancelled()){
			searchString(searchString,"/mnt/",SEARCH_CATEGORY_DOCUMENTS);
		}
		if(isMusic && !isCancelled()){
			searchString(searchString,"/mnt/",SEARCH_CATEGORY_MUSIC);
		}
		if(isVideo && !isCancelled()){
			searchString(searchString,"/mnt/",SEARCH_CATEGORY_VIDEOS);
		}
		if(isOther && !isCancelled()){
			/*other files*/
		}
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<InformationHolder> result) {
		super.onPostExecute(result);
		if (null != mCallBack) {
			mCallBack.taskComplete(this, result);
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (null != mCallBack) {
			mCallBack.taskCancelled(this);
		}
	}

	@Override
	public void cancelTask() {
		this.cancel(true);
	}

	public static Cursor query(Context context, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder,
			int limit) {

		try {
			ContentResolver resolver = context.getContentResolver();
			if (resolver == null) {
				return null;
			}
			if (limit > 0) {
				uri = uri.buildUpon().appendQueryParameter("limit", "" + limit)
						.build();
			}
			return resolver.query(uri, projection, selection, selectionArgs,
					sortOrder);
		} catch (Exception ex) {
			return null;
		}

	}

	public ArrayList<String> getExtensionSpecificFiles(
			ArrayList<String> extensions) {

		String[] selectionArgs = null;
		String selectionMimeType = null;
		if (extensions != null && extensions.size() > 0) {
			selectionArgs = new String[extensions.size()];
			selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE
					+ " IN ( ";
			for (int i = 0; i < extensions.size(); i++) {
				if (i < extensions.size() - 1) {
					selectionMimeType += "?,";
				} else {
					selectionMimeType += "?";
				}

				selectionArgs[i] = MimeTypeMap.getSingleton()
						.getMimeTypeFromExtension(extensions.get(i));
				LogUtils.i("TestAndroidActivity-TAG", "Mime types - "
						+ selectionArgs[i]);
			}

			selectionMimeType += ")";
		}
		LogUtils.i("TestAndroidActivity-TAG", "selectionMimeType-"
				+ selectionMimeType);

		String[] projections = new String[] { MediaStore.MediaColumns.DATA };
		Cursor c = query(mContext, MediaStore.Files.getContentUri("external"),
				projections, selectionMimeType, selectionArgs, null, 0);

		if (c != null) {
			ArrayList<String> returnedArray = new ArrayList<String>();
			String str = null;
			while (c.moveToNext()) {
				str = c.getString(c
						.getColumnIndex(MediaStore.MediaColumns.DATA));
				LogUtils.i("TestAndroidActivity-Documents", "Name -" + str);
				returnedArray.add(str);
			}
			// LogUtils.i(TAG,"Documents size - "+returnedArray.size());
			return returnedArray;
		} else {
			return new ArrayList<String>();
		}
	}

	public ArrayList<InformationHolder> searchString(String filename, String path, int searchPref) {
		ArrayList<String> returnedArray = new ArrayList<String>();
		if (searchPref == Constants.FileFormatConstants.SEARCH_CATEGORY_DOCUMENTS) {
			returnedArray = getExtensionSpecificFiles(UtilityClass
					.getExtensionList(Constants.FileFormatConstants.SEARCH_CATEGORY_DOCUMENTS));
		} else {
			Cursor c = query(mContext,
					MediaStore.Files.getContentUri("external"), null, null,
					null, null, 0);
			if (c != null) {

				// Container tempContainer = null;
				String str = null;
				while (c.moveToNext()) {
					str = c.getString(c
							.getColumnIndex(MediaStore.MediaColumns.DATA));
					LogUtils.i("TestAndroidActivity-Files ", "Name -" + str);
					returnedArray.add(str);
				}
				LogUtils.i(TAG, "Documents size - " + returnedArray.size());

				LogUtils.e("TestAndroidActivity-Count", returnedArray.size()
						+ " ");
			}

		}
		ArrayList<String> filteredData= filterData(returnedArray, path, filename);
		return null;
		

	}

	public ArrayList<String> filterData(ArrayList<String> array, String parent,
			String searchString) {
		String temp, temp1;
		ArrayList<String> filteredList = new ArrayList<String>();
		for (String str : array) {
			temp = str.substring(0, str.lastIndexOf("/") + 1);
			temp1 = str.substring(str.lastIndexOf("/") + 1);
			LogUtils.i("FullPath", str);
			LogUtils.i("Path-", temp + " FileName-" + temp1);
			if (searchString.isEmpty()
					|| (temp.contains(parent) && temp1.contains(searchString))) {
				LogUtils.i("TestAndroidActivity-Searched path", str);
				filteredList.add(str);
			}
		}
		return filteredList;
	}

}
