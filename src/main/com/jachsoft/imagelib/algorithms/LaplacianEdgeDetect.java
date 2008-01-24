package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.DataArray;
import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBImage;

public class LaplacianEdgeDetect extends ImageOperator {
	int thresh=0;

	public LaplacianEdgeDetect() {
	}

	public void setThreshold(int val){
		this.thresh=val;
	}
	
	public LaplacianEdgeDetect(RGBImage image) {
		super(image);
	}
	
	public RGBImage apply() {
		int w=source.getWidth();
		int h=source.getHeight();
		
		RGBImage retval=new RGBImage(w,h);
		RGBImage gray=source.getGrayScaleImage();
		ConvolutionKernel kernel = new ConvolutionKernel().laplacianMask();
		int offset=kernel.getOffset();		
		DataArray raw = gray.getDataArray(1);	
		
		//Apply the laplacian kernel using convolution
		Convolution convolution = new Convolution(gray);
		convolution.setParameters(kernel);
		raw = convolution.convolve();		
		
		
		//Check for zero crossings
		for (int y=offset; y<(h-offset);y++){
			for (int x=offset; x<(w-offset);x++){
				int min=9999999;
				int max=-min;
				int newval=0;
				int th=0;
				for (int i=(y-offset); i<=(y+offset); i++){
					for (int j=(x-offset);j<=(x+offset);j++){
						newval = (int)raw.getValue(j, i);
						if (newval < min) min = newval;
						if (newval > max) max = newval;
					}			
				}
				th=(int)raw.getValue(x,y);
				if (thresh !=0 )
					th=thresh;
				if (min<-th && max>th){
					retval.setRGB(x, y, 0, 0, 0);
				}else {
					retval.setRGB(x, y, 255, 255, 255);
				}
				
			}
		}		
		
		return retval;
	}

}
