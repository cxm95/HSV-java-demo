package config;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GameConfig {
    /**
     * �߿���
     */
	private int width;
	
	/**
	 * �߿�߶�
	 */
	private int height;
	
	/**
	 * �߿�ߴ�
	 */
	private int windowSize;
	
	/**
	 * �߿��ڱ߾�
	 */
	private int padding;
	
	/**
	 * ͼ������
	 */
	private List<LayerConfig> layersConfig;
   /**
    * ���캯��
    * ��ȡXML�ļ�����ȡȫ����Ϸ����
    * 
    * @throws Exception
    */
    public GameConfig() throws Exception{
       //����XML��ȡ��
       SAXReader reader = new SAXReader();
       //��ȡXML�ļ�
 	   Document doc= reader.read("config/cfg.xml");
 	   //��ȡXML�ļ��ĸ��ڵ�
 	   Element game= doc.getRootElement();
 	  //���ô���
 	   this.setuiConfig(game.element("frame"));
 	   //����ϵͳ����
 	   this.setSysetmConfig(game.element("system"));
 	   //�������ݷ��ʲ���
 	   this.setDataConfig(game.element("data"));
    }
    
    /**
     * ���ô���
     * 
     * @param frame �����ļ���������Ԫ��
     */
    
    private void setuiConfig(Element frame){
       this.width=Integer.parseInt(frame.attributeValue("width"));
  	   this.height=Integer.parseInt(frame.attributeValue("height"));
  	   this.padding=Integer.parseInt(frame.attributeValue("padding"));
  	   this.windowSize=Integer.parseInt(frame.attributeValue("windowSize"));
  	   List<Element> layers=frame.elements("layer");
   	   layersConfig=new ArrayList<LayerConfig>();
  	     for(Element layer:layers){
  		  LayerConfig lc=new LayerConfig(
  				layer.attributeValue("className"),
  				Integer.parseInt(layer.attributeValue("x")),
  				Integer.parseInt(layer.attributeValue("y")),
  				Integer.parseInt(layer.attributeValue("w")),
  				Integer.parseInt(layer.attributeValue("h"))
  				);
  		  layersConfig.add(lc);
  		  
  	  }
    }
    /**
     * ����ϵͳ����
     * 
     * @param frame
     */
    private void setSysetmConfig(Element frame){
    	//TODO ����ϵͳ����
    }
    /**
     * �������ݷ��ʲ���
     * 
     * @param frame
     */
    private void setDataConfig(Element frame){
    	//TODO  �������ݷ��ʲ���
    }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getWindowSize() {
		return windowSize;
	}

	public int getPadding() {
		return padding;
	}

	public List<LayerConfig> getLayersConfig() {
		return layersConfig;
	}
    
    
}


