package com.jachsoft.imagelib.util;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class ImagelibLogger {

	public static Logger getLogger(String name){
		BasicConfigurator.configure();
		return Logger.getLogger(name);
	}
	
	public static Logger getLogger(Class name){
		BasicConfigurator.configure();
		return Logger.getLogger(name);
	}
	
}
