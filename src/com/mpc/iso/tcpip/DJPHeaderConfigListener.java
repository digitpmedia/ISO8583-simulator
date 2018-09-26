package com.mpc.iso.tcpip;

import java.io.DataInputStream;
import java.io.IOException;

import org.jpos.iso.ISOException;

/***
 * @author yovi.putra
 * @date Sep 26, 2018
 */
public class DJPHeaderConfigListener implements HeaderConfiguration{

	@Override
	public byte[] sendMessageLength(int len) throws IOException {
		if (len > 0xFFFF)
			throw new IOException (len + " exceeds maximum length");
		byte b[] = new byte[2];
		b[0] = (byte) (len);
		b[1] = (byte) (len>>8);
		return String.format("ISOMPNGEN2%c%c", b[0],b[1]).getBytes();
	}

	@Override
	public int getMessageLength(DataInputStream serverIn) throws IOException, ISOException {
		byte[] b = new byte[12];
		serverIn.readFully(b,0,12);
		return serverIn.available();
	}

}
