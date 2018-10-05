package com.mpc.iso.services.impl;

import java.io.IOException;

import com.mpc.iso.creational.ChannelHeader;
import com.mpc.iso.model.HeaderConfig;

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
		
		byte header[] = "ISOMPNGEN2".getBytes();
		
		byte[] c = new byte[header.length + b.length];
		System.arraycopy(header, 0, c, 0, header.length);
		System.arraycopy(b, 0, c, header.length, b.length);
		
		return c;//build(b);
	}
}
