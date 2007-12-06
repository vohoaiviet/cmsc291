package com.jachsoft.cmsc291.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import com.jachsoft.cmsc291.exer1.Exercise1;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.ContrastStretching;
import com.jachsoft.imagelib.algorithms.Histogram;


public class MainApp implements ActionListener {
	JFrame frame=new JFrame("CMSC 291");
	JMenuBar menubar=new JMenuBar();
	JMenu fileMenu=new JMenu("File");
	JMenu actionMenu=new JMenu("Action");
	JMenu helpMenu=new JMenu("Help");
	JMenuItem openFile = new JMenuItem("Open");
	JMenuItem saveFile = new JMenuItem("Save");
	JMenuItem grayScaleAction = new JMenuItem("Grayscale");	
	JMenuItem setAction = new JMenuItem("Student Evaluation");
	JMenuItem histogramAction = new JMenuItem("View Image Histogram");
	JMenuItem equalizeAction = new JMenuItem("Histogram Equalization");
	JMenuItem negativeAction = new JMenuItem("Negative");
	JMenuItem thresholdAction = new JMenuItem("Threshold");
	JMenuItem contrastAction = new JMenuItem("Contrast Stretching");
	
	JMenuItem aboutHelp = new JMenuItem("About");
	
	ImagePanel imagePanel=new ImagePanel();
	JFileChooser chooser=new JFileChooser();
	
	
	public MainApp(){
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		imagePanel.setPreferredSize(new Dimension(1024,768));
		frame.getContentPane().add(imagePanel, BorderLayout.CENTER);
		menubar.add(fileMenu);
		menubar.add(actionMenu);
		menubar.add(helpMenu);
		
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		
		actionMenu.add(grayScaleAction);
		actionMenu.add(setAction);
		actionMenu.add(histogramAction);
		actionMenu.add(equalizeAction);
		actionMenu.add(negativeAction);
		actionMenu.add(contrastAction);
		actionMenu.add(thresholdAction);
		
		helpMenu.add(aboutHelp);
		
		
		frame.setJMenuBar(menubar);
		
		openFile.addActionListener(this);
		saveFile.addActionListener(this);
		
		grayScaleAction.addActionListener(this);
		equalizeAction.addActionListener(this);
		histogramAction.addActionListener(this);
		setAction.addActionListener(this);
		negativeAction.addActionListener(this);
		aboutHelp.addActionListener(this);
		contrastAction.addActionListener(this);
		thresholdAction.addActionListener(this);
		
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String args[]){
		new MainApp();
	}
	
	
	public void actionPerformed(ActionEvent ae){
		if (ae.getSource().equals(aboutHelp)){
			JOptionPane.showMessageDialog(frame, "by Joseph Anthony C. Hermocilla\nfor\nCMSC 291");
		}
		if (ae.getSource().equals(thresholdAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			ContrastStretching con= new ContrastStretching(rgb);
			
			JSlider slider=new JSlider(0,255,127);
				
			JOptionPane.showMessageDialog(frame, slider);
			int t=slider.getValue();
			con.setParameters(t, 0, t, 255);
			imagePanel.setImage(con.apply().getBufferedImage());
			
			
		}
		if (ae.getSource().equals(contrastAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			ContrastStretching con= new ContrastStretching(rgb);
			
			JPanel p=new JPanel();

			JSlider sliderR1=new JSlider(0,255,127);
			JSlider sliderS1=new JSlider(0,255,127);
			JSlider sliderR2=new JSlider(0,255,127);
			JSlider sliderS2=new JSlider(0,255,127);
				
			p.setLayout(new GridLayout(4,1));
			p.add(sliderR1);
			p.add(sliderS1);
			p.add(sliderR2);
			p.add(sliderS2);
			
			JOptionPane.showMessageDialog(frame, p);
			con.setParameters(sliderR1.getValue(), sliderS1.getValue(), sliderR1.getValue(), sliderS2.getValue());
			imagePanel.setImage(con.apply().getBufferedImage());
			
			
		}
		if (ae.getSource().equals(negativeAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			imagePanel.setImage(rgb.getNegative().getBufferedImage());
		}
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
			Histogram hist= new Histogram(rgb);
			imagePanel.setImage(hist.equalize().getBufferedImage());
		}
		if (ae.getSource().equals(histogramAction)){			
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Histogram hist= new Histogram(rgb);
			JFrame fr=new JFrame("Histogram-red");
			JLabel lr=new JLabel();
			lr.setPreferredSize(new Dimension(hist.getHistogramAsImage(Histogram.RED).getWidth(),hist.getHistogramAsImage(Histogram.RED).getHeight()));
			lr.setIcon(new ImageIcon(hist.getHistogramAsImage(Histogram.RED).getBufferedImage()));
			fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fr.add(lr);
			fr.pack();
			fr.setVisible(true);
			
			JFrame fg=new JFrame("Histogram-green");
			JLabel lg=new JLabel();
			lg.setPreferredSize(new Dimension(hist.getHistogramAsImage(Histogram.RED).getWidth(),hist.getHistogramAsImage(Histogram.RED).getHeight()));
			lg.setIcon(new ImageIcon(hist.getHistogramAsImage(Histogram.GREEN).getBufferedImage()));
			fg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fg.add(lg);
			fg.pack();
			fg.setVisible(true);
			
			JFrame fb=new JFrame("Histogram-blue");
			JLabel lb=new JLabel();
			lb.setPreferredSize(new Dimension(hist.getHistogramAsImage(Histogram.RED).getWidth(),hist.getHistogramAsImage(Histogram.RED).getHeight()));
			lb.setIcon(new ImageIcon(hist.getHistogramAsImage(Histogram.BLUE).getBufferedImage()));
			fb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fb.add(lb);
			fb.pack();
			fb.setVisible(true);
			
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
