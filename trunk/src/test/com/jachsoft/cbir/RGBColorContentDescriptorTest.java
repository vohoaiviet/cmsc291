package com.jachsoft.cbir;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.Convolution;

import junit.framework.TestCase;

public class RGBColorContentDescriptorTest extends TestCase {

	public void testApply() {		
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			RGBColorContentDescriptor iccd = new RGBColorContentDescriptor(img);
			System.out.println(iccd.getRedDescriptor());
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
			
		}
	}
	
}
