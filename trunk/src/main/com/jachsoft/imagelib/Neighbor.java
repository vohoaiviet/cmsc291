package com.jachsoft.imagelib;

public class Neighbor extends DataArray {
	public static final int THREE = 3;
	public static final int FIVE = 5;
	public static final int SEVEN = 7;
	public static final int NINE = 9;
	public static final int ELEVEN = 11;
	public static final int THIRTEEN = 13;
	public static final int FIFTEEN = 15;
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
		case SEVEN: return 3;
		case NINE: return 4;
		case ELEVEN: return 5;
		case THIRTEEN: return 6;
		case FIFTEEN: return 7;
		}
		return 0;
	}
}
