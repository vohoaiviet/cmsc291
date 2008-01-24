package com.jachsoft.imagelib.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jachsoft.imagelib.RGBImage;

/**
 * This class abstract a series of operators as a single
 * operator. This is useful in some algorthms where the output
 * of 1 operator is used as input of another operator.
 * 
 * Note: Some operators destroy original image instead of create a new image 
 * @author jach
 *
 */

public class SerialProcessor extends ImageOperator {
	List<ImageOperator> operators = new ArrayList<ImageOperator>();
	List<RGBImage> intermmediate = new ArrayList<RGBImage>();
	boolean storeIntermmediate=false;
	
	public SerialProcessor(){}
	
	public SerialProcessor(RGBImage source){
		super(source);
	}
	
	public void addOperator(ImageOperator operator){
		operators.add(operator);
	}
	
	public RGBImage apply(){
		RGBImage retval = source;
		Iterator<ImageOperator> ite = operators.iterator();
		while (ite.hasNext()){
			ImageOperator operator = ite.next();
			operator.setSource(retval);
			//System.out.println("Applying operator...");
			retval = operator.apply();
			if (storeIntermmediate){
				RGBImage result = new RGBImage(retval);
				intermmediate.add(result);
			}			
		}
		return retval;
	}
	
	public void setStoreIntermmediate(boolean b){
		this.storeIntermmediate = b;		
	}
	
	public boolean setStoreIntermmediate(){
		return storeIntermmediate;
	}
	
	public List<RGBImage> getIntermmediate(){
		return intermmediate;
	}
}
