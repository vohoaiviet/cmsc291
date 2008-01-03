package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.RGBImage;

public class Convolution implements IImageOperator{
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

		
		return retval;
		
	}
	
	
}
