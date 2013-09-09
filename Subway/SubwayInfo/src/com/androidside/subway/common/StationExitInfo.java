package com.androidside.subway.common;

public class StationExitInfo {
	private String strExitNumber;
	private String strExitInfo;

	/**
	 * @param strExitNumber
	 * @param strExitInfo
	 */
	public StationExitInfo(String strExitNumber, String strExitInfo) {
		super();
		this.strExitNumber = strExitNumber;
		this.strExitInfo = strExitInfo;
	}

	public String getStrExitNumber() {
		return strExitNumber;
	}

	public String getStrExitInfo() {
		return strExitInfo;
	}
}