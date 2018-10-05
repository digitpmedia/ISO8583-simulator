package com.mpc.iso.creational;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.ISO87APackager;

import com.mpc.iso.model.Configuration;
import com.mpc.iso.model.HeaderConfig;
import com.mpc.iso.services.iChannel;
import com.mpc.iso.services.impl.Channel;
import com.mpc.iso.services.impl.DJPHeaderConfigListener;
import com.mpc.iso.services.impl.GenericHeaderConfigListener;

/***
 * @author yovi.putra
 */
public class ConnectionBuilder {
	private iChannel channel;
	private Configuration config;
	private HeaderConfig headerConfig;
	
	private ConnectionBuilder(Configuration config) {
		this.config = config;
		this.headerConfig = config.getHeaderConfig();
	}
	
	public static ConnectionBuilder setConfiguration(Configuration config) {
		return new ConnectionBuilder(config);
	}
	
	public iChannel createConnection() throws ISOException {
		if(config.getIp().isEmpty()) {
			channel = Channel.Builder().Server("MyServer", config.getPort(), getPackager(), false);
		}else {
			channel = Channel.Builder().Client("MyClient", config.getIp(), config.getPort(),getPackager() , false);
		}
		channel.setHeaderConfiguration(getChannelHeader());
		return channel;
	}
	
	private ISOPackager getPackager() throws ISOException {
		if(config.getCustomPackager().isEmpty()) {
			System.out.println("Create packager iso87");
			return new ISO87APackager();
		}else {
			System.out.println("Create packager " + config.getCustomPackager());
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
