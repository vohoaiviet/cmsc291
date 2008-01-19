package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.ImageOperationException;
import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class Convolution extends ImageOperator{
	RGBImage source;
	ImageRegion region;
	ConvolutionKernel kernel;
	
	public Convolution(RGBImage source){
		this.source = source;
	}
	
	public void setParameters(ConvolutionKernel kernel){
		this.kernel = kernel;
	}
	
	public void setRegion(ImageRegion region){
		this.region = region;
	}
	
	public RGBImage apply(){
		RGBImage retval = source;
		int size=kernel.getSize();
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
				Neighbor redNbor=source.getNeighbor(x, y, RGBColor.RED_CHANNEL,size);
				float newval=0;
				for (int i=0;i<kernel.getHeight();i++){
					for (int j=0;j<kernel.getWidth();j++){
						newval +=(int)redNbor.getValue(i, j) * kernel.getValue(i, j);
					}						
				}
				int red = (int)newval;
									
				Neighbor greenNbor=source.getNeighbor(x, y, RGBColor.GREEN_CHANNEL, size);
				newval=0;
				for (int i=0;i<kernel.getHeight();i++){
					for (int j=0;j<kernel.getWidth();j++){
						newval +=(int)greenNbor.getValue(i, j) * kernel.getValue(i, j);
					}						
				}
				int green = (int)newval;
									
				Neighbor blueNbor=source.getNeighbor(x, y, RGBColor.BLUE_CHANNEL, size);
				newval=0;
				for (int i=0;i<kernel.getHeight();i++){
					for (int j=0;j<kernel.getWidth();j++){
						newval +=(int)blueNbor.getValue(i, j) * kernel.getValue(i, j);
					}						
				}
				int blue = (int)newval;
				
				retval.setRGB(x, y, red, green, blue);					
			}
		}

		
		return retval;
		
	}
	
	
}
