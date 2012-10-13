/*
 * 
 */
package com.patelbiraj.ultrasearch.utils;

import android.net.Uri;

/**
 * The Class Constants for all the constant values in the application.
 */
public class Constants {

	/** The Constant MNT_FOLDER. */
	public static final String MNT_FOLDER = "/mnt/";

	/**
	 * The Class FileFormatConstants.
	 */
	public static class FileFormatConstants {

		public static final String FORMAT_HTML = ".html";
		public static final String FORMAT_HTM = ".htm";
		public static final String FORMAT_XML = ".xml";
		public static final String FORMAT_PDF = ".pdf";
		public static final String FORMAT_ZIP = ".zip";
		public static final String FORMAT_MP3 = ".mp3";
		public static final String FORMAT_MP2 = ".mp2";
		public static final String FORMAT_M4A = ".m4a";
		public static final String FORMAT_AAC = ".aac";
		public static final String FORMAT_AMR = ".amr";
		public static final String FORMAT_AMV = ".amv";
		public static final String FORMAT_AC3 = ".ac3";
		public static final String FORMAT_WAV = ".wav";
		public static final String FORMAT_M2A = ".m2a";
		public static final String FORMAT_WMA = ".wma";
		public static final String FORMAT_M4V = ".m4v";
		public static final String FORMAT_3GP = ".3gp";
		public static final String FORMAT_WMV = ".wmv";
		public static final String FORMAT_MP4 = ".mp4";
		public static final String FORMAT_OGG = ".ogg";
		public static final String FORMAT_M1V = ".m1v";
		public static final String FORMAT_M2V = ".m2v";
		public static final String FORMAT_DV = ".dv";
		public static final String FORMAT_F4V = ".f4v";
		public static final String FORMAT_FLV = ".flv";
		public static final String FORMAT_RV = ".rv";
		public static final String FORMAT_M4E = ".m4e";
		public static final String FORMAT_RM = ".rm";
		public static final String FORMAT_RAM = ".ram";
		public static final String FORMAT_MP2V = ".mp2v";
		public static final String FORMAT_JPEG = ".jpeg";
		public static final String FORMAT_JPG = ".jpg";
		public static final String FORMAT_PNG = ".png";
		public static final String FORMAT_GIF = ".gif";
		public static final String FORMAT_TIFF = ".tiff";
		public static final String FORMAT_BMP = ".bmp";
		public static final String FORMAT_DOC = ".doc";
		public static final String FORMAT_DOCX = ".docx";
		public static final String FORMAT_XLS = ".xls";
		public static final String FORMAT_XLSX = ".xlsx";
		public static final String FORMAT_PPT = ".ppt";
		public static final String FORMAT_PPTX = ".pptx";
		public static final String FORMAT_APK = ".apk";
		public static final String FORMAT_TEXT = ".txt";

		public static final int TYPE_DIRECTORY = 0;
		public static final int TYPE_MUSIC = 1;
		public static final int TYPE_VIDEO = 2;
		public static final int TYPE_PICTURE = 3;
		public static final int TYPE_APK = 4;
		public static final int TYPE_ZIP = 5;
		public static final int TYPE_PDF = 6;
		public static final int TYPE_DOC = 7;
		public static final int TYPE_XLS = 8;
		public static final int TYPE_PPT = 9;
		public static final int TYPE_TEXT = 10;
		public static final int TYPE_UNKNOWN = 11;

		/** The Constant DOC_FORMATS. */
		public static final String[] DOC_FORMATS = { FORMAT_PDF, FORMAT_DOC,
				FORMAT_DOCX, FORMAT_XLS, FORMAT_XLSX, FORMAT_PPT, FORMAT_PPTX,
				FORMAT_TEXT, FORMAT_HTM, FORMAT_HTML, FORMAT_XML };

		/** The Constant PIX_FORMATS. */
		public static final String[] PIX_FORMATS = { FORMAT_JPEG, FORMAT_JPG,
				FORMAT_PNG, FORMAT_GIF, FORMAT_TIFF, FORMAT_BMP };

		/** The Constant MUSIC_FORMATS. */
		public static final String[] MUSIC_FORMATS = { FORMAT_MP3, FORMAT_M4A,
				FORMAT_AAC, FORMAT_M2A, FORMAT_MP2, FORMAT_AMR, FORMAT_AMV,
				FORMAT_AC3, FORMAT_WAV, FORMAT_WMA };

		/** The Constant VIDEO_FORMATS. */
		public static final String[] VIDEO_FORMATS = { FORMAT_M4V, FORMAT_3GP,
				FORMAT_WMV, FORMAT_MP4, FORMAT_OGG, FORMAT_M1V, FORMAT_M2V,
				FORMAT_M4E, FORMAT_DV, FORMAT_F4V, FORMAT_FLV, FORMAT_RV,
				FORMAT_RM, FORMAT_RAM, FORMAT_MP2V };

		public static final int SEARCH_CATEGORY_DIRECTORY = 0;
		public static final int SEARCH_CATEGORY_MUSIC = 1;
		public static final int SEARCH_CATEGORY_VIDEOS = 2;
		public static final int SEARCH_CATEGORY_PICTURES = 3;
		public static final int SEARCH_CATEGORY_DOCUMENTS = 4;
		public static final int SEARCH_CATEGORY_OTHERS = 5;

	}

	/**
	 * The Class ButtonConstants.
	 */
	public static class ButtonConstants {

		public static final int BUTTON_PICTURES = 0;

		public static final int BUTTON_MUSIC = 1;

		public static final int BUTTON_VIDEOS = 2;

		public static final int BUTTON_DOCUMENTS = 3;

		public static final int BUTTON_USER_CHOICE = 4;

	}

	/**
	 * The Class ContentProviderConstants.
	 */
	public static final class ContentProviderConstants {
		public static final String DATABASE_NAME = "UltraSearchDatabase";
		public static final int DATABASE_VERSION = 1;
		public static final String CONTENT_TYPE = "vnd.android.cursor.item";
		public static final String INDEX = "_id";
		public static final int CHECKED = 1;
		public static final int UNCHECKED = 0;
		public static final int FIRST_ROW = 1;
		public static final int NO_ROWS = 0;

		public static final class FormatTableConstants {
			public static final String MEDIA_FORMAT_PREF_TABLE_NAME = "MediaFormatPreferences";
			public static final String USER_PREF_TABLE_NAME = "UserFormatPreferences";
			public static final String COLUMN_FORMAT_SELECTION = "FormatSelection";
			public static final String COLUMN_FORMAT_NAME = "FormatName";
		}

		public static final class TableUris {

			public static final String SCHEME = "content://";
			public static final String AUTHORITY = "com.patelbiraj.ultrasearch.contentprovider.UltraSearchContentProvider";

			public static final Uri MEDIA_FORMAT_CONTENT_URI = Uri.parse(SCHEME
					+ AUTHORITY + "/"
					+ FormatTableConstants.MEDIA_FORMAT_PREF_TABLE_NAME);

			public static final Uri USER_PREF_CONTENT_URI = Uri.parse(SCHEME
					+ AUTHORITY + "/"
					+ FormatTableConstants.USER_PREF_TABLE_NAME);

			public static final int MEDIA_FORMAT_TABLE_CODE = 0;
			public static final int USER_PREF_TABLE_CODE = 1;

		}

	}

}