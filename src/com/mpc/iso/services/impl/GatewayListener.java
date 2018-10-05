package com.mpc.iso.services.impl;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

import com.mpc.formater.GeneralFormaterServiceImpl;
import com.mpc.iso.creational.FormaterService;

public class GatewayListener implements ISORequestListener{
	private Logger log = Logger.getLogger(getClass());
	private FormaterService formaterService;
	
	@Override
	public boolean process(ISOSource isoSrc, ISOMsg isoMsg) {
		try {
			/***
			 * TODO
			 * Using formater service
			 * send, receive or process messaging
			 */
			/*BaseChannel channel = (BaseChannel) ((ISOMUX) isoSrc).getISOChannel();
            if(isoMsg.isRequest()) {
            	IsoTracing.printRowMessage(channel.getName(), isoMsg, true);
            }else {
            	IsoTracing.printRowMessage(channel.getName(), isoMsg, false);
            }*/
            if(formaterService == null) {
            	formaterService = new GeneralFormaterServiceImpl();
            }
        	formaterService.processMessage(isoSrc, isoMsg);
        } catch (Exception ex) {
            log.error(getClass().getSimpleName(), ex);
        }
        return false;
    }
}
