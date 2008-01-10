package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class GraySlicing extends ImageOperator {
	RGBImage source;
	int r0;
	int s0;
	int r1;
	int r2;
	int s1;
	int s2;
	int r3;
	int s3;
	int intensity;
	
	float m1,m2,m3;
	float b1,b2,b3;
	
	int ulx,uly,w,h;
	
	public GraySlicing(RGBImage source){
		this.source = source;
		this.w=this.source.getWidth();
		this.h=this.source.getHeight();
	}
	
	public void setParameters(int r0,int s0,int r1,int s1,int r2, int s2,int r3,int s3,int intensity){
		this.r0=r0;
		this.s0=s0;
		this.r1=r1;
		this.s1=s1;
		this.r2=r2;
		this.s2=s2;
		this.s3=s3;
		this.r3=r3;
		this.intensity=intensity;
	}
	
	public void setRegion(ImageRegion r){
		this.ulx=r.getUlx();
		this.uly=r.getUly();
		this.w=r.getW();
		this.h=r.getH();
	}
	
	public RGBImage apply(){
		RGBImage retval;
		retval = source;//new RGBImage(source.getWidth(),source.getHeight());
	
		m1=(float)(s1-s0)/(r1-r0);
		m2=(float)(s2-s1)/(r2-r1);
		m3=(float)(s3-s2)/(r3-r2);
		
		b1=s1-m1*r1;
		b2=s2-m2*r2;
		b3=s3-m3*r3;
		
		int width=ulx+this.w;
		int height=uly+this.h;
		
		for (int y=uly;y<height;y++){
			for (int x=ulx;x<width;x++){
				RGBColor color=source.getRGBColor(x, y);
				int red=color.getRed();
				int green=color.getGreen();
				int blue=color.getBlue();
				
				if (red <= r1){
					red=(int)(red*m1+b1);
				}else
				if (red > r1 && red <= r2){
					red=intensity;
				}else				
				if (red > r2 && red <= r3){
					red=(int)(red*m3+b3);
				}	
					
				if (green <= r1){
					green=(int)(green*m1+b1);
				}else
				if (green > r1 && green <= r2){
					green=intensity;
				}else
				if (green > r2 && green <= r3){
					green=(int)(green*m3+b3);
				}
				
				if (blue <= r1){
					blue=(int)(blue*m1+b1);
				}else				
				if (blue > r1 && blue <= r2){
					blue=intensity;
				}else
				if (blue > r2 && blue <= r3){
					blue=(int)(blue*m3+b3);
				}
			
				retval.setRGB(x, y, red, green, blue);
			}
		}
		
		
		return retval;
	}
	
}
