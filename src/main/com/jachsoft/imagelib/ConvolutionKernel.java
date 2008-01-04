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
	
	public static ConvolutionKernel sobelSx(){
		int type=Neighbor.THREE;
		ConvolutionKernel kernel=new ConvolutionKernel(type,type);
		
		kernel.setValue(0, 0, -1);
		kernel.setValue(1, 0, 0);
		kernel.setValue(2, 0, 1);
		kernel.setValue(0, 1, -2);
		kernel.setValue(1, 1, 0);
		kernel.setValue(2, 1, 2);
		kernel.setValue(0, 2, -1);
		kernel.setValue(1, 2, 0);
		kernel.setValue(2, 2, 1);
		
		return kernel;
	}
	
	public static ConvolutionKernel sobelSy(){
		int type=Neighbor.THREE;
		ConvolutionKernel kernel=new ConvolutionKernel(type,type);
		
		kernel.setValue(0, 0, 1);
		kernel.setValue(1, 0, 2);
		kernel.setValue(2, 0, 1);
		kernel.setValue(0, 1, 0);
		kernel.setValue(1, 1, 0);
		kernel.setValue(2, 1, 0);
		kernel.setValue(0, 2, -1);
		kernel.setValue(1, 2, -2);
		kernel.setValue(2, 2, -11);
		
		return kernel;
	}
	
		
}
