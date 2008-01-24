package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class SobelEdgeDetect extends ImageOperator {
	
	public SobelEdgeDetect() {
		super();
	}

	public SobelEdgeDetect(RGBImage source){
		super(source);
	}

	public RGBImage apply() {				
		RGBImage gray=source.getGrayScaleImage();		
		Neighbor nbor=new Neighbor(3);
		
		int w=source.getWidth();
		int h=source.getHeight();		
		int offset=nbor.getOffset();
		
		
		/**
		 * TODO: can be improved by removing the call to getNeighbor()
		 */
		for (int y=offset; y<(h-offset);y++){
			for (int x=offset; x<(w-offset);x++){
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

						int sx = Math.abs((p3+2*p6+p9)-(p1+2*p4+p7));
						int sy = Math.abs((p1+2*p2+p3)-(p7+2*p8+p9));
						int g = sx + sy;
						
						g=(int)scale(g);
						
						if (g > 255) g =255;
						if (g < 0) g = 0;
						gray.setRGB(x, y, g, g, g);
					}						
				}
			}
		}
		
		return gray;
	}
}
