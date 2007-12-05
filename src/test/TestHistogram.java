package src.test;

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
			img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
					
			GrayScaleImage gray = img.getGrayScaleImage();
			ImageIO.write(gray.getBufferedImage(),"jpg",new File("gray.jpg"));			
			hist=new Histogram(gray);
			ImageIO.write(hist.getHistogramAsImage().getBufferedImage(),"jpg",new File("gray-hist.jpg"));
			
			gray=hist.equalize();
			ImageIO.write(gray.getBufferedImage(),"jpg",new File("equalized.jpg"));
			hist=new Histogram(gray);
			ImageIO.write(hist.getHistogramAsImage().getBufferedImage(),"jpg",new File("equalized-hist.jpg"));
			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}
