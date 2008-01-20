package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class ConvolutionTest extends TestCase {

	public void testApply() {		
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/sample1.jpg")));
			Convolution convo = new Convolution(img);
			ConvolutionKernel kernel=new ConvolutionKernel(3);
			
			/*
			convo.setParameters(kernel.meanFilter());
			ImageIO.write(convo.apply().getBufferedImage(),"jpg",new File("data/mean.jpg"));
			 
			
			kernel = new ConvolutionKernel(5);
			convo.setParameters(kernel.gaussianFilter(1.0f));
			ImageIO.write(convo.apply().getBufferedImage(),"jpg",new File("data/gaussian.jpg"));
			*/			
			kernel = new ConvolutionKernel(5);
			convo.setParameters(kernel.laplacianFilter(1.4f));
			ImageIO.write(convo.apply().getBufferedImage(),"jpg",new File("data/laplacian.jpg"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
