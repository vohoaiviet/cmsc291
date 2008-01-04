package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.DataArray;
import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class EdgeDetect implements IImageOperator {
	RGBImage source;
	
	public EdgeDetect(RGBImage source){
		this.source = source;
	}
	
	

	public RGBImage apply() {
		RGBImage retval=null;
		
		DataArray data = source.getDataArray(RGBColor.BLUE_CHANNEL);
		
		
		return retval;
	}

	public void setRegion(ImageRegion region) {
		// TODO Auto-generated method stub

	}
	
	

}
