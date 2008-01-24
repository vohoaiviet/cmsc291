package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;


import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class ConvolutionTest extends TestCase {

	public void testApply() {		
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			Convolution convo = new Convolution(img);
			ConvolutionKernel kernel=new ConvolutionKernel(7);
			convo.setParameters(kernel.meanFilter());			
			ImageIO.write(convo.apply().getBufferedImage(),"jpg",new File("tests/convolution_7x7.jpg"));			
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
			
		}
	}

}
