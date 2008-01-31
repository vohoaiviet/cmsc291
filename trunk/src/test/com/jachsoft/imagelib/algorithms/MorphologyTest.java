package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.StructuringElement;

import junit.framework.TestCase;

public class MorphologyTest extends TestCase {

	public void testDilation() {
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			img = img.getGrayScaleImage();
			ContrastStretching operator= new ContrastStretching(img);
			int t=127;
			operator.threshold(t);
			img = operator.apply();			
			ImageIO.write(img.getBufferedImage(),"jpg",new File("tests/jach-160-bw.jpg"));
			Morphology morph = new Morphology(img);
			StructuringElement kernel = new StructuringElement(3,3);
			kernel.setValue(0, 1);
			kernel.setValue(1, 1);
			kernel.setValue(2, 1);
			kernel.setValue(3, 1);
			kernel.setValue(4, 1);
			kernel.setValue(5, 1);
			kernel.setValue(6, 1);
			kernel.setValue(7, 1);
			morph.setParameters(Morphology.DILATION, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/dilation.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}
	
	public void testErosion(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			img = img.getGrayScaleImage();
			ContrastStretching operator= new ContrastStretching(img);
			int t=127;
			operator.threshold(t);
			img = operator.apply();			
			ImageIO.write(img.getBufferedImage(),"jpg",new File("tests/jach-160-bw.jpg"));
			Morphology morph = new Morphology(img);
			StructuringElement kernel = new StructuringElement(3,3);
			kernel.setValue(0, 1);
			kernel.setValue(1, 1);
			kernel.setValue(2, 1);
			kernel.setValue(3, 1);
			kernel.setValue(4, 1);
			kernel.setValue(5, 1);
			kernel.setValue(6, 1);
			kernel.setValue(7, 1);
			morph.setParameters(Morphology.EROSION, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/erosion.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}	
	}
/*
	
	public void testOpening(){
		fail("Not yet implemented");
	}
	
	public void testClosing(){
		fail("Not yet implemented");
	}
	
	public void testThinning(){
		fail("Not yet implemented");
	}
*/
}
