package com.mpc.service;

import org.jpos.iso.ISOMUX;

import com.mpc.iso.tcpip.Channel;

public class ConnectionService implements iConnection{

	@Override
	public ISOMUX start(Channel channel) {
		return null;
	}

	@Override
	public ISOMUX stop(Channel channel) {
		return null;
	}

	@Override
	public ISOMUX pause(Channel channel) {
		return null;
	}
}
