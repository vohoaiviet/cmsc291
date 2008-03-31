package com.jachsoft.video;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.ImageOperator;
import com.jachsoft.imagelib.algorithms.SobelEdgeDetect;

import junit.framework.TestCase;

public class MotionDetectorTest extends TestCase {
	public void testMotion(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/video/frame00001.jpg")));
			RGBImage img2=new RGBImage(ImageIO.read(new File("data/video/frame00005.jpg")));
			MotionDetector md = new MotionDetector();
			md.motion(99);
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}
}
