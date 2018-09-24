package com.mpc.iso;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOMUX;
import org.jpos.util.LogSource;

public class ISOMux extends ISOMUX{
	public ISOMux(ISOChannel channel, ISOLog isoLog) {
		super(channel);
		create(channel, new GatewayListener(), isoLog);
	}
	
	public ISOMux(ISOChannel channel,GatewayListener gatewayListener, ISOLog isoLog) {
		super(channel);
		create(channel, gatewayListener, isoLog);
	}
	
	private void create(ISOChannel channel,GatewayListener gatewayListener, ISOLog isoLog) {
		this.setISORequestListener(gatewayListener);
		((LogSource) channel).setLogger(isoLog, channel.getName());
		this.setLogger(isoLog, "mux-"+channel.getName());
	}
}
