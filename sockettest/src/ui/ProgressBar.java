package ui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.URLRunnable;
public class ProgressBar implements ActionListener,ChangeListener {
JFrame frame=null;
JProgressBar progressbar;
JLabel label;
Timer timer;
JButton b;
//public int i;
//public int j;
public ProgressBar(){
   frame=new JFrame("更新");
   frame.setBounds(100, 100, 400, 130);
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   Container contentPanel=frame.getContentPane();
   label=new JLabel("",JLabel.CENTER);
   progressbar = new JProgressBar();
   progressbar.setOrientation(JProgressBar.HORIZONTAL);
   progressbar.setMinimum(0);
   progressbar.setMaximum(100);
   progressbar.setValue(0);
   progressbar.setStringPainted(true);
   progressbar.addChangeListener(this);
   progressbar.setPreferredSize(new Dimension(300,20));
   progressbar.setBorderPainted(true);
   progressbar.setBackground(Color.pink);
  
   
   
   
   JPanel panel=new JPanel();
   b=new JButton("更新");
   b.setForeground(Color.blue);
   b.addActionListener(this);
   panel.add(b);
   timer=new Timer(1000,this);
   contentPanel.add(panel,BorderLayout.NORTH);
   contentPanel.add(progressbar,BorderLayout.CENTER);
   contentPanel.add(label,BorderLayout.SOUTH);
   //frame.pack();
   frame.setVisible(true);
  
  
}
public void actionPerformed(ActionEvent e) {
	URLRunnable r=new URLRunnable();
	if(e.getSource()==b){
     Thread t=new Thread(r);
     t.start();
	 timer.start();
   }
     //if(e.getSource()==timer){
     int value=progressbar.getValue();
   
     if(value<100)
   {     try {
	Thread.sleep(500);
} catch (InterruptedException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
    	 //value++;
    	 value=(100*r.i/r.j);
        // System.out.println("value:"+value);
    	// System.out.println("ProgressBar:"+r.i);
    	 progressbar.setValue(value);
     }
     else{
        timer.stop();
        frame.dispose();
     }
     
  
}
public void stateChanged(ChangeEvent e1) {
   int value=progressbar.getValue();
   if(e1.getSource()==progressbar){
    label.setText("目前已完成进度："+Integer.toString(value)+"%");
    label.setForeground(Color.blue);
   }
  
}
public static void main() {
   ProgressBar app=new ProgressBar();
}
}