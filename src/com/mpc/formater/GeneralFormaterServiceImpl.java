package com.mpc.formater;

import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;

import com.mpc.iso.creational.FormaterService;
import com.mpc.utils.MsgType;
import com.mpc.utils.RcUtils;

public class GeneralFormaterServiceImpl extends FormaterService{
	
	@Override
	public void processMessage(ISOSource isoSrc, ISOMsg isoMsg) throws ISOException, IOException {
		int msgtype = 800;
		if(isoMsg.isRequest()) {
			msgtype = Integer.parseInt(isoMsg.getMTI());
			if(MsgType.isNetwork(msgtype)) {
				isoMsg.set(39,RcUtils.APPROVED);
			}else {
				Response.searching(isoMsg);
			}
			isoMsg.setResponseMTI();
		}
	}

	@Override
	public void sendMessage(ISOSource isoSrc, ISOMsg isoMsg) throws ISOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivedMessage(ISOMsg isoMsg) throws ISOException {
		// TODO Auto-generated method stub
		
	}
}
