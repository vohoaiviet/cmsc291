package com.jachsoft.cbir;

import com.jachsoft.imagelib.*;


/**
 * @author RNDuldulao, Jr.
 *
 *	Usage:  RGBImage input to this class must be a binary image with white as the outline color, black
 *  as the background color, containing the eroded outline of the image boundary.
 */
/**
 * @author admin
 *
 */
public class OutlineDescriptor implements ImageContentDescriptor {
	RGBImage image = null;
	int centerX, centerY;
	double bins[] = null;
	
	
	public OutlineDescriptor(RGBImage image) {
		this(image, image.getWidth()/2, image.getHeight()/2);
	}
	
	
	public OutlineDescriptor(RGBImage image, int centerX, int centerY) {
		super();
		this.image = image;
		this.centerX = centerX;
		this.centerY = centerY;
		System.out.println("" + centerX + ", " + centerY);
	}


	private java.awt.Point bresenhamSearch(RGBImage img, int c_x, int c_y, int e_x, int e_y) {
		boolean steep = Math.abs(e_y - c_y) > Math.abs(e_x - c_x);
		int x0 = c_x, y0 =c_y, x1 = e_x, y1=e_y;
		int temp = 0;
		if (steep) {
			x0 = c_y; y0 = c_x;
			x1 = e_y; y1= e_x;
		}
		if (x0 > x1) {
			temp = x0;
			x0 = x1;
			x1 = temp;
			
			temp = y0;
			y0 = y1;
			y1 = temp;
			
		}
		
		int deltax = x1-x0;
		int deltay = Math.abs(y1-y0);
		float error = 0;
		float deltaerr = (float)deltay / deltax;
		int y = y0;
		int ystep = 1;
		if (y0 < y1) ystep=1; else ystep = -1;
		for (int x = x0; x < x1; x++) {
			RGBColor RGB = null;
			if (steep)  RGB = img.getRGBColor(y,x); 
			else RGB = img.getRGBColor(x,y);
			error = error + deltaerr;
			if (error >=  0.5) {
				y +=ystep;
				error -= 1;
			}
			
			
			int w = RGB.getBlue() & 0xFF;
			if (w > 240) {
			if ( steep) return new java.awt.Point(y,x);
			else  return new java.awt.Point(x,y);
			}
			
		}
			
		return null;
		
	}
	
	/**
	 * The idea of this method:  The image is divided into 8 "slices" with centerX and centerY as the center point, two per quadrant, one side of the
	 * slice is a part of the image boundary.  The algorithm goes around the rectangular boundary from 0 degrees (3'oclock position) going 360 degrees
	 * counter-clockwise.  Angle theta is computed/derived from the boundary point to the centery point and made as
	 * the index for the feature array.  The the first point found with the least (first) floored value of theta is used
	 * in the feature array.
	 * 
	 * This method uses the Bressenham's line drawing algorithm to get to the point in the outline that intersects
	 * with the line going from the center to a point in the boundary as defined by the angle theta from the center point.
	 * 
	 * The array returned by this method is 720 items long items 0-359 are x coordinates of the points while >360 are the corresponding y coordinates (offset
	 * by 360 from its x-coordinate).
	 * 
	 * @param img
	 * @param c_x
	 * @param c_y
	 * @return
	 */
	public double[] trace(RGBImage img, int c_x, int c_y){
		if (img==null) return null;
		int width=img.getWidth();
		int height = img.getHeight();
		double[] feature_array = new double[360*2];
		double n_x = 0, n_y = 0;
			
		// Q1
		int starting_x = width;
		int starting_y = c_y;
		
		int theta = 0;	
	
		
		
		//constant x, changing y;
		n_x = (width -1) - c_x;
		for (int y=starting_y; y> 0; y--) {
			//compute theta
			//normalize transpos x and y based on a 0, 0 coord
			n_y = c_y - y;
			
		
			theta = (int) Math.floor( Math.toDegrees(Math.atan( n_y / n_x )));  
			
			if (feature_array[theta]==0 && feature_array[theta + 360]==0) {
				//do walk bresenham;
				java.awt.Point p = bresenhamSearch(img, c_x,c_y, width -1, y);
				if (p != null)  { feature_array[theta] = p.x; feature_array[theta + 360]=p.y; }
				
			}
		
			
			
		}
		
		// 2 *  Q1/2
		starting_x = width;
		starting_y = 0;
		 n_y = c_y;
		for (int x= starting_x; x>= c_x; x--) {
			 n_x = x - c_x;
			
			
					
			theta = (int) Math.floor( Math.toDegrees(Math.atan( n_y / n_x )));  
		
			if (theta < 0) continue;
			if (feature_array[theta]==0 && feature_array[theta+360]==0) {
				//do walk bresenham;
				java.awt.Point p = bresenhamSearch(img, c_x,c_y, x, 0);
				if (p != null)  { feature_array[theta] = p.x; feature_array[theta + 360]=p.y; }
				
			}
		}
	
		// 1 *  Q2/1
		starting_x = c_x -1;
		starting_y = 0;
		 n_y = c_y  ;
		for (int x= starting_x ; x>= 0; x--) {
			 n_x = x - c_x;
			
			
					
			theta =  180 + (int) Math.floor( Math.toDegrees(Math.atan( n_y / n_x )));
			if (theta < 0) continue;
	
			if (feature_array[theta]==0 && feature_array[theta+360]==0) {
				//do walk bresenham;
				java.awt.Point p = bresenhamSearch(img, c_x,c_y, x, 0);
				if (p != null)  { feature_array[theta] = p.x; feature_array[theta + 360]=p.y; }
				
			}
		}
		
		// 2 Q2 /2
		
		starting_x = 0;
		 starting_y = 0;
		 n_x = 0 - c_x ;
		for (int y=starting_y; y<=c_y ; y++) {
			
			
			 n_y =  c_y - y;
			
		
			theta =   180 + (int) Math.floor( Math.toDegrees(Math.atan( n_y / n_x )));  
		
			
			if (theta < 0 ) continue;
			if (feature_array[theta]==0 && feature_array[theta + 360]==0) {
				//do walk bresenham;
				java.awt.Point p = bresenhamSearch(img, c_x,c_y, 0, y);
				if (p != null)  { feature_array[theta] = p.x; feature_array[theta + 360]=p.y; }
				
			}
		
			
			
		}
		
		//1 Q3/2
		
		starting_x = 0;
		 starting_y = c_y;
		 	
		 n_x =  0 - c_x;
		
		for (int y=starting_y; y< height ; y++) {
			
			
			n_y =  c_y - y;
			
		
			theta =  180 +  (int) Math.floor( Math.toDegrees(Math.atan( n_y / n_x )));  
		
			if (theta < 0) continue;
			if (feature_array[theta]==0 && feature_array[theta + 360]==0) {
				//do walk bresenham;
				java.awt.Point p = bresenhamSearch(img, c_x,c_y, 0, y);
				if (p != null)  { feature_array[theta] = p.x; feature_array[theta + 360]=p.y; }
				
			}
			
		}
		
		// 2 *  Q3/2
		starting_x = 0;
		starting_y = height-1;
		 n_y =  c_y - (height-1) ;
		for (int x= starting_x; x<=c_x; x++) {
			 n_x = x - c_x;
			
			
					
			theta =  180 + (int) Math.floor( Math.toDegrees(Math.atan( n_y / n_x )));  
		
			if (theta < 0) continue;
			if (feature_array[theta]==0 && feature_array[theta+360]==0) {
				//do walk bresenham;
				java.awt.Point p = bresenhamSearch(img, c_x,c_y, x, height-1);
				if (p != null)  { feature_array[theta] = p.x; feature_array[theta + 360]=p.y; }
				
			}
		}
		
		// 1 *  Q4/2
		starting_x = c_x+1;
		starting_y = height;
		 n_y = c_y - (height - 1) ;
		for (int x= starting_x; x< width; x++) {
			 n_x = x - c_x;
			
			
					
			theta = 360 + (int) Math.floor( Math.toDegrees(Math.atan( n_y / n_x )));  
			if (theta < 0 ) continue;
			if (feature_array[theta]==0 && feature_array[theta+360]==0) {
				//do walk bresenham;
				java.awt.Point p = bresenhamSearch(img, c_x,c_y, x, height-1);
				if (p != null)  { feature_array[theta] = p.x; feature_array[theta + 360]=p.y; }
				
			}
		}
		
//1 Q4/2
		
		starting_x = 0;
		starting_y = height -1;
		 	
		
		n_x = (width -1) - c_x;
		for (int y=starting_y; y > c_y ; y--) {
			//compute theta
			//normalize transpos x and y based on a 0, 0 coord
		
			 
			 n_y = c_y - y;
		
			theta =  360 + (int) Math.floor( Math.toDegrees(Math.atan( n_y / n_x )));  
		
			if (feature_array[theta]==0 && feature_array[theta + 360]==0) {
				//do walk bresenham;
				java.awt.Point p = bresenhamSearch(img, c_x,c_y, width - 1, y);
				if (p != null)  { feature_array[theta] = p.x; feature_array[theta + 360]=p.y; }
				
			}
			
		}
		
		return feature_array;
	}
	public double[] getBins() {
		// TODO Auto-generated method stub
		if (bins==null) bins =this.trace(this.image, centerX, centerY); 
		return bins;
	}

	public void normalize() {
		// TODO Auto-generated method stub

	}

}
