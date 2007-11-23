package com.jachsoft.imagelib;

public class Image {
	int width;
	int height;
	int pixels[][];

	
	public Image(){
		
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
