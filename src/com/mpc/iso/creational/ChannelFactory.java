package com.mpc.iso.creational;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOChannel;

import com.mpc.iso.ISOLog;
import com.mpc.iso.ISOMux;
import com.mpc.iso.services.iChannel;
import com.mpc.iso.services.impl.Gateway;

/***
 * @author yovi.putra
 * @date Sep 26, 2018
 */
public class ChannelFactory {
	
	public static ISOMux createInstance(iChannel channel) {
		BaseChannel ch = getBaseChannel((ISOChannel) channel);
		ISOLog isoLog = new ISOLog("config/log4J.properties");
		ISOMux mux = new ISOMux(ch,isoLog);
		new Gateway(mux,channel.isDisablePort());
		return mux;
	}
	
	public static BaseChannel getBaseChannel(ISOChannel channel) {
		BaseChannel ch = (BaseChannel) channel;
		return ch;
	}
}
