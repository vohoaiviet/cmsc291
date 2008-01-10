package com.jachsoft.imagelib;

public class ConvolutionKernel extends DataArray {
	int type;
	
	public ConvolutionKernel(int m, int n){
		super(m,n);
	}
	
	public static ConvolutionKernel meanFilter(int type){
		ConvolutionKernel kernel=new ConvolutionKernel(type,type);
		for (int y=0;y<type;y++){
			for (int x=0;x < type;x++){
				kernel.setValue(x, y,(float)1/(type*type));
			}
		}
		return kernel;
	}
	
	
	
		
}
