package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class EdgeDetectTest extends TestCase {

	public void testApply() {
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/wdg2.gif")));
			SobelEdgeDetect edge = new SobelEdgeDetect(img);			
			
			ImageIO.write(edge.apply().getBufferedImage(),"jpg",new File("data/edges.jpg"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
