package config;

public class ConfigFactory {
   
	private static GameConfig Game_CONFIG =null;
	
	static{
		try {
			Game_CONFIG = new GameConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
     public static GameConfig gerGameConfig(){
    	 return Game_CONFIG;
    	 
     }
}
