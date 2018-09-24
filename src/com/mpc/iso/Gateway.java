package com.mpc.iso;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.packager.GenericPackager;

import com.mpc.iso.tcpip.ChannelFactory;

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
		
		BaseChannel ch = ChannelFactory.getBaseChannel(mux.getISOChannel());
		int port = 0;
		if(ch.getSocket() != null)
			port = ch.getSocket().getPort();
		else 
			port = ch.getServerSocket().getLocalPort();
		
		log.info("Open connection ["+ch.getName()+"] port["+port+"]");
	}
}
