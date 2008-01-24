package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class RobertsEdgeDetect extends ImageOperator {
	
	public RobertsEdgeDetect() {
		super();
	}

	
	public RobertsEdgeDetect(RGBImage source){
		this.source = source;
	}
	
	
	public RGBImage apply(){
		RGBImage gray=source.getGrayScaleImage();		
		
		Neighbor nbor=new Neighbor(3);
		int startX=0;
		int startY=0;
		int endX=source.getWidth();
		int endY=source.getHeight();
		int offset=nbor.getOffset();
		
		startX=startX+offset;
		startY=startY+offset;
		endX=endX-offset;
		endY=endY-offset;
		
		for (int y=startY; y<endY;y++){
			for (int x=startX; x<endX;x++){
				nbor=source.getNeighbor(x, y, RGBColor.RED_CHANNEL, 3);
				for (int i=0;i<nbor.getHeight();i++){
					for (int j=0;j<nbor.getWidth();j++){
						int p1=(int)nbor.getValue(1,1);
						int p2=(int)nbor.getValue(2,1);
						int p3=(int)nbor.getValue(1,2);
						int p4=(int)nbor.getValue(2,2);
												
						int gc=Math.abs(p1-p4);
						gc=gc+Math.abs(p2-p3);
						
						gc = (int)scale(gc);
						
						if (gc > 255) gc=255; 
						if (gc < 0) gc = 0;
						gray.setRGB(x, y, gc, gc, gc);
					}						
				}
			}
		}
		
		return gray;
	}
	
}
