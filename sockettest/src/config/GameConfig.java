package config;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GameConfig {
    /**
     * 边框宽度
     */
	private int width;
	
	/**
	 * 边框高度
	 */
	private int height;
	
	/**
	 * 边框尺寸
	 */
	private int windowSize;
	
	/**
	 * 边框内边距
	 */
	private int padding;
	
	/**
	 * 图层属性
	 */
	private List<LayerConfig> layersConfig;
   /**
    * 构造函数
    * 读取XML文件，获取全部游戏配置
    * 
    * @throws Exception
    */
    public GameConfig() throws Exception{
       //创建XML读取器
       SAXReader reader = new SAXReader();
       //读取XML文件
 	   Document doc= reader.read("config/cfg.xml");
 	   //获取XML文件的根节点
 	   Element game= doc.getRootElement();
 	  //配置窗口
 	   this.setuiConfig(game.element("frame"));
 	   //配置系统参数
 	   this.setSysetmConfig(game.element("system"));
 	   //配置数据访问参数
 	   this.setDataConfig(game.element("data"));
    }
    
    /**
     * 配置窗口
     * 
     * @param frame 配置文件窗口配置元素
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
     * 配置系统参数
     * 
     * @param frame
     */
    private void setSysetmConfig(Element frame){
    	//TODO 配置系统参数
    }
    /**
     * 配置数据访问参数
     * 
     * @param frame
     */
    private void setDataConfig(Element frame){
    	//TODO  配置数据访问参数
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


