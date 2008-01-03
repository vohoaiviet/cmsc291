package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class ConvolutionTest extends TestCase {

	public void testApply() {		
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/sample1.jpg")));
			Convolution convo = new Convolution(img);
			ConvolutionKernel kernel=ConvolutionKernel.meanFilter(Neighbor.EIGHT);
			
			
			convo.setParameters(kernel,Neighbor.EIGHT);
			ImageIO.write(convo.apply().getBufferedImage(),"jpg",new File("data/meanfilter.jpg"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
