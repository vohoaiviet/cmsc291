package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class Histogram{
	RGBImage rgb;

	int hr[]=new int[256];	
	int hg[]=new int[256];	
	int hb[]=new int[256];

	public static final int RED=0;
	public static final int GREEN=1;
	public static final int BLUE=2;
	
	int ulx,uly,w,h;
	
	public Histogram(RGBImage rgb){
		this.rgb=rgb;
		int width=rgb.getWidth();
		int height=rgb.getHeight();
		setParameters(0,0,width,height);
	}
	
	public Histogram(RGBImage rgb,int ulx, int uly, int w, int h){
		this.rgb=rgb;
		setParameters(ulx,uly,w,h);
	}
	
	public void setParameters(int ulx, int uly, int w, int h){
		this.ulx=ulx;
		this.uly=uly;
		this.w=w;
		this.h=h;
		
		//Count frequency 
		for (int y=uly;y<uly+h;y++){
			for (int x=ulx;x<ulx+w;x++){
				RGBColor color=rgb.getRGBColor(x, y);
				hr[color.getRed()]++;				
				hg[color.getGreen()]++;				
				hb[color.getBlue()]++;
			}
		}
	}
	
	public int[] getRed(){
		return hr;
	}
	
	public int[] getGreen(){
		return hg;
	}
	
	public int[] getBlue(){
		return hb;
	
	}
	
	public RGBImage getImage(){
		return rgb;
	}
		
	public int getUlx(){
		return ulx;
	}
	
	public int getUly(){
		return uly;	
	}
	
	public int getW(){
		return w;
	}
	
	
	public int getH(){
		return h;
	}
	
	public int getMax(int channel){
		int h[]=null;
		switch(channel){
			case RED:h=hr;break;
			case GREEN:h=hg;break;
			case BLUE:h=hb;break;
		}
		
		int max=0;		
		for (int i=1;i<256;i++){
			if (h[i] > h[max])
				max=i;
		}
		return max;
	}
	
	public int getMin(int channel){
		int h[]=null;
		switch(channel){
			case RED:h=hr;break;
			case GREEN:h=hg;break;
			case BLUE:h=hb;break;
		}

		int min=0;
		for (int i=1;i<256;i++){
			if (h[i] < h[min])
				min=i;
		}
		return min;
	}
	
	
	public RGBImage getHistogramAsImage(int channel){
		int h[]=null;
		int color=0;
		switch(channel){
			case RED:h=hr;color=0xFFFF0000;break;
			case GREEN:h=hg;color=0xFF00FF00;break;
			case BLUE:h=hb;color=0xFF0000FF;break;
		}

		int max=0;		
		RGBImage retval=null;
		
		max=getMax(channel);
		
		retval=new RGBImage(256,100);
		
		for (int x=0;x<256;x++){
			int scaled=(int)(h[x]*(100.0/h[max]));
			for (int y=0;y<scaled;y++){
				retval.setRGB(x, 100-1-y, color);
			}
		}
		return retval;
	}
	
}
