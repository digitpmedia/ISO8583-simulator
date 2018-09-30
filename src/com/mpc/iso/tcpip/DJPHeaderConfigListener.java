package com.mpc.iso.tcpip;

import java.io.DataInputStream;
import java.io.IOException;

import com.mpc.model.HeaderConfig;

/***
 * @author yovi.putra
 * @date Sep 26, 2018
 */
public class DJPHeaderConfigListener extends ChannelHeader{
	public DJPHeaderConfigListener(HeaderConfig headerValue) {
		super(headerValue);
	}
	
	@Override
	public byte[] setHeaderMessageLength(int len) throws IOException {
		if (len > 0xFFFF)
			throw new IOException (len + " exceeds maximum length");
		byte b[] = new byte[2];
		b[0] = (byte) (len);
		b[1] = (byte) (len>>8);
		return build(b);
	}

	@Override
	public int getHeaderMessageLength(DataInputStream serverIn) throws IOException{
		byte[] b = new byte[12];
		serverIn.readFully(b,0,12);
		return serverIn.available();
	}
}
