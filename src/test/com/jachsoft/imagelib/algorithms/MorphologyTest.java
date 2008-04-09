package com.jachsoft.imagelib.algorithms;

import java.io.File;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.StructuringElement;

import junit.framework.TestCase;

public class MorphologyTest extends TestCase {

	public void testDilation() {
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));
			Morphology morph = new Morphology(img);
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
			morph.setParameters(Morphology.DILATION, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/dilation.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}
	
	public void testErosion(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));		
			Morphology morph = new Morphology(img);
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
			morph.setParameters(Morphology.EROSION, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/erosion.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}	
	}
	
	public void testOpening(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));
			Morphology morph = new Morphology(img);
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
			morph.setParameters(Morphology.OPENING, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/opening.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}
	
	public void testClosing(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));
			Morphology morph = new Morphology(img);
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
			morph.setParameters(Morphology.CLOSING, kernel);
			ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/closing.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}


	
	public void testHitAndMiss(){
		try{
			RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));			
			//RGBImage img=new RGBImage(ImageIO.read(new File("data/wdg2thr3.gif")));
			RGBImage retval,tmp;
			ImageArithmetic ar;
			ImageOperator sobel = new SobelEdgeDetect(img);
			
			//img = sobel.apply();
			
			Morphology morph = new Morphology(img);
			StructuringElement kernel = new StructuringElement(new double[][]{{-1,1,-1},{0,1,1},{0,0,-1}});			
			morph.setParameters(Morphology.HITMISSED, kernel);
			retval = morph.apply();
			
			
			//ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/hit-miss.jpg"));
			morph = new Morphology(img);
			kernel = new StructuringElement(new double[][]{{-1,1,-1},{1,1,0},{-1,0,0}});			
			morph.setParameters(Morphology.HITMISSED, kernel);
			tmp = morph.apply();
			ar = new ImageArithmetic(retval,tmp);
			ar.setOperation(ImageArithmetic.OR);
			retval = ar.apply();
			
			//ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/hit-miss2.jpg"));
			
			morph = new Morphology(img);
			kernel = new StructuringElement(new double[][]{{-1,0,0},{1,1,0},{-1,1,-1}});			
			morph.setParameters(Morphology.HITMISSED, kernel);
			tmp = morph.apply();
			ar = new ImageArithmetic(retval,tmp);
			ar.setOperation(ImageArithmetic.OR);
			retval = ar.apply();
			//ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/hit-miss3.jpg"));
			
			morph = new Morphology(img);
			kernel = new StructuringElement(new double[][]{{0,0,-1},{0,1,1},{-1,1,-1}});			
			morph.setParameters(Morphology.HITMISSED, kernel);
			tmp = morph.apply();
			ar = new ImageArithmetic(retval,tmp);
			ar.setOperation(ImageArithmetic.OR);
			retval = ar.apply();
			//ImageIO.write(morph.apply().getBufferedImage(),"jpg",new File("tests/hit-miss4.jpg"));
			ImageIO.write(retval.getBufferedImage(),"jpg",new File("tests/hit-miss.jpg"));
			
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}
	
	public void testThinning(){
		try{
			//RGBImage img=new RGBImage(ImageIO.read(new File("data/scr1-bin-sobel-thr40.jpg")));
			RGBImage img=new RGBImage(ImageIO.read(new File("data/pham.jpg")));
			RGBImage result=img;
			Morphology morph = new Morphology(result);
			morph.setParameters(Morphology.THINNING, null);
			
			/*
			StructuringElement kernels[] = new StructuringElement[8]; 
			
			kernels[0] = new StructuringElement(new double[][]{{0,0,0},{-1,1,-1},{1,1,1}});
			kernels[1] = new StructuringElement(new double[][]{{-1,0,0},{1,1,0},{-1,1,-1}});
			kernels[2] = new StructuringElement(new double[][]{{1,-1,0},{1,1,0},{1,-1,0}});
			kernels[3] = new StructuringElement(new double[][]{{-1,1,-1},{1,1,0},{-1,0,0}});
			kernels[4] = new StructuringElement(new double[][]{{1,1,1},{-1,1,-1},{0,0,0}});
			kernels[5] = new StructuringElement(new double[][]{{-1,1,-1},{0,1,1},{0,0,-1}});
			kernels[6] = new StructuringElement(new double[][]{{0,-1,1},{0,1,1},{0,-1,1}});
			kernels[7] = new StructuringElement(new double[][]{{0,0,-1},{0,1,1},{-1,1,-1}});
			
			RGBImage result=img;
			for (int iter=0;iter < 20;iter++){
				for (int k=0;k<8;k++){			
					Morphology morph = new Morphology(result);					
					morph.setParameters(Morphology.THINNING, kernels[k]);
					result = morph.apply();
				}
				ImageIO.write(result.getBufferedImage(),"jpg",new File("tests/thinned.jpg"));
			}
			*/
			
			for (int i=0;i < 1;i++){
				morph = new Morphology(result);
				morph.setParameters(Morphology.THINNING, null);
				result = morph.apply();
			}
			
			ImageIO.write(result.getBufferedImage(),"jpg",new File("tests/thinned.jpg"));
			
		}catch(Exception e){			
			e.printStackTrace();
			fail("Caught an exception");
		}
	}

}
