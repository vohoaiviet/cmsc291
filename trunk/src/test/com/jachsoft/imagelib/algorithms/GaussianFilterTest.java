package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class GaussianFilterTest extends TestCase {
	
	public void testApply() {		
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			Convolution convo = new Convolution(img);
			ConvolutionKernel kernel=new ConvolutionKernel(7);
			convo.setParameters(kernel.gaussianFilter(3));			
			ImageIO.write(convo.apply().getBufferedImage(),"jpg",new File("tests/gaussian_7x7_3.jpg"));			
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}
}
