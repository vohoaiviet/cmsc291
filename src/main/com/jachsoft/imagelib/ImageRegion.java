package com.jachsoft.imagelib;

public class ImageRegion {
	int ulx,uly,w,h;


	public ImageRegion(){}
	
	public ImageRegion(int ulx, int uly, int w, int h){
		this.ulx=ulx;
		this.uly=uly;
		this.w=w;
		this.h=h;
	}
	
	
	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
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

	public int getWidth() {
		return w;
	}

	public void setWidth(int w) {
		this.w = w;
	}
	
}
