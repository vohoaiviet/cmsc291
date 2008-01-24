package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class PowerLawTransformationTest extends TestCase {

	public void testApply(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			PowerLawTransformation operator = new PowerLawTransformation(img);
			operator.setParameters(1, 5);
			ImageIO.write(operator.apply().getBufferedImage(),"jpg",new File("tests/gamma_correction.jpg"));			
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}

}
