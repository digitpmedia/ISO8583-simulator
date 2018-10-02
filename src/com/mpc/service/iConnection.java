package com.mpc.service;

import com.mpc.iso.ISOMux;
import com.mpc.model.Configuration;

public interface iConnection {
	ISOMux start(Configuration configuration);
	void stop(ISOMux mux);
	void pause(ISOMux mux);
}
