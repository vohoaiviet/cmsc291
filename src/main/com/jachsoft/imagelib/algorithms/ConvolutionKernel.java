package com.jachsoft.imagelib.algorithms;

public class ConvolutionKernel {
	float data[][];
	int m,n;
	
	public ConvolutionKernel(){}
	
	public ConvolutionKernel(int m,int n){
		this.m=m;
		this.n=n;
		data=new float[m][n];
	}
	
	public void setValue(int x, int y,float value){
		data[y][x] = value;
	}
	
	public float getValue(int x, int y){
		return data[y][x];
	}
	
	public int getM(){
		return m;
	}
	
	public int getN(){
		return n;
	}
	
}
