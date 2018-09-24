package com.mpc.iso;

import org.apache.log4j.Logger;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOMUX;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

import com.mpc.iso.utils.IsoTracing;

public class GatewayListener implements ISORequestListener{
	Logger log = Logger.getLogger(getClass());
	
	@Override
	public boolean process(ISOSource isoSrc, ISOMsg isoMsg) {
		try {
			BaseChannel channel = (BaseChannel) ((ISOMUX) isoSrc).getISOChannel();
            if(isoMsg.isRequest()) {
            	IsoTracing.printRowMessage(channel.getName(), isoMsg, true);
            	isoMsg.setResponseMTI();
            	isoMsg.set(39, "00");
                isoSrc.send(isoMsg);
            }else {
            	IsoTracing.printRowMessage(channel.getName(), isoMsg, false);
            }
        } catch (Exception ex) {
            log.error(getClass().getSimpleName(), ex);
        }
        return false;
    }
}
