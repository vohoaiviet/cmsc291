package com.jachsoft.imagelib.algorithms;

import java.io.File;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class SerialProcessorTest extends TestCase {

	public void testApply() {
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/jach-160.jpg")));
			SerialProcessor p = new SerialProcessor();
			MedianFilter median = new MedianFilter();
			SobelEdgeDetect sobel = new SobelEdgeDetect();
			Convolution mean = new Convolution();
			Convolution gaussian = new Convolution();
			Equalization equalization = new Equalization();
			
			//setup median filter
			median.setSize(9);
					
			//setup convolution for mean filter
			ConvolutionKernel kernel=new ConvolutionKernel(3);
			mean.setParameters(kernel.meanFilter());
			
			//setup convolution for gaussian filter
			kernel=new ConvolutionKernel(9);
			gaussian.setParameters(kernel.gaussianFilter(1.0f));
	
			//Setup thresholding
			ContrastStretching thresh = new ContrastStretching();
			thresh.threshold(127);
			
			p.setSource(img);
			p.setStoreIntermmediate(true);
			//p.addOperator(mean);
			//p.addOperator(median);
			p.addOperator(equalization);
			p.addOperator(gaussian);
			p.addOperator(sobel);
			p.addOperator(thresh);
			ImageIO.write(p.apply().getBufferedImage(),"jpg",new File("tests/serial.jpg"));
			
			//Process Intermediate results
			Iterator<RGBImage> ite = p.getIntermmediate().iterator();
			int c=0;
			while(ite.hasNext()){
				RGBImage im = ite.next();
				ImageIO.write(im.getBufferedImage(),"jpg",new File("tests/serial"+c+".jpg"));
				c++;
			}			
		}catch(Exception e){
			fail("Caught an exception");
			e.printStackTrace();
		}
		
	}

}
