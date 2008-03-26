package com.jachsoft.cbir;

import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.Histogram;

public class RGBColorContentDescriptor extends BasicContentDescriptor {
               
       RGBImage img;
       Histogram hist;
       
       public RGBColorContentDescriptor(RGBImage img){
    	   		super(768);
    	   		this.img = img;
    	   		hist = new Histogram(img);
    	
    	   		
    	   		/**
    	         *  0-255 : RED
    	         *  256-511:GREEN
    	         *  512-767:BLUE
    	         */
    	   		
                int colorDist[];
                int i,j;
                
                colorDist = hist.getRed();
                for (i=0,j=0;i < 256;i++,j++){
             	   bins[i] = colorDist[j];
                }
                
                colorDist = hist.getGreen();
                for (j=0;i < 512;i++,j++){
             	   bins[i] = colorDist[j];
                }
                
                colorDist = hist.getBlue();
                for (j=0;i < 768;i++,j++){
             	   bins[i] = colorDist[j];
                }
       }
}
