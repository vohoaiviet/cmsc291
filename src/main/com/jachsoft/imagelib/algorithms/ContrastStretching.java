package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class ContrastStretching extends ImageOperator{
	RGBImage source;
	int r1;
	int r2;
	int s1;
	int s2;
	
	float m1,m2,m3;
	float b1,b2,b3;
	
	int ulx,uly,w,h;
	
	
	public ContrastStretching(RGBImage source){
		this.source = source;
		w=this.source.getWidth();
		h=this.source.getHeight();
	}
	
	public void setParameters(int r1,int s1,int r2, int s2){
		this.r1=r1;
		this.s1=s1;
		this.r2=r2;
		this.s2=s2;
	}
	
	public void setRegion(ImageRegion r){
		this.ulx=r.getUlx();
		this.uly=r.getUly();
		this.w=r.getW();
		this.h=r.getH();
	}
	
	public RGBImage apply(){
		RGBImage retval;
		//retval = new RGBImage(source.getWidth(),source.getHeight());
		retval=source;
	
		m1=(float)s1/r1;
		m2=(float)(s2-s1)/(r2-r1);
		m3=(float)(255-s2)/(255-r2);
		
		b1=s1-m1*r1;
		b2=s2-m2*r2;
		b3=255-m3*255;
		
		int width=ulx+w;
		int height=uly+h;
		
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
					red=(int)(red*m2+b2);
				}else				
				if (red > r2 && red <= 255){
					red=(int)(red*m3+b3);
				}	
					
				if (green <= r1){
					green=(int)(green*m1+b1);
				}else
				if (green > r1 && green <= r2){
					green=(int)(green*m2+b2);
				}else
				if (green > r2 && green <= 255){
					green=(int)(green*m3+b3);
				}
				
				if (blue <= r1){
					blue=(int)(blue*m1+b1);
				}else				
				if (blue > r1 && blue <= r2){
					blue=(int)(blue*m2+b2);
				}else
				if (blue > r2 && blue <= 255){
					blue=(int)(blue*m3+b3);
				}
			
				retval.setRGB(x, y, red, green, blue);
			}
		}
		
		
		return retval;
	}
	
}
