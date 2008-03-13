package com.jachsoft.cbir;

public interface ImageDatabase {
	int getCount();
	public void initialize();
	public ImageDatabaseEntry get(int i);
	public void add(ImageDatabaseEntry entry);
	public void shutdown();
}
