package com.patelbiraj.UltraSearch.Comparators;

import java.io.File;
import java.util.Comparator;

import com.patelbiraj.UltraSearch.POJO.InformationHolder;
import com.patelbiraj.UltraSearch.POJO.OfflineFileInformationHolder;
import com.patelbiraj.UltraSearch.Utils.LogUtils;

/**
 * The Class SortBySize implements Comparator interface and is used to sort by
 * size the collection of InformationHolder.
 */
public class SortBySize implements Comparator<InformationHolder> {

	private String TAG = "SortBySize";

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
		if (null != fileInformationHolderObjectOne
				&& null != fileInformationHolderObjectTwo) {
			File file1 = fileInformationHolderObjectOne.getFileObject();
			File file2 = fileInformationHolderObjectTwo.getFileObject();
			if (null == file1 || null == file2) {
				LogUtils.i(TAG, "file Objects null return 0");
				return 0;
			}
			Long size1 = 0L, size2 = 0L;
			if (file1.isDirectory()) {
				if (null != file1.list()) {
					size1 = new Long(file1.list().length);
				}
			} else {
				size1 = file1.length();
			}
			if (file2.isDirectory()) {
				if (null != file2.list()) {
					size2 = new Long(file2.list().length);
				}
			} else {
				size2 = file2.length();
			}
			return size1.compareTo(size2);
		} else {
			LogUtils.i(TAG, "fileInformationHolderObjects null return 0");
		}
		return 0;

	}
}
