package com.mpc.iso.services.impl;

import java.io.IOException;
import java.net.ServerSocket;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;

import com.mpc.iso.creational.ChannelHeader;
import com.mpc.iso.services.iChannel;

public class Channel extends BaseChannel implements iChannel{
	private boolean disablePort = false;
	private ChannelHeader headerConfiguration;
	
	private Channel() {}
	
	public static Channel Builder() {
		return new Channel();
	}
	
	private Channel(String channelName, String host, int port, ISOPackager packager) {
		super(host, port, packager);
		this.setName(channelName);
	}
	
	private Channel(String channelName, ServerSocket serverSocket, ISOPackager packager) throws IOException {
		super(packager, serverSocket);
		this.setName(channelName);
	}
	
	@Override
	public iChannel Client(String channelName, String host, int port, ISOPackager packager, boolean disablePort) {
		this.disablePort = disablePort;
		if(!disablePort){
			return new Channel(channelName,host,port,packager);
		}
        return new Channel();
	}

	@Override
	public iChannel Server(String channelName, int port, ISOPackager packager, boolean disablePort) {
		this.disablePort = disablePort;
		if(!disablePort){
			try {
				return new Channel(channelName,new ServerSocket(port),packager);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return new Channel();
	}

	@Override
	public boolean isDisablePort() {
		return disablePort;
	}

	@Override
	public void setHeaderConfiguration(ChannelHeader hedearConfiguration) {
		this.headerConfiguration = hedearConfiguration;
	}
	
	@Override
	protected void sendMessageLength(int len) throws IOException {
		if(headerConfiguration != null) {
			try {
				serverOut.write(headerConfiguration.setHeaderMessageLength(len));
			} catch (ISOException e) {
				e.printStackTrace();
			}
		}else {
			super.sendMessageLength(len);
		}
	}
	
	@Override
	protected int getMessageLength() throws IOException, ISOException {
		if(headerConfiguration != null) {
			return headerConfiguration.getHeaderMessageLength(serverIn);
		}else {
			return super.getMessageLength();
		}
	}
}
