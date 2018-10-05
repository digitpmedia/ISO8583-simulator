package com.mpc.iso.services.impl;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;

import com.mpc.iso.ISOMux;
import com.mpc.iso.creational.ChannelFactory;
import com.mpc.iso.creational.ConnectionBuilder;
import com.mpc.iso.model.Configuration;
import com.mpc.iso.services.iChannel;
import com.mpc.iso.services.iConnection;

public class ConnectionService implements iConnection{
	private Logger log = Logger.getLogger(getClass().getSimpleName());
	
	@Override
	public ISOMux start(Configuration configuration) {
		log.trace("Configuration " + configuration);
		iChannel channel = null;
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
