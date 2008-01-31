package com.jachsoft.imagelib;

public class DataArray {
	protected double data[][];
	protected int width;
	protected int height;
	
	public DataArray(){}
	
	public DataArray(double data[][]){
		this.width = data[0].length;
		this.height = data.length;
		this.data=new double[height][width];
		for (int y=0;y<width;y++){
			for (int x=0;x<width;x++){
				this.data[y][x] = data[y][x];
			}
		}
	}
	
	public DataArray(int width,int height){
		this.width=width;
		this.height=height;
		//data=new float[width * height];		
		data=new double[height][width];
	}
	
	public void setValue(int x, int y,double value){
		//data[y*width+x]=value;
		data[y][x] =value;
	}
	
	public void setValue(int i, double value){
		//data[i] = value;
		int y=(i/height);
		int x=(i-y*width);		
		data[y][x]=value;
	}
	
	public double getValue(int x, int y){
		//return data[y*width+x];
		return data[y][x];
	}
	
	public double getValue(int i){
		//return data[i];
		int y=(i/height);
		int x=(i-y*width);
		return data[y][x];
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public double[][] getData(){
		return data;
	}
	
	public double[] getData1D(){
		int n=width*height;
		double[] retval = new double[width*height];
		for (int i=0; i<n; i++){
			int y=(i/height);
			int x=(i-y*width);
			retval[i]=data[y][x];
		}		
		return retval;
	}
	
	public String toString(){
		String retval="";
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				retval += data[y * width + x] + "\t";
			}
			retval+="\n";
		}
		return retval;		
	}
}
