package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class DynamicCompression implements IImageOperator{

	RGBImage img;
	int c;
	
	public DynamicCompression(RGBImage img){
		this.img=img;
	}
	
	public void setParameter(int c){
		this.c=c;
	}
	
	public RGBImage apply(){
		int w = img.getWidth();
		int h = img.getHeight();
		
		RGBImage retval = new RGBImage(w,h);
				
		for (int y=0; y < h;y++){
			for (int x=0; x < w; x++){
				RGBColor col=img.getRGBColor(x, y);
				int r=(int)(c*Math.log(1+col.getRed()));			
				int g=(int)(c*Math.log(1+col.getGreen()));
				int b=(int)(c*Math.log(1+col.getBlue()));
					
				retval.setRGB(x, y, r, g, b);
			}
		}
		return retval;
	}
	
	
}
