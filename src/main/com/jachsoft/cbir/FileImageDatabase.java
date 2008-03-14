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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import com.jachsoft.imagelib.RGBImage;

public class FileImageDatabase implements ImageDatabase {
	String fileName = "cbir.dat";
	Map	images = new HashMap();
	
	
	public void shutdown(){
				
	}
	
	public void initialize(){
		
		try{
		
		File f= new File(fileName);
		
		if (!f.exists()){
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
	
	public void add(String url){
		images.put(url,createEntry(url));
	}
	
	private ImageDatabaseEntry createEntry(String url){
		ImageDatabaseEntry entry= new ImageDatabaseEntry();
		ImageIcon icon = null;
		entry.setUrl(url);
		try{
		 icon = new ImageIcon(new URL(url));
		}catch(MalformedURLException e){
			System.out.println("Error in URL!");
		}
		Image image = icon.getImage();
		BufferedImage bImage = new BufferedImage(
				image.getWidth(null),
				image.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		RGBImage rgb = new RGBImage(bImage);
		entry.setDescriptor(new RGBColorContentDescriptor(rgb));
		return entry;	
		
	}
	
	public Collection getAllImages() {
		return images.values();
	}
	
	public int getCount() {
		return images.size();
	}
}
