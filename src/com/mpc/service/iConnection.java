package com.mpc.service;

import org.jpos.iso.ISOMUX;

import com.mpc.iso.tcpip.Channel;

public interface iConnection {
	ISOMUX start(Channel channel);
	ISOMUX stop(Channel channel);
	ISOMUX pause(Channel channel);
}
