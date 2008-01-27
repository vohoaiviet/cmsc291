package com.jachsoft.cmsc291.exercises;


import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.Convolution;
import com.jachsoft.imagelib.algorithms.ImageOperator;
import com.jachsoft.imagelib.algorithms.LaplacianEdgeDetect;
import com.jachsoft.imagelib.algorithms.MedianFilter;
import com.jachsoft.imagelib.algorithms.SerialProcessor;

public class PlateLocalization extends ImageOperator {
	double horizontal[];
	double vertical[];
	RGBImage scratch;
	
	//Parameters
	
	//Dimension of search region
	int x0;
	int y0;
	int x1;
	int y1;
	
	//Adjustment values for searching the plate
	double cx;
	double cy;
	
	//offset values for searching the plates	
	int deltaX;
	int deltaY;
	
	//band
	int xbm;
	int xb0;
	int xb1;
	
	//plate
	int yb0;
	int yb1;
	int ybm;
	
	RGBImage orig;
	
	
	public PlateLocalization() {
		super();
	}

	public PlateLocalization(RGBImage source) {
		super(source);
	}

	public RGBImage apply() {
		RGBImage retval=new RGBImage(source);
		orig=new RGBImage(source);
		
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
		
		scratch = new RGBImage(edges);
		
		//Step 4. Count the number of transitions 
		x0=0;
		y0=0;
		x1=w-1;
		y1=h-1;
		
		
		y0 = (h/4)*1;
		y1 = y0 + 2*(h/4);
		//x0 = (w/4)*1;
		//x1 = x0 + 2*(w/4);
		
		horizontal = new double[w];
		vertical = new double[h];		
		
		double maxY=-999;		
		int prevY=0;
		int currY;		
		ybm=0;		
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
		for (int y=y0;y<y1;y++){
			vertical[y]=vertical[y]/maxY;
			int i=(int)(vertical[y]*100);
			for (int x=0;x<i;x++){
				scratch.setRGB(x, y, 255, 0, 255);
			}
		}
		
		//Step 6. Band Detection				
		double cy=0.55;		
		yb0=0;
		yb1=0;
		deltaY=10;
		
		for (int y=y0;y<ybm;y++){
			if (vertical[y] <= (vertical[ybm]*cy)){
				if ((ybm-y) > deltaY) 
					yb0=y;
			}			
		}		
		
		for (int y=y1;y>ybm;y--){
			if (vertical[y] <= (vertical[ybm]*cy)){
				if ((y-ybm) > deltaY)
					yb1=y;
			}			
		}		
		for (int x=x0;x<x1;x++){
			scratch.setRGB(x, yb0, 0, 255, 255);
			scratch.setRGB(x, yb1, 0, 255, 255);
			scratch.setRGB(x, ybm, 255, 255, 0);
		}
		
		//7. Plate Detection
		xbm=0;
		int currX=0;		
		for (int x=x0;x<x1;x++){
			for (int y=(yb0+1);y<=yb1;y++){
				currX = edges.getRGBColor(x, y).getBlue();				
				horizontal[x]+=currX;
			}
		}
		
		//find the peak
		xbm = x0;
		for (int x=x0;x<x1;x++){
			//System.out.println(x+","+horizontal[x]);
			if (horizontal[xbm] < horizontal[x]){
				xbm = x;
			}
		}
			
		//normalize and plot
		double maxX=horizontal[xbm];
		for (int x=x0;x<x1;x++){
			double normalized=horizontal[x]/maxX;
			horizontal[x]=normalized;
			int i=(int)(horizontal[x]*100);

			for (int y=0;y<i;y++){
				scratch.setRGB(x, y, 255, 255, 0);
			}
		}

		
		cx=0.86;
		xb0=x0;
		xb1=x1;		
		deltaX=20;
		
		//find xb0
		for (int x=x0;x<xbm;x++){
			if (horizontal[x] <= (horizontal[xbm]*cx)){
				if ((xbm-x) >= deltaX)
					xb0=x;
			}else{
				break;
			}		
		}

		
		//find xb1
		for (int x=x1;x>xbm;x--){
			if (horizontal[x] <= (horizontal[xbm]*cx)){
				if ((x-xbm) >= deltaX)
					xb1=x;
			}else{
				break;
			}	
		}
		
		for (int y=yb0;y<yb1;y++){
			scratch.setRGB(xb0, y, 0, 0, 255);
			scratch.setRGB(xb1, y, 0, 0, 255);
			scratch.setRGB(xbm, y, 255, 255, 0);
		}
				
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
		
		//try{
		
		//ImageIO.write(vp.getBufferedImage(),"jpg",new File("vertical_projection.jpg"));
		//ImageIO.write(retval.getRegion(new ImageRegion(xb0,yb0,(xb1-xb0),(yb1-yb0))).getBufferedImage(),"jpg",new File("plate_number.jpg"));
		
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
		
		//}catch(IOException ioe){
		//	ioe.printStackTrace();
		//}		
		
		return retval;
	}
	
	
	public RGBImage getScratch(){
		return scratch;
	}
	
	public RGBImage getPlateNumber(){
		return orig.getRegion(new ImageRegion(xb0,yb0,(xb1-xb0),(yb1-yb0)));
	}

}
