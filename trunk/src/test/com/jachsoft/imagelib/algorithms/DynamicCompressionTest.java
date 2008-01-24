package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class DynamicCompressionTest extends TestCase {

	public void testApply(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			DynamicCompression operator = new DynamicCompression(img);
			operator.setParameter(1);
			ImageIO.write(operator.apply().getBufferedImage(),"jpg",new File("tests/dynamic_compression.jpg"));			
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}

}
