package com.mpc.service;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.ISO87APackager;

import com.mpc.iso.tcpip.AsciiChannel;
import com.mpc.iso.tcpip.Channel;
import com.mpc.iso.tcpip.ChannelHeader;
import com.mpc.iso.tcpip.DJPHeaderConfigListener;
import com.mpc.iso.tcpip.GenericHeaderConfigListener;
import com.mpc.iso.tcpip.HexChannel;
import com.mpc.model.Configuration;
import com.mpc.model.HeaderConfig;
import com.mpc.model.HeaderConfig.HEADER_TYPE;

/***
 * @author yovi.putra
 */
public class ConnectionBuilder {
	private Channel channel;
	private Configuration config;
	private HeaderConfig headerConfig;
	
	private ConnectionBuilder(Configuration config) {
		this.config = config;
		this.headerConfig = config.getHeaderConfig();
	}
	
	public static ConnectionBuilder setConfiguration(Configuration config) {
		return new ConnectionBuilder(config);
	}
	
	public Channel createConnection() throws ISOException {
		if(this.headerConfig.getHeaderType() == HEADER_TYPE.HEX) {
			if(config.getIp().isEmpty()) {
				channel = HexChannel.Builder().Server("", config.getPort(), getPackager(), false);
			}else {
				channel = HexChannel.Builder().Client("", config.getIp(), config.getPort(),getPackager() , false);
			}
		}else {
			if(config.getIp().isEmpty()) {
				channel = AsciiChannel.Builder().Server("", config.getPort(), getPackager(), false);
			}else {
				channel = AsciiChannel.Builder().Client("", config.getIp(), config.getPort(),getPackager() , false);
			}
		}
		channel.setHeaderConfiguration(getChannelHeader());
		return channel;
	}
	
	private ISOPackager getPackager() throws ISOException {
		if(config.getCustomPackager().isEmpty()) {
			return new ISO87APackager();
		}else {
			return new GenericPackager("iso/" + config.getCustomPackager());
		}
	}
	
	private ChannelHeader getChannelHeader() {
		if(config.getCustomPackager().toLowerCase().contains("djp")) {
			return new DJPHeaderConfigListener(headerConfig);
		}
		return new GenericHeaderConfigListener(headerConfig);
	}
}
