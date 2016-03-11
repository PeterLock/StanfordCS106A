package com.chapter5;

import java.awt.Color;
import java.awt.Font;

import acm.graphics.*;
import acm.program.*;

public class London extends GraphicsProgram {
	
	public void run(){
		
		GLabel label = new GLabel("hello world");
		
		label.setFont("Old London-82");
		
	    double x = (getWidth()/2 - label.getWidth()/2);
		double y = (getHeight() + label.getAscent())/2;
				
		
		label.setColor(Color.MAGENTA);
		add(label, x, y);
	}

}
