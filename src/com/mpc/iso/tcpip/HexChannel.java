package com.mpc.iso.tcpip;

import java.io.IOException;
import java.net.ServerSocket;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.HEXChannel;

public class HexChannel extends HEXChannel implements Channel{
	private boolean disablePort;
	
	public HexChannel() {}

	public static HexChannel getInstantce() {
		return new HexChannel();
	}
	
	private HexChannel(ISOPackager packager, byte[] TPDU,
			ServerSocket serverSocket) throws IOException {
		super(packager, TPDU, serverSocket);
	}
	
	private HexChannel (String host, int port, ISOPackager p, byte[] TPDU) {
		super(host, port, p, TPDU);
	}

	@Override
	protected void sendMessageLength(int len) throws IOException {
		if (len > 0xFFFF)
			throw new IOException (len + " exceeds maximum length");
		byte[] b = new byte[2];
		b[0] =(byte)( len >> 8 );
		b[1] =(byte)( len );
		
		serverOut.write (b);
	}
	
	protected int getMessageLength() throws IOException, ISOException {
		byte[] b = new byte[2];
		serverIn.readFully(b,0,2);
		int len =  checkSigned(( ( b[0]) << 8), 65536) + checkSigned(b[1], 256);
		return len;
	}
	
	private int checkSigned(int x, int shift){
		if(x < 0){
			return shift + x;
		}
		return x;
	}
	
	@Override
	public Channel Client(String channelName, String host, int port, ISOPackager packager, boolean disablePort) {
		this.disablePort = disablePort;
		if(!disablePort){
			return new HexChannel(host,port,packager,null);
		}
        return new HexChannel();
	}

	@Override
	public Channel Server(String channelName, int port, ISOPackager packager, boolean disablePort) {
		this.disablePort = disablePort;
		if(!disablePort){
			try {
				return new HexChannel(packager, null, new ServerSocket(port));
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
}
