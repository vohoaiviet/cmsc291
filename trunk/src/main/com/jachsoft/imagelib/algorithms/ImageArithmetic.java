package com.jachsoft.imagelib.algorithms;

import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;




public class ImageArithmetic extends ImageOperator {	

	public ImageArithmetic() {
		super();
	}


	public ImageArithmetic(RGBImage image) {
		super(image);
	}

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
	
	int currentOp;
	float blend=1;
	
	public ImageArithmetic(RGBImage s1, RGBImage s2){
		this.source = s1;
		this.source2 = s2;		
	}
	
	
	public void setOperation(int op){
		currentOp = op;
	}
	
	public RGBImage apply(){
		switch (currentOp){
		case ADD: return add();
		case SUB: return sub();
		case MUL: return mul();
		case DIV: return div();
		case BLEND: return blend();
		default: return source;		
		}
	}
	
	public void setBlend(float x){
		blend=x;		
	}
	
	private RGBImage blend(){
		int w = source.getWidth();
		int h = source.getHeight();
		RGBImage retval = new RGBImage(w,h);
		
		for (int y = 0; y < h; y++){
			for (int x = 0; x < w; x++){
				RGBColor color = source.getRGBColor(x, y);
				RGBColor color2 = source2.getRGBColor(x, y);
				RGBColor newColor = new RGBColor(
						(int)(blend*color.getRed()		+	(1-blend)*color2.getRed()),
						(int)(blend*color.getGreen()	+	(1-blend)*color2.getGreen()),
						(int)(blend*color.getBlue()		+	(1-blend)*color2.getBlue())						
				);				
				retval.setRGB(x, y, Math.abs(newColor.getRed()), Math.abs(newColor.getGreen()),Math.abs( newColor.getBlue()));
			}			
		}		
		return retval;
	}
	private RGBImage div(){
		int w = source.getWidth();
		int h = source.getHeight();
		RGBImage retval = new RGBImage(w,h);
		
		for (int y = 0; y < h; y++){
			for (int x = 0; x < w; x++){
				RGBColor color = source.getRGBColor(x, y);
				RGBColor color2 = source2.getRGBColor(x, y);
				RGBColor newColor = new RGBColor(
						color.getRed()		/	color2.getRed(),
						color.getGreen()	/	color2.getGreen(),
						color.getBlue()		/	color2.getBlue()						
				);				
				retval.setRGB(x, y, Math.abs(newColor.getRed()), Math.abs(newColor.getGreen()),Math.abs( newColor.getBlue()));
			}			
		}		
		return retval;
	}
	
	private RGBImage add(){
		int w = source.getWidth();
		int h = source.getHeight();
		RGBImage retval = new RGBImage(w,h);
		
		for (int y = 0; y < h; y++){
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
	
	private RGBImage sub(){
		int w = source.getWidth();
		int h = source.getHeight();
		RGBImage retval = new RGBImage(w,h);
		
		for (int y = 0; y < h; y++){
			for (int x = 0; x < w; x++){
				RGBColor color = source.getRGBColor(x, y);
				RGBColor color2 = source2.getRGBColor(x, y);
				RGBColor newColor = new RGBColor(
						color.getRed()		-	color2.getRed(),
						color.getGreen()	-	color2.getGreen(),
						color.getBlue()		-	color2.getBlue()						
				);				
				retval.setRGB(x, y, Math.abs(newColor.getRed()), Math.abs(newColor.getGreen()),Math.abs( newColor.getBlue()));
			}			
		}		
		return retval;
	}
	
	private RGBImage mul(){
		int w = source.getWidth();
		int h = source.getHeight();
		RGBImage retval = new RGBImage(w,h);
		
		for (int y = 0; y < h; y++){
			for (int x = 0; x < w; x++){
				RGBColor color = source.getRGBColor(x, y);
				RGBColor color2 = source2.getRGBColor(x, y);
				RGBColor newColor = new RGBColor(
						color.getRed()		*	color2.getRed(),
						color.getGreen()	*	color2.getGreen(),
						color.getBlue()		*	color2.getBlue()						
				);				
				retval.setRGB(x, y, Math.abs(newColor.getRed()), Math.abs(newColor.getGreen()),Math.abs( newColor.getBlue()));
			}			
		}		
		return retval;
	}
	
}
