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
		
		//Apply the operators
		retval=p.apply();
		
		//Step 4. Count the number of transitions 
		int x0=0;
		int y0=0;
		int x1=w;
		int y1=h;
		
		y0 = (h/2)*1;
		y1 = y0 + (h/2);
		
		
		horizontal = new double[w];
		vertical = new double[h];		
		
		
		double maxY=-999;		
		int prevY=0;
		int currY;		
		int ybm=0;		
		for (int y=y0;y<y1;y++){
			for (int x=x0;x<x1;x++){				
				RGBColor rgb = retval.getRGBColor(x, y);
				
				currY = rgb.getBlue();
				if (currY != prevY){
					vertical[y]++;
					prevY=currY;
				}
				if (maxY < vertical[y]){
					maxY = vertical[y];
					ybm=y;
				}				
			}
		}
		
		//Step 5. Normalize projections, generate visualization of projections
		RGBImage vp = new RGBImage(101,h);
		for (int y=0;y<h;y++){
			vertical[y]=vertical[y]/maxY;
			int i=(int)(vertical[y]*100);
			for (int x=0;x<i;x++){
				retval.setRGB(x, y, 255, 0, 255);
			}
		}
		
		//Step 6. Band Detection				
		double cy=0.55;
		int yb0=0;
		int yb1=0;
		for (int y=0;y<ybm;y++){
			if (vertical[y] <= (vertical[ybm]*cy)){
				yb0=y;
			}			
		}		
		for (int y=h-1;y>ybm;y--){
			if (vertical[y] <= (vertical[ybm]*cy)){
				yb1=y;
			}			
		}		
		for (int x=0;x<w;x++){
			retval.setRGB(x, yb0, 0, 255, 255);
			retval.setRGB(x, yb1, 0, 255, 255);
			retval.setRGB(x, ybm, 255, 255, 0);
		}
		
		//7. Plate Detection
		int prevX=0;
		int xbm=0;
		int currX=0;
		double maxX=-999;
		for (int y=yb0;y<=yb1;y++){
			for (int x=0;x<w;x++){
				RGBColor rgb = retval.getRGBColor(x, y);				
				currX = rgb.getBlue();				
				if (currX != prevX){
					horizontal[x]++;
					prevX=currX;
				}				
				if (maxX < horizontal[x]){
					maxX = horizontal[x];
					xbm=x;
				}	
			}
		}
		int max=0;
		xbm=0;
		for (int x=0;x<w;x++){
			horizontal[x]=horizontal[x]/maxX;			
			int i=(int)(horizontal[x]*100);
			System.out.println(i);			
			if (max < i){
				System.out.println("Max:"+i+","+x);
				xbm=x;
				max=i;
			}			
			for (int y=0;y<i;y++){
				retval.setRGB(x, y, 255, 255, 0);
			}
		}
		
		//System.out.println(xbm);		
		
		double cx=0.86;
		int xb0=0;
		int xb1=0;
		for (int x=0;x<xbm;x++){
			if (horizontal[x] <= (horizontal[xbm]*cx)){
				xb0=x;
			}			
		}		
		for (int x=w-1;x>xbm;x--){
			if (horizontal[x] <= (horizontal[xbm]*cx)){
				xb1=x;
			}			
		}
		
		for (int y=yb0;y<yb1;y++){
			retval.setRGB(xb0, y, 0, 255, 255);
			retval.setRGB(xb1, y, 0, 255, 255);
			retval.setRGB(xbm, y, 255, 255, 0);
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
