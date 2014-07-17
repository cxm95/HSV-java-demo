package ui;

import java.awt.Graphics;

public class LayerWhite extends Layer {
	public LayerWhite(int x,int y ,int w ,int h){
		super(x,y,w,h);
	}

	
	public void paint(Graphics g){
		this.creatWindow(g);
	}
}