package com.jachsoft.cmsc291.exercises;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class PlateLocalizationTest extends TestCase {

	public void testApply() {
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/exer3/test_022.jpg")));
			PlateLocalization operator = new PlateLocalization(img);
			ImageIO.write(operator.apply().getBufferedImage(),"jpg",new File("tests/plate_localization.jpg"));			
			ImageIO.write(operator.getScratch().getBufferedImage(),"jpg",new File("tests/plate_scratch.jpg"));
			ImageIO.write(operator.getPlateNumber().getBufferedImage(),"jpg",new File("tests/plate_number.jpg"));
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}	
	}

}
