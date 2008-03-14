package com.jachsoft.cbir;

import java.util.List;

public interface ImageDatabase {
	int getCount();
	public void initialize();
	public void add(ImageDatabaseEntry entry);
	public void add(String url);
	public ImageDatabaseEntry get(String url);
	public void shutdown();
	public List getAll();
}
