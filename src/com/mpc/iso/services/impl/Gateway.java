package com.mpc.iso.services.impl;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.packager.GenericPackager;

import com.mpc.iso.ISOMux;
import com.mpc.iso.creational.ChannelFactory;

public class Gateway {
	Logger log = Logger.getLogger(getClass().getSimpleName());
	
	public Gateway(ISOMux mux, boolean signon, boolean custom,boolean disableport){
		if(custom){
			ISOChannel channel = mux.getISOChannel();
			InputStream isp = Gateway.class.getClassLoader()
					.getResourceAsStream("config/iso/iso87ascii.xml");
			GenericPackager packager = null;
			try {
				packager = new GenericPackager(isp);
			} catch (ISOException e) {
				e.printStackTrace();
			}
			channel.setPackager(packager);
		}
		
		new Gateway(mux,signon,disableport);
	}
	
	public Gateway(ISOMux mux, boolean signon,boolean disableport){
		if(signon){
			//new Thread(new SignonManager(mux, this)).start();
		}
		new Gateway(mux, disableport);
	}
	
	public Gateway(ISOMux mux, boolean disableport){
		if(!disableport){
			new Thread(mux).start();
		}
		
		try {
			String host = "";
			int port = 0;
			BaseChannel ch = ChannelFactory.getBaseChannel(mux.getISOChannel());

			if(ch.getSocket() != null) {
				host = ch.getSocket().getLocalAddress().getHostAddress();
				port = ch.getSocket().getPort();
			}else if(ch.getServerSocket() != null) {
				host = ch.getServerSocket().getInetAddress().getHostAddress();
				port = ch.getServerSocket().getLocalPort();
			}else {
				port = ch.getPort();
			}
			log.info("Open connection ["+ch.getName()+"] ip["+host+"] port["+port+"]");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
