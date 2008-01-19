package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

public class ImageArithmetic extends ImageOperator {	
	RGBImage source;
	RGBImage source2;
	
	public static final int ADD = 0;
	public static final int SUB = 1;
	public static final int MUL = 2;
	public static final int DIV = 3	;
	public static final int BLEND = 4;
	public static final int AND = 5;
	public static final int NAND = 6;
	public static final int OR = 7;
	public static final int NOR = 8;
	public static final int XOR = 9;
	public static final int XNOR = 10;
	public static final int NOT = 11;
	public static final int SHIFT = 12;
	
	
	private RGBImage add(){
		int w = source.getWidth();
		int h = source.getHeight();
		RGBImage retval = new RGBImage(w,h);
		
		for (int y=0; y < h; y++){
			for (int x = 0; x < w; x++){
				RGBColor color = source.getRGBColor(x, y);
				RGBColor color2 = source2.getRGBColor(x, y);
				RGBColor newColor = new RGBColor(
						color.getRed()		+	color2.getRed(),
						color.getGreen()	+	color2.getGreen(),
						color.getBlue()		+	color2.getBlue()						
				);
				retval.setRGB(x, y, newColor.getRed(), newColor.getGreen(), newColor.getBlue());
			}			
		}		
		return retval;
	}
	
}
