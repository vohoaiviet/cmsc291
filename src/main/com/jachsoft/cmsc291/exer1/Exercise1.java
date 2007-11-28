package com.jachsoft.cmsc291.exer1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.BinaryImage;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;

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
	
	RGBImage img;
	Scanner scanner;
	long time;
	
	
	public Exercise1(RGBImage inputRGBImage,Scanner scanner){
		this.img = inputRGBImage;
		this.scanner=scanner;
	}
	
	private void fillRect(int x1,int y1,int x2,int y2, int rgb){
		for (int y=y1; y <= y2; y++)
			for (int x=x1; x <= x2; x++){
				img.setRGB(x, y, rgb);
			}
	}
	
	private int drawRect(int x1,int y1,int x2,int y2, int rgb){
		int freq_black=0;
		for (int y=y1; y <= y2; y++){
			for (int x=x1; x <= x2; x++){
				//System.out.println(img.getRGBColor(x, y));
				if (img.getRGBColor(x, y).equals(RGBColor.BLACK)){
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
	
	
	public RGBImage getImage(){
		return img;
	}
	
	public long getTime(){
		return time;
	}
	
	
	public void process(){
		long startTime,endTime;
		startTime=System.currentTimeMillis();
		
	    img=new BinaryImage(img,127);
	
	    /* Detection of choices */
	    int delta=13;
	    int counter=0;
	    List options=new ArrayList();	    
	    while(scanner.hasNextInt()){
	    	counter++;		    	
	    	int x=scanner.nextInt();
	    	int y=scanner.nextInt();		    	
	    	//System.out.println("("+x+","+y+")");
	    	int f=drawRect(x-delta, y-delta, x+delta, y+delta, 0xFF0000FF);
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
	    			fillRect(choice.x-delta, choice.y-delta, choice.x+delta, choice.y+delta, 0xFFFF0000);
	    		}
	    		options.clear();
	    		//System.out.println("Q");
	    		counter=0;
	    	}
	    }
        endTime=System.currentTimeMillis();
        time=endTime-startTime;
	}

	public static void main(String args[]){
		RGBImage img=null;
		Scanner scanner=null;	
		Exercise1 exer1=null;
		
		try{
			img=new RGBImage(ImageIO.read(new File("/home/jachermocilla/cmsc291-workspace/cmsc291/data/exer1/0005.jpg")));
			scanner=new Scanner(new File("/home/jachermocilla/cmsc291-workspace/cmsc291/data/exer1/fields39.csv"));
			
			exer1=new Exercise1(img,scanner);
			exer1.process();
			ImageIO.write(img.getBufferedImage(),"jpg",new File("output.jpg"));
		}catch(IOException ioe){
			ioe.printStackTrace();
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
	  Source: Wikipedia:
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
