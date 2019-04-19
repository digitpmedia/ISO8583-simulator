package com.mpc.formater;

import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.iso.packager.GenericPackager;

import com.mpc.iso.creational.FormaterService;

public class GeneralFormaterServiceImpl extends FormaterService{
	
	public static void main(String []args) {
		String data = "21004030004180810000059950200000000007420181008175827601807119000007119115300000000F00000020310000000965878310530000000000000";
		ISOMsg iso = new ISOMsg();
		try {
			iso.setPackager(new GenericPackager("iso/iso2003.xml"));
			/*iso.setMTI("0200");
			iso.set(2,"627482923");*/
			//System.out.println(new String(iso.pack()));
			iso.unpack(data.getBytes());
			logISOMsg(iso);
			
			iso.setResponseMTI();
			iso.set(39,"00");
			System.out.println(new String(iso.pack()));
			logISOMsg(iso);
		} catch (ISOException e) {
			e.printStackTrace();
		}
	}
	
	private static void logISOMsg(ISOMsg msg) {
		System.out.println("----ISO MESSAGE-----");
		try {
			System.out.println(String.format("    MTI  : %s" , msg.getMTI()));
			for (int i=1;i<=msg.getMaxField();i++) {
				if (msg.hasField(i)) {
					System.out.println(String.format("    Field-%-3d : %s",i,msg.getString(i)));
				}
			}
		} catch (ISOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("--------------------");
		}

	}
	
	@Override
	public void processMessage(ISOSource isoSrc, ISOMsg isoMsg) throws ISOException, IOException {
		if(isoMsg.isRequest()) {
			Response.searching(isoMsg);
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
