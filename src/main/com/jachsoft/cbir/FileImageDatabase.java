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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.jachsoft.imagelib.RGBImage;

public class FileImageDatabase implements ImageDatabase {
	String fileName = "cbir.dat";
	Map	images = new HashMap();
	BufferedReader reader;
	PrintWriter writer;
	
	
	public void shutdown(){
				
	}
	
	public void initialize(){
		
		try{
		
		File f= new File(fileName);			
		
		if (!f.exists()){			
			add("http://jachermocilla.googlepages.com/jach-160.jpg");						
			save();			
		}
				
		reader= new BufferedReader(new FileReader(fileName));		
		String line;
		while ((line=reader.readLine()) != null){
			ImageDatabaseEntry entry = new ImageDatabaseEntry();
			String[] tokens = line.split(","); 
			entry.setUrl(tokens[0]);
			//System.out.println(tokens.length);
			BasicContentDescriptor descriptor = new BasicContentDescriptor(tokens.length - 1);
			for (int i = 1; i < tokens.length;i++){
				descriptor.setBinValue(i-1, Double.parseDouble(tokens[i]));
			}			
			entry.setDescriptor(descriptor);
			this.add(entry);
			//System.out.println(line);
		}
		
		}catch(FileNotFoundException fnfe){
			System.out.println("File not found");
		}catch(IOException ioe){
			System.out.println("I/O Error");
		}
		
	}
	
	public void add(ImageDatabaseEntry entry){
		images.put(entry.getUrl(), entry);
	}
	
	public void add(String url){
		images.put(url,createEntry(url));
	}
	
	private ImageDatabaseEntry createEntry(String url){
		ImageDatabaseEntry entry= new ImageDatabaseEntry();
		BufferedImage bImage= null;
		entry.setUrl(url);
		
		try{			
			URL ul = new URL(url);
	        bImage = ImageIO.read(ul);		
		}catch(MalformedURLException e){
			System.out.println("Error in URL!");
		}catch(IOException ioe){
			System.out.println("Error reading image!");
		}
		
		
		RGBImage rgb = new RGBImage(bImage);
		entry.setDescriptor(new RGBColorContentDescriptor(rgb));
		return entry;		
	}
	
	public void save(){
		try{
			writer = new PrintWriter(new FileWriter(fileName));
		
		
		ArrayList retval = new ArrayList();
		Iterator ite = images.keySet().iterator();
		while (ite.hasNext()){
			String key = (String)ite.next();
			ImageDatabaseEntry entry = (ImageDatabaseEntry)images.get(key);
			saveEntry(entry);
		}
		writer.close();
		
		}catch(IOException ioe){
			System.out.println("I/O Error");
		}
	}
	
	private void saveEntry(ImageDatabaseEntry entry){
		String line = "";
		line += entry.getUrl();
		double[] bins = entry.getDescriptor().getBins(); 
		int n = bins.length;
		for (int i = 0; i < n; i++){
			line += ","+ bins[i];
		}		
		//System.out.println(line);
		writer.println(line);
	}
	
	
	public List getAll() {
		ArrayList retval = new ArrayList();
		Iterator ite = images.keySet().iterator();
		while (ite.hasNext()){
			String key = (String)ite.next();
			retval.add(images.get(key));			
		}
		return retval;		
	}
	
	public ImageDatabaseEntry get(String url){
		return (ImageDatabaseEntry)images.get(url);
	}
	
	public int getCount() {
		return images.size();
	}
	
	
	
}
