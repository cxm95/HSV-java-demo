package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class LayerBackGround extends Layer {

	  private static  Image IMG_BG=new ImageIcon("graphics/background/stop.png").getImage();
	  private static  Image IMG_BG2=new ImageIcon("graphics/background/start.png").getImage();
	  private static  Image IMG_BG3=new ImageIcon("graphics/background/skin.png").getImage();
;//	  private static  Image IMG_BE=new ImageIcon("graphics/button/button_blue_play.png").getImage();
//	  private static  Image IMG_ST=new ImageIcon("graphics/button/button_blue_stop.png").getImage();
	  
	public LayerBackGround(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint( final Graphics g) {
		JPanelGame back=new JPanelGame();
		if(back.back%2==1&&back.skin%2==1){
			g.drawImage(IMG_BG,0,0,800,532,null);
         System.out.print("s");}
		else if(back.back%2==0&&back.skin%2==1)
			 g.drawImage(IMG_BG2,0,0,800,532,null);
		else 
			g.drawImage(IMG_BG3,0,0,800,532,null);
		
	}

}
