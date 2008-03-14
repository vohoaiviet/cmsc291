package com.jachsoft.cbir;

public class SearchResult {
	private String url;
	private double distanceFromInput;
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public double getDistanceFromInput() {
		return distanceFromInput;
	}
	
	public void setDistanceFromInput(double distanceFromInput) {
		this.distanceFromInput = distanceFromInput;
	}
	
}
