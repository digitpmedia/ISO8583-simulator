package com.mpc.iso.tcpip;

import java.io.IOException;
import java.net.ServerSocket;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.HEXChannel;

public class HexChannel extends HEXChannel implements Channel{
	private HeaderConfiguration headerConfiguration;
	private boolean disablePort;
	
	private HexChannel() {}

	public static HexChannel Builder() {
		return new HexChannel();
	}
	
	private HexChannel(String channelName,
			ISOPackager packager, 
			byte[] TPDU,
			ServerSocket serverSocket) throws IOException {
		super(packager, TPDU, serverSocket);
		this.setName(channelName);
	}
	
	private HexChannel (String channelName,
			String host, 
			int port, 
			ISOPackager p, 
			byte[] TPDU) {
		super(host, port, p, TPDU);
		this.setName(channelName);
	}

	
	@Override
	public Channel Client(String channelName, String host, int port, ISOPackager packager, boolean disablePort) {
		this.disablePort = disablePort;
		if(!disablePort){
			return new HexChannel(channelName,host,port,packager,null);
		}
        return new HexChannel();
	}

	@Override
	public Channel Server(String channelName, int port, ISOPackager packager, boolean disablePort) {
		this.disablePort = disablePort;
		if(!disablePort){
			try {
				return new HexChannel(channelName,packager, null, new ServerSocket(port));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return new HexChannel();
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
