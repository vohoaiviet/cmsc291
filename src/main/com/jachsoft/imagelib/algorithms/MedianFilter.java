package com.jachsoft.imagelib.algorithms;

import java.util.Arrays;

import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class MedianFilter extends ImageOperator {
	public MedianFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	int size=3;
	
	public MedianFilter(RGBImage source){
		super(source);
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public RGBImage apply(){
		RGBImage retval=source;
		
		float median;
		Neighbor nbor=new Neighbor(size);
		int startX=0;
		int startY=0;
		int endX=source.getWidth();
		int endY=source.getHeight();
		int offset=nbor.getOffset();
		
		startX=startX+offset;
		startY=startY+offset;
		endX=endX-offset;
		endY=endY-offset;
		
		for (int y=startY; y<endY;y++){
			for (int x=startX; x<endX;x++){
				
				nbor=source.getNeighbor(x, y, RGBColor.RED_CHANNEL, size);
				Arrays.sort(nbor.getData());
				median=nbor.getCenterValue();
				int r=(int)median;
				
				nbor=source.getNeighbor(x, y, RGBColor.GREEN_CHANNEL, size);
				Arrays.sort(nbor.getData());
				median=nbor.getCenterValue();
				int g=(int)median;
				
				nbor=source.getNeighbor(x, y, RGBColor.BLUE_CHANNEL, size);
				Arrays.sort(nbor.getData());
				median=nbor.getCenterValue();
				int b=(int)median;		
				
				retval.setRGB(x, y, r, g, b);
			}
		}		
		return retval;
	}	
}
