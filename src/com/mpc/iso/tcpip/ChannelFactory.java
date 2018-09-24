package com.mpc.iso.tcpip;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOChannel;

import com.mpc.iso.Gateway;
import com.mpc.iso.ISOLog;
import com.mpc.iso.ISOMux;

public class ChannelFactory {
	
	public static void createInstance(Channel channel) {
		BaseChannel ch = getBaseChannel((ISOChannel) channel);
		ISOLog isoLog = new ISOLog("config/log4J.properties");
		ISOMux mux = new ISOMux(ch,isoLog);
		new Gateway(mux,channel.isDisablePort());
	}
	
	public static BaseChannel getBaseChannel(ISOChannel channel) {
		BaseChannel ch = (BaseChannel) channel;
		return ch;
	}
}
