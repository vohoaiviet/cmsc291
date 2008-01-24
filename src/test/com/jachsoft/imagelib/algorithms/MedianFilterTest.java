package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class MedianFilterTest extends TestCase {

	public void testApply() {
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			MedianFilter filter = new MedianFilter(img);
			filter.setSize(3);			
			ImageIO.write(filter.apply().getBufferedImage(),"jpg",new File("tests/median_filter_3x3.jpg"));
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}

	

}
