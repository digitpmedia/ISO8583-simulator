package com.mpc.iso.tcpip;

import java.io.DataInputStream;
import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import com.mpc.model.HeaderConfig;
import com.mpc.model.HeaderConfig.HEADER_TYPE;

/***
 * @author yovi.putra
 * @date Sep 26, 2018
 */
public abstract class ChannelHeader {
	
	protected HeaderConfig headerConfig;
	
	public ChannelHeader(HeaderConfig headerValue) {
		this.headerConfig = headerValue;
	}
	
	protected final byte[] build(byte[] messageLength) {
		if(headerConfig.getStartValue().contains(HeaderConfig.TAG_LEN)) {
			return sortingByte(messageLength, 
					headerConfig.getMiddleValue().getBytes(), 
					headerConfig.getEndValue().getBytes());
		}else if(headerConfig.getMiddleValue().contains(HeaderConfig.TAG_LEN)) {
			return sortingByte(headerConfig.getStartValue().getBytes(), 
					messageLength, 
					headerConfig.getEndValue().getBytes());
		}else if(headerConfig.getEndValue().contains(HeaderConfig.TAG_LEN)) {
			return sortingByte(headerConfig.getStartValue().getBytes(),
					headerConfig.getMiddleValue().getBytes(),
					messageLength);
		}
		return null;
	}
	
	private byte[] sortingByte(byte start[], byte middle[], byte end[]) {
		byte sorting[] = new byte[start.length + middle.length + end.length];
		System.arraycopy(start, 0, sorting, 0, start.length);
		System.arraycopy(middle, 0, sorting, start.length, middle.length);
		System.arraycopy(end, 0, sorting, start.length+middle.length, end.length);
		return sorting;
	}
	
	protected byte[] setHeaderMessageLength(int len) throws IOException, ISOException {
		if (len > 0xFFFF)
			throw new IOException (len + " exceeds maximum length");

		if(headerConfig.getHeaderType() == HEADER_TYPE.HEX) {
			return ISOUtil.zeropad (Integer.toString (len % 0xFFFF,16), headerConfig.getHeaderLength()).getBytes();
		}else {
			return ISOUtil.zeropad (Integer.toString(len), headerConfig.getHeaderLength()).getBytes();
		}
	}

	protected int getHeaderMessageLength(DataInputStream serverIn) throws IOException {
		byte b[] = new byte[headerConfig.getHeaderLength()];
		serverIn.readFully(b,0,headerConfig.getHeaderLength());
		return serverIn.available();
	}
}
