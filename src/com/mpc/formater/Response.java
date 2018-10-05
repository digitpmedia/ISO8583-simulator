package com.mpc.formater;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;

import com.mpc.utils.RcUtils;

public class Response {
	private static Logger log = Logger.getLogger(Response.class);
	private static final String sperator = "`````";
	
	public static void searching(ISOMsg isomsg){
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(new File("data/data_response.vsim"));
			prop.load(is);
			for(Object obj : prop.keySet()) {
				String name = (String) obj;
				String data = prop.get(name).toString();
				composeResponse(isomsg, data);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private static void composeResponse(ISOMsg isomsg, String value) {
		value= value.replace("|", sperator);
		String sData[] = value.split(sperator);
		int len = sData.length;
		
		try {
			if(len == 2 && isMatch(isomsg, sData[0])) {
				setISO(isomsg, sData[1]);
				return;
			}else {
				isomsg.set(39,RcUtils.APPROVED);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/***
	 * 
	 * @param isomsg
	 * @param conditions
	 * @return
	 * example condition:3:0:6:301100;2:1010000105
	 */
	private static boolean isMatch(ISOMsg isomsg, String conditions) {
		//split
		String sConditios[] = conditions.split(";");
		for(String condition : sConditios) {
			if(!isMatchCondition(isomsg, condition)) {
				return false;
			}
		}
		return true;
	}
	
	/***
	 * @param isomsg
	 * @param value
	 * @return
	 * 
	 * Example condition value: 
	 *  - 3:0:6:301100 (substring 0-6)
	 *  - 2:1010000105 (set full)
	 */
	private static boolean isMatchCondition(ISOMsg isomsg,String value) {
		String sValue[] = value.split(":");
		int len = sValue.length;
		int bit = Integer.parseInt(sValue[0]);
		
		if(isValidBit(bit)) {
			if(len == 2) {
				String val = isomsg.getString(bit);
				String valCondition = sValue[1];
				return val.equals(valCondition);
			}else if(len == 4) {
				int start = Integer.parseInt(sValue[1]);
				int end = Integer.parseInt(sValue[2]);
				
				String val = isomsg.getString(bit);
				val = val.substring(start, end);
				String valCondition = sValue[3];
				return val.equals(valCondition);
			}
		}
		return false;
	}
	
	private static boolean isValidBit(int bit) {
		if(bit < 0 || bit > 128)
			return false;
		else 
			return true;
	}
	
	private static void setISO(ISOMsg isomsg,String value) throws Exception {
		if(value != null) {
			String sValue[] = value.split(";");
			for(String bitData : sValue) {
				setBit(isomsg, bitData);
			}
		}
	}

	private static void setBit(ISOMsg isomsg,String bitData) throws Exception {
		String sBitData[] = bitData.split(":");
		String bit = sBitData[0];
		String val = sBitData[1];
		isomsg.set(Integer.parseInt(bit),val);
	}
}
