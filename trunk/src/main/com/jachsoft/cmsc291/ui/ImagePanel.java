package com.jachsoft.cmsc291.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import components.Corner;
import components.Rule;
import components.ScrollablePicture;

public class ImagePanel extends JPanel implements ItemListener {
	private Rule columnView;
	private Rule rowView;
	private JToggleButton isMetric;
	private ScrollablePicture picture;
	private JScrollPane pictureScrollPane;
	BufferedImage image = null;
	ImageIcon icon=null;
	
	public void show() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		//Create the row and column headers.
		columnView = new Rule(Rule.HORIZONTAL, true);
		rowView = new Rule(Rule.VERTICAL, true);

		if (icon != null) {
			columnView.setPreferredWidth(icon.getIconWidth());
			rowView.setPreferredHeight(icon.getIconHeight());
		} else {
			columnView.setPreferredWidth(320);
			rowView.setPreferredHeight(480);
		}

		//Create the corners.
		JPanel buttonCorner = new JPanel(); //use FlowLayout
		isMetric = new JToggleButton("cm", true);
		isMetric.setFont(new Font("SansSerif", Font.PLAIN, 11));
		isMetric.setMargin(new Insets(2,2,2,2));
		isMetric.addItemListener(this);
		buttonCorner.add(isMetric); 

		//Set up the scroll pane.
		picture = new ScrollablePicture(icon, columnView.getIncrement());
		pictureScrollPane=new JScrollPane(picture);
		pictureScrollPane.setPreferredSize(new Dimension(300, 250));
		pictureScrollPane.setViewportBorder(
				BorderFactory.createLineBorder(Color.black));

		pictureScrollPane.setColumnHeaderView(columnView);
		pictureScrollPane.setRowHeaderView(rowView);

		pictureScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
            buttonCorner);
		pictureScrollPane.setCorner(JScrollPane.LOWER_LEFT_CORNER,
            new Corner());
		pictureScrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER,
            new Corner());

		//Put it in this panel.
		add(pictureScrollPane);
		setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			//Turn it to metric.
			rowView.setIsMetric(true);
			columnView.setIsMetric(true);
		} else {
			//Turn it to inches.
			rowView.setIsMetric(false);
			columnView.setIsMetric(false);
		}
		picture.setMaxUnitIncrement(rowView.getIncrement());
	}


	public ImagePanel(){
		
	}
	
	public ImagePanel(BufferedImage image) {
		this.image = image;
	}
		
	public void setImage(BufferedImage image){
		this.image = image;
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
		
	}
	
}
