package model;

/**
 * The following project Ninten-Do is an application that allows users to input tasks they must complete in a game like manner.
 * The goal of the application is to make daily chores fun and engaging for the user. The java application levelsData.java
 * is created in order to set the logic and calls needed to allow the application's levels to be updated for the Ninten-Do application.
 * 
 *
 */
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class LevelsData {
	//logic to set the levels data
	private static LevelsData instance = new LevelsData();
	private HashMap<Integer, Integer> levels;
	private int maxLevel = 20;
	
	private LevelsData() {}
	
	public void loadLevels() {
		//hashmap used for the levels data to be stored via identifiers
		levels = new HashMap<Integer, Integer>();
		try {
			Scanner inFile = new Scanner(new File("resources/data/levels.csv"));
			String line;
			//scanner used to read in csv file for levels
			
			while(inFile.hasNextLine()) {
				line = inFile.nextLine();
				String splitted[] = line.split(",");
				levels.put(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
			}
			//used to parse data
			
			inFile.close();
			//closes the csv file
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getLevelXp(int level) {
		return this.levels.get(level);
	}
	//function to return the get level
	
	public int getMaxLevel() {
		return this.maxLevel;
	}
	//function to return the max level
	public static LevelsData getInstance() {
		return instance;
	}
	//function to return instance
}
