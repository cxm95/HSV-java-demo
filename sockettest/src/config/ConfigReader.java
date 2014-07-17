package config;



import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigReader {
   public static void readConfig() throws Exception{
	   SAXReader reader = new SAXReader();
	   Document doc= reader.read("config/cfg.xml");
	   Element game= doc.getRootElement();
	   Element frame=game.element("frame");
	   List<Element> layers=frame.elements("layer");
	   String str=frame.attributeValue("width");
   }
 public static  void main(String[] args){
	 
 }
}
