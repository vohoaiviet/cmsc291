package com.jachsoft.imagelib;

public class RGBColor {
	private int color;
	private int r;
	private int g;
	private int b;
	private int a;
	
	public RGBColor(){
	}
	
	public RGBColor(int color){
		this.color=color;
		b = color & 255;
		g = (color >> 8) & 255;
		r = (color >> 16) & 255;
		a = (color >> 24) & 255;
	}
	
	
	public int getRed(){
		return r;
	}
	
	public int getGreen(){
		return g;
	}
	
	public int getBlue(){
		return b;
	}
	
	public static int pack(int r, int g, int b){
		int color = 255 << 8;
		color |= r;
		color <<= 8;
		color |= g;
		color <<= 8;
		color |= b;	
		return color;
	}
}
