package com.jachsoft.cmsc291.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApp {
	JFrame frame=new JFrame("CMSC 291");

	public MainApp(){
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel emptyLabel = new JPanel();
		emptyLabel.setPreferredSize(new Dimension(840, 600));
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

		//Display the window.
		frame.setSize(800, 600);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String args[]){
		new MainApp();
	}
}
