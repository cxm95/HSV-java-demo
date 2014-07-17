package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class Layer {
   
   protected static final int PADDING=16;
	
   private static final int SIZE=7;
   private static final Image Window_IMG=new ImageIcon("graphics/window/Window.png").getImage();
   private  static int WINDOW_W=Window_IMG.getWidth(null);
   private static int  WINDOW_H=Window_IMG.getHeight(null);
	//�������Ͻ�x����
   protected int x;
   //�������Ͻ�y����
   protected int y;
   //���ڿ��
   protected int w;
   //���ڸ߶�
   protected int h;
   
   protected Layer(int x,int y,int w,int h){
	   this.x=x;
	   this.y=y;
	   this.w=w;
	   this.h=h;
   }
   
   /**
    * ���ƴ���
    */
   protected void creatWindow(Graphics g){
	 //�����Ͻ�
	      g.drawImage(Window_IMG, x, y, x+SIZE, y+SIZE, 0, 0, SIZE, SIZE, null);
	      //�����ϲ���
	      g.drawImage(Window_IMG, x+SIZE, y, x+w-SIZE,y+SIZE, SIZE, 0 , WINDOW_W-SIZE, SIZE, null);
	      //�����ϲ���
	      g.drawImage(Window_IMG, x+w-SIZE, y, x+w,y+SIZE, WINDOW_W-SIZE, 0 , WINDOW_W, SIZE, null);
	      //������
	      g.drawImage(Window_IMG, x, y+SIZE, x+SIZE,y+h-SIZE, 0, SIZE ,SIZE, WINDOW_H-SIZE, null);
	      //��
	      g.drawImage(Window_IMG, x+SIZE, y+SIZE, x+w-SIZE,y+h-SIZE, SIZE, SIZE , WINDOW_W-SIZE, WINDOW_H-SIZE, null);
	      //����
	      g.drawImage(Window_IMG, x+w-SIZE, y+SIZE, x+w,y+h-SIZE, WINDOW_W-SIZE, SIZE , WINDOW_W, WINDOW_H-SIZE, null);
	      //����
	      g.drawImage(Window_IMG, x, y+h-SIZE, x+SIZE,y+h, 0, WINDOW_H-SIZE , SIZE, WINDOW_H, null);
	      //����
	      g.drawImage(Window_IMG, x+SIZE, y+h-SIZE, x+w-SIZE,y+h, SIZE, WINDOW_H-SIZE , WINDOW_W-SIZE, WINDOW_H, null);
	      //����
	      g.drawImage(Window_IMG, x+w-SIZE, y+h-SIZE, x+w,y+h, WINDOW_W-SIZE, WINDOW_H-SIZE , WINDOW_W, WINDOW_H, null);
	    
   }
   /**
    * ˢ����Ϸ
    * @param g=����
    */
  abstract public void paint(Graphics g);
	   
   
}
