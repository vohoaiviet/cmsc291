package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class LaplacianEdgeDetect extends ImageOperator {

	public LaplacianEdgeDetect() {
		// TODO Auto-generated constructor stub
	}

	public LaplacianEdgeDetect(RGBImage image) {
		super(image);
		// TODO Auto-generated constructor stub
	}
	
	public RGBImage apply() {
		
		RGBImage gray=source.getGrayScaleImage();
		//System.out.println(gray.getDataArray(2));
		Convolution convo = new Convolution(gray);
		convo.setParameters((new ConvolutionKernel()).laplacianMask());
		gray = convo.apply();
		//System.out.println(gray.getDataArray(1));
		
		return gray;
	}

}
