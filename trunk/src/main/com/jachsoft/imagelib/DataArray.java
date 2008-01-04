package com.jachsoft.imagelib;

public class DataArray {
	float data[];
	int width;
	int height;
	
	public DataArray(){}
	
	public DataArray(int width,int height){
		this.width=width;
		this.height=height;
		data=new float[width * height];
	}
	
	public void setValue(int x, int y,float value){
		data[y*width+x]=value;
	}
	
	public void setValue(int i, float value){
		data[i] = value;
	}
	
	public float getValue(int x, int y){
		return data[y*width+x];
	}
	
	public float getValue(int i){
		return data[i];
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
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
