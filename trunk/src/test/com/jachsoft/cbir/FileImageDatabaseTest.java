package com.jachsoft.cbir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import junit.framework.TestCase;

public class FileImageDatabaseTest extends TestCase {
	public void testAdd() {	
		FileImageDatabase db = new FileImageDatabase();
		db.add("http://www.google.com.ph/intl/en_com/images/logo_plain.png");
		db.add("http://images.google.com.ph/intl/tl_ALL/images/images_hp.gif");
		System.out.println(db.getCount());
		
		List images = db.getAllImages();
		
		for (int i=0; i < images.size();i++){
			ImageDatabaseEntry entry = (ImageDatabaseEntry)images.get(i);
			System.out.println(entry.getUrl());
		}
		
		
	}
}
