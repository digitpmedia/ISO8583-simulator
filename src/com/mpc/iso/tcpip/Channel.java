package com.mpc.iso.tcpip;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOPackager;

public interface Channel extends ISOChannel {
	
	Channel Client(String channelName, String host, int port, ISOPackager packager, boolean disablePort);
	
	Channel Server(String channelName, int port, ISOPackager packager, boolean disablePort);
	
	void setHeaderConfiguration(HeaderConfiguration hedearConfiguration);
	
	boolean isDisablePort();
}
