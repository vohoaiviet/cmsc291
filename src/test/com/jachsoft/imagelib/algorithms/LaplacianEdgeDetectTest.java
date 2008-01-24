package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class LaplacianEdgeDetectTest extends TestCase {

	public void testApply(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/cln1.gif")));
			Convolution gaussian = new Convolution(img);
			ConvolutionKernel kernel = new ConvolutionKernel(5);
			gaussian.setParameters(kernel.gaussianFilter(1));
			
			//Do a gaussian filter first			
			img = gaussian.apply();
			
			LaplacianEdgeDetect operator = new LaplacianEdgeDetect(img);
			operator.setParameters(8);
			ImageIO.write(operator.apply().getBufferedImage(),"jpg",new File("tests/laplacian.jpg"));			
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}

}
