package com.jachsoft.cbir;

public interface ImageDatabase {
	int getCount();
	public void initialize();
	public void add(ImageDatabaseEntry entry);
	public void add(String url);
	public void shutdown();
}
