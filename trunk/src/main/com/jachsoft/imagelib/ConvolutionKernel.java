package com.jachsoft.imagelib;

public class ConvolutionKernel extends DataArray {
	int size;
	
	public ConvolutionKernel(int size){
		super(size,size);
		this.size=size;
	}
	
	public int getSize(){
		return size;
	}
	
	public ConvolutionKernel meanFilter(){
		ConvolutionKernel kernel=new ConvolutionKernel(size);
		for (int y=0;y<size;y++){
			for (int x=0;x < size;x++){
				kernel.setValue(x, y,(float)1/(size*size));
			}
		}
		return kernel;
	}
	
	public ConvolutionKernel gaussianFilter(float sd){
		ConvolutionKernel kernel=new ConvolutionKernel(size);
		double value;
		double min=0;
		double sum = 0;
		
		for (int y=0;y<size;y++){
			for (int x=0;x < size;x++){
				int center = size / 2;
				int i = x - center;
				int j = y - center;				
				value=(Math.exp(-(((i*i)+(j*j))/(2*sd*sd))))/(2*Math.PI*sd*sd);
				if (x == 0 && y == 0){
					min = value;
				}
				value = value / min;
				sum = sum + value;
				kernel.setValue(x, y,(float) value);
			}
		}
		
		for (int y=0;y<size;y++){
			for (int x=0;x < size;x++){
				kernel.setValue(x,y,(float)(kernel.getValue(x,y)/sum));
			}
		}		
		return kernel;
	}
	
	
		
}
