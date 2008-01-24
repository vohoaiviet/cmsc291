package com.jachsoft.cmsc291.exercises;



import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.ContrastStretching;
import com.jachsoft.imagelib.algorithms.Convolution;
import com.jachsoft.imagelib.algorithms.ImageOperator;
import com.jachsoft.imagelib.algorithms.LaplacianEdgeDetect;
import com.jachsoft.imagelib.algorithms.MedianFilter;
import com.jachsoft.imagelib.algorithms.SerialProcessor;
import com.jachsoft.imagelib.algorithms.SobelEdgeDetect;

public class PlateLocalization extends ImageOperator {
	double horizontal[];
	double vertical[];
	
	public PlateLocalization() {
		super();
	}

	public PlateLocalization(RGBImage source) {
		super(source);
	}

	public RGBImage apply() {
		RGBImage retval=null;
		
		int w=source.getWidth();
		int h=source.getHeight();
		
		
		SerialProcessor p = new SerialProcessor(source);
		
		//Store intermmediate results
		p.setStoreIntermmediate(true);
	
		//Step 1. Median Filter
		MedianFilter median = new MedianFilter();
		median.setSize(3);
		p.addOperator(median);
		
		//Step 2. Gaussian Filter
		Convolution blur= new Convolution();
		ConvolutionKernel kernel = new ConvolutionKernel(3);
		blur.setParameters(kernel.gaussianFilter(9));		
		p.addOperator(blur);
		
		//Step 3. Laplacian Edge Detection
		LaplacianEdgeDetect laplacian = new LaplacianEdgeDetect();
		laplacian.setParameters(7);
		p.addOperator(laplacian);					
		
		
		
		median = new MedianFilter();
		median.setSize(3);
		p.addOperator(laplacian);
		
		
		//Apply the operators
		retval=p.apply();
		
		//Step 4. Derive the vertical and horizontal projections
		horizontal = new double[w];
		vertical = new double[h];		
		double maxX=-999;
		double maxY=-999;
		for (int y=0;y<h;y++){
			for (int x=0;x<w;x++){
				RGBColor rgb = retval.getRGBColor(x, y);
				vertical[y]=vertical[y]+rgb.getBlue();
				horizontal[x]=horizontal[x]+rgb.getBlue();
				
				
				
				if (maxX < horizontal[x]){
					maxX = horizontal[x];
				}				
				
				if (maxY < vertical[y]){
					maxY = vertical[y];
				}				
			}
		}
		
		//Step 5. Normalize projections, generate visualization of projections
		RGBImage vp = new RGBImage(101,h);
		RGBImage hp = new RGBImage(w,101);
		for (int x=0;x<w;x++){
			horizontal[x]=horizontal[x]/maxX;
			int i=(int)(horizontal[x]*100);
			for (int y=0;y<i;y++){
				retval.setRGB(x, y, 255, 255, 0);
			}
		}		
		for (int y=0;y<h;y++){
			vertical[y]=vertical[y]/maxY;
			int i=(int)(vertical[y]*100);
			for (int x=0;x<i;x++){
				retval.setRGB(x, y, 255, 0, 255);
			}
		}
		
		
		try{
		
		ImageIO.write(vp.getBufferedImage(),"jpg",new File("vertical_projection.jpg"));
		
		//Save Intermediate results
		/*
		
			Iterator<RGBImage> ite = p.getIntermmediate().iterator();
			int c=0;
			while(ite.hasNext()){
				RGBImage im = ite.next();
				ImageIO.write(im.getBufferedImage(),"jpg",new File("plate_locate_step_"+c+".jpg"));
				c++;
			}
		*/
		
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		
		return retval;
	}

}
