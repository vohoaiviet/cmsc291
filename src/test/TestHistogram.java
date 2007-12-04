package src.test;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.Histogram;

import junit.framework.TestCase;

public class TestHistogram extends TestCase {
	public void testNow(){
		RGBImage img=null;
		Histogram hist=null;
		
		try{
			img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
					
			hist=new Histogram(img);
			
			System.out.println(hist.getBlueMax());
			System.out.println(hist.getRedMax());
			System.out.println(hist.getGreenMax());
		
			System.out.println(hist.getBlueMin());
			System.out.println(hist.getRedMin());
			System.out.println(hist.getGreenMin());
		
			ImageIO.write(hist.getHistogramAsImage(0).getBufferedImage(),"jpg",new File("red.jpg"));
			ImageIO.write(hist.getHistogramAsImage(1).getBufferedImage(),"jpg",new File("green.jpg"));
			ImageIO.write(hist.getHistogramAsImage(2).getBufferedImage(),"jpg",new File("blue.jpg"));
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}
