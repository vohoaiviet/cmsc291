package com.jachsoft.cmsc291.exercises;



import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.ContrastStretching;
import com.jachsoft.imagelib.algorithms.Convolution;
import com.jachsoft.imagelib.algorithms.ImageOperator;
import com.jachsoft.imagelib.algorithms.LaplacianEdgeDetect;
import com.jachsoft.imagelib.algorithms.MedianFilter;
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
		
		//Store intermmediate results
		p.setStoreIntermmediate(true);
	
		//Step 1. Median Filter
		MedianFilter median = new MedianFilter();
		median.setSize(3);
		p.addOperator(median);
		
		//Step 2. Gaussian Filter
		Convolution blur= new Convolution();
		ConvolutionKernel kernel = new ConvolutionKernel(3);
		blur.setParameters(kernel.gaussianFilter(9));		
		p.addOperator(blur);
		
		//Step 3. Laplacian Edge Detection
		LaplacianEdgeDetect laplacian = new LaplacianEdgeDetect();
		laplacian.setParameters(7);
		p.addOperator(laplacian);					
		
		
		//Apply the operators
		retval=p.apply();
		
		//Save Intermediate results
		try{
			Iterator<RGBImage> ite = p.getIntermmediate().iterator();
			int c=0;
			while(ite.hasNext()){
				RGBImage im = ite.next();
				ImageIO.write(im.getBufferedImage(),"jpg",new File("plate_locate_step_"+c+".jpg"));
				c++;
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		return retval;
	}

}
