package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class LaplacianEdgeDetectTest extends TestCase {

	public void testApply(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/sample3.jpg")));
			LaplacianEdgeDetect operator = new LaplacianEdgeDetect(img);
			operator.setParameters(20);
			ImageIO.write(operator.apply().getBufferedImage(),"jpg",new File("tests/laplacian.jpg"));			
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}

}
