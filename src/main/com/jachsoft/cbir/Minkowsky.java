package com.jachsoft.cbir;

public class Minkowsky implements SimilarityMeasure {
       private double p;
       
       public double getP() {
               return p;
       }

       public void setP(double p) {
               this.p = p;
       }

       public Minkowsky(){}
       
       public Minkowsky(int p){
               this.p = p;
       }
       
       public double computeDistance(ImageContentDescriptor input,
                       ImageContentDescriptor target) {
               
               double inputBins[] = input.getBins();
               double targetBins[] = target.getBins();
               double sum=0;
               double d = 0;
               
               if (inputBins.length != targetBins.length){
                       return -1;
               }
               
               for (int i=0; i < inputBins.length; i++){
                       sum += Math.pow(Math.abs(inputBins[i] - targetBins[i]),p);
               }
               
               d = Math.pow(sum, (1/p));
               
               return d;
       }
}
