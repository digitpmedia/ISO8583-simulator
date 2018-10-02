package com.mpc.service;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;

import com.mpc.iso.ISOMux;
import com.mpc.iso.tcpip.Channel;
import com.mpc.iso.tcpip.ChannelFactory;
import com.mpc.model.Configuration;

public class ConnectionService implements iConnection{
	private Logger log = Logger.getLogger(getClass().getSimpleName());
	
	@Override
	public ISOMux start(Configuration configuration) {
		log.trace("Configuration " + configuration);
		Channel channel = null;
		try {
			channel = ConnectionBuilder.setConfiguration(configuration).createConnection();
		} catch (ISOException e) {
			e.printStackTrace();
		}
		return ChannelFactory.createInstance(channel);
	}

	@Override
	public void stop(ISOMux mux) {
		mux.terminate();
		log.trace("Close connection " + mux.getName());
	}

	@Override
	public void pause(ISOMux mux) {
		//TODO
	}
}
