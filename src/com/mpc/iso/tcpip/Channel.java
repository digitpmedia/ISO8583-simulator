package com.mpc.iso.tcpip;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOPackager;

/***
 * @author yovi.putra
 * @date Sep 26, 2018
 */
public interface Channel extends ISOChannel {
	
	Channel Client(String channelName, String host, int port, ISOPackager packager, boolean disablePort);
	
	Channel Server(String channelName, int port, ISOPackager packager, boolean disablePort);
	
	void setHeaderConfiguration(ChannelHeader hedearConfiguration);
	
	boolean isDisablePort();
}