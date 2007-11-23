package com.jachsoft.imagelib;

public class RGBImage extends Image{

	public RGBImage(){
		super();
	}
	
	public RGBImage(int w,int h){
		super(w,h);
	}
	
	public void setPixel(int x, int y, int r, int g, int b){
		pixels[y][x]=RGBColor.pack(r, g, b);
	}
	
	public RGBColor getRGBColor(int x, int y){
		RGBColor rgb = new RGBColor(pixels[y][x]);
		return rgb;
	}
	
}
