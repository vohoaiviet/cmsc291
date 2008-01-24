package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;
import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class SobelEdgeDetectTest extends TestCase {

	public void testApply(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/cln1.gif")));
			ImageOperator operator = new SobelEdgeDetect(img);		
			ImageIO.write(operator.apply().getBufferedImage(),"jpg",new File("tests/sobel.jpg"));			
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}

}
