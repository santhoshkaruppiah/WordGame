package com.game.play;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class DataSource {

	public void readAndLoadFromFile(final Map<String,String> dic ) {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("file/wordlist");
			 InputStreamReader isr = new InputStreamReader(is);
			 BufferedReader br = new BufferedReader(isr);) {
			String line;
			while ((line = br.readLine()) != null) {
				dic.put(line, line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
//	public static void main(String[] args) {
//		DataSource ds = new DataSource();
//		ds.readAndLoadFromFile();
//	}
}
