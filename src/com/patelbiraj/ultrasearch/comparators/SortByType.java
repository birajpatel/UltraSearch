package com.patelbiraj.ultrasearch.comparators;

import java.util.Comparator;

import com.patelbiraj.ultrasearch.pojo.InformationHolder;
import com.patelbiraj.ultrasearch.pojo.OfflineFileInformationHolder;
import com.patelbiraj.ultrasearch.utils.UtilityClass;

import static com.patelbiraj.ultrasearch.utils.Constants.FileFormatConstants.*;
import android.content.Context;

/**
 * The Class SortByType implements comparator used to sort by type.
 */
public class SortByType implements Comparator<InformationHolder> {

	/** The context to get string variables from resources. */
	private Context mContext = null;

	/**
	 * Instantiates a new sort by date.
	 * 
	 * @param mContext
	 *            the context
	 */
	public SortByType(Context context) {
		mContext = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(InformationHolder first, InformationHolder second) {
		if (null == first || null == second) {
			return 0;
		}
		OfflineFileInformationHolder objectOne = (OfflineFileInformationHolder) first;
		OfflineFileInformationHolder objectTwo = (OfflineFileInformationHolder) second;

		String extenstionOne = null;
		String extenstionTwo = null;
		String fileNameOne = objectOne.getItemName();
		String fileNameTwo = objectTwo.getItemName();
		Integer typeOne = -1, typeTwo = -1;
		if (objectOne.isDirectory()) {
			typeOne = TYPE_DIRECTORY;
			extenstionOne = "";
		} else {
			try {
				extenstionOne = "."
						+ fileNameOne.substring(
								fileNameOne.lastIndexOf(".") + 1,
								fileNameOne.length()).toLowerCase();
			} catch (Exception e) {
				extenstionOne = "";
			}
			typeOne = findTypeByExtension(extenstionOne);
		}
		if (objectTwo.isDirectory()) {
			typeTwo = TYPE_DIRECTORY;
			extenstionTwo = "";
		} else {
			try {
				extenstionTwo = "."
						+ fileNameTwo.substring(
								fileNameTwo.lastIndexOf(".") + 1,
								fileNameTwo.length()).toLowerCase();
				typeTwo = findTypeByExtension(extenstionTwo);
			} catch (Exception e) {
				extenstionTwo = "";
			}
		}
		int typeCompare = typeOne.compareTo(typeTwo);
		if (typeCompare == 0) {
			if (null != extenstionOne && null != extenstionTwo) {
				int extensionCompare = extenstionOne
						.compareToIgnoreCase(extenstionTwo);
				if (extensionCompare == 0) {
					if (null != fileNameOne && null != fileNameTwo) {
						return fileNameOne.compareToIgnoreCase(fileNameTwo);
					} else {
						return 0;
					}
				} else {
					return extensionCompare;
				}
			} else {
				return 0;
			}
		} else {
			return typeCompare;
		}
	}

	/**
	 * Find type by extension. return type as document ,video ,picture music
	 * etc.
	 * 
	 * @param itemExtension
	 *            the item extension
	 * @return the int
	 */
	public int findTypeByExtension(String itemExtension) {
		if (null == itemExtension || itemExtension.isEmpty()) {
			return TYPE_UNKNOWN;
		}
		return UtilityClass.getFileTypeFromExtention(itemExtension);
	}

}
