package src.test;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.GrayScaleImage;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.Histogram;

import junit.framework.TestCase;

public class TestGrayScaleImage extends TestCase {
	public void testNow(){
		RGBImage img=null;
		Histogram hist=null;
		
		try{
			img=new RGBImage(ImageIO.read(new File("data/father.jpg")));
			GrayScaleImage gray=img.getGrayScaleImage();

			ImageIO.write(gray.getBufferedImage(),"jpg",new File("gray.jpg"));
			
			int w=gray.getWidth();
			int h=gray.getHeight();
			for (int y=0;y<h;y++){
				for (int x=0;x<w;x++){
					float color=gray.getColor(x, y);
					gray.setColor(x, y, 1-color);
				}
			}
			
			ImageIO.write(gray.getBufferedImage(),"jpg",new File("negative.jpg"));
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}
