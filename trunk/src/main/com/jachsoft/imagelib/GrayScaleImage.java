package com.jachsoft.imagelib;

import java.awt.image.BufferedImage;

public class GrayScaleImage {
	int width;
	int height;
	float data[];
	
	public GrayScaleImage(int width, int height){
		this.width=width;
		this.height=height;
		data = new float[width*height];
	}
	
	public float getColor(int x, int y){
		return data[y*width+x];
	}
	
	public void setColor(int x, int y,float color){
		data[y*width+x]=color;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public BufferedImage getBufferedImage(){
		RGBImage rgbImage = new RGBImage(width,height);
		
		for (int y=0; y < height; y++){
			for (int x=0; x < width; x++){
				float color = data[y*width+x];
				int rgb=(int)(255*color);
				rgbImage.setRGB(x, y, rgb, rgb, rgb);
			}
		}
		return rgbImage.getBufferedImage();
	}	
	
}
