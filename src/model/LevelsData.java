package model;


//comment to push file

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class LevelsData {
	private static LevelsData instance = new LevelsData();
	private HashMap<Integer, Integer> levels;
	private int maxLevel = 20;
	
	private LevelsData() {}
	
	public void loadLevels() {
		levels = new HashMap<Integer, Integer>();
		try {
			Scanner inFile = new Scanner(new File("resources/data/levels.csv"));
			String line;
			
			while(inFile.hasNextLine()) {
				line = inFile.nextLine();
				String splitted[] = line.split(",");
				levels.put(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
			}
			
			inFile.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getLevelXp(int level) {
		return this.levels.get(level);
	}
	
	public int getMaxLevel() {
		return this.maxLevel;
	}
	
	public static LevelsData getInstance() {
		return instance;
	}
}
