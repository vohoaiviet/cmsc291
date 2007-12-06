package com.jachsoft.cmsc291.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import com.jachsoft.cmsc291.exer1.Exercise1;
import com.jachsoft.imagelib.GrayScaleImage;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.Histogram;


public class MainApp implements ActionListener {
	JFrame frame=new JFrame("CMSC 291");
	JMenuBar menubar=new JMenuBar();
	JMenu fileMenu=new JMenu("File");
	JMenu actionMenu=new JMenu("Action");
	JMenuItem openFile = new JMenuItem("Open");
	JMenuItem saveFile = new JMenuItem("Save");
	JMenuItem grayScaleAction = new JMenuItem("Grayscale");	
	JMenuItem setAction = new JMenuItem("Student Evaluation");
	JMenuItem histogramAction = new JMenuItem("View Image Histogram");
	JMenuItem equalizeAction = new JMenuItem("Histogram Equalization");
	
	ImagePanel imagePanel=new ImagePanel();
	JFileChooser chooser=new JFileChooser();
	
	
	public MainApp(){
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		imagePanel.setPreferredSize(new Dimension(1024,768));
		frame.getContentPane().add(imagePanel, BorderLayout.CENTER);
		menubar.add(fileMenu);
		menubar.add(actionMenu);
		
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		
		actionMenu.add(grayScaleAction);
		actionMenu.add(setAction);
		actionMenu.add(histogramAction);
		actionMenu.add(equalizeAction);
		
		
		frame.setJMenuBar(menubar);
		
		openFile.addActionListener(this);
		saveFile.addActionListener(this);
		
		grayScaleAction.addActionListener(this);
		equalizeAction.addActionListener(this);
		histogramAction.addActionListener(this);
		setAction.addActionListener(this);
		
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String args[]){
		new MainApp();
	}
	
	
	public void actionPerformed(ActionEvent ae){
		if (ae.getSource().equals(openFile)){
		    int returnVal = chooser.showOpenDialog(frame);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	File file = chooser.getSelectedFile();
		    	try{
		    		BufferedImage bimg=ImageIO.read(file);
		    		imagePanel.setImage(bimg);
		    	}catch(IOException ioe){
		    		ioe.printStackTrace();
		    	}
		    }
		}
		
		if (ae.getSource().equals(saveFile)){
		    int returnVal = chooser.showSaveDialog(frame);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	File file = chooser.getSelectedFile();
		    	try{		    		
		    		RGBImage rgb=new RGBImage(imagePanel.getImage());
		    		ImageIO.write(rgb.getBufferedImage(),"jpg",file);
		    	}catch(IOException ioe){ioe.printStackTrace();}
		    }
		}		
		if (ae.getSource().equals(grayScaleAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			imagePanel.setImage(rgb.getGrayScaleImage().getBufferedImage());
		}
		if (ae.getSource().equals(equalizeAction)){			
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Histogram hist= new Histogram(rgb.getGrayScaleImage());
			imagePanel.setImage(hist.equalize().getBufferedImage());
		}
		if (ae.getSource().equals(histogramAction)){			
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Histogram hist= new Histogram(rgb.getGrayScaleImage());
			imagePanel.setImage(rgb.getGrayScaleImage().getBufferedImage());
			JFrame f=new JFrame("Histogram");
			JLabel l=new JLabel();
			l.setPreferredSize(new Dimension(hist.getHistogramAsImage().getWidth(),hist.getHistogramAsImage().getHeight()));
			l.setIcon(new ImageIcon(hist.getHistogramAsImage().getBufferedImage()));
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.add(l);
			f.pack();
			f.setVisible(true);			
		}
		if (ae.getSource().equals(setAction)){			
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			int returnVal = chooser.showOpenDialog(frame);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	File file = chooser.getSelectedFile();
		    	try{
		    		Scanner scanner=new Scanner(file);
		    		Exercise1 exer1=new Exercise1(rgb,scanner);
		    		exer1.process();
		    		imagePanel.setImage(rgb.getBufferedImage());
		    	}catch(IOException ioe){ioe.printStackTrace();
		    	}
		    }		    			
		}
	}
}
