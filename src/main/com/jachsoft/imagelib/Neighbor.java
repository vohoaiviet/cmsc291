package com.jachsoft.imagelib;

public class Neighbor extends DataArray {
	public static final int THREE = 3;
	public static final int FIVE = 5;
	public static final int EIGHT = 8;
	int type;
	
	public Neighbor(){
		super();
	}
	
	public Neighbor(int type){
		super(type,type);
		this.type=type;
	}
	
	public int getType(){
		return type;
	}
	
	public int getOffset(){
		switch(type){
		case THREE: return 1;
		case FIVE: return 2;
		case EIGHT: return 3;
		}
		return 0;
	}
}
