package com.jachsoft.cbir;

public class BasicContentDescriptor implements ImageContentDescriptor {
        protected double bins[];
        protected double normalized[];
        protected int size;
                    
        public BasicContentDescriptor(){
        	
        }
        
        public BasicContentDescriptor(int size){
        		this.size = size;
                bins = new double[size];
        }

        public double[] getBins() {
                return bins;
        }
        
        public double[] getNormalizedBins(){
        	if (normalized == null){
        		normalized = new double[size];
              	double max = findMax();
        		for (int i=0; i < size; i++){
        			if (max > 0){
        				normalized[i] = bins[i]/max;
        			}else{
        				normalized[i] = 0;
        			}
            	}	
        	}
        	return normalized;        	
        }
        
        private double findMax(){
        	double max=-1;
        	
        	for (int i=0; i < size; i++){
        		if (bins[i] > max)
        			max = bins[i];
        	}
        	return max;
        }
        
        
        public void setBinValue(int i, double value){
                bins[i] = value; 
        }
        
        public double getBinValue(int i){
                return bins[i];
        }
        
        public String toString(){
        	String retval="{";
        	for (int i = 0; i < bins.length; i++){
        		retval += " " + bins[i];
        	}
        	retval += " } ";
        	return retval;        	
        }
}
