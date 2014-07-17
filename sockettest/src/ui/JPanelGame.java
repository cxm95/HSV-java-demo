package ui;

import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import config.ConfigFactory;
import config.GameConfig;
import config.LayerConfig;

public class JPanelGame extends JPanel{
    public static int back=1;
    public static int skin=1;
    public static int fliter=1;
	
	private List<Layer> layers=null;
	// Lay lay4=new Lay(768,32,334,100);
	//private Lay lay5=new Lay(740,152,117,100);
	//private Lay lay6=new Lay(841,152,117,100);
	//private Lay lay7=new Lay(740,272,334,130);
	//private Lay lay8=new Lay(740,422,334,120);
	public JPanelGame(){
		
       try {
        	//�����Ϸ����
    		GameConfig cfg=ConfigFactory.gerGameConfig();
    		//��ò�����
    		List<LayerConfig> layersCfg=cfg.getLayersConfig();
    		//������Ϸ������
            layers =new ArrayList<Layer>(layersCfg.size());
            //ѭ���������Բ����
            for (LayerConfig layerCfg:layersCfg) {
        	    //��������
            	Class<?> cls=Class.forName(layerCfg.getClassName());
            	//��ù��캯��
            	Constructor<?> ctr=cls.getConstructor(int.class,int.class,int.class,int.class);
            	//���ù��캯����������
            	Layer l=(Layer)ctr.newInstance(
            			layerCfg.getX(),layerCfg.getY(),layerCfg.getW(),layerCfg.getH()
            			);
            	//�Ѵ�����layer�������list
            	layers.add(l);
    	}
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	
		//    	lays=new Layer[]{
//    			new LayerBackGround(0,0,0,0),
//    			new LayerDataBase(40,32,334,279),
//    			new LayerDisk(40,343,334,279),
//    			new LayerGame(414,32,334,590),
//    			new LayerButton(788,32,334,124),
//    			new LayerNext(788,188,176,148),
//    			new LayerLevel(964,188,158,148),
//    			new LayerPoint(788,368,334,200),
//    	};
    }
    
    @Override
    public void paintComponent(Graphics g){
     
    	//ѭ��ˢ����Ϸ����
    	for (int i = 0; i < layers.size(); i++) {
    		layers.get(i).paint(g);
	}
     
    }
}
