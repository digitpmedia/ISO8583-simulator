package com.mpc.iso.services;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOPackager;

import com.mpc.iso.creational.ChannelHeader;

/***
 * @author yovi.putra
 * @date Sep 26, 2018
 */
public interface iChannel extends ISOChannel {
	
	iChannel Client(String channelName, String host, int port, ISOPackager packager, boolean disablePort);
	
	iChannel Server(String channelName, int port, ISOPackager packager, boolean disablePort);
	
	void setHeaderConfiguration(ChannelHeader hedearConfiguration);
	
	boolean isDisablePort();
}