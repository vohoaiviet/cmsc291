package com.jachsoft.imagelib;

public class Image{
	protected int width;
	protected int height;
	protected int pixels[][];

	public Image(){
		pixels=new int[1][1];
	}
	
	public Image(int w,int h){
		width=w;
		height=h;
		pixels=new int[h][w];
	}
	
	public void setPixel(int x, int y, int color){
		pixels[y][x]=color;
	}
	
	public int getPixel(int x, int y){
		return pixels[y][x];
	}
}
