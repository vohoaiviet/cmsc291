package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class DynamicCompression extends ImageOperator{

	RGBImage img;
	int c;
	
	int ulx,uly,w,h;
	
	public DynamicCompression(RGBImage img){
		this.img=img;
		this.w=this.img.getWidth();
		this.h=this.img.getHeight();
	}
	
	public void setParameter(int c){
		this.c=c;
	}
	
	public void setRegion(ImageRegion r){
		this.ulx=r.getUlx();
		this.uly=r.getUly();
		this.w=r.getW();
		this.h=r.getH();
	}
	
	public RGBImage apply(){
		int w = ulx+this.w;
		int h = uly+this.h;
		
		RGBImage retval;// = new RGBImage(w,h);
		retval=img;
				
		for (int y=uly; y < h;y++){
			for (int x=ulx; x < w; x++){
				RGBColor col=img.getRGBColor(x, y);
				float r=(float)(c*Math.log(1+col.getRedN()));			
				float g=(float)(c*Math.log(1+col.getGreenN()));
				float b=(float)(c*Math.log(1+col.getBlueN()));
				retval.setRGB(x, y, r, g, b);
			}
		}
		return retval;
	}
	
	
}
