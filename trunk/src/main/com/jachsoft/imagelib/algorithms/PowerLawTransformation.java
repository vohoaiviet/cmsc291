package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class PowerLawTransformation extends ImageOperator {
	public PowerLawTransformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	RGBImage source;
	int c;
	float gamma;
	
	
	int ulx,uly,w,h;
	
	public PowerLawTransformation(RGBImage source){
		this.source=source;
		this.w=this.source.getWidth();
		this.h=this.source.getHeight();
	}
	
	
	public void setParameters(int c, float gamma){
		this.c=c;
		this.gamma=gamma;
	}
	
	public void setRegion(ImageRegion r){
		this.ulx=r.getUlx();
		this.uly=r.getUly();
		this.w=r.getW();
		this.h=r.getH();
	}
	
	public RGBImage apply() {
		RGBImage retval;
		retval = source;//new RGBImage(source.getWidth(),source.getHeight());

		int width=ulx+this.w;
		int height=uly+this.h;
		
		for (int y=uly;y<height;y++){
			for (int x=ulx;x<width;x++){
				RGBColor color=source.getRGBColor(x, y);
				
				float red=color.getRedN();
				float green=color.getGreenN();
				float blue=color.getBlueN();
				
				red=(float)(c*Math.pow(red, gamma));
				green=(float)(c*Math.pow(green, gamma));
				blue=(float)(c*Math.pow(blue, gamma));
				
				retval.setRGB(x, y, red, green, blue);
			}
		}
		return retval;
	}
}
