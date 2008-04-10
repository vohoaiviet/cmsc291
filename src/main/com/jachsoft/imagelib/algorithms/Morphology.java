package com.jachsoft.imagelib.algorithms;

import org.apache.log4j.Logger;

import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.StructuringElement;
import com.jachsoft.imagelib.util.ImagelibLogger;

public class Morphology extends ImageOperator {	
	public static final int DILATION = 1;
	public static final int EROSION = 2;
	public static final int OPENING = 3;
	public static final int CLOSING = 4;
	public static final int HITMISSED = 5;
	public static final int THINNING = 6;
	
	
	static final int BACKGROUND=0;
	static final int FOREGROUND=1;
	static final int DONTCARE=-1;
		
	StructuringElement kernel;
	int operation;
		
	static Logger logger = ImagelibLogger.getLogger(Morphology.class);

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
		//logger.info("Applying operator...");
		switch(operation){
		case DILATION: return dilated(source);
		case EROSION: return eroded(source);
		case OPENING: return opened(source);
		case CLOSING: return closed(source);
		case HITMISSED: return hitAndMissed(source);
		case THINNING: return thinned(source);
		}
			
		return null;
	}
	
	
	public RGBImage dilated(RGBImage source){
		RGBImage retval;	
		int w=source.getWidth();
		int h=source.getHeight();	
		retval=new RGBImage(w,h);		
		//assumes kernel is a square!
		int offset=kernel.getHeight()/2;		
		for (int y=offset; y<(h-offset);y++){
			for (int x=offset; x<(w-offset);x++){				
				RGBColor rgb = source.getRGBColor(x, y);
				int normalized = (int)rgb.getBlueN();
				retval.setRGB(x, y, rgb.getRed(), rgb.getGreen(), rgb.getBlue());
				for (int i=(y-offset),k=0; i <= y+offset; i++,k++){
					for (int j=(x-offset),l=0; j <= x+offset;j++,l++){			
						if (normalized == BACKGROUND){							
							if ((int)(kernel.getValue(l, k)) == 1){																
								RGBColor c = source.getRGBColor(j, i);
								int n=(int)c.getBlueN();																
								if (( n & 1 ) == 1){
									//System.out.println(x+","+y+","+n);
									retval.setRGB(x, y, 255, 255, 255);
								}
							}
						}
					}			
				}				
			}
		}		
		return retval;
	}
	
	public RGBImage eroded(RGBImage source){
		RGBImage retval;	
		int w=source.getWidth();
		int h=source.getHeight();	
		retval=new RGBImage(w,h);		
		//assumes kernel is a square!
		int offset=kernel.getHeight()/2;		
		for (int y=offset; y<(h-offset);y++){
			for (int x=offset; x<(w-offset);x++){
				RGBColor rgb = source.getRGBColor(x, y);
				int normalized = (int)rgb.getBlueN();
				retval.setRGB(x, y, rgb.getRed(), rgb.getGreen(), rgb.getBlue());
				for (int i=(y-offset),k=0; i <= y+offset; i++,k++){
					for (int j=(x-offset),l=0; j <= x+offset;j++,l++){			
						if (normalized == FOREGROUND){							
							if ((int)(kernel.getValue(l, k)) == 1){																
								RGBColor c = source.getRGBColor(j, i);
								int n=(int)c.getBlueN();								
								if (( n & 1 ) == 0){
									retval.setRGB(x, y, 0, 0, 0);
								}
							}
						}
					}			
				}
			}
		}		
		return retval;
	}
	
	public RGBImage opened(RGBImage source){
		return dilated(eroded(source));
	}
	
	public RGBImage closed(RGBImage source){
		return eroded(dilated(source));
	}
	
	public RGBImage hitAndMissed(RGBImage source){
		RGBImage retval;	
		int w=source.getWidth();
		int h=source.getHeight();	
		retval=new RGBImage(w,h);		
		//assumes kernel is a square!
		int offset=kernel.getHeight()/2;		
		for (int y=offset; y<(h-offset);y++){
			for (int x=offset; x<(w-offset);x++){				
				int matched=0;				
				for (int i=(y-offset),k=0; i <= y+offset; i++,k++){
					for (int j=(x-offset),l=0; j <= x+offset;j++,l++){
						RGBColor rgb = source.getRGBColor(j, i);
						int normalized = (int)rgb.getBlueN();
						int kernelVal = (int)(kernel.getValue(l, k));
						if ( kernelVal == -1){
							matched++;
						}else
						if (kernelVal == 1){							
							if (normalized == FOREGROUND){
								matched++;
							}
						}else if (kernelVal == 0){							
							if (normalized == BACKGROUND){
								matched++;
							}
						}
					}			
				}
				if (matched==9){
					retval.setRGB(x, y, 255, 255, 255);
				}else{
					retval.setRGB(x, y, 0, 0, 0);
				}

			}
		}
		return retval;
	}
	
	public RGBImage thinned(RGBImage source){
		RGBImage retval;	
		int w=source.getWidth();
		int h=source.getHeight();		
		
		StructuringElement kernels[] = new StructuringElement[8]; 		
		kernels[0] = new StructuringElement(new double[][]{{0,0,0},{-1,1,-1},{1,1,1}});
		kernels[1] = new StructuringElement(new double[][]{{-1,0,0},{1,1,0},{-1,1,-1}});
		kernels[2] = new StructuringElement(new double[][]{{1,-1,0},{1,1,0},{1,-1,0}});
		kernels[3] = new StructuringElement(new double[][]{{-1,1,-1},{1,1,0},{-1,0,0}});
		kernels[4] = new StructuringElement(new double[][]{{1,1,1},{-1,1,-1},{0,0,0}});
		kernels[5] = new StructuringElement(new double[][]{{-1,1,-1},{0,1,1},{0,0,-1}});
		kernels[6] = new StructuringElement(new double[][]{{0,-1,1},{0,1,1},{0,-1,1}});
		kernels[7] = new StructuringElement(new double[][]{{0,0,-1},{0,1,1},{-1,1,-1}});
		
		retval=source;//new RGBImage(w,h);	
		
		int count=0;
		RGBColor rgb=null;
		//assumes kernel is a square!		
		int offset=kernels[0].getHeight()/2;
		int z,y,x;
		boolean matched= false;
		int nk=8;	//number of kernels
		
		for (int f=1;f<100;f++){		
			matched=false;
			logger.info("Pass: "+f);
			for (z = 0; z < nk ;z++){
				for (y=offset; y<(h-offset);y++){
					for (x=offset; x<(w-offset);x++){
						kernel = kernels[z];
						offset=kernel.getHeight()/2;
						count=0;			
						for (int i=(y-offset),k=0; i <= y+offset; i++,k++){
							for (int j=(x-offset),l=0; j <= x+offset;j++,l++){							
								rgb = retval.getRGBColor(j, i);
								int normalized = (int)rgb.getBlueN();							
								int kernelVal = (int)(kernel.getValue(l, k));
								if (kernelVal == DONTCARE){
									count++;
								}else if ((kernelVal == FOREGROUND) && (normalized == FOREGROUND)){	
										count++;
								}else if ((kernelVal == BACKGROUND) && (normalized == BACKGROUND)){							
										count++;
								}			
							}
						}
						if (count==9){
							matched = true;
							retval.setRGB(x, y, 0, 0, 0);
						}
					}
					if (z==nk){
						rgb = source.getRGBColor(x, y);
						retval.setRGB(x, y, rgb.getRed(), rgb.getGreen(), rgb.getBlue());
						}
				}
			}
			if (!matched)
				break;
		}
		return retval;
	}
}
