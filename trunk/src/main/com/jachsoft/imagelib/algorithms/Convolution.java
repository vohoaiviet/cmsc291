package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.DataArray;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

/**
 * The convolution operator
 * 
 * @author jach
 *
 */

public class Convolution extends ImageOperator{
	public Convolution() {
		super();
	}

	ConvolutionKernel kernel;
	
	public Convolution(RGBImage source){
		this.source = source;
	}
	
	public void setParameters(ConvolutionKernel kernel){
		this.kernel = kernel;
	}
	
	/**
	 * performs convolution but returns a 
	 * more lightweight object
	 * This method is useful when performing intermmediate computations
	 * say edge detection
	 * 
	 * @return
	 */
	public DataArray convolve(){
	    int w=source.getWidth();
	    int h=source.getHeight();	
		DataArray retval=new DataArray(w,h);			
		int offset=kernel.getOffset();		
		for (int y=offset; y<(h-offset);y++){
			for (int x=offset; x<(w-offset);x++){
				double value=0;
				for (int i=(y-offset),k=0; i <= y+offset; i++,k++){
					for (int j=(x-offset),l=0; j <= x+offset;j++,l++){
						RGBColor rgb = source.getRGBColor(j, i);
						value+=rgb.getBlue()*kernel.getValue(l, k);
					}			
				}
				retval.setValue(x, y, value);
			}
		}		
		return retval;		
	}
	
	public RGBImage apply(){
	    int w=source.getWidth();
	    int h=source.getHeight();
		RGBImage retval = new RGBImage(w,h);
		int offset=kernel.getOffset();

		for (int y=offset; y<(h-offset);y++){
			for (int x=offset; x<(w-offset);x++){
				int rvalue=0;
				int gvalue=0;
				int bvalue=0;
				for (int i=(y-offset),k=0; i <= y+offset; i++,k++){
					for (int j=(x-offset),l=0; j <= x+offset;j++,l++){
						RGBColor rgb = source.getRGBColor(j, i);
						double weight=kernel.getValue(l,k);
						rvalue+=rgb.getRed()*weight;
						gvalue+=rgb.getGreen()*weight;
						bvalue+=rgb.getBlue()*weight;						
					}			
				}
				int red=(int)rvalue;
				int green=(int)gvalue;
				int blue=(int)bvalue;				
				retval.setRGB(x, y, red, green, blue);					
			}
		}		
		return retval;		
	}	
}
