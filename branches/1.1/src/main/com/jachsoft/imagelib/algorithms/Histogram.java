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
		int width=rgb.getWidth();
		int height=rgb.getHeight();
		
		for (int y=0;y<height;y++){
			for (int x=0;x<width;x++){
				RGBColor color=rgb.getRGBColor(x, y);
				hr[color.getRed()]++;				
				pr[color.getRed()]=(float)hr[color.getRed()]/(width*height);
				hg[color.getGreen()]++;				
				pg[color.getGreen()]=(float)hg[color.getGreen()]/(width*height);
				hb[color.getBlue()]++;				
				pb[color.getBlue()]=(float)hb[color.getBlue()]/(width*height);
			}
		}		
	}

	
	public RGBImage equalize(int ulx,int uly,int w,int h){
		int width=rgb.getWidth();
		int height=rgb.getHeight();
		RGBImage retval=new RGBImage(width,height);

		
		for (int y=0;y<height;y++){
			for (int x=0;x<width;x++){
				RGBColor color=rgb.getRGBColor(x, y);
				float skr=0;
				float skg=0;
				float skb=0;
				if ((x>=ulx && x<=ulx+w) && (y>=uly && y<=uly+h))
				{
					int red=color.getRed();
					for (int i=0;i<=red;i++){
						skr=skr+pr[i];
					}
					
					int green=color.getGreen();
					for (int i=0;i<=green;i++){
						skg=skg+pg[i];
					}
					
					int blue=color.getBlue();
					for (int i=0;i<=blue;i++){
						skb=skb+pb[i];
					}
					
				}
				retval.setRGB(x, y, (int)(skr*255),(int)(skg*255), (int)(skb*255));
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
