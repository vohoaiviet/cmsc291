package com.jachsoft.cbir;

import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.Histogram;

public class RGBColorContentDescriptor implements ImageContentDescriptor {
               
       RGBImage img;
       Histogram hist;
       
       public RGBColorContentDescriptor(RGBImage img){
               this.img = img;
               hist = new Histogram(img);
       }
       
       public double[] getBins() {
               return null;
       }
       
       public ImageContentDescriptor getRedDescriptor(){
               BasicContentDescriptor retval = null;          
               retval = new BasicContentDescriptor(256);              
               int[] colorDist = hist.getRed();
               for (int i=0;i<colorDist.length;i++){
                       retval.setBinValue(i, colorDist[i]);
               }
               return retval;
       }
       
       public ImageContentDescriptor getGreenDescriptor(){
               BasicContentDescriptor retval = null;          
               retval = new BasicContentDescriptor(256);              
               int[] colorDist = hist.getGreen();
               for (int i=0;i<colorDist.length;i++){
                       retval.setBinValue(i, colorDist[i]);
               }
               return retval;  }
       
       public ImageContentDescriptor getBlueDescriptor(){
               BasicContentDescriptor retval = null;          
               retval = new BasicContentDescriptor(256);              
               int[] colorDist = hist.getBlue();
               for (int i=0;i<colorDist.length;i++){
                       retval.setBinValue(i, colorDist[i]);
               }
               return retval;  
       }
}
