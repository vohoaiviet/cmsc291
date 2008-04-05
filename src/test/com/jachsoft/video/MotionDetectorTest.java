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
			MotionDetector md = new MotionDetector();
			md.motion(738);
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}
}
