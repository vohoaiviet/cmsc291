package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.DataArray;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.StructuringElement;

public class Morphology extends ImageOperator {	
	public static final int DILATION = 1;
	public static final int EROSION = 2;
	
	static final int BACKGROUND=0;
	static final int FOREGROUND=1;
	
	
	StructuringElement kernel;
	int operation;
		

	public Morphology() {
		super();
	}

	public Morphology(RGBImage source) {
		super(source);
	}
	
	public void setParameters(int operation, StructuringElement kernel){
		this.kernel = kernel;
		this.operation = operation;
	}
	
	
	public RGBImage apply(){
		switch(operation){
		case DILATION: return dilated();
		case EROSION: return eroded();
		}
			
		return null;
	}
	
	
	public RGBImage dilated(){
		RGBImage retval;	
		int w=source.getWidth();
		int h=source.getHeight();	
		retval=new RGBImage(w,h);		
		//assumes kernel is a square!
		int offset=kernel.getHeight()/2;		
		for (int y=offset; y<(h-offset);y++){
			for (int x=offset; x<(w-offset);x++){
				retval.setRGB(x, y, 0, 0, 0);
				for (int i=(y-offset),k=0; i <= y+offset; i++,k++){
					for (int j=(x-offset),l=0; j <= x+offset;j++,l++){
						RGBColor rgb = source.getRGBColor(j, i);
						int normalized = (int)rgb.getBlueN();			
						//System.out.println(normalized);
						if (normalized == BACKGROUND){
							if ((normalized & (int)(kernel.getValue(l, k))) == 1){
								retval.setRGB(x, y, 255, 255, 255);
							}
						}
					}			
				}				
			}
		}		
		return retval;
	}
	
	public RGBImage eroded(){
		RGBImage retval;	
		int w=source.getWidth();
		int h=source.getHeight();	
		retval=new RGBImage(w,h);		
		//assumes kernel is a square!
		int offset=kernel.getHeight()/2;		
		for (int y=offset; y<(h-offset);y++){
			for (int x=offset; x<(w-offset);x++){
				retval.setRGB(x, y, 255, 255, 255);
				for (int i=(y-offset),k=0; i <= y+offset; i++,k++){
					for (int j=(x-offset),l=0; j <= x+offset;j++,l++){
						RGBColor rgb = source.getRGBColor(j, i);						
						int normalized = (int)rgb.getBlueN();			
						//System.out.println(normalized);
						if (normalized == FOREGROUND){
							if ((normalized & (int)(kernel.getValue(l, k))) != 1){
								retval.setRGB(x, y, 0, 0, 0);
							}
						}
					}			
				}				
			}
		}		
		return retval;
	}
	
}
