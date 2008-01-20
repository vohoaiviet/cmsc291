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
			
			//setup median filter
			median.setSize(9);
					
			//setup convolution for mean filter
			ConvolutionKernel kernel=new ConvolutionKernel(3);
			mean.setParameters(kernel.meanFilter());
			
			//setup convolution for gaussian filter
			kernel=new ConvolutionKernel(9);
			gaussian.setParameters(kernel.gaussianFilter(1.0f));
			
			p.setSource(img);
			p.setStoreIntermmediate(true);
			p.addOperator(mean);
			p.addOperator(median);
			p.addOperator(gaussian);
			p.addOperator(sobel);
			ImageIO.write(p.apply().getBufferedImage(),"jpg",new File("data/serial.jpg"));
			
			Iterator<RGBImage> ite = p.getIntermmediate().iterator();
			int c=0;
			while(ite.hasNext()){
				RGBImage im = ite.next();
				ImageIO.write(im.getBufferedImage(),"jpg",new File("data/serial"+c+".jpg"));
				c++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
