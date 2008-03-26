package com.jachsoft.cbir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImageSearchEngine {
	ImageDatabase db;
	List images;
	SimilarityMeasure similarity = new Minkowsky(2);
	
	public void setImageDatabase(ImageDatabase db){
		this.db = db;		
	}
	
	public List search(ImageContentDescriptor descriptor){
		images = db.getAll();
		List results = new ArrayList();		
		
		for (int i=0; i < images.size();i++){
			ImageDatabaseEntry entry = (ImageDatabaseEntry)images.get(i);
			
			SearchResult result = new SearchResult();
			result.setUrl(entry.getUrl());
			
			//each descriptor in the collection have been normalized already!
			descriptor.normalize();			
			result.setDistanceFromInput(similarity.computeDistance(descriptor, entry.getDescriptor()));
			results.add(result);						
			//System.out.println(entry.getUrl());
		}

		Collections.sort(results, new Comparator(){
			public int compare(Object a, Object b){
				SearchResult c = (SearchResult)a;
				SearchResult d = (SearchResult)b;
				
				if (c.getDistanceFromInput() < d.getDistanceFromInput())
					return -1;
				
				if (c.getDistanceFromInput() > d.getDistanceFromInput())
					return 1;
				
				return 0;
			}
		});

		return results;
	}
	
}
