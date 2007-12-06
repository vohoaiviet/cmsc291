package com.jachsoft.cmsc291.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import com.jachsoft.imagelib.GrayScaleImage;
import com.jachsoft.imagelib.RGBImage;


public class MainApp implements ActionListener {
	JFrame frame=new JFrame("CMSC 291");
	JMenuBar menubar=new JMenuBar();
	JMenu fileMenu=new JMenu("File");
	JMenu actionMenu=new JMenu("Action");
	JMenuItem openFile = new JMenuItem("Open");
	JMenuItem grayScaleAction = new JMenuItem("Grayscale");
	
	ImagePanel imagePanel=new ImagePanel();
	JFileChooser chooser=new JFileChooser();
	
	
	public MainApp(){
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		imagePanel.setPreferredSize(new Dimension(1024,768));
		frame.getContentPane().add(imagePanel, BorderLayout.CENTER);
		menubar.add(fileMenu);
		menubar.add(actionMenu);
		
		fileMenu.add(openFile);
		actionMenu.add(grayScaleAction);
		
		frame.setJMenuBar(menubar);
		
		openFile.addActionListener(this);
		grayScaleAction.addActionListener(this);
		
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
		    	}catch(IOException ioe){ioe.printStackTrace();}
		    }
		}
		if (ae.getSource().equals(grayScaleAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			imagePanel.setImage(rgb.getGrayScaleImage().getBufferedImage());
		}
	}
}
