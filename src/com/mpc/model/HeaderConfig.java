package com.mpc.model;

/**
 * @author yovi.putra
 *
 */
public class HeaderConfig {
	public static String TAG_LEN = "_LEN_MSG_";
	
	public static enum HEADER_TYPE{
		ASCII, HEX
	}
	
	private String startValue;
	private String middleValue;
	private String endValue;
	private HEADER_TYPE headerType;
	
	public String getStartValue() {
		return startValue == null ? "" : startValue;
	}
	public void setStartValue(String startValue) {
		this.startValue = startValue;
	}
	public String getMiddleValue() {
		return middleValue == null ? "" : middleValue;
	}
	public void setMiddleValue(String middleValue) {
		this.middleValue = middleValue;
	}
	public String getEndValue() {
		return endValue == null ? "" : endValue;
	}
	public void setEndValue(String endValue) {
		this.endValue = endValue;
	}
	public HEADER_TYPE getHeaderType() {
		return headerType;
	}
	public void setHeaderType(HEADER_TYPE headerType) {
		this.headerType = headerType;
	}
	@Override
	public String toString() {
		return startValue+middleValue+endValue;
	}
}
