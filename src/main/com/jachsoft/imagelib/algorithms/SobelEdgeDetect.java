package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class SobelEdgeDetect extends ImageOperator {
	public SobelEdgeDetect() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SobelEdgeDetect(RGBImage source){
		super(source);
	}
	
	

	public RGBImage apply() {
				
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
						int p1=(int)nbor.getValue(0, 0);
						int p2=(int)nbor.getValue(1,0);
						int p3=(int)nbor.getValue(2,0);
						int p4=(int)nbor.getValue(0,1);
						int p6=(int)nbor.getValue(2,1);
						int p7=(int)nbor.getValue(0,2);
						int p8=(int)nbor.getValue(1,2);
						int p9=(int)nbor.getValue(2,2);
						
						int gc=Math.abs((p1+2*p2+p3)-(p7+2*p8+p9));
						gc=gc+Math.abs((p3+2*p6+p9)-(p1+2*p4+p7));
						
						gc=(int)scale(gc);
						
						if (gc > 255) gc =255;
						if (gc < 0) gc = 0;
						gray.setRGB(x, y, gc, gc, gc);
					}						
				}
			}
		}
		
		return gray;
	}
}
