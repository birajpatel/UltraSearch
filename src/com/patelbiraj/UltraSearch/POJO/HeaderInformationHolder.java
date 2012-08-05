package com.patelbiraj.UltraSearch.POJO;

public class HeaderInformationHolder implements InformationHolder {

	String mHeaderName = null;

	public HeaderInformationHolder(String headerName) {
		mHeaderName = headerName;
	}

	@Override
	public String getItemName() {
		return mHeaderName;
	}

}
