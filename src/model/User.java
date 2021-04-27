package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.ObservableList;
/**
 * The following project Ninten-Do is an application that allows users to input tasks they must complete in a game like manner.
 * The goal of the application is to make daily chores fun and engaging for the user. The java Model application User.java
 * is created in order to set the logic, functions and calls needed to allow users to view and update
 * the data entered for the tasks necessary for the Ninten-Do application.
 * 
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private ArrayList<TaskItem> completedTasks; //add task if completed
	private int userXp; //calculated by dividing task rank by 10?
	private int xpToNextLvl; //read from file containing lvl,xp
	private int userMaxBossHp; //get by adding all ranks of tasks
	private int bossDamage; //get by adding all ranks of tasks in completedTasks
	private int userLevel; //current user level
	private int userID;
	
	
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
	
	public String getFirstName() {
		return this.firstName;
	}
	//returns the first name
	
	public String getLastName() {
		return this.lastName;
	}
	//returns the lastname
	
	public String getUserName() {
		return this.userName;
	}
	//returns the string for get username
	
	public String getPassword() {
		return this.password;
	}
	//returns the string for get password
	
	public void setCompletedTask(TaskItem task) {
		//sets the task items
		this.completedTasks.add(task);
		hitBoss(task.getRank());
		addXp(task.getRank()/10);
	}
	
	public void setBossHpStats(ObservableList<TaskItem> totalTasks) {
		//hashmap to store items in identifiers to calculate the total tasks and ranks
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
	
	public void setMaxBossHp(int hp) {
		if(this.userMaxBossHp == -1) {
			this.userMaxBossHp = hp;
			this.bossDamage = 0;
		}
		//sets the max boss hp
	}
	
	public int getMaxBossHp() {
		return this.userMaxBossHp;
	}
	
	//returns the usermax boss hp
	public int getBossDmg() {
		return this.bossDamage;
	}
	//returns the loss
	
	private void hitBoss(int amount) {
		//logic for the amount calculations
		int newBossDmg = this.bossDamage + amount;
		
		if(this.userMaxBossHp != -1) {
			if(newBossDmg > this.userMaxBossHp) {
				this.userMaxBossHp = -1;
			}else {
				this.bossDamage = newBossDmg;
			}
		}
	}
	
	public int getUserLvl() {
		return this.userLevel;
	}
	//returns the user level
	
	private void addXp(int xp) {
		//logic to add the xp 
		
		if(this.userLevel != -1) {
			int newXp = this.userXp + xp;
			//logic to add xp and store
			if(newXp > this.xpToNextLvl) {
				this.userLevel++;
				int remainingXp = newXp - this.xpToNextLvl;
				this.userXp = remainingXp;
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
	
	public int getUserXp() {
		return this.userXp;
	}
	//returns userxp
	
	public void setNextLvlXp(int xp) {
		this.xpToNextLvl = xp;
	}
	//sets next xp
	public int getNextLvlXp() {
		return this.xpToNextLvl;
	}
	//returns get new xp
	
	public int getUserID() {
		return this.userID;
	}
	//returns userID
	
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}
	//returns first name and last name called via String toString

}
