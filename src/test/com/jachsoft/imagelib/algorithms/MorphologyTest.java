package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.StructuringElement;

import junit.framework.TestCase;

public class MorphologyTest extends TestCase {

	public void testDilation() {
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));
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
			kernel.setValue(8, 1);
			morph.setParameters(Morphology.DILATION, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/dilation.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}
	
	public void testErosion(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));		
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
			kernel.setValue(8, 1);
			morph.setParameters(Morphology.EROSION, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/erosion.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}	
	}
	
	public void testOpening(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));
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
			kernel.setValue(8, 1);
			morph.setParameters(Morphology.OPENING, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/opening.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}
	
	public void testClosing(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));
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
			kernel.setValue(8, 1);
			morph.setParameters(Morphology.CLOSING, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/closing.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}


	
	public void testHitAndMiss(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));
			Morphology morph = new Morphology(img);
			StructuringElement kernel = new StructuringElement(3,3);
			kernel.setValue(0, -1);
			kernel.setValue(1, 1);
			kernel.setValue(2, -1);
			kernel.setValue(3, 0);
			kernel.setValue(4, 1);
			kernel.setValue(5, 1);
			kernel.setValue(6, 0);
			kernel.setValue(7, 0);
			kernel.setValue(8, -1);
			morph.setParameters(Morphology.HITMISSED, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/hit-miss.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}
	
	public void testThinning(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));
			Morphology morph = new Morphology(img);
			StructuringElement kernel = new StructuringElement(3,3);
			kernel.setValue(0, 0);
			kernel.setValue(1, 0);
			kernel.setValue(2, 0);
			kernel.setValue(3, -1);
			kernel.setValue(4, 1);
			kernel.setValue(5, -1);
			kernel.setValue(6, 1);
			kernel.setValue(7, 1);
			kernel.setValue(8, 1);
			morph.setParameters(Morphology.THINNING, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/thinned.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}

}
