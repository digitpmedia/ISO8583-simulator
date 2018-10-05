package com.mpc.iso.services;

import com.mpc.iso.ISOMux;
import com.mpc.iso.model.Configuration;

public interface iConnection {
	ISOMux start(Configuration configuration);
	void stop(ISOMux mux);
	void pause(ISOMux mux);
}
