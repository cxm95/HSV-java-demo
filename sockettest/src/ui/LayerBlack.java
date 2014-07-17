package ui;

import java.awt.Graphics;

import javax.swing.JTextField;

public class LayerBlack extends Layer {
	public LayerBlack(int x,int y ,int w ,int h){
		super(x,y,w,h);
	}

	
	public void paint(Graphics g){
		this.creatWindow(g);
		
	}
}
