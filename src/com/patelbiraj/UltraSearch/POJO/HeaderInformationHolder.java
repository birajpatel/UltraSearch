package com.patelbiraj.UltraSearch.POJO;


/**
 * The Class HeaderInformationHolder.
 */
public class HeaderInformationHolder implements InformationHolder {

	/** The header name. */
	String mHeaderName = null;

	/**
	 * Instantiates a new header information holder.
	 *
	 * @param headerName the header name
	 */
	public HeaderInformationHolder(String headerName) {
		mHeaderName = headerName;
	}

	/* (non-Javadoc)
	 * @see com.patelbiraj.UltraSearch.POJO.InformationHolder#getItemName()
	 */
	@Override
	public String getItemName() {
		return mHeaderName;
	}

}
