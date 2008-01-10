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
	
	
	
		
}
