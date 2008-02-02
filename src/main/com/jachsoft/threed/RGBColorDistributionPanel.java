package com.jachsoft.threed;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jachsoft.imagelib.RGBColor;
import com.jachsoft.imagelib.RGBImage;


public class RGBColorDistributionPanel extends JPanel implements Runnable, MouseListener, MouseMotionListener {
	
	    Model3D md;
	    boolean painted = true;
	    float xfac;
	    int prevx, prevy;
	    float xtheta, ytheta;
	    float scalefudge = 1;
	    Matrix3D amat = new Matrix3D(), tmat = new Matrix3D();
	    RGBImage image;

	    
	    public static void main(String args[]){
	    	try{
	    	RGBImage img=new RGBImage(ImageIO.read(new File("data/windows_logo.jpg")));
	    	RGBColorDistributionPanel p= new RGBColorDistributionPanel(img);
	    	JFrame f = new JFrame();
	    	f.getContentPane().add(p,BorderLayout.CENTER);
	    	f.setSize(new Dimension(640,480));
	    	f.setVisible(true);	    	
	    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	}catch(Exception ioe){
	    		ioe.printStackTrace();
	    	}
	    }
	    
	    
	    
	    public RGBColorDistributionPanel(RGBImage image) {
	    	this.image = image;
	    	amat.yrot(20);
	    	amat.xrot(20);		
	    	this.setSize(new Dimension(640,480));
		
	    	addMouseListener(this);
	    	addMouseMotionListener(this);
		    new Thread(this).start();
	    }

	    public void setImage(RGBImage image){
	    	this.image =image;
	    }
	    
	    public void run() {
		try {
		    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		    System.out.println("Thread started");
		    
		    Model3D m = this.createModel();
		    
		    md = m;
		    m.findBB();
		    m.compress();
		    float xw = m.xmax - m.xmin;
		    float yw = m.ymax - m.ymin;
		    float zw = m.zmax - m.zmin;
		    if (yw > xw)
			xw = yw;
		    if (zw > xw)
			xw = zw;
		    float f1 = getSize().width / xw;
		    float f2 = getSize().height / xw;
		    xfac = 0.7f * (f1 < f2 ? f1 : f2) * scalefudge;
		} catch(Exception e) {
		    md = null;
		    e.printStackTrace();
		}			
			repaint();
	    }


	    public  void mouseClicked(MouseEvent e) {
	    }

	    public  void mousePressed(MouseEvent e) {
	        prevx = e.getX();
	        prevy = e.getY();
	        e.consume();
	    }

	    public  void mouseReleased(MouseEvent e) {
	    }

	    public  void mouseEntered(MouseEvent e) {
	    }

	    public  void mouseExited(MouseEvent e) {
	    }

	    public  void mouseDragged(MouseEvent e) {
	        int x = e.getX();
	        int y = e.getY();

	        tmat.unit();
	        float xtheta = (prevy - y) * 360.0f / getSize().width;
	        float ytheta = (x - prevx) * 360.0f / getSize().height;
	        tmat.xrot(xtheta);
	        tmat.yrot(ytheta);
	        amat.mult(tmat);
	        if (painted) {
	            painted = false;
	            repaint();
	        }
	        prevx = x;
	        prevy = y;
	        e.consume();
	    }

	    public  void mouseMoved(MouseEvent e) {
	    }

	    public void paintComponent(Graphics g) {
	    	super.paintComponent(g); //paint background\
	    	this.setBackground(Color.BLACK);
		    
		    if (md != null) {
		    	md.mat.unit();
		    	md.mat.translate(-(md.xmin + md.xmax) / 2,
		    			-(md.ymin + md.ymax) / 2,
		    			-(md.zmin + md.zmax) / 2);
		    	md.mat.mult(amat);
		    	md.mat.scale(xfac, -xfac, 16 * xfac / getSize().width);
		    	md.mat.translate(getSize().width / 2, getSize().height / 2, 8);
		    	md.transformed = false;		    
		    	md.paint(g);
		    	setPainted();
		    }
	    }

	    private synchronized void setPainted() {
	    	painted = true;
	    	notifyAll();
	    }
	    
	    public Model3D createModel(){
	    	Model3D model=new Model3D();
	    	
	    	model.addVert(0, 0, 0);			//0 black
	    	model.addVert(255, 0, 0);		//1 red corner
	    	model.addVert(0, 255, 0);		//2 green corner
	    	model.addVert(0, 0, 255);
	    	/*
	    	model.addVert(255, 255, 0);	    			
	    	model.addVert(255, 0, 255);		 
	    	model.addVert(255, 255, 255);		
	    	model.addVert(0, 255, 255);		
	    	*/
	    	model.add(0,1);					//red axis (x)
	    	model.add(0,2);
	    	model.add(0,3);
	    	/*
	    	model.add(3,0);	    	
	    	model.add(4,5);
	    	model.add(5,6);
	    	model.add(6,7);
	    	model.add(7,4);
	    	
	    	model.add(4,0);					//blue axis (z)
	    	model.add(1,5);
	    	model.add(2,6);
	    	model.add(3,7);
	    	*/
	    	int lastPoint=4;
	    	
	    	
	    	
	    	if (image==null)
	    		return null;
	    	
	    	int h=image.getHeight();
	    	int w=image.getWidth();
	    	
	    	Hashtable<RGBColor, RGBColor> plotted = new Hashtable<RGBColor, RGBColor>();
	    	
	    	for (int y=0;y<h;y++){
	    		for (int x=0; x< w;x++){
	    			RGBColor c = image.getRGBColor(x, y);
	    			if (!plotted.containsKey(c)){
	    				model.addVert(c.getRed(),c.getGreen(),c.getBlue());	    				
	    				lastPoint++;
	    				plotted.put(c, c);
	    			}else{
	    				//System.out.println(c+" already plotted!");
	    			}
	    		}
	    	}
	    	
	    	
	    	return model;	    	
	    }
	    
}

