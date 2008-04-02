package com.jachsoft.cbir;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import com.jachsoft.imagelib.RGBImage;

import junit.framework.TestCase;

public class ImageSearchEngineTest extends TestCase {
	
	public void testSearch(){
		ImageSearchEngine engine = new ImageSearchEngine();
		FileImageDatabase db = new FileImageDatabase();
		String inputURL = "http://jachermocilla.googlepages.com/jach-cartoon.jpg";
		RGBImage input= null;
		RGBColorContentDescriptor inputDescriptor;
		
		try{			
			URL ul = new URL(inputURL);
	        input = new RGBImage(ImageIO.read(ul));		
		}catch(MalformedURLException e){
			System.out.println("Error in URL!");
		}catch(IOException ioe){
			System.out.println("Error reading image!");
		}
		
		inputDescriptor = new RGBColorContentDescriptor(input); 
		
		db.initialize();
		
		db.add("http://orlandonest.files.wordpress.com/2007/09/dog.jpg");
		
		engine.setImageDatabase(db);
		
		List results = engine.search(inputDescriptor);
		
		Iterator ite = results.iterator();
		
		while(ite.hasNext()){
			SearchResult result = (SearchResult)ite.next();
			System.out.println(result.getUrl()+":"+result.getDistanceFromInput());
		}
	}
	
	
	public void testSearchOutline(){
		ImageSearchEngine engine = new ImageSearchEngine();
		FileImageDatabase db = new FileImageDatabase("dahon.dat");
		String inputURL = "http://localhost/leaf/mango1807.jpg";
		RGBImage input= null;
		OutlineDescriptor inputDescriptor;
		
		try{			
			URL ul = new URL(inputURL);
	        input = new RGBImage(ImageIO.read(ul));		
		}catch(MalformedURLException e){
			System.out.println("Error in URL!");
		}catch(IOException ioe){
			System.out.println("Error reading image!");
		}
		
		inputDescriptor = new OutlineDescriptor(input); 
		
		db.initialize();
		
		ImageDatabaseEntry entry;
		RGBImage img;
		OutlineDescriptor outline=null;		
		String url1;
		
		url1="http://localhost/leaf/banaba1779.jpg";		
		entry = new ImageDatabaseEntry();
		entry.setUrl(url1);
		try{			
			URL ul = new URL(url1);
	        img = new RGBImage(ImageIO.read(ul));
	        outline = new OutlineDescriptor(img);
		}catch(MalformedURLException e){
			System.out.println("Error in URL!");
		}catch(IOException ioe){
			System.out.println("Error reading image!");
		}
		entry.setDescriptor(outline);
		db.add(entry);
		
		url1="http://localhost/leaf/banaba1776.jpg";		
		entry = new ImageDatabaseEntry();
		entry.setUrl(url1);
		try{			
			URL ul = new URL(url1);
	        img = new RGBImage(ImageIO.read(ul));
	        outline = new OutlineDescriptor(img);
		}catch(MalformedURLException e){
			System.out.println("Error in URL!");
		}catch(IOException ioe){
			System.out.println("Error reading image!");
		}
		entry.setDescriptor(outline);
		db.add(entry);
		
		url1="http://localhost/leaf/banaba1782.jpg";		
		entry = new ImageDatabaseEntry();
		entry.setUrl(url1);
		try{			
			URL ul = new URL(url1);
	        img = new RGBImage(ImageIO.read(ul));
	        outline = new OutlineDescriptor(img);
		}catch(MalformedURLException e){
			System.out.println("Error in URL!");
		}catch(IOException ioe){
			System.out.println("Error reading image!");
		}
		entry.setDescriptor(outline);
		db.add(entry);
		
		url1="http://localhost/leaf/mango1808.jpg";		
		entry = new ImageDatabaseEntry();
		entry.setUrl(url1);
		try{			
			URL ul = new URL(url1);
	        img = new RGBImage(ImageIO.read(ul));
	        outline = new OutlineDescriptor(img);
		}catch(MalformedURLException e){
			System.out.println("Error in URL!");
		}catch(IOException ioe){
			System.out.println("Error reading image!");
		}
		entry.setDescriptor(outline);
		db.add(entry);
		
		url1="http://localhost/leaf/mango1811.jpg";		
		entry = new ImageDatabaseEntry();
		entry.setUrl(url1);
		try{			
			URL ul = new URL(url1);
	        img = new RGBImage(ImageIO.read(ul));
	        outline = new OutlineDescriptor(img);
		}catch(MalformedURLException e){
			System.out.println("Error in URL!");
		}catch(IOException ioe){
			System.out.println("Error reading image!");
		}
		entry.setDescriptor(outline);
		db.add(entry);
		
		url1="http://localhost/leaf/mango1806.jpg";		
		entry = new ImageDatabaseEntry();
		entry.setUrl(url1);
		try{			
			URL ul = new URL(url1);
	        img = new RGBImage(ImageIO.read(ul));
	        outline = new OutlineDescriptor(img);
		}catch(MalformedURLException e){
			System.out.println("Error in URL!");
		}catch(IOException ioe){
			System.out.println("Error reading image!");
		}
		entry.setDescriptor(outline);
		db.add(entry);
		
		
		db.save();
		
		
		engine.setImageDatabase(db);
		
		List results = engine.search(inputDescriptor);
		
		Iterator ite = results.iterator();
		
		while(ite.hasNext()){
			SearchResult result = (SearchResult)ite.next();
			System.out.println(result.getUrl()+":"+result.getDistanceFromInput());
		}
	}

}
