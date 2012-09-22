package com.patelbiraj.ultrasearch.comparators;

import java.util.Comparator;

import com.patelbiraj.ultrasearch.pojo.InformationHolder;
import com.patelbiraj.ultrasearch.pojo.OfflineFileInformationHolder;

/**
 * The Class SortByName implements comparator interface.this class is used to
 * sort in-case sensitive alphabetically.
 */
public class SortByName implements Comparator<InformationHolder> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(InformationHolder object1, InformationHolder object2) {
		if (null == object1 || null == object2) {
			return 0;
		}
		OfflineFileInformationHolder obj1 = null;
		OfflineFileInformationHolder obj2 = null;
		if (object1 instanceof OfflineFileInformationHolder) {
			obj1 = (OfflineFileInformationHolder) object1;
		}
		if (object2 instanceof OfflineFileInformationHolder) {
			obj2 = (OfflineFileInformationHolder) object2;
		}
		if (null != obj1 && null != obj2) {
			String firstFileName = obj1.getItemName();
			String secondFileName = obj2.getItemName();
			if (null != firstFileName && null != secondFileName) {
				return firstFileName.compareToIgnoreCase(secondFileName);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
}
