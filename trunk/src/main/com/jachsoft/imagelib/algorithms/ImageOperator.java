package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.RGBImage;

public class ImageOperator implements IImageOperator {
	protected RGBImage source;
	protected ImageRegion region;
	
	public ImageOperator(){}
	
	public ImageOperator(RGBImage image){
		this.source = image;
	}

	public RGBImage apply() {
		return source;
	}

	public void setRegion(ImageRegion region) {
		this.region = region;
	}
	
	public void setSource(RGBImage source){
		this.source=source;
	}

}
