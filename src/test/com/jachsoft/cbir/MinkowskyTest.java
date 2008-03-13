package com.jachsoft.cbir;
import java.io.File;
import javax.imageio.ImageIO;
import com.jachsoft.imagelib.RGBImage;
import junit.framework.TestCase;
public class MinkowskyTest extends TestCase {

	public void testApply() {	
		try{		
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			RGBColorContentDescriptor iccd = new RGBColorContentDescriptor(img);
			Minkowsky m = new Minkowsky(2);			
			assertTrue((m.computeDistance(iccd, iccd) == 0));		
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
	}
}
