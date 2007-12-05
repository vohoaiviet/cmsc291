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
			
			hist=new Histogram(gray);
			
			System.out.println(hist.getMax());
			System.out.println(hist.getMin());
		
			ImageIO.write(hist.getHistogramAsImage().getBufferedImage(),"jpg",new File("hist.jpg"));
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}
