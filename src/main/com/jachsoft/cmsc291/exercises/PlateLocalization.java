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
		RGBImage retval=new RGBImage(source);
		
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
		RGBImage edges=p.apply();
		
		RGBImage scratch = new RGBImage(edges);
		
		//Step 4. Count the number of transitions 
		int x0=0;
		int y0=0;
		int x1=w-1;
		int y1=h-1;
		
		/*
		y0 = (h/3)*1;
		y1 = y0 + (h/3);
		x0 = (w/3)*1;
		x1 = x0 + (w/3);
		
		*/
		
		horizontal = new double[w];
		vertical = new double[h];		
		
		double maxY=-999;		
		int prevY=0;
		int currY;		
		int ybm=0;		
		for (int y=y0;y<y1;y++){
			for (int x=x0;x<x1;x++){				
				RGBColor rgb = edges.getRGBColor(x, y);
				
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
		for (int y=y0;y<y1;y++){
			vertical[y]=vertical[y]/maxY;
			int i=(int)(vertical[y]*100);
			for (int x=0;x<i;x++){
				scratch.setRGB(x, y, 255, 0, 255);
			}
		}
		
		//Step 6. Band Detection				
		double cy=0.55;
		int yb0=0;
		int yb1=0;
		for (int y=y0;y<ybm;y++){
			if (vertical[y] <= (vertical[ybm]*cy)){
				yb0=y;
			}			
		}		
		for (int y=y1;y>ybm;y--){
			if (vertical[y] <= (vertical[ybm]*cy)){
				yb1=y;
			}			
		}		
		for (int x=x0;x<x1;x++){
			scratch.setRGB(x, yb0, 0, 255, 255);
			scratch.setRGB(x, yb1, 0, 255, 255);
			scratch.setRGB(x, ybm, 255, 255, 0);
		}
		
		//7. Plate Detection
		int prevX;
		int xbm=0;
		int currX=0;
		
		for (int x=x0;x<x1;x++){
			//prevX=retval.getRGBColor(x, yb0).getBlue();
			for (int y=(yb0+1);y<=yb1;y++){
				currX = edges.getRGBColor(x, y).getBlue();				
				/*
				if (currX != prevX){
					horizontal[x]++;
					prevX=currX;
				}
				*/
				horizontal[x]+=currX;
			}
		}
		
		//find the peak
		xbm = 0;
		for (int x=x0;x<x1;x++){
			//System.out.println(x+","+horizontal[x]);
			if (horizontal[xbm] < horizontal[x]){
				xbm = x;
			}
		}
			
		double maxX=horizontal[xbm];
		for (int x=x0;x<x1;x++){
			double normalized=horizontal[x]/maxX;
			horizontal[x]=normalized;
			int i=(int)(horizontal[x]*100);
			for (int y=0;y<i;y++){
				scratch.setRGB(x, y, 255, 255, 0);
			}
		}
		
		//System.out.println(xbm+","+horizontal[xbm]);		
		
		double cx=0.86;
		int xb0=0;
		int xb1=0;
		int offset=50;
		for (int x=(xbm-offset);x<xbm;x++){
			//System.out.println(horizontal[x]+","+horizontal[xbm]*cx);
			if (horizontal[x] <= (horizontal[xbm]*cx)){
				xb0=x;
			}			
		}		
		System.out.println(xb0);
		for (int x=(xbm+offset);x>xbm;x--){
			if (horizontal[x] <= (horizontal[xbm]*cx)){
				xb1=x;
			}			
		}
		
		for (int y=yb0;y<yb1;y++){
			scratch.setRGB(xb0, y, 0, 255, 255);
			scratch.setRGB(xb1, y, 0, 255, 255);
			scratch.setRGB(xbm, y, 255, 255, 0);
		}
				
		
		
		xb0 = x0;//xbm-100;
		xb1 = x1;//xbm+100;
		for (int y=yb0;y<=yb1;y++){
			for (int x=xb0;x<=xb1;x++){
				if ((x==xb0 || x== xb1)){
					retval.setRGB(x, y, 0, 0, 255);
				}
				if ((y==yb0 || y== yb1)){
					retval.setRGB(x, y, 0, 0, 255);
				}

			}
			
		}
		
		
		
		//retval = scratch;
		
		
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
