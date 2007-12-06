package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class Histogram {
	RGBImage rgb;

	int hr[]=new int[256];
	float pr[]=new float[256];
	int hg[]=new int[256];
	float pg[]=new float[256];
	int hb[]=new int[256];
	float pb[]=new float[256];
	
	public static final int RED=0;
	public static final int GREEN=1;
	public static final int BLUE=2;
	
	public Histogram(RGBImage rgb){
		this.rgb=rgb;
	}

	
	public RGBImage equalize(int ulx,int uly,int w,int h){
		int width=rgb.getWidth();
		int height=rgb.getHeight();
		int n=w*h;
		RGBImage retval=new RGBImage(width,height);
		
		//Do frequency count
		for (int y=uly;y<uly+h;y++){
			for (int x=ulx;x<ulx+w;x++){
				//if ((x>=ulx && x<=ulx+w) && (y>=uly && y<=uly+h))
				{
					RGBColor color=rgb.getRGBColor(x, y);
					hr[color.getRed()]++;				
					hg[color.getGreen()]++;				
					hb[color.getBlue()]++;
				}
			}
		}
		
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
		for (int y=0;y<height;y++){
			for (int x=0;x<width;x++){
				RGBColor color=rgb.getRGBColor(x, y);
				if ((x>=ulx && x<ulx+w) && (y>=uly && y<uly+h))
				{
					int red=hr[color.getRed()];
					int green=hg[color.getGreen()];
					int blue=hb[color.getBlue()];
					retval.setRGB(x, y,red,green,blue);	
				}else{
					retval.setRGB(x, y,color.getRed(),color.getGreen(),color.getBlue());
				}
			}
		}
		return retval;
		
	}

	
	public RGBImage equalize(){
		return equalize(0,0,rgb.getWidth(),rgb.getHeight());
		
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
		switch(channel){
			case RED:h=hr;break;
			case GREEN:h=hg;break;
			case BLUE:h=hb;break;
		}

		int max=0;		
		RGBImage retval=null;
		int color=0;
		
		max=getMax(channel);
		color=0xFFFFFFFF;
		
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
