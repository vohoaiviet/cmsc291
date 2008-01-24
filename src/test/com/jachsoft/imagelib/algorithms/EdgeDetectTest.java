package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class EdgeDetectTest extends TestCase {

	public void testApply() {
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			SobelEdgeDetect edge = new SobelEdgeDetect(img);			
			ImageIO.write(edge.apply().getBufferedImage(),"jpg",new File("test/sobel.jpg"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
