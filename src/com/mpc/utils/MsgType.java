package com.mpc.utils;

public class MsgType {
	public static final int NETWORK_REQUEST 			= 800;
	public static final int NETWORK_RESPONSE			= 810;
	
	public static final int FINANCIAL_REQUEST 			= 200;
	public static final int FINANCIAL_RESPONSE			= 210;
	public static final int FINANCIAL_ADVICE_REQUEST	= 220;
	public static final int FINANCIAL_ADVICE_RESPONSE	= 230;
	
	public static final int REVERSAL_REQUEST			= 400;
	public static final int REVERSAL_RESPONSE			= 410;
	public static final int REVERSAL_ADVICE_REQUEST		= 420;
	public static final int REVERSAL_ADVICE_RESPONSE	= 430;
	
	public static boolean isNetwork(int mti){
		return mti / 100 == 4;
	}
	
	public static boolean isReversal(int mti){
		return mti / 100 == 4;
	}
	
	public static boolean isFinancial(int mti){
		return mti == FINANCIAL_REQUEST || mti == FINANCIAL_RESPONSE;
	}
	
	public static boolean isNotif(int mti){
		return mti == FINANCIAL_ADVICE_REQUEST || mti == FINANCIAL_ADVICE_RESPONSE;
	}
}
