package com.jachsoft.cobair;

public class BasicContentDescriptor implements com.jachsoft.cobair.ImageContentDescriptor {
	double bins[];
	            
	public BasicContentDescriptor(int size){
		bins = new double[size];
	}

	public double[] getBins() {
		return bins;
	}
	
	public void setBinValue(int i, double value){
		bins[i] = value; 
	}
	
	public double getBinValue(int i){
		return bins[i];
	}
}
