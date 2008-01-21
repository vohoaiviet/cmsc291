package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.DataArray;
import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class LaplacianEdgeDetect extends ImageOperator {

	public LaplacianEdgeDetect() {
	}

	public LaplacianEdgeDetect(RGBImage image) {
		super(image);
	}
	
	public RGBImage apply() {
		
		RGBImage retval=new RGBImage(source.getWidth(),source.getHeight());
		RGBImage gray=source.getGrayScaleImage();
		
		int w = source.getWidth();
		int h = source.getHeight();
		
		DataArray raw = gray.getDataArray(1);
		//System.out.println(raw);
		DataArray retRaw = new DataArray(w,h);		
		
		Neighbor nbor=new Neighbor(3);		
		int startX=0;
		int startY=0;
		int endX=source.getWidth();
		int endY=source.getHeight();
		int offset=nbor.getOffset();
		
		startX=startX+offset;
		startY=startY+offset;
		endX=endX-offset;
		endY=endY-offset;

		ConvolutionKernel kernel = new ConvolutionKernel().laplacianMask();
		//ConvolutionKernel kernel = new ConvolutionKernel(5);
		//kernel = kernel.LoGFilter(5.0f);
		
		for (int y=startY; y<endY;y++){
			for (int x=startX; x<endX;x++){
				Neighbor redNbor=source.getNeighbor(x, y, RGBColor.RED_CHANNEL,3);
				float newval=0;
				for (int i=0;i<kernel.getHeight();i++){
					for (int j=0;j<kernel.getWidth();j++){
						newval +=(int)redNbor.getValue(i, j) * kernel.getValue(i, j);
					}						
				}
				int red = (int)newval;
				retRaw.setValue(x, y, red);
			}
		}
		
		//System.out.println(retRaw);
		

		for (int y=startY; y<endY;y++){
			for (int x=startX; x<endX;x++){
				int min=9999999;
				int max = -min;
				int newval=0;
				int th=2;
				for (int i=(y-offset); i <= y+offset; i++){
					for (int j=(x-offset); j <= x+offset;j++){
						newval = (int)retRaw.getValue(j, i);
						if (newval < min) min = newval;
						if (newval > max) max = newval;
					}			
				}
				th=(int)retRaw.getValue(x,y);
				if (min<-th && max>th) retval.setRGB(x, y, 255, 255, 255);
				else retval.setRGB(x, y, 0, 0, 0);				
			}
		}
		
		//System.out.println(retval.getDataArray(1));

		
		
		return retval;
	}

}
