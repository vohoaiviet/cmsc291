package com.jachsoft.imagelib.algorithms;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.GrayScaleImage;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.Histogram;

import junit.framework.TestCase;

public class TestHistogram extends TestCase {
	public void testNow(){
		RGBImage img=null;
		Histogram hist=null;
		
		try{
			img=new RGBImage(ImageIO.read(new File("data/father2.jpg")));
					
			GrayScaleImage gray = img.getGrayScaleImage();
			ImageIO.write(gray.getBufferedImage(),"jpg",new File("gray.jpg"));			
			hist=new Histogram(img);
			ImageIO.write(hist.getHistogramAsImage(Histogram.RED).getBufferedImage(),"jpg",new File("orig-red-hist.jpg"));
			ImageIO.write(hist.getHistogramAsImage(Histogram.GREEN).getBufferedImage(),"jpg",new File("orig-green-hist.jpg"));
			ImageIO.write(hist.getHistogramAsImage(Histogram.BLUE).getBufferedImage(),"jpg",new File("orig-blue-hist.jpg"));
			
			/*img=hist.apply();
			ImageIO.write(img.getBufferedImage(),"jpg",new File("equalized.jpg"));
			hist=new Histogram(img);
			*/
			ImageIO.write(hist.getHistogramAsImage(Histogram.RED).getBufferedImage(),"jpg",new File("equalized-red-hist.jpg"));
			ImageIO.write(hist.getHistogramAsImage(Histogram.GREEN).getBufferedImage(),"jpg",new File("equalized-green-hist.jpg"));
			ImageIO.write(hist.getHistogramAsImage(Histogram.BLUE).getBufferedImage(),"jpg",new File("equalized-blue.jpg"));
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}
