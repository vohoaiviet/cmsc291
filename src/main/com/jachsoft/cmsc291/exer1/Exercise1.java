package com.jachsoft.cmsc291.exer1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Objective:
 *   Detect the answers in a scanned image of a questionnaire
 * 
 * Approach:
 *   1. Binarize the image
 *   2. Mark positions of options uisng a bounding box
 *   3. Compute the frequency of black in the bounding box
 *   4. Sort based on the frequency
 *   5. The one woth the highest frequency is the selected option
 * 
 * @author Joseph Anthony C. Hermocilla
 * 
 */


public class Exercise1 {

	public static void fillRect(int x1,int y1,int x2,int y2, int rgb,BufferedImage img){
		for (int y=y1; y <= y2; y++)
			for (int x=x1; x <= x2; x++){
				img.setRGB(x, y, rgb);
			}
	}
	
	
	public static int drawRect(int x1,int y1,int x2,int y2, int rgb,BufferedImage img){
		int freq_black=0;
		for (int y=y1; y <= y2; y++){
			for (int x=x1; x <= x2; x++){
				//System.out.println(img.getRGB(x, y));
				if (img.getRGB(x, y)==-16777216){
					freq_black++;
				}
				if (x==x1 || x==x2){
					img.setRGB(x, y, rgb);
				}
				if (y==y1 || y==y2){
					img.setRGB(x, y, rgb);
				}
			}		
		}
		return freq_black;
	}
	
	
	public static void main(String args[]){
				
		if (args.length < 2){
			System.out.println("Usage: java -jar exer1.jar <image path> <coordinates file>");
			return;
		}
		
		
		String dataSrc="/home/jachermocilla/cmsc291/data/exer1/";
/*		
		for (int i=1;i<=9;i++){
			Exercise1.doit(dataSrc+"000"+i+"");
		}		
		
		for (int i=10;i<=99;i++){
			Exercise1.doit(dataSrc+"00"+i+"");
		}		
		Exercise1.doit("0100");
*/		
		
		Exercise1.doit(dataSrc+"0005"); 
	}
		
		
	public static void doit(String fname){
		long startTime,endTime;
		BufferedImage img = null;
		try {
			System.out.print("Processing "+fname+"...");
			startTime=System.currentTimeMillis();
		    img = ImageIO.read(new File(fname));
		    
		    /* Binarize the image */
		    for (int y=0;y < img.getHeight(); y++){
		    	for (int x=0; x < img.getWidth(); x++){
		    		int rgb=img.getRGB(x, y);
		    		
		    		int b = rgb & 255;
		    		int g = (rgb >> 8) & 255;
		    		int r = (rgb >> 16) & 255;
		    		int a = (rgb >> 24) & 255;
		    		double ave=(b+r+g)/3;
/*	
		    		int iave=(int)ave;
		    		int color = 255 << 8;
		    		color |= iave;
		    		color <<= 8;
		    		color |= iave;
		    		color <<= 8;
		    		color |= iave;
		    		img.setRGB(x, y, color);
*/		    	
		    		
		    		int threshold=127;
		    		int white = 0xFFFFFFFF;
		    		if (ave > threshold){
		    			img.setRGB(x, y, white);
		    		}else{
		    			img.setRGB(x, y, 0x00000000);
		    		}
		    		//System.out.println(a+","+r+","+g+","+b);
		    	}		    	
		    }
		    
		    
		    
		    /* Detection of choices */
		    int delta=13;
		    Scanner scanner=new Scanner(new File("/home/jachermocilla/cmsc291/data/exer1/fields39.csv"));
		    int counter=0;
		    List options=new ArrayList();
		    while(scanner.hasNextInt()){
		    	counter++;		    	
		    	int x=scanner.nextInt();
		    	int y=scanner.nextInt();		    	
		    	//System.out.println("("+x+","+y+")");
		    	int f=Exercise1.drawRect(x-delta, y-delta, x+delta, y+delta, 0xFF0000FF, img);
		    	//System.out.println(f);
	    		Option option=new Option();
	    		option.x=x;
	    		option.y=y;
	    		option.f=f;
	    		options.add(option);

		    	if (counter>6){
		    		//Collections.sort(options);
		    		InsertionSort.sort(options);
		    		
		    		int thresh=40;
		    		Option choice;
		    		Option choice1=(Option)options.get(0);
		    		Option choice2=(Option)options.get(1);
		    		int dist=(choice1.f-choice2.f);
		    		//System.out.println(choice1.f-choice2.f);
		    		
		    		if ((dist <= thresh) && (dist > 5)){
		    			choice=choice2;
		    		}else if (dist <= 5){
		    			choice=null;
		    		}else{
		    			choice=choice1;
		    		}
		    		
		    		//disable heuristic by hardcoding choice to choice1!
		    		choice=choice1;
		    		//System.out.println(choice.f);
		    		
		    		if (choice.f <= 75)
		    			choice=null;
		    		//he shaded somthing
		    		if (choice !=null){
		    			Exercise1.fillRect(choice.x-delta, choice.y-delta, choice.x+delta, choice.y+delta, 0xFFFF0000, img);
		    		}
		    		options.clear();
		    		//System.out.println("Q");
		    		counter=0;
		    	}
		    	
		    	
		    }
		    File outputfile = new File(fname+"-done.jpg");
	        ImageIO.write(img, "jpg", outputfile);
	        endTime=System.currentTimeMillis();
	        double secs=(endTime-startTime)/1000.0;
	        System.out.println("done! "+(secs)+ "s");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


/*Options*/
class Option implements Comparable{
	int x;
	int y;
	int f;
	
	public int compareTo(Object o){		
		Option a=(Option)o;
		//System.out.println(this.f+","+a.f);
		return (a.f-this.f);		
	}	
}

class InsertionSort {
 	
	
	/*
	  Source: Eikipedia:
	  insertionSort(array A)
        for i = 1 to length[A]-1 do
        value = A[i] 
        j = i-1
        while j >= 0 and A[j] > value do
            A[j + 1] = A[j]
            j = j-1
        A[j+1] = value
	*/
	
	public static void sort(List A){
		int lengthA=A.size();
		for (int i=1; i<=(lengthA-1);i++){
			Comparable value=(Comparable)A.get(i);
			int j=i-1;
			Comparable Aj=(Comparable)A.get(j);
			while (j >= 0 && (Aj.compareTo(value) > 0)){				
				A.set(j+1, Aj);				
				j=j-1;			
				if (j>=0){
					Aj=(Option)A.get(j);
				}
			}
			A.set(j+1, value);
		}
	}	
}
