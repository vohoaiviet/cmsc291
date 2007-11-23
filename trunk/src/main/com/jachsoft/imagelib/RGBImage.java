package com.jachsoft.imagelib;

import java.awt.image.BufferedImage;

public class RGBImage{
	
    BufferedImage bimg;
	
	public RGBImage(int w,int h){
		bimg=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
	}
	
	public void setRGB(int x, int y, int r, int g, int b){
		int rgb=RGBColor.pack(r, g, b);
		bimg.setRGB(x, y, rgb);
	}
	
	public RGBColor getRGBColor(int x, int y){
		RGBColor rgb = new RGBColor(bimg.getRGB(x, y));
		return rgb;
	}
	
	public BufferedImage getBufferedImage(){
		return bimg;
	}
	
}
