package com.jachsoft.video;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.StructuringElement;
import com.jachsoft.imagelib.algorithms.ContrastStretching;
import com.jachsoft.imagelib.algorithms.Convolution;
import com.jachsoft.imagelib.algorithms.ImageArithmetic;
import com.jachsoft.imagelib.algorithms.Morphology;

public class MotionDetector {
	
	public RGBImage diff(RGBImage frame1, RGBImage frame2){
		RGBImage retval;

		
		
		RGBImage gray1 = frame1.getGrayScaleImage();
		RGBImage gray2 = frame2.getGrayScaleImage();

		
		ImageArithmetic ar = new ImageArithmetic(gray1,gray2);
		ar.setOperation(ImageArithmetic.SUB);
		retval = ar.apply();
		
		Convolution convo = new Convolution(retval);
		ConvolutionKernel k=new ConvolutionKernel(3);
		convo.setParameters(k.meanFilter());		
		retval = convo.apply();

		
		ContrastStretching operator= new ContrastStretching(retval);
		operator.setParameters(15, 0, 15, 255);
		retval = operator.apply();

		Morphology morp = new Morphology(retval);
		StructuringElement kernel = new StructuringElement(3,3);
		kernel.setValue(0, 1);
		kernel.setValue(1, 1);
		kernel.setValue(2, 1);
		kernel.setValue(3, 1);
		kernel.setValue(4, 1);
		kernel.setValue(5, 1);
		kernel.setValue(6, 1);
		kernel.setValue(7, 1);
		kernel.setValue(8, 1);
		morp.setParameters(Morphology.CLOSING, kernel);
		retval = morp.apply();
		return retval;
	}
	
	public void motion(int n){
		for(int i=n; i >= 100;i--){
			String n2 = "frame00"+i+".jpg";
			String n1 = "frame00"+(i-1)+".jpg";
			try{
				RGBImage img=new RGBImage(ImageIO.read(new File("data/video/"+n1)));
				RGBImage img2=new RGBImage(ImageIO.read(new File("data/video/"+n2)));
				MotionDetector md = new MotionDetector();
				img2=md.diff(img, img2);
				int w=img.getWidth();
				int h=img.getHeight();
				
				RGBImage result = new RGBImage(w*2,h);
				
				for (int y=0;y<h;y++){
					int x;
					for (x=0;x < w;x++){
						RGBColor c = img.getRGBColor(x, y);
						result.setRGB(x, y, c.getRed(), c.getGreen(), c.getBlue());
					}					
					for (int z=0;z < w;z++,x++){
						RGBColor c = img2.getRGBColor(z, y);
						if (c.getRed() == 255){
							c = img.getRGBColor(z, y);
							result.setRGB(x, y, c.getRed(), c.getGreen(), c.getBlue());
						}
					}
				}
				ImageIO.write(result.getBufferedImage(),"jpg",new File("tests/video/frame00"+i+".jpg"));			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	
	
}
