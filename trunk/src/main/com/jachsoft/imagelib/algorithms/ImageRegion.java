package com.jachsoft.imagelib.algorithms;

public class ImageRegion {
	int ulx,uly,w,h;


	public ImageRegion(){}
	
	public ImageRegion(int ulx, int uly, int w, int h){
		this.ulx=ulx;
		this.uly=uly;
		this.w=w;
		this.h=h;
	}
	
	
	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getUlx() {
		return ulx;
	}

	public void setUlx(int ulx) {
		this.ulx = ulx;
	}

	public int getUly() {
		return uly;
	}

	public void setUly(int uly) {
		this.uly = uly;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}
	
}
