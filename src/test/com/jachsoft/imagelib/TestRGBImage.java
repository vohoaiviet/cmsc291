package com.jachsoft.imagelib;

import junit.framework.TestCase;

public class TestRGBImage extends TestCase {
	public void testGetNeighbor() {
		RGBImage img=new RGBImage(3,3);
		img.setRGB(0, 0, 1,1,1);
		img.setRGB(1, 0, 2,2,2);
		img.setRGB(2, 0, 3,3,3);
		img.setRGB(0, 1, 4,4,4);
		img.setRGB(1, 1, 5,5,5);
		img.setRGB(2, 1, 6,6,6);
		img.setRGB(0, 2, 7,7,7);
		img.setRGB(1, 2, 8,8,8);
		img.setRGB(2, 2, 9,9,9);
		
		try{
			Neighbor nbor=img.getNeighbor(1, 1, RGBColor.ALL_CHANNELS, Neighbor.THREE);
			System.out.println(nbor);
			assertEquals(nbor.getValue(1, 1),5.0f);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
}
