package com.mpc.iso.tcpip;

import java.io.IOException;
import java.net.ServerSocket;

import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.ASCIIChannel;

/***
 * @author yovi.putra
 * @date Sep 23, 2018
 */
public class AsciiChannel extends ASCIIChannel implements Channel{
	private boolean disablePort = false;
	
	public AsciiChannel() {}
	
	public static AsciiChannel getInstantce() {
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
}
