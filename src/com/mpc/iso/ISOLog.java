package com.mpc.iso;

import org.apache.log4j.PropertyConfigurator;
import org.jpos.util.Log4JListener;
import org.jpos.util.Logger;

public class ISOLog extends Logger {
	public ISOLog(String fileConfig) {
		try {
			Log4JListener log4jlistener = new Log4JListener();
			PropertyConfigurator.configure(fileConfig);
			log4jlistener.setLevel("INFO");
			this.addListener(log4jlistener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
