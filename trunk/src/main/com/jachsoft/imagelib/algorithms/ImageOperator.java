package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.RGBImage;

/**
 * Superclass of all image operators
 * 
 * @author jach
 *
 */

public class ImageOperator implements IImageOperator {
	
	/** The source image */
	protected RGBImage source;
	
	/** The region */
	protected ImageRegion region;
	
	/** Offset value*/
	protected float offset = 0;
	
	/** Scaling factor*/
	protected float scale = 1;
	
	public ImageOperator(){}
	
	public ImageOperator(RGBImage image){
		this.source = image;
		this.region = new ImageRegion(0,0,source.getWidth(),source.getHeight());
	}

	public RGBImage apply() {
		return source;
	}

	public void setRegion(ImageRegion region) {
		this.region = region;
	}
	
	public void setSource(RGBImage source){
		this.source=source;
		this.region = new ImageRegion(0,0,source.getWidth(),source.getHeight());
	}

	public float getOffset() {
		return offset;
	}

	public void setOffset(float offset) {
		this.offset = offset;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public float scale(float value){
		return scale * value + offset;
	}
	
	
}
