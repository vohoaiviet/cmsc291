package com.jachsoft.imagelib;

import java.awt.image.BufferedImage;

public class RGBImage extends BufferedImage{

	
	public RGBImage(int w,int h){
		super(w,h,BufferedImage.TYPE_INT_RGB);
	}
	
	public void setPixel(int x, int y, int r, int g, int b){
		int rgb=RGBColor.pack(r, g, b);
		this.setRGB(x, y, rgb);
	}
	
	public RGBColor getRGBColor(int x, int y){
		RGBColor rgb = new RGBColor(pixels[y][x]);
		return rgb;
	}
	
}
