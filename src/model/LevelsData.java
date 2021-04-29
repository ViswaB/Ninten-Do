package model;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Singleton class allows Levels data
 * to be accessible throughout entire application
 * No need to instantiate separate instances 
 * whenever application needs access to Levels data
 * 
 * @author Filiberto Rios
 *
 */
public class LevelsData {
	private static LevelsData instance = new LevelsData();
	private HashMap<Integer, Integer> levels;
	private int maxLevel = 20;
	
	private LevelsData() {}
	
	public void loadLevels() {
		// HashMap used for the levels data to be stored via identifiers
		levels = new HashMap<Integer, Integer>();
		try {
			//scanner used to read in csv file for levels
			Scanner inFile = new Scanner(new File("resources/data/levels.csv"));
			String line;
			
			//used to parse data
			while(inFile.hasNextLine()) {
				line = inFile.nextLine();
				String splitted[] = line.split(",");
				levels.put(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
			}
			
			//closes the csv file
			inFile.close();
		}catch(IOException e) {
		}
	}
	
	/**
	 * Returns the XP required for completing
	 * current User level
	 * 
	 * @param level
	 * @return
	 */
	public int getLevelXp(int level) {
		return this.levels.get(level);
	}
	
	/**
	 * Gets internally set Max level
	 * 
	 * @return
	 */
	public int getMaxLevel() {
		return this.maxLevel;
	}
	
	/**
	 * Returns instance of this Singleton class
	 * @return
	 */
	public static LevelsData getInstance() {
		return instance;
	}
}
