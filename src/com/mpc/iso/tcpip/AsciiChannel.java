package com.mpc.iso.tcpip;

import java.io.IOException;
import java.net.ServerSocket;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.ASCIIChannel;

/***
 * @author yovi.putra
 * @date Sep 23, 2018
 */
public class AsciiChannel extends ASCIIChannel implements Channel{
	private boolean disablePort = false;
	private HeaderConfiguration headerConfiguration;
	
	private AsciiChannel() {}
	
	public static AsciiChannel Builder() {
		return new AsciiChannel();
	}
	
	private AsciiChannel(String channelName, String host, int port, ISOPackager packager) {
		super(host, port, packager);
		this.setName(channelName);
	}
	
	private AsciiChannel(String channelName, ServerSocket serverSocket, ISOPackager packager) throws IOException {
		super(packager, serverSocket);
		this.setName(channelName);
	}
	
	@Override
	public Channel Client(String channelName, String host, int port, ISOPackager packager, boolean disablePort) {
		this.disablePort = disablePort;
		if(!disablePort){
			return new AsciiChannel(channelName,host,port,packager);
		}
        return new AsciiChannel();
	}

	@Override
	public Channel Server(String channelName, int port, ISOPackager packager, boolean disablePort) {
		this.disablePort = disablePort;
		if(!disablePort){
			try {
				return new AsciiChannel(channelName,new ServerSocket(port),packager);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return new AsciiChannel();
	}

	@Override
	public boolean isDisablePort() {
		return disablePort;
	}

	@Override
	public void setHeaderConfiguration(HeaderConfiguration hedearConfiguration) {
		this.headerConfiguration = hedearConfiguration;
	}
	
	@Override
	protected void sendMessageLength(int len) throws IOException {
		if(headerConfiguration != null) {
			serverOut.write(headerConfiguration.sendMessageLength(len));
		}else {
			super.sendMessageLength(len);
		}
	}
	
	@Override
	protected int getMessageLength() throws IOException, ISOException {
		if(headerConfiguration != null) {
			return headerConfiguration.getMessageLength(serverIn);
		}else {
			return super.getMessageLength();
		}
	}

}
