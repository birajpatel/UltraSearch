package com.patelbiraj.ultrasearch.Utils;


import static com.patelbiraj.ultrasearch.Utils.Constants.*;
import static com.patelbiraj.ultrasearch.Utils.Constants.FileFormatConstants.*;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class UtilityClass.
 */
public class UtilityClass {

	/** The tag. */
	private static String TAG = "UtilityClass";

	/**
	 * Gets the file type from extention.
	 *
	 * @param itemExtension the item extension
	 * @return the file type from extention
	 */
	public static final int getFileTypeFromExtention(String itemExtension) {
		if (null == itemExtension || itemExtension.isEmpty()) {
			LogUtils.i(TAG,
					"extension is null in getFileTypeFromExtention returnin unknown fileformat");
			return TYPE_UNKNOWN;
		}
		if (itemExtension.equalsIgnoreCase(FORMAT_PDF)) {
			return TYPE_PDF;
		} else if (itemExtension.equalsIgnoreCase(FORMAT_ZIP)) {
			return TYPE_ZIP;
		} else if (itemExtension.equalsIgnoreCase(FORMAT_MP3)
				|| itemExtension.equalsIgnoreCase(FORMAT_M4A)
				|| itemExtension.equalsIgnoreCase(FORMAT_AAC)
				|| itemExtension.equalsIgnoreCase(FORMAT_M2A)
				|| itemExtension.equalsIgnoreCase(FORMAT_MP2)
				|| itemExtension.equalsIgnoreCase(FORMAT_AMR)
				|| itemExtension.equalsIgnoreCase(FORMAT_AMV)
				|| itemExtension.equalsIgnoreCase(FORMAT_AC3)
				|| itemExtension.equalsIgnoreCase(FORMAT_WAV)
				|| itemExtension.equalsIgnoreCase(FORMAT_WMA)) {
			return TYPE_MUSIC;
		} else if (itemExtension.equalsIgnoreCase(FORMAT_M4V)
				|| itemExtension.equalsIgnoreCase(FORMAT_3GP)
				|| itemExtension.equalsIgnoreCase(FORMAT_WMV)
				|| itemExtension.equalsIgnoreCase(FORMAT_MP4)
				|| itemExtension.equalsIgnoreCase(FORMAT_OGG)
				|| itemExtension.equalsIgnoreCase(FORMAT_M1V)
				|| itemExtension.equalsIgnoreCase(FORMAT_M2V)
				|| itemExtension.equalsIgnoreCase(FORMAT_M4E)
				|| itemExtension.equalsIgnoreCase(FORMAT_DV)
				|| itemExtension.equalsIgnoreCase(FORMAT_F4V)
				|| itemExtension.equalsIgnoreCase(FORMAT_FLV)
				|| itemExtension.equalsIgnoreCase(FORMAT_RV)
				|| itemExtension.equalsIgnoreCase(FORMAT_RM)
				|| itemExtension.equalsIgnoreCase(FORMAT_RAM)
				|| itemExtension.equalsIgnoreCase(FORMAT_MP2V)) {
			return TYPE_VIDEO;
		} else if (itemExtension.equalsIgnoreCase(FORMAT_JPEG)
				|| itemExtension.equalsIgnoreCase(FORMAT_JPG)
				|| itemExtension.equalsIgnoreCase(FORMAT_PNG)
				|| itemExtension.equalsIgnoreCase(FORMAT_GIF)
				|| itemExtension.equalsIgnoreCase(FORMAT_TIFF)
				|| itemExtension.equalsIgnoreCase(FORMAT_BMP)) {
			return TYPE_PICTURE;
		} else if (itemExtension.equalsIgnoreCase(FORMAT_DOC)
				|| itemExtension.equalsIgnoreCase(FORMAT_DOCX)) {
			return TYPE_DOC;
		} else if (itemExtension.equalsIgnoreCase(FORMAT_XLS)
				|| itemExtension.equalsIgnoreCase(FORMAT_XLSX)) {
			return TYPE_XLS;
		} else if (itemExtension.equalsIgnoreCase(FORMAT_PPT)
				|| itemExtension.equalsIgnoreCase(FORMAT_PPTX)) {
			return TYPE_PPT;
		} else if (itemExtension.equalsIgnoreCase(FORMAT_APK)) {
			return TYPE_APK;
		} else if (itemExtension.equalsIgnoreCase(FORMAT_TEXT)) {
			return TYPE_TEXT;
		} else {
			return TYPE_UNKNOWN;
		}
	}

	/**
	 * Gets the extension list as per search category like pix,videos,music.
	 * 
	 * @param searchCategory
	 *            the search category
	 * @return the extension list
	 */
	public static final ArrayList<String> getExtensionList(int searchCategory) {
		if (searchCategory == SEARCH_CATEGORY_DOCUMENTS) {
			return selectedDocFormats();
		}
		if (searchCategory == SEARCH_CATEGORY_PICTURES) {
			return selectedPixFormats();
		}
		if (searchCategory == SEARCH_CATEGORY_MUSIC) {
			return selectedMusicFormats();
		}
		if (searchCategory == SEARCH_CATEGORY_VIDEOS) {
			return selectedVideoFormats();
		}
		return null;
	}

	/**
	 * Selected video formats.
	 *
	 * @return the array list
	 */
	private static ArrayList<String> selectedVideoFormats() {
		ArrayList<String> extensionList = new ArrayList<String>();
		for (String extension : VIDEO_FORMATS) {
			if (/* selection criteria */true) {
				extensionList.add(extension);
			}
		}
		return extensionList;
	}

	/**
	 * Selected music formats.
	 *
	 * @return the array list
	 */
	private static ArrayList<String> selectedMusicFormats() {
		ArrayList<String> extensionList = new ArrayList<String>();
		for (String extension : MUSIC_FORMATS) {
			if (/* selection criteria */true) {
				extensionList.add(extension);
			}
		}
		return extensionList;
	}

	/**
	 * Selected pix formats.
	 *
	 * @return the array list
	 */
	private static ArrayList<String> selectedPixFormats() {
		ArrayList<String> extensionList = new ArrayList<String>();
		for (String extension : PIX_FORMATS) {
			if (/* selection criteria */true) {
				extensionList.add(extension);
			}
		}
		return extensionList;
	}

	/**
	 * Selected doc formats.
	 *
	 * @return the array list
	 */
	private static ArrayList<String> selectedDocFormats() {
		ArrayList<String> extensionList = new ArrayList<String>();
		for (String extension : DOC_FORMATS) {
			if (/* selection criteria */true) {
				extensionList.add(extension);
			}
		}
		return extensionList;
	}

}
