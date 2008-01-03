package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class ConvolutionTest extends TestCase {

	public void testApply() {		
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/father2.jpg")));
			Convolution convo = new Convolution(img);
			ConvolutionKernel kernel=new ConvolutionKernel(3,3);
			kernel.setValue(0, 0, (float)1/9);
			kernel.setValue(1, 0, (float)1/9);
			kernel.setValue(2, 0, (float)1/9);
			kernel.setValue(0, 1, (float)1/9);
			kernel.setValue(1, 1, (float)1/9);
			kernel.setValue(2, 1, (float)1/9);
			kernel.setValue(0, 2, (float)1/9);
			kernel.setValue(1, 2, (float)1/9);
			kernel.setValue(2, 2, (float)1/9);
			
			convo.setParameters(kernel);
			ImageIO.write(convo.apply().getBufferedImage(),"jpg",new File("data/meanfilter.jpg"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
