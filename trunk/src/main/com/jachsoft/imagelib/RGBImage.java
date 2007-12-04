package com.jachsoft.imagelib;

import java.awt.image.BufferedImage;

public class RGBImage{
	
    protected BufferedImage bimg;
	
    
    public RGBImage(){
    	
    }
    
    public RGBImage (BufferedImage bimg){
    	this.bimg=bimg;
    }
    
    
	public RGBImage(int w,int h){
		bimg=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
	}
	
	public void setRGB(int x, int y, int r, int g, int b){
		int rgb=RGBColor.pack(r, g, b);
		bimg.setRGB(x, y, rgb);
	}
	
	public void setRGB(int x, int y, int rgb){
		bimg.setRGB(x, y, rgb);
	}
	
	public RGBColor getRGBColor(int x, int y){
		RGBColor rgb = new RGBColor(bimg.getRGB(x, y));
		return rgb;
	}
	
	public BufferedImage getBufferedImage(){
		return bimg;
	}
	
	public void setBufferedImage(BufferedImage bimg){
		this.bimg = bimg;		
	}
		
	public int getWidth(){
		return bimg.getWidth();
	}
	
	public int getHeight(){
		return bimg.getHeight();
	}
	
	public void clear(){
		int width=bimg.getWidth();
		int height=bimg.getHeight();
		for (int y=0;y < height;y++){
			for (int x=0;x < width;x++){
				bimg.setRGB(x, y, 0xFF000000);
			}
		}
	}
	
}
