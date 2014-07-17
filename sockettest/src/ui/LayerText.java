package ui;

import java.awt.Graphics;

public class LayerText extends Layer {
	public LayerText(int x,int y ,int w ,int h){
		super(x,y,w,h);
	}

	
	public void paint(Graphics g){
		this.creatWindow(g);
	}
}
