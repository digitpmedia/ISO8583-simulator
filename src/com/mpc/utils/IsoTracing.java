package com.mpc.utils;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;


public class IsoTracing {
	static Logger log = Logger.getLogger(IsoTracing.class);
	
	public static void printRowMessage(String channel,ISOMsg isomsg, boolean in){
		byte[] data = null;
		try {
			data = isomsg.pack();
		} catch (ISOException e) {
			e.printStackTrace();
		}
		String sData   = new String(data);
		String inbound = in ? "In" : "Out";
		try {
			String rc = "--", fid="";
			if(isomsg.hasField(39)) rc = isomsg.getString(39);
			if(!isomsg.hasField(33)) fid="117";
			log.info("["+StringUtils.padLeft(channel," ",12) + "]" 
					+ StringUtils.padLeft(inbound, " ", 3)+" : " 
					+ "m"+StringUtils.padLeft(isomsg.getMTI(),"0",4) 
					+ "/p"+ StringUtils.padLeft(isomsg.getString(3),"0",6)
					+ "/t"+ StringUtils.padLeft(isomsg.getString(11),"0",6)
					+ "/mt"+ StringUtils.padLeft(isomsg.getString(18),"0",4)
					+ "/c"+ StringUtils.padRight(isomsg.getString(41)," ",8)
					+ "/a"+ StringUtils.padLeft(isomsg.getString(32)," ",6)
					+ "/f"+ StringUtils.padLeft(fid," ",6)
					+ "/r" + StringUtils.padLeft(rc,"0",2));
		} catch (ISOException e) {}
		log.trace("Raw message : \nLen: ["+ sData.length()+ "]\nMsg: ["+ sData+ "]");
	}
}
