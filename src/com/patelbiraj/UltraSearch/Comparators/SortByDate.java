package com.patelbiraj.UltraSearch.Comparators;

import java.io.File;
import java.util.Comparator;

import com.patelbiraj.UltraSearch.POJO.InformationHolder;
import com.patelbiraj.UltraSearch.POJO.OfflineFileInformationHolder;
import com.patelbiraj.UltraSearch.Utils.LogUtils;

/**
 * The Class SortByDate. sorts informationHolder by date.
 */
public class SortByDate implements Comparator<InformationHolder> {

	private String TAG = "SortByDate";

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(InformationHolder informationHolderObjectOne,
			InformationHolder informationHolderObjectTwo) {
		if (null == informationHolderObjectOne
				|| null == informationHolderObjectTwo) {
			return 0;
		}
		OfflineFileInformationHolder fileInformationHolderObjectOne = null;
		OfflineFileInformationHolder fileInformationHolderObjectTwo = null;
		if (informationHolderObjectOne instanceof OfflineFileInformationHolder) {
			fileInformationHolderObjectOne = (OfflineFileInformationHolder) informationHolderObjectOne;
		}
		if (informationHolderObjectTwo instanceof OfflineFileInformationHolder) {
			fileInformationHolderObjectTwo = (OfflineFileInformationHolder) informationHolderObjectTwo;
		}
		if (null != fileInformationHolderObjectTwo
				&& null != fileInformationHolderObjectOne) {
			File file1 = fileInformationHolderObjectOne.getFileObject();
			File file2 = fileInformationHolderObjectTwo.getFileObject();
			if (null != file1 && null != file2) {
				return new Long(file1.lastModified()).compareTo(new Long(file2
						.lastModified()));
			} else {
				LogUtils.i(TAG, "File obj null returning 0");
				return 0;
			}

		} else {
			LogUtils.e(TAG, "fileInformationHolderObjectOne is null? "
					+ ((fileInformationHolderObjectOne) == null));
			LogUtils.e(TAG, "fileInformationHolderObjectTwo is null? "
					+ (fileInformationHolderObjectTwo == null));
			return 0;
		}
	}

}
