package com.jachsoft.cbir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import junit.framework.TestCase;

public class FileImageDatabaseTest extends TestCase {
	public void testAdd() {	
		FileImageDatabase db = new FileImageDatabase();
		String img1 = "http://jachermocilla.googlepages.com/jach-160.jpg";
		String img2 = "http://jachermocilla.googlepages.com/jach.jpg";
		String img3 = "http://jachermocilla.googlepages.com/jach-cartoon.jpg";
		
		
		db.initialize();
				
		db.add(img1);
		db.add(img2);
		db.add(img3);
				
		
		System.out.println(db.getCount());
		
		List images = db.getAll();
		
		for (int i=0; i < images.size();i++){
			ImageDatabaseEntry entry = (ImageDatabaseEntry)images.get(i);
			System.out.println(entry.getUrl());
		}
		
		ImageDatabaseEntry e1 = db.get(img1);
		ImageDatabaseEntry e2 = db.get(img2);
		ImageDatabaseEntry e3 = db.get(img3);
		
		Minkowsky m = new Minkowsky(2);
		
		/*
		for (int i = 0;i < e1.getDescriptor().getBins().length;i++)
			System.out.println(e1.getDescriptor().getBins()[i]);
		*/
		
		System.out.println(m.computeDistance(e1.getDescriptor(), e2.getDescriptor()));
		System.out.println(m.computeDistance(e1.getDescriptor(), e3.getDescriptor()));
				
		
		
		
	}
}
