package com.jachsoft.cmsc291.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.jachsoft.imagelib.ImageRegion;


public class ImagePanel extends JPanel{
	static final long serialVersionUID=0;

	BufferedImage image = null;
	ImageRegion selection;


	public ImagePanel(){
		
	}
	
	public ImagePanel(BufferedImage image) {
		this.image = image;
	}
		
	public void setImage(BufferedImage image){
		this.image = image;
		this.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
		repaint();
	}

	public BufferedImage getImage(){
		return image;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); //paint background
		Graphics2D g2 = (Graphics2D)g;
		
		if (image != null) { //there is a picture: draw it
			g2.drawImage(image, 0, 0, this);
		}
		if (selection != null){
			g2.setColor(Color.BLUE);
			g2.drawRect(selection.getUlx(), selection.getUly(),
					selection.getWidth(),selection.getHeight());
		}
	}
	
	public void updateSelection(ImageRegion selection){
		this.selection=selection;
		repaint();
	}
	
}
