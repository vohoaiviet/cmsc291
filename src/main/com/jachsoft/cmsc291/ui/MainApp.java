package com.jachsoft.cmsc291.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;

import com.jachsoft.cmsc291.exer1.Exercise1;
import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.Neighbor;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.algorithms.ContrastStretching;
import com.jachsoft.imagelib.algorithms.Convolution;
import com.jachsoft.imagelib.algorithms.DynamicCompression;
import com.jachsoft.imagelib.algorithms.Equalization;
import com.jachsoft.imagelib.algorithms.GraySlicing;
import com.jachsoft.imagelib.algorithms.Histogram;
import com.jachsoft.imagelib.algorithms.ImageRegion;
import com.jachsoft.imagelib.algorithms.PowerLawTransformation;


public class MainApp implements ActionListener {
	String title="CMSC 291-Digital Image Processing Workbench";
	JFrame frame=new JFrame(title);
	JMenuBar menubar=new JMenuBar();
	JMenu fileMenu=new JMenu("File");
	JMenu selectionMenu=new JMenu("Selection");
	JMenu actionMenu=new JMenu("Enhancements");
	JMenu filterMenu=new JMenu("Filters");
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
	JMenuItem compressAction = new JMenuItem("Dynamic Range Compression");
	JMenuItem sliceAction = new JMenuItem("Gray Level Slicing");
	JMenuItem powerLawAction = new JMenuItem("Power Law (Gamma Correction)");
	JMenuItem meanFilter = new JMenuItem("Mean Filter (5x5)");
	
	JMenuItem selectAllSelection = new JMenuItem("Select All");
	JMenuItem selectRegionSelection = new JMenuItem("Select Region");
	
	JToolBar statusBar=new JToolBar();
	JLabel curr=new JLabel("Pixel at (x,y)");
	
	
	JMenuItem aboutHelp = new JMenuItem("About");
	
	ImagePanel imagePanel=new ImagePanel();
	JFileChooser chooser=new JFileChooser();
	
	ImageRegion selection;
	
	
	String currentPixel="";
	
	public MainApp(){
	
		statusBar.add(curr);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		imagePanel.setPreferredSize(new Dimension(800,600));
		frame.getContentPane().add(new JScrollPane(imagePanel), BorderLayout.CENTER);
		frame.getContentPane().add(statusBar,BorderLayout.SOUTH);
		menubar.add(fileMenu);
		menubar.add(selectionMenu);
		menubar.add(actionMenu);
		menubar.add(filterMenu);
		menubar.add(helpMenu);
		
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		
		selectionMenu.add(selectAllSelection);
		selectionMenu.add(selectRegionSelection);
		
		actionMenu.add(grayScaleAction);
		actionMenu.add(setAction);
		actionMenu.add(histogramAction);
		actionMenu.add(equalizeAction);
		actionMenu.add(negativeAction);
		actionMenu.add(contrastAction);
		actionMenu.add(thresholdAction);
		actionMenu.add(compressAction);
		actionMenu.add(sliceAction);
		actionMenu.add(powerLawAction);
		
		filterMenu.add(meanFilter);
		
		helpMenu.add(aboutHelp);
		
		
		frame.setJMenuBar(menubar);
		
		openFile.addActionListener(this);
		saveFile.addActionListener(this);
		
		selectAllSelection.addActionListener(this);
		selectRegionSelection.addActionListener(this);
		
		
		grayScaleAction.addActionListener(this);
		equalizeAction.addActionListener(this);
		histogramAction.addActionListener(this);
		setAction.addActionListener(this);
		negativeAction.addActionListener(this);
		aboutHelp.addActionListener(this);
		contrastAction.addActionListener(this);
		thresholdAction.addActionListener(this);
		sliceAction.addActionListener(this);
		compressAction.addActionListener(this);
		powerLawAction.addActionListener(this);
		meanFilter.addActionListener(this);
		
		//Display the window.
		frame.pack();
		frame.setVisible(true);
		
		
		
		
		imagePanel.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me){
				int x=me.getX();
				int y=me.getY();
				
				if (imagePanel.getImage() != null){
				
					int w=imagePanel.getImage().getWidth();
					int h=imagePanel.getImage().getHeight();
				
					if ((x < w) && (y < h)){
				
						RGBImage rgb=new RGBImage(imagePanel.getImage());
						RGBColor col=rgb.getRGBColor(x, y);
						currentPixel="("+x+","+y+"):("+col.getRed()+","+col.getGreen()+","+col.getBlue()+")";
						curr.setText(currentPixel);
					}
				}
			}
		});
	}
	
	public static void main(String args[]){
		new MainApp();
	}
	
	
	public void actionPerformed(ActionEvent ae){
		if (ae.getSource().equals(meanFilter)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Convolution operator=new Convolution(rgb);			
			ConvolutionKernel kernel=ConvolutionKernel.meanFilter(Neighbor.FIVE);
			operator.setParameters(kernel,Neighbor.FIVE);
			operator.setRegion(selection);
			imagePanel.setImage(operator.apply().getBufferedImage());
		}
		if (ae.getSource().equals(selectAllSelection)){
			selection.setUlx(0);
			selection.setUly(0);
			selection.setH(imagePanel.getImage().getHeight());
			selection.setW(imagePanel.getImage().getWidth());
			imagePanel.updateSelection(selection);
		}
		if (ae.getSource().equals(selectRegionSelection)){
			int w=imagePanel.getImage().getWidth();
			int h=imagePanel.getImage().getHeight();
			
			JPanel p=new JPanel();
			
			JSlider sliderR1=new JSlider(0,w,selection.getUlx());
			sliderR1.setLabelTable(sliderR1.createStandardLabels(w/4));			
			sliderR1.setPaintLabels(true);
			sliderR1.setPaintTicks(true);
			JSlider sliderS1=new JSlider(0,h,selection.getUly());
			sliderS1.setLabelTable(sliderS1.createStandardLabels(h/4));
			sliderS1.setPaintLabels(true);
			sliderS1.setPaintTicks(true);
			JSlider sliderR2=new JSlider(0,w,selection.getUlx()+selection.getW());
			sliderR2.setLabelTable(sliderR2.createStandardLabels(w/4));			
			sliderR2.setPaintLabels(true);
			sliderR2.setPaintTicks(true);
			JSlider sliderS2=new JSlider(0,h,selection.getUly()+selection.getH());
			sliderS2.setLabelTable(sliderS2.createStandardLabels(h/4));			
			sliderS2.setPaintLabels(true);
			sliderS2.setPaintTicks(true);
				
			p.setLayout(new GridLayout(4,2));
			p.add(new JLabel("ULX"));
			p.add(sliderR1);
			p.add(new JLabel("ULY"));
			p.add(sliderS1);
			p.add(new JLabel("LRX"));
			p.add(sliderR2);
			p.add(new JLabel("LRY"));
			p.add(sliderS2);
			
			JOptionPane.showMessageDialog(frame, p);
			
			selection.setUlx(sliderR1.getValue());
			selection.setUly(sliderS1.getValue());
			selection.setW(sliderR2.getValue()-sliderR1.getValue());
			selection.setH(sliderS2.getValue()-sliderS1.getValue());
			imagePanel.updateSelection(selection);
			
		}		
		if (ae.getSource().equals(aboutHelp)){
			JOptionPane.showMessageDialog(frame, "by Joseph Anthony C. Hermocilla\nfor\nCMSC 291");
		}
		if (ae.getSource().equals(powerLawAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			PowerLawTransformation operator=new PowerLawTransformation(rgb);			
			JSlider slider=new JSlider(0,500,1);
			slider.setLabelTable(slider.createStandardLabels(100));
			slider.setPaintLabels(true);
			JOptionPane.showMessageDialog(frame, slider);
			operator.setParameters(1,slider.getValue()/100f);
			operator.setRegion(selection);
			imagePanel.setImage(operator.apply().getBufferedImage());
		}

		if (ae.getSource().equals(sliceAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			GraySlicing operator= new GraySlicing(rgb);
			operator.setRegion(selection);
			
			JPanel p=new JPanel();

			JSlider sliderR0=new JSlider(0,255,0);
			sliderR0.setLabelTable(sliderR0.createStandardLabels(50));
			sliderR0.setPaintLabels(true);
			JSlider sliderS0=new JSlider(0,255,60);
			sliderS0.setLabelTable(sliderR0.createStandardLabels(50));
			sliderS0.setPaintLabels(true);
			sliderS0.setPaintTicks(true);
			JSlider sliderR1=new JSlider(0,255,63);
			sliderR1.setLabelTable(sliderR0.createStandardLabels(50));
			sliderR1.setPaintLabels(true);
			sliderR1.setPaintTicks(true);
			JSlider sliderS1=new JSlider(0,255,60);
			sliderS1.setLabelTable(sliderR0.createStandardLabels(50));
			sliderS1.setPaintLabels(true);
			sliderS1.setPaintTicks(true);
			JSlider sliderR2=new JSlider(0,255,126);
			sliderR2.setLabelTable(sliderR0.createStandardLabels(50));
			sliderR2.setPaintLabels(true);
			sliderR2.setPaintTicks(true);
			JSlider sliderS2=new JSlider(0,255,60);
			sliderS2.setLabelTable(sliderR0.createStandardLabels(50));
			sliderS2.setPaintLabels(true);
			sliderS2.setPaintTicks(true);
			JSlider sliderR3=new JSlider(0,255,255);
			sliderR3.setLabelTable(sliderR0.createStandardLabels(50));
			sliderR3.setPaintLabels(true);
			sliderR3.setPaintTicks(true);
			JSlider sliderS3=new JSlider(0,255,60);
			sliderS3.setLabelTable(sliderR0.createStandardLabels(50));
			sliderS3.setPaintLabels(true);
			sliderS3.setPaintTicks(true);
			JSlider sliderIntensity=new JSlider(0,255,200);
			sliderIntensity.setLabelTable(sliderR0.createStandardLabels(50));
			sliderIntensity.setPaintLabels(true);
			sliderIntensity.setPaintTicks(true);
				
			p.setLayout(new GridLayout(9,1));
			p.add(new JLabel("R0"));
			p.add(sliderR0);
			p.add(new JLabel("S0"));
			p.add(sliderS0);
			p.add(new JLabel("R1"));
			p.add(sliderR1);
			p.add(new JLabel("S1"));
			p.add(sliderS1);
			p.add(new JLabel("R2"));
			p.add(sliderR2);
			p.add(new JLabel("S2"));
			p.add(sliderS2);
			p.add(new JLabel("R3"));
			p.add(sliderR3);
			p.add(new JLabel("S3"));
			p.add(sliderS3);
			p.add(new JLabel("Intensity"));			
			p.add(sliderIntensity);
			JOptionPane.showMessageDialog(frame, p);
			operator.setParameters(sliderR0.getValue(),sliderS0.getValue(),sliderR1.getValue(), sliderS1.getValue(), 
					sliderR2.getValue(), sliderS2.getValue(),sliderR3.getValue(), sliderS3.getValue(),sliderIntensity.getValue());
			imagePanel.setImage(operator.apply().getBufferedImage());
		}
		if (ae.getSource().equals(compressAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			DynamicCompression operator= new DynamicCompression(rgb);
			operator.setRegion(selection);
			JSlider slider=new JSlider(1,255,1);		
			slider.setLabelTable(slider.createStandardLabels(50));
			slider.setPaintLabels(true);
			JOptionPane.showMessageDialog(frame, slider);
			operator.setParameter(slider.getValue());
			imagePanel.setImage(operator.apply().getBufferedImage());
		}
		if (ae.getSource().equals(thresholdAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			ContrastStretching operator= new ContrastStretching(rgb);
			operator.setRegion(selection);
			
			JPanel p=new JPanel(new GridLayout(1,2));
			JSlider slider=new JSlider(0,255,127);
			slider.setLabelTable(slider.createStandardLabels(50));
			slider.setPaintLabels(true);
			p.add(new JLabel("Intensity"));
			p.add(slider);
				
			JOptionPane.showMessageDialog(frame, p);
			int t=slider.getValue();
			operator.setParameters(t, 0, t, 255);
			imagePanel.setImage(operator.apply().getBufferedImage());
			
			
		}
		if (ae.getSource().equals(contrastAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			ContrastStretching operator= new ContrastStretching(rgb);
			operator.setRegion(selection);
			
			JPanel p=new JPanel();

			JSlider sliderR1=new JSlider(0,255,127);
			sliderR1.setLabelTable(sliderR1.createStandardLabels(50));			
			sliderR1.setPaintLabels(true);
			sliderR1.setPaintTicks(true);
			JSlider sliderS1=new JSlider(0,255,127);
			sliderS1.setLabelTable(sliderS1.createStandardLabels(50));
			sliderS1.setPaintLabels(true);
			sliderS1.setPaintTicks(true);
			JSlider sliderR2=new JSlider(0,255,127);
			sliderR2.setLabelTable(sliderR2.createStandardLabels(50));			
			sliderR2.setPaintLabels(true);
			sliderR2.setPaintTicks(true);
			JSlider sliderS2=new JSlider(0,255,127);
			sliderS2.setLabelTable(sliderS2.createStandardLabels(50));			
			sliderS2.setPaintLabels(true);
			sliderS2.setPaintTicks(true);
				
			p.setLayout(new GridLayout(4,2));
			p.add(new JLabel("R1"));
			p.add(sliderR1);
			p.add(new JLabel("S1"));
			p.add(sliderS1);
			p.add(new JLabel("R2"));
			p.add(sliderR2);
			p.add(new JLabel("S2"));
			p.add(sliderS2);
			
			JOptionPane.showMessageDialog(frame, p);
			operator.setParameters(sliderR1.getValue(), sliderS1.getValue(), sliderR1.getValue(), sliderS2.getValue());
			imagePanel.setImage(operator.apply().getBufferedImage());
			
			
		}
		if (ae.getSource().equals(negativeAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			imagePanel.setImage(rgb.getNegative().getBufferedImage());
		}
		if (ae.getSource().equals(openFile)){
		    int returnVal = chooser.showOpenDialog(frame);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	File file = chooser.getSelectedFile();
		    	frame.setTitle(title+" ("+file.getName()+")");
		    	try{
		    		BufferedImage bimg=ImageIO.read(file);
		    		selection=new ImageRegion(0,0,bimg.getWidth(),bimg.getHeight());
		    		imagePanel.setImage(bimg);
		    		imagePanel.updateSelection(selection);
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
			Histogram hist=new Histogram(rgb,selection.getUlx(),selection.getUly(),selection.getW(),selection.getH());
			//Histogram hist=new Histogram(rgb);
			Equalization operator=new Equalization(hist);
			imagePanel.setImage(operator.apply().getBufferedImage());
		}
		if (ae.getSource().equals(histogramAction)){			
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Histogram hist= new Histogram(rgb,selection.getUlx(),selection.getUly(),selection.getW(),selection.getH());
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
