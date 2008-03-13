package com.jachsoft.cbir;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.net.URL;

import javax.swing.ImageIcon;

import com.jachsoft.imagelib.RGBImage;

public class FileImageDatabase implements ImageDatabase {
	String fileName = "cbir.dat";
	List images = new ArrayList();
	
	
	public void shutdown(){
				
	}
	
	public void initialize(){
		
		try{
		
		File f= new File(fileName);
		
		if (!f.exists()){
			ImageDatabaseEntry initial= new ImageDatabaseEntry();
			initial.setUrl("http://www.google.com/intl/en_ALL/images/logo.gif");
			ImageIcon icon = new ImageIcon(new URL("http://www.google.com/intl/en_ALL/images/logo.gif"));
			Image image = icon.getImage();
			BufferedImage bImage = new BufferedImage(
					image.getWidth(null),
					image.getHeight(null),
					BufferedImage.TYPE_INT_RGB);
			RGBImage rgb = new RGBImage(bImage);
			
			RGBColorContentDescriptor desc = new RGBColorContentDescriptor(rgb);
			
			
			PrintWriter writer = new PrintWriter(new FileWriter(fileName)); 
			
		}
		
		
		
		BufferedReader reader= new BufferedReader(new FileReader(fileName));
		
		String line;
		while ((line=reader.readLine()) != null){
			System.out.println(line);
		}
		
		}catch(FileNotFoundException fnfe){
			
		}catch(IOException ioe){
			
		}
		
	}
	
	public void add(ImageDatabaseEntry entry){
		
	}
	

	public ImageDatabaseEntry get(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
