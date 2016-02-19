package com.game.play;

import java.util.HashMap;
import java.util.Map;

public interface Constants {
	
	enum GamePhase {
		INIT,START,END;
	}
	
	String alphabet = "abcdefghijklmnopqrstuvwxyz";
	String MAGIC_WORD="challenge";
	Map<String,String> dic = new HashMap<>();
	
	public static void load(){
		if(dic.size()==0){
			DataSource ds = new DataSource();
			ds.readAndLoadFromFile(dic);
		}
	}
	
	public static Map<String,String> getDictionary() {
		load();
		return (dic);
	}
}