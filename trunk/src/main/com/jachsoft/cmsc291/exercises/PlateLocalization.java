package com.jachsoft.cmsc291.exercises;



import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.ContrastStretching;
import com.jachsoft.imagelib.algorithms.Convolution;
import com.jachsoft.imagelib.algorithms.ImageOperator;
import com.jachsoft.imagelib.algorithms.SerialProcessor;
import com.jachsoft.imagelib.algorithms.SobelEdgeDetect;

public class PlateLocalization extends ImageOperator {
	
	public PlateLocalization() {
		super();
	}

	public PlateLocalization(RGBImage source) {
		super(source);
	}

	public RGBImage apply() {
		RGBImage retval=null;
		SerialProcessor p = new SerialProcessor(source);
		p.setStoreIntermmediate(true);
		Convolution blur= new Convolution();
		ConvolutionKernel kernel = new ConvolutionKernel(5);
		blur.setParameters(kernel.gaussianFilter(3));
		
		p.addOperator(blur);
		
		
		SobelEdgeDetect sobel = new SobelEdgeDetect();
		p.addOperator(sobel);
		
		
		/*
		LaplacianEdgeDetect sobel = new LaplacianEdgeDetect();
		sobel.setThreshold(6);
		p.addOperator(sobel);
		*/			
		
		retval=p.apply();
		
		return retval;
	}

}
