package com.mpc.formater;

import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;

public class GeneralFormaterServiceImpl extends FormaterService{
	
	@Override
	public void processMessage(ISOSource isoSrc, ISOMsg isoMsg) throws ISOException, IOException {
		if(isoMsg.isRequest()) {
			/*if(!isoMsg.getString(3).equals("301100")) {
				isoMsg.set(39,"27");
			}else {
				isoMsg.set(39,"00");
			}*/
			isoMsg.set(4, "9000000000");
			isoMsg.set(48, "024331241021000Nugroho Juli Purnama                              JALAN LAPANGAN BANTENG TIMUR NO.2-4               41112210005052013000000000000000000000000000000000B4CEE6I1FHF5FNKN");
			isoMsg.setResponseMTI();
			isoSrc.send(isoMsg);
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
