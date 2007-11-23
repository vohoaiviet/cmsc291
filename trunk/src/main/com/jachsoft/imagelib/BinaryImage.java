package com.jachsoft.imagelib;

public class BinaryImage extends RGBImage{
	public BinaryImage(RGBImage img, int threshold){
		this.bimg=img.bimg;
	    for (int y=0;y < img.getHeight(); y++){
	    	for (int x=0; x < img.getWidth(); x++){
	    		RGBColor rgb=img.getRGBColor(x, y);
	    		int b = rgb.getBlue();
	    		int g = rgb.getGreen();
	    		int r = rgb.getRed();

	    		double ave=(b+r+g)/3;
	    		
	    		if (ave > threshold){
	    			img.setRGB(x, y, 0xFF,0xFF,0xFF);
	    		}else{
	    			img.setRGB(x, y, 0x00,0x00,0x00);
	    		}
	    	}		    	
	    }
	}
}
