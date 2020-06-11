package com.attilax.util;

import java.io.Serializable;

public interface Callback   extends Serializable {

	void process(Object rzt);
	 
}
