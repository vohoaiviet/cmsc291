package com.jachsoft.threed;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Testing {
	public static void main(String args[]){
		Sample p = new Sample();
		JFrame f=new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(640, 480);
		f.getContentPane().add(p,BorderLayout.CENTER);
		f.setVisible(true);
	}
}


class Sample extends JPanel{
	public void paintComponent(Graphics g){
		g.setColor(Color.RED);
		g.drawString("Hello",10,40);
	}
}
