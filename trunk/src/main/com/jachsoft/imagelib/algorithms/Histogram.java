package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class Histogram {
	RGBImage target;
	int redHist[]=new int[256];
	int greenHist[]=new int[256];
	int blueHist[]=new int[256];
	
	public Histogram(RGBImage target){
		this.target=target;
		
		int width=target.getWidth();
		int height=target.getHeight();
		
		for (int y=0;y<height;y++){
			for (int x=0;x<width;x++){
				RGBColor color=target.getRGBColor(x, y);
				redHist[color.getRed()]++;
				greenHist[color.getGreen()]++;
				blueHist[color.getBlue()]++;
			}
		}		
	}
	
	
	public int getRedMax(){
		int max=0;
		for (int i=1;i<256;i++){
			if (redHist[i] > redHist[max])
				max=i;
		}
		return max;
	}
	
	public int getRedMin(){
		int min=0;
		for (int i=1;i<256;i++){
			if (redHist[i] < redHist[min])
				min=i;
		}
		return min;
	}
	
	public int getBlueMax(){
		int max=0;
		for (int i=1;i<256;i++){
			if (blueHist[i] > blueHist[max])
				max=i;
		}
		return max;
	}
	
	public int getBlueMin(){
		int min=0;
		for (int i=1;i<256;i++){
			if (blueHist[i] < blueHist[min])
				min=i;
		}
		return min;
	}
	
	public int getGreenMax(){
		int max=0;
		for (int i=1;i<256;i++){
			if (greenHist[i] > greenHist[max])
				max=i;
		}
		return max;
	}
	
	public int getGreenMin(){
		int min=0;
		for (int i=1;i<256;i++){
			if (greenHist[i] < greenHist[min])
				min=i;
		}
		return min;
	}
	
	public RGBImage getHistogramAsImage(int channel){
		int max=0;		
		RGBImage retval=null;
		int data[]=null;
		int color=0;
		
		if (channel == 0){
			max=getRedMax();
			data=redHist;
			color=0xFFFF0000;
		}else if (channel == 2){
			max=getBlueMax();
			data=blueHist;
			color=0xFF0000FF;
		}else if (channel == 1){
			max=getGreenMax();
			data=greenHist;
			color=0xFF00FF00;
		}
		
		retval=new RGBImage(256,100);
		//retval.clear();
		
		for (int x=0;x<256;x++){
			int scaled=(int)(data[x]*(100.0/data[max]));
			for (int y=0;y<scaled;y++){
				retval.setRGB(x, 100-1-y, color);
			}
		}
		return retval;
	}
}
