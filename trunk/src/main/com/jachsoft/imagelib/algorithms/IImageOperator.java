package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.RGBImage;
public interface IImageOperator {
	public void setRegion(ImageRegion region);
	public RGBImage apply();
	
}
