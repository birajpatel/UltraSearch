package com.patelbiraj.UltraSearch.POJO;

public class SearchPreference {

	private String searchString = "";

	/**
	 * The is document marked variable to know if documents needs to be searched
	 * or not.
	 */
	private boolean isDocumentMarked = true;

	private boolean isMusicMarked = true;

	private boolean isPictureMarked = true;

	private boolean isVideoMarked = true;

	private boolean isOthersMarked = true;

	public boolean isOthersMarked() {
		return isOthersMarked;
	}

	public void setOthersMarked(boolean isOthersMarked) {
		this.isOthersMarked = isOthersMarked;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public boolean isDocumentMarked() {
		return isDocumentMarked;
	}

	public void setDocumentMarked(boolean isDocumentMarked) {
		this.isDocumentMarked = isDocumentMarked;
	}

	public boolean isMusicMarked() {
		return isMusicMarked;
	}

	public void setMusicMarked(boolean isMusicMarked) {
		this.isMusicMarked = isMusicMarked;
	}

	public boolean isPictureMarked() {
		return isPictureMarked;
	}

	public void setPictureMarked(boolean isPictureMarked) {
		this.isPictureMarked = isPictureMarked;
	}

	public boolean isVideoMarked() {
		return isVideoMarked;
	}

	public void setVideoMarked(boolean isVideoMarked) {
		this.isVideoMarked = isVideoMarked;
	}

}
