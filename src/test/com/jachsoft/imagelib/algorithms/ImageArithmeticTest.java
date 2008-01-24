package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class ImageArithmeticTest extends TestCase {

	public void testAdd() {
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1.gif")));
			RGBImage img2=new RGBImage(ImageIO.read(new File("data/scr2.gif")));
			
			
			ImageArithmetic arith = new ImageArithmetic(img,img2);
			/*
			arith.setOperation(ImageArithmetic.ADD);			
			ImageIO.write(arith.apply().getBufferedImage(),"jpg",new File("../add.jpg"));
			
			arith.setOperation(ImageArithmetic.SUB);			
			ImageIO.write(arith.apply().getBufferedImage(),"jpg",new File("../sub.jpg"));
			
			arith.setOperation(ImageArithmetic.MUL);			
			ImageIO.write(arith.apply().getBufferedImage(),"jpg",new File("../mul.jpg"));
			
			arith.setOperation(ImageArithmetic.DIV);			
			ImageIO.write(arith.apply().getBufferedImage(),"jpg",new File("../div.jpg"));
			*/
			arith.setOperation(ImageArithmetic.BLEND);		
			arith.setBlend(0.50f);
			ImageIO.write(arith.apply().getBufferedImage(),"jpg",new File("../blend.jpg"));
			
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}
	
	

}
