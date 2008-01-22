package com.jachsoft.imagelib;

public class Neighbor extends DataArray {
	int size=3;
	
	public Neighbor(){
		super();
	}
	
	public Neighbor(int size){
		super(size,size);
		this.size=size;
	}
	
	public int getSize(){
		return size;
	}
	
	public int getOffset(){
		return size / 2;
	}
	
	public double getCenterValue(){
		return this.getValue(size/2,size/2);
	}
	
}
