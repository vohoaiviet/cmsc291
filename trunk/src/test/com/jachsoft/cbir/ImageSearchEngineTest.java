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
		String inputURL = "http://jachermocilla.googlepages.com/jach-160.jpg";
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
		
		engine.setImageDatabase(db);
		
		List results = engine.search(inputDescriptor);
		
		Iterator ite = results.iterator();
		
		while(ite.hasNext()){
			SearchResult result = (SearchResult)ite.next();
			System.out.println(result.getUrl()+":"+result.getDistanceFromInput());
		}
	}

}
