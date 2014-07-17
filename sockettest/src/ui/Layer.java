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
	//窗口左上角x坐标
   protected int x;
   //窗口左上角y坐标
   protected int y;
   //窗口宽度
   protected int w;
   //窗口高度
   protected int h;
   
   protected Layer(int x,int y,int w,int h){
	   this.x=x;
	   this.y=y;
	   this.w=w;
	   this.h=h;
   }
   
   /**
    * 绘制窗口
    */
   protected void creatWindow(Graphics g){
	 //画左上角
	      g.drawImage(Window_IMG, x, y, x+SIZE, y+SIZE, 0, 0, SIZE, SIZE, null);
	      //画中上部分
	      g.drawImage(Window_IMG, x+SIZE, y, x+w-SIZE,y+SIZE, SIZE, 0 , WINDOW_W-SIZE, SIZE, null);
	      //画右上部分
	      g.drawImage(Window_IMG, x+w-SIZE, y, x+w,y+SIZE, WINDOW_W-SIZE, 0 , WINDOW_W, SIZE, null);
	      //画左中
	      g.drawImage(Window_IMG, x, y+SIZE, x+SIZE,y+h-SIZE, 0, SIZE ,SIZE, WINDOW_H-SIZE, null);
	      //中
	      g.drawImage(Window_IMG, x+SIZE, y+SIZE, x+w-SIZE,y+h-SIZE, SIZE, SIZE , WINDOW_W-SIZE, WINDOW_H-SIZE, null);
	      //右中
	      g.drawImage(Window_IMG, x+w-SIZE, y+SIZE, x+w,y+h-SIZE, WINDOW_W-SIZE, SIZE , WINDOW_W, WINDOW_H-SIZE, null);
	      //左下
	      g.drawImage(Window_IMG, x, y+h-SIZE, x+SIZE,y+h, 0, WINDOW_H-SIZE , SIZE, WINDOW_H, null);
	      //中下
	      g.drawImage(Window_IMG, x+SIZE, y+h-SIZE, x+w-SIZE,y+h, SIZE, WINDOW_H-SIZE , WINDOW_W-SIZE, WINDOW_H, null);
	      //右下
	      g.drawImage(Window_IMG, x+w-SIZE, y+h-SIZE, x+w,y+h, WINDOW_W-SIZE, WINDOW_H-SIZE , WINDOW_W, WINDOW_H, null);
	    
   }
   /**
    * 刷新游戏
    * @param g=画笔
    */
  abstract public void paint(Graphics g);
	   
   
}
