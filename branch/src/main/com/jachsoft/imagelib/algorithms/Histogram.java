package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.GrayScaleImage;
import com.jachsoft.imagelib.RGBImage;

public class Histogram {
	GrayScaleImage target;
	int h[]=new int[256];
	float p[]=new float[256];
	
	public Histogram(GrayScaleImage target){
		this.target=target;
		int width=target.getWidth();
		int height=target.getHeight();
		
		for (int y=0;y<height;y++){
			for (int x=0;x<width;x++){
				float color=target.getColor(x, y);
				float scaled=color*255;
				int grayLevel=(int)scaled;
				h[grayLevel]++;				
				p[grayLevel]=(float)h[grayLevel]/(width*height);
			}
		}		
	}
	
	public GrayScaleImage equalize(int ulx,int uly,int w,int h){
		int width=target.getWidth();
		int height=target.getHeight();
		GrayScaleImage retval=new GrayScaleImage(width,height);

		
		for (int y=0;y<height;y++){
			for (int x=0;x<width;x++){
				float color=target.getColor(x, y);
				float sk=color;
				if ((x>=ulx && x<=ulx+w) && (y>=uly && y<=uly+h))
				{
				
					float scaled=color*255;
					int grayLevel=(int)scaled;
				
					sk=0;
					for (int i=0;i<=grayLevel;i++){
						sk=sk+p[i];
					}
				}
				retval.setColor(x, y, sk);
			}
		}
		return retval;
		
	}
	
	
	public GrayScaleImage equalize(){
		return equalize(0,0,target.getWidth(),target.getHeight());
		
	}

	
	public int getMax(){
		int max=0;
		for (int i=1;i<256;i++){
			if (h[i] > h[max])
				max=i;
		}
		return max;
	}
	
	public int getMin(){
		int min=0;
		for (int i=1;i<256;i++){
			if (h[i] < h[min])
				min=i;
		}
		return min;
	}
	
	
	public RGBImage getHistogramAsImage(){
		int max=0;		
		RGBImage retval=null;
		int color=0;
		
		max=getMax();
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