package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class Equalization extends ImageOperator {	
	float pr[]=new float[256];
	float pg[]=new float[256];
	float pb[]=new float[256];
	Histogram hist;
	RGBImage rgb;
	
	int ulx,uly,w,h;
	
	public Equalization(Histogram hist){
		this.hist=hist;
		this.rgb=hist.getImage();
		this.h=this.rgb.getHeight();
		this.w=this.rgb.getWidth();
		
	}
	
	
	public void setRegion(ImageRegion r){
		this.ulx=r.getUlx();
		this.uly=r.getUly();
		this.w=r.getW();
		this.h=r.getH();
	}
	
	public RGBImage apply(){

		int w=hist.getW();
		int h=hist.getH();
		int n=h*w;

		int ulx=hist.getUlx();
		int uly=hist.getUly();
		 
		
		int[] hr=hist.getRed();
		int[] hg=hist.getGreen();
		int[] hb=hist.getBlue();
			
		//compute probability
		for (int i=0;i<256;i++){		
			pr[i]=(float)hr[i]/n;
			pg[i]=(float)hg[i]/n;
			pb[i]=(float)hb[i]/n;
		}
			
       //compute new sk
		for (int i=1;i<256;i++){
			pr[i]=pr[i]+pr[i-1];
			pg[i]=pg[i]+pg[i-1];
			pb[i]=pb[i]+pb[i-1];
			hr[i]=(int)(pr[i]*255);
			hg[i]=(int)(pg[i]*255);
			hb[i]=(int)(pb[i]*255);
		}
			
		//Draw it
		for (int y=uly;y<uly+h;y++){
			for (int x=ulx;x<ulx+w;x++){
				RGBColor color=rgb.getRGBColor(x,y);
				int red=hr[color.getRed()];
					int green=hg[color.getGreen()];
					int blue=hb[color.getBlue()];
					rgb.setRGB(x, y,red,green,blue);	
				}
			}
		return rgb;
	}
}
