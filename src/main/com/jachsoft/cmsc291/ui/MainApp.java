package com.jachsoft.cmsc291.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.JDialog;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jachsoft.cmsc291.exercises.PlateLocalization;
import com.jachsoft.cmsc291.exercises.StudentEvaluation;
import com.jachsoft.imagelib.ConvolutionKernel;
import com.jachsoft.imagelib.ImageRegion;
import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;
import com.jachsoft.imagelib.StructuringElement;
import com.jachsoft.imagelib.algorithms.ContrastStretching;
import com.jachsoft.imagelib.algorithms.Convolution;
import com.jachsoft.imagelib.algorithms.DynamicCompression;
import com.jachsoft.imagelib.algorithms.IImageOperator;
import com.jachsoft.imagelib.algorithms.LaplacianEdgeDetect;
import com.jachsoft.imagelib.algorithms.Morphology;
import com.jachsoft.imagelib.algorithms.PrewittEdgeDetect;
import com.jachsoft.imagelib.algorithms.RobertsEdgeDetect;
import com.jachsoft.imagelib.algorithms.SobelEdgeDetect;
import com.jachsoft.imagelib.algorithms.Equalization;
import com.jachsoft.imagelib.algorithms.GraySlicing;
import com.jachsoft.imagelib.algorithms.Histogram;
import com.jachsoft.imagelib.algorithms.MedianFilter;
import com.jachsoft.imagelib.algorithms.PowerLawTransformation;
import com.jachsoft.threed.RGBColorDistributionPanel;


public class MainApp implements ActionListener, ChangeListener {
	String title="CMSC 291-Digital Image Processing Workbench";
	JFrame frame=new JFrame(title);
	JMenuBar menubar=new JMenuBar();
	JMenu fileMenu=new JMenu("File");
	JMenu selectionMenu=new JMenu("Selection");
	JMenu actionMenu=new JMenu("Enhancements");
	JMenu filterMenu=new JMenu("Filters");
	JMenu edgeMenu=new JMenu("Edge Detection");
	JMenu morphMenu=new JMenu("Morphology");
	JMenu colorMenu=new JMenu("Color");
	JMenu exerMenu=new JMenu("Exercises");
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
	JMenuItem meanFilterAction = new JMenuItem("Mean Filter");
	JMenuItem gaussianFilterAction = new JMenuItem("Gaussian Filter");
	JMenuItem laplacianEdgeAction = new JMenuItem("Laplacian");
	JMenuItem medianFilterAction = new JMenuItem("Median Filter");
	
	JMenuItem sobelEdgeAction = new JMenuItem("Sobel");
	JMenuItem robertsEdgeAction = new JMenuItem("Roberts");
	JMenuItem prewittEdgeAction = new JMenuItem("Prewitt");
	
	JMenuItem plateExerAction = new JMenuItem("Plate Localization");
	
	JMenuItem selectAllSelection = new JMenuItem("Select All");
	JMenuItem selectRegionSelection = new JMenuItem("Select Region");
	
	JMenuItem dilationMorphAction = new JMenuItem("Dilation");
	JMenuItem erosionMorphAction = new JMenuItem("Erosion");
	JMenuItem openingMorphAction = new JMenuItem("Closing");
	JMenuItem closingMorphAction = new JMenuItem("Opening");	
	JMenuItem hitMorphAction = new JMenuItem("Hit and Miss");
	JMenuItem thinningMorphAction = new JMenuItem("Thinning");
	
	JMenuItem distColorAction = new JMenuItem("Color Distribution");
	
	
	JToolBar statusBar=new JToolBar();
	JLabel curr=new JLabel("");
	JLabel time=new JLabel("");
	
	
	JMenuItem aboutHelp = new JMenuItem("About");
	
	ImagePanel imagePanel=new ImagePanel();
	JFileChooser chooser=new JFileChooser();
	
	ImageRegion selection;
	
	
	String currentPixel="";


	
	public MainApp(){
	
		statusBar.add(curr);
		statusBar.add(time);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		imagePanel.setPreferredSize(new Dimension(800,600));
		frame.getContentPane().add(new JScrollPane(imagePanel), BorderLayout.CENTER);
		frame.getContentPane().add(statusBar,BorderLayout.SOUTH);
		menubar.add(fileMenu);
		menubar.add(selectionMenu);
		menubar.add(actionMenu);
		menubar.add(filterMenu);
		menubar.add(edgeMenu);
		menubar.add(morphMenu);
		menubar.add(colorMenu);
		menubar.add(exerMenu);		
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
		
		filterMenu.add(meanFilterAction);
		filterMenu.add(medianFilterAction);
		filterMenu.add(gaussianFilterAction);
			
		edgeMenu.add(robertsEdgeAction);
		edgeMenu.add(sobelEdgeAction);		
		edgeMenu.add(prewittEdgeAction);
		edgeMenu.add(laplacianEdgeAction);

		morphMenu.add(dilationMorphAction);
		morphMenu.add(erosionMorphAction);
		morphMenu.add(openingMorphAction);
		morphMenu.add(closingMorphAction);
		morphMenu.add(hitMorphAction);
		morphMenu.add(thinningMorphAction);		
		
		colorMenu.add(distColorAction);
		
		
		exerMenu.add(plateExerAction);
		
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
		
		meanFilterAction.addActionListener(this);
		medianFilterAction.addActionListener(this);
		gaussianFilterAction.addActionListener(this);
		
		laplacianEdgeAction.addActionListener(this);
		sobelEdgeAction.addActionListener(this);
		robertsEdgeAction.addActionListener(this);
		prewittEdgeAction.addActionListener(this);
		
		dilationMorphAction.addActionListener(this);
		erosionMorphAction.addActionListener(this);
		openingMorphAction.addActionListener(this);
		closingMorphAction.addActionListener(this);
		hitMorphAction.addActionListener(this);
		thinningMorphAction.addActionListener(this);
		
		distColorAction.addActionListener(this);
		
				
		plateExerAction.addActionListener(this);

		

		
		
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
						currentPixel="X:"+x+",Y:"+y+",R:"+col.getRed()+",G:"+col.getGreen()+",B:"+col.getBlue();
						curr.setText(currentPixel);
					}
				}
			}
		});
	}
	
	public static void main(String args[]){
		new MainApp();
	}
	
	
	private void applyOperator(IImageOperator operator){
		time.setForeground(Color.RED);
		time.setText("Performing selected operation...please wait...");
		operator.setRegion(selection);
		imagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		long startTime = System.currentTimeMillis();
		imagePanel.setImage(operator.apply().getBufferedImage());
		long endTime = System.currentTimeMillis();
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		imagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		time.setForeground(Color.BLUE);
		time.setText(",Last operation took " + (endTime - startTime) +" ms");
	}
	
	public void actionPerformed(ActionEvent ae){
		if (ae.getSource().equals(distColorAction)){
			RGBImage img=new RGBImage(imagePanel.getImage());
	    	RGBColorDistributionPanel p= new RGBColorDistributionPanel(img);
	    	JFrame f = new JFrame("Color Distribution");
	    	f.getContentPane().add(p,BorderLayout.CENTER);
	    	f.setSize(new Dimension(640,480));
	    	f.setVisible(true);	    	
	    	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		if (ae.getSource().equals(thinningMorphAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Morphology operator = new Morphology(rgb);
			StructuringElement kernel = new StructuringElement(new double[][]{{0,0,0},{-1,1,-1},{1,1,1}});
			operator.setParameters(Morphology.THINNING, kernel);
			applyOperator(operator);
		}
		if (ae.getSource().equals(hitMorphAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Morphology operator = new Morphology(rgb);
			StructuringElement kernel = new StructuringElement(new double[][]{{-1,1,-1},{0,1,1},{0,0,-1}});
			operator.setParameters(Morphology.HITMISSED, kernel);
			applyOperator(operator);
		}
		if (ae.getSource().equals(closingMorphAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Morphology operator = new Morphology(rgb);
			StructuringElement kernel = new StructuringElement(new double[][]{{1,1,1},{1,1,1},{1,1,1}});
			operator.setParameters(Morphology.CLOSING, kernel);
			applyOperator(operator);
		}
		if (ae.getSource().equals(openingMorphAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Morphology operator = new Morphology(rgb);
			StructuringElement kernel = new StructuringElement(new double[][]{{1,1,1},{1,1,1},{1,1,1}});
			operator.setParameters(Morphology.OPENING, kernel);
			applyOperator(operator);
		}
		if (ae.getSource().equals(erosionMorphAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Morphology operator = new Morphology(rgb);
			StructuringElement kernel = new StructuringElement(new double[][]{{1,1,1},{1,1,1},{1,1,1}});
			operator.setParameters(Morphology.EROSION, kernel);
			applyOperator(operator);
		}
		if (ae.getSource().equals(dilationMorphAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Morphology operator = new Morphology(rgb);
			StructuringElement kernel = new StructuringElement(new double[][]{{1,1,1},{1,1,1},{1,1,1}});
			operator.setParameters(Morphology.DILATION, kernel);
			applyOperator(operator);
		}		
		if (ae.getSource().equals(plateExerAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			PlateLocalization operator=new PlateLocalization(rgb);			
			applyOperator(operator);
			JFrame fr=new JFrame("Scratch");
			JLabel lr=new JLabel();
			lr.setPreferredSize(new Dimension(rgb.getWidth(),rgb.getHeight()));
			lr.setIcon(new ImageIcon(operator.getScratch().getBufferedImage()));
			fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fr.add(lr);
			fr.pack();
			fr.setVisible(true);
			fr=new JFrame("Plate Number");
			lr=new JLabel();
			RGBImage plateNum=operator.getPlateNumber();
			lr.setPreferredSize(new Dimension(plateNum.getWidth(),plateNum.getHeight()));
			lr.setIcon(new ImageIcon(plateNum.getBufferedImage()));
			fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fr.add(lr);
			fr.pack();
			fr.setVisible(true);
		}
		if (ae.getSource().equals(laplacianEdgeAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			LaplacianEdgeDetect operator=new LaplacianEdgeDetect(rgb);
			String s=JOptionPane.showInputDialog("Enter threshold:","3");
			operator.setParameters(Integer.parseInt(s));						
			applyOperator(operator);
		}
		if (ae.getSource().equals(gaussianFilterAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Convolution operator=new Convolution(rgb);			
			String s=JOptionPane.showInputDialog("Enter filter size:","3");
			ConvolutionKernel kernel=new ConvolutionKernel(Integer.parseInt(s));
			operator.setRegion(selection);
			s=JOptionPane.showInputDialog("Enter standard deviation:","1.0");
			operator.setParameters(kernel.gaussianFilter(Float.parseFloat(s)));
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
		}
		if (ae.getSource().equals(medianFilterAction)){			
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			MedianFilter operator=new MedianFilter(rgb);
			String s=JOptionPane.showInputDialog("Enter filter size:","3");			
			operator.setSize(Integer.parseInt(s));			
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
			
		}
		if (ae.getSource().equals(prewittEdgeAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			PrewittEdgeDetect operator=new PrewittEdgeDetect(rgb);			
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
		}
		if (ae.getSource().equals(robertsEdgeAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			RobertsEdgeDetect operator=new RobertsEdgeDetect(rgb);			
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
		}
		if (ae.getSource().equals(sobelEdgeAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			SobelEdgeDetect operator=new SobelEdgeDetect(rgb);			
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
		}
		if (ae.getSource().equals(meanFilterAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Convolution operator=new Convolution(rgb);			
			String s=JOptionPane.showInputDialog("Enter filter size:","3");
			ConvolutionKernel kernel=new ConvolutionKernel(Integer.parseInt(s));
			operator.setParameters(kernel.meanFilter());
			operator.setRegion(selection);
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
		}
		if (ae.getSource().equals(selectAllSelection)){
			selection.setUlx(0);
			selection.setUly(0);
			selection.setHeight(imagePanel.getImage().getHeight());
			selection.setWidth(imagePanel.getImage().getWidth());
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
			JSlider sliderR2=new JSlider(0,w,selection.getUlx()+selection.getWidth());
			sliderR2.setLabelTable(sliderR2.createStandardLabels(w/4));			
			sliderR2.setPaintLabels(true);
			sliderR2.setPaintTicks(true);
			JSlider sliderS2=new JSlider(0,h,selection.getUly()+selection.getHeight());
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
			selection.setWidth(sliderR2.getValue()-sliderR1.getValue());
			selection.setHeight(sliderS2.getValue()-sliderS1.getValue());
			imagePanel.updateSelection(selection);
			
		}		
		if (ae.getSource().equals(aboutHelp)){
			JOptionPane.showMessageDialog(frame,title+"\nCopyright 2007-2008 by" +
					"Joseph Anthony C. Hermocilla\n" +
					"(jachermocilla@gmail.com)\n" +
					"All Rights Reserved");
		}
		if (ae.getSource().equals(powerLawAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			PowerLawTransformation operator=new PowerLawTransformation(rgb);			
			JSlider slider=new JSlider(0,500,1);
			slider.setLabelTable(slider.createStandardLabels(100));
			slider.setPaintLabels(true);
			JOptionPane.showMessageDialog(frame, slider);
			operator.setParameters(1,slider.getValue()/100f);
			//operator.setRegion(selection);
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
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
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
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
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
		}
		if (ae.getSource().equals(thresholdAction)){
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			ContrastStretching operator= new ContrastStretching(rgb);
			operator.setRegion(selection);	
			String val = JOptionPane.showInputDialog("Threshold");
			int t=Integer.parseInt(val);
            operator.setParameters(t, 0, t, 255);
            applyOperator(operator);                       		
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
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
			
			
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
			Histogram hist=new Histogram(rgb,selection.getUlx(),selection.getUly(),selection.getWidth(),selection.getHeight());
			//Histogram hist=new Histogram(rgb);
			Equalization operator=new Equalization(hist);
			//imagePanel.setImage(operator.apply().getBufferedImage());
			applyOperator(operator);
		}
		if (ae.getSource().equals(histogramAction)){			
			RGBImage rgb=new RGBImage(imagePanel.getImage());
			Histogram hist= new Histogram(rgb,selection.getUlx(),selection.getUly(),selection.getWidth(),selection.getHeight());
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
		    		StudentEvaluation exer1=new StudentEvaluation(rgb,scanner);
		    		exer1.process();
		    		imagePanel.setImage(rgb.getBufferedImage());
		    	}catch(IOException ioe){ioe.printStackTrace();
		    	}
		    }		    			
		}		
	}
	
	
	public void stateChanged(ChangeEvent ce){
		
		
	}
}
