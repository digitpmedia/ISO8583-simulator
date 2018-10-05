package com.mpc.iso.creational;

import java.io.DataInputStream;
import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import com.mpc.iso.model.HEADER_TYPE;
import com.mpc.iso.model.HeaderConfig;

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
		System.out.println("Header:" + headerConfig.toString() +" " + headerConfig.getHeaderLength());
		if(headerConfig.getStartValue().contains(HeaderConfig.TAG_LEN)) {
			System.out.println("Start");
			return sortingByte(messageLength, 
					headerConfig.getMiddleValue().getBytes(), 
					headerConfig.getEndValue().getBytes());
		}else if(headerConfig.getMiddleValue().contains(HeaderConfig.TAG_LEN)) {
			System.out.println("Middleware");
			return sortingByte(headerConfig.getStartValue().getBytes(), 
					messageLength, 
					headerConfig.getEndValue().getBytes());
		}else if(headerConfig.getEndValue().contains(HeaderConfig.TAG_LEN)) {
			System.out.println("End");
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
	
	protected int getHeaderLength() {
		int len = headerConfig.getHeaderLength();
		if(len <= 0) {
			if(headerConfig.getHeaderType() == HEADER_TYPE.HEX) {
				len = 2;
			}else
				len = 4;
		}
		if(len<0) len =0;
		return len;
	}
	
	public byte[] setHeaderMessageLength(int len) throws IOException, ISOException {
		if (len > 0xFFFF)
			throw new IOException (len + " exceeds maximum length");

		if(headerConfig.getHeaderType() == HEADER_TYPE.HEX) {
			return ISOUtil.zeropad (Integer.toString (len % 0xFFFF,16), getHeaderLength()).getBytes();
		}else {
			return ISOUtil.zeropad (Integer.toString(len), getHeaderLength()).getBytes();
		}
	}

	public int getHeaderMessageLength(DataInputStream serverIn) throws IOException {
		byte b[] = new byte[headerConfig.getLength()];
		serverIn.readFully(b,0,headerConfig.getLength());
		return serverIn.available();
	}
}
