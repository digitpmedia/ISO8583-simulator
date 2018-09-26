package com.mpc.iso.tcpip;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOChannel;

import com.mpc.iso.Gateway;
import com.mpc.iso.ISOLog;
import com.mpc.iso.ISOMux;

/***
 * @author yovi.putra
 * @date Sep 26, 2018
 */
public class ChannelFactory {
	
	public static ISOMux createInstance(Channel channel) {
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
