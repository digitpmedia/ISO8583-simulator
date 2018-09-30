package com.mpc.formater;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;

public abstract class FormaterService {
	protected Logger logger = Logger.getLogger(getClass().getSimpleName());
	
	public abstract void sendMessage(ISOSource isoSrc, ISOMsg isoMsg) throws ISOException, IOException;
	
	public abstract void receivedMessage(ISOMsg isoMsg) throws ISOException;

	public abstract void processMessage(ISOSource isoSrc, ISOMsg isoMsg) throws ISOException, IOException;
}
