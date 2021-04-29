package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.ObservableList;

/**
 * Model class for Users
 * Implements Serializable interface
 * to allow class to be written to file
 * 
 * @author Filiberto Rios
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private ArrayList<TaskItem> completedTasks; //add task if completed
	private int userXp; //calculated by dividing task rank by 10
	private int xpToNextLvl; //read from file containing lvl,xp
	private int userMaxBossHp; //get by adding all ranks of tasks
	private int bossDamage; //get by adding all ranks of tasks in completedTasks
	private int userLevel; //current user level
	private int userID;
	
	/**
	 * Constructor to initialize User fields with given values
	 * Sets some default values
	 * 
	 * @param firstName
	 * @param lastName
	 * @param userName
	 * @param password
	 * @param id
	 */
	public User(String firstName, String lastName, String userName, String password, int id) {
		this.userName = userName;
		this.lastName = lastName;
		this.firstName = firstName;
		this.password = password;
		this.userID = id;
		this.userLevel = 0;
		this.userXp = 0;
		this.userMaxBossHp = 0;
		this.bossDamage = 0;
		this.completedTasks = new ArrayList<TaskItem>();
	}
	
	/**
	 * @return User's First Name
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * @return User's Last Name
	 */
	public String getLastName() {
		return this.lastName;
	}
	
	/**
	 * @return User's UserName
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * @return User's Password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Adds given task to the completed task list
	 * Updates boss HP and User XP stats
	 * 
	 * @param task
	 */
	public void setCompletedTask(TaskItem task) {
		this.completedTasks.add(task);
		hitBoss(task.getRank());
		addXp(task.getRank()/10);
	}
	
	/**
	 * Sets Boss HP stats based on given list of tasks
	 * and User's completed task list
	 * 
	 * @param totalTasks
	 */
	public void setBossHpStats(ObservableList<TaskItem> totalTasks) {
		if(this.userMaxBossHp != -1) {
			int bossHp = 0;
			int remainingBossHp = 0;
			for(TaskItem task : totalTasks) {
				bossHp += task.getRank();
			}
			this.userMaxBossHp = bossHp;
			
			for(TaskItem task : completedTasks) {
				remainingBossHp += task.getRank();
			}
			this.bossDamage = remainingBossHp;
		}
	}
	
	/**
	 * Sets the max boss HP
	 * 
	 * @param hp
	 */
	public void setMaxBossHp(int hp) {
		if(this.userMaxBossHp == -1) {
			this.userMaxBossHp = hp;
			this.bossDamage = 0;
		}
	}
	
	/**
	 * @return Boss' max HP
	 */
	public int getMaxBossHp() {
		return this.userMaxBossHp;
	}
	
	/**
	 * @return Damage one to boss by user
	 */
	public int getBossDmg() {
		return this.bossDamage;
	}
	
	/**
	 * Damages boss based on given amount
	 * Amount is based on Task rank
	 * 
	 * @param amount
	 */
	private void hitBoss(int amount) {
		//logic for the amount calculations
		int newBossDmg = this.bossDamage + amount;
		
		if(this.userMaxBossHp != -1) {
			if(newBossDmg > this.userMaxBossHp) {
				// Sets Boss' HP to -1 upon "defeating" the boss
				this.userMaxBossHp = -1;
			}else {
				this.bossDamage = newBossDmg;
			}
		}
	}
	
	/**
	 * @return User's current level
	 */
	public int getUserLvl() {
		return this.userLevel;
	}
	
	/**
	 * Grants the user XP based on TaskRank / 10
	 * Called in setCompletedTask() method
	 * 
	 * @param xp
	 */
	private void addXp(int xp) {
		// If user is not MAX level
		if(this.userLevel != -1) {
			int newXp = this.userXp + xp;
			
			// Checks to see if current user XP is enough
			// to reach next level
			if(newXp > this.xpToNextLvl) {
				// increases user level
				this.userLevel++;
				
				//calculates remaining XP for next level completion progress
				int remainingXp = newXp - this.xpToNextLvl;
				this.userXp = remainingXp;
				
				// Sets User level, if above 20, sets to -1 for easier level checking
				if(this.userLevel >= LevelsData.getInstance().getMaxLevel()) {
					this.userLevel = -1;
				}else {
					setNextLvlXp(LevelsData.getInstance().getLevelXp(this.userLevel+1));
				}
			}else {
				this.userXp = newXp;
			}
		}
	}

	/**
	 * @return User's current XP
	 */
	public int getUserXp() {
		return this.userXp;
	}
	
	/**
	 * Sets XP amount needed to reach the next level
	 * Value is retrieved from LevelsData singleton
	 * 
	 * @param xp
	 */
	public void setNextLvlXp(int xp) {
		this.xpToNextLvl = xp;
	}
	
	/**
	 * @return XP required to reach next level
	 */
	public int getNextLvlXp() {
		return this.xpToNextLvl;
	}
	
	/**
	 * @return User's ID
	 */
	public int getUserID() {
		return this.userID;
	}
	
	/**
	 * Removes task upon completion
	 * 
	 * @param task
	 */
	public void removeTask(TaskItem task) {
		if(task.getCompleted()) 
			completedTasks.remove(task);
	}
	
	/**
	 * Removes all tasks associated with user
	 */
	public void clearTasks() {
		completedTasks.clear();
	}
	
	/**
	 * @return User's FirstName and LastName to represent User class
	 */
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}

}
