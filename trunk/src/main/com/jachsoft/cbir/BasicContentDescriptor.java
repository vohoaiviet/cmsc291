package com.jachsoft.cbir;

public class BasicContentDescriptor implements ImageContentDescriptor {
        double bins[];
                    
        public BasicContentDescriptor(int size){
                bins = new double[size];
        }

        public double[] getBins() {
                return bins;
        }
        
        public void setBinValue(int i, double value){
                bins[i] = value; 
        }
        
        public double getBinValue(int i){
                return bins[i];
        }
}
