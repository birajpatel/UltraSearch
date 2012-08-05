package com.patelbiraj.UltraSearch.POJO;

import java.io.File;

public class OfflineFileInformationHolder implements InformationHolder {


	/** The file obj. */
	File mFileObj = null;

	/**
	 * Instantiates a new information holder.
	 * 
	 * @param file
	 *            the file
	 */
	public OfflineFileInformationHolder(File file) {
		mFileObj = file;
	}

	/**
	 * Gets the file object associated with this information holder.
	 * 
	 * @return the file object
	 */
	public File getFileObject() {
		return mFileObj;
	}

	/**
	 * Checks if this information holder is directory.
	 * 
	 * @return true, if is directory
	 */
	public boolean isDirectory() {
		if (null != mFileObj) {
			return mFileObj.isDirectory();
		}
		return false;
	}

	/**
	 * Gets the item name.
	 * 
	 * @return the item name
	 */
	public String getItemName() {
		if (null != mFileObj) {
			return mFileObj.getName();
		}
		return "";
	}

	/**
	 * Gets the item absolute location.
	 * 
	 * @return the item absolute location
	 */
	public String getItemAbsoluteLocation() {
		if (null != mFileObj) {
			return mFileObj.getAbsolutePath();
		}
		return "";
	}

	/**
	 * Gets the item parent path.
	 * 
	 * @return the item parent path
	 */
	public String getItemParentPath() {
		if (null != mFileObj) {
			return mFileObj.getParent();
		}
		return "";
	}

}
