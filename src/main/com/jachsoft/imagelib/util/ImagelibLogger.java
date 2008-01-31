package com.jachsoft.imagelib.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ImagelibLogger {

	public static Logger getLogger(String name){
		PropertyConfigurator.configure("log4j.properties");		
		return Logger.getLogger(name);
	}
	
	public static Logger getLogger(Class name){
		PropertyConfigurator.configure("log4j.properties");
		return Logger.getLogger(name);
	}
	
}
