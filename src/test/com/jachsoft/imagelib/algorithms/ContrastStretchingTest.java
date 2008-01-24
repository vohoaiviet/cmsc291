package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class ContrastStretchingTest extends TestCase {

	public void testApply() {
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			ContrastStretching contrast=new ContrastStretching(img);
			contrast.setParameters(50, 100, 200, 219);			
			ImageIO.write(contrast.apply().getBufferedImage(),"jpg",new File("tests/contrast_stretching.jpg"));			
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
			
		}
	}

}
