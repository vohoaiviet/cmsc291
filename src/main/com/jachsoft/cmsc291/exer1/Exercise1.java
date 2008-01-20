package com.jachsoft.cmsc291.exer1;

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
 *   2. Mark positions of options using a bounding box
 *   3. Bounding box is adjusted to obtain the minimal box
 *   4. Compute the frequency of black in the bounding box
 *   5. Sort based on the frequency
 *   6. The one with the highest frequency is the selected option
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
	
	private void fillRect(Option o, int rgb){
		for (int y=o.y1; y <= o.y2; y++)
			for (int x=o.x1; x <= o.x2; x++){
				img.setRGB(x, y, rgb);
			}
	}
	
	private int drawRect(Option o, int rgb){
		boolean hit=false;
		//top to bottom scan
		for (int y=o.y1; !hit; y++){
			for (int x=o.x1; x <= o.x2; x++){
				if (img.getRGBColor(x, y).equals(RGBColor.BLACK)){
					o.y1=y;
					hit=true;
					break;
				}
			}
		}

		hit=false;
		//bottom to top scan
		for (int y=o.y2; !hit; y--){
			for (int x=o.x1; x <= o.x2; x++){
				if (img.getRGBColor(x, y).equals(RGBColor.BLACK)){
					o.y2=y;
					hit=true;
					break;
				}
			}
		}


		hit=false;
		//left to right
		for (int x=o.x1; !hit; x++){
			for (int y=o.y1; y <= o.y2; y++){
				if (img.getRGBColor(x, y).equals(RGBColor.BLACK)){
					o.x1=x;
					hit=true;
					break;
				}
			}
		}


		hit=false;
		//right to left
		for (int x=o.x2; !hit; x--){
			for (int y=o.y1; y <= o.y2; y++){
				if (img.getRGBColor(x, y).equals(RGBColor.BLACK)){
					o.x2=x;
					hit=true;
					break;
				}
			}
		}


		int freq_black=0;
		for (int y=o.y1; y <= o.y2; y++){
			for (int x=o.x1; x <= o.x2; x++){
				if (img.getRGBColor(x, y).equals(RGBColor.BLACK)){
					freq_black++;
				}
				if (x==o.x1 || x==o.x2){
					img.setRGB(x, y, rgb);
				}
				if (y==o.y1 || y==o.y2){
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
	    List<Option> options=new ArrayList<Option>();	    
	    while(scanner.hasNextInt()){
	    	counter++;		    	
	    	int x=scanner.nextInt();
	    	int y=scanner.nextInt();		    	
    		Option option=new Option();
    		option.x=x;
    		option.y=y;
			option.x1=x-delta;
			option.y1=y-delta;
			option.x2=x+delta;
			option.y2=y+delta;
	    	option.f=drawRect(option, 0xFF0000FF);
    		
			options.add(option);

	    	if (counter>6){
	    		//Collections.sort(options);
	    		InsertionSort.sort(options);
	
	    		Option choice=(Option)options.get(0);
	    		Option choice2=(Option)options.get(1);
			
				if ((choice.getPerimeter() - choice2.getPerimeter()) > 10)
						  choice=choice2;				
		    		
	    		if (choice.f <= 80)
	    			choice=null;
	    		//he shaded somthing
	    		if (choice !=null){
	    			fillRect(choice, 0xFFFF0000);
	    		}
	    		options.clear();
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
		
		if (args.length < 2){
			System.out.println("Usage: java -jar exer1.jar <source image> <csv file>");
			return;
		}
		
		try{
			//--/home/jachermocilla/cmsc291-workspace/cmsc291/data/exer1/0005.jpg /home/jachermocilla/cmsc291-workspace/cmsc291/data/exer1/fields39.csv
			
			img=new RGBImage(ImageIO.read(new File(args[0])));
			scanner=new Scanner(new File(args[1]));
			
			exer1=new Exercise1(img,scanner);
			exer1.process();
			
			ImageIO.write(img.getBufferedImage(),"jpg",new File("output.jpg"));
			System.out.println("Time: "+exer1.getTime()+" ms");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}


}


/*Options*/
class Option implements Comparable{
	int x1,y1,x2,y2;
	int x;
	int y;
	int f;
	
	public int compareTo(Object o){		
		Option a=(Option)o;
		return (a.f-this.f);		
	}

	public int getPerimeter(){
		return (2*(x2-x1)+2*(y2-y1));
	}
	
}

class InsertionSort {
	public static void sort(List<Option> A){
		int lengthA=A.size();
		for (int i=1; i<=(lengthA-1);i++){
			Option value=(Option)A.get(i);
			int j=i-1;
			Option Aj=(Option)A.get(j);
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
