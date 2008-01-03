package com.jachsoft.imagelib;

public class DataArray {
	float data[][];
	int m,n;
	
	public DataArray(){}
	
	public DataArray(int m,int n){
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
	
	public String toString(){
		String retval="";
		for (int y=0; y < m;y++){
			for (int x=0; x < n; x++){
				retval+=data[y][x]+"\t";
			}
			retval+="\n";
		}
		return retval;		
	}
}
