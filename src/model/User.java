package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.ObservableList;

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
	private int reducedBossHp; //get by adding all ranks of tasks in completedTasks
	private int userLevel; //current user level
	private int userID;
	
	
	public User(String firstName, String lastName, String userName, String password) {
		this.userName = userName;
		this.lastName = lastName;
		this.firstName = firstName;
		this.password = password;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	
	public String getLastName() {
		return this.lastName;
	}
	
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setCompletedTask(ObservableList<TaskItem> totalTasks) {
		for(TaskItem task : totalTasks) {
			if(task.getCompleted()) {
				this.completedTasks.add(task);
			}
		}
	}
	
	public void setBossHpStats(ObservableList<TaskItem> totalTasks) {
		int bossHp = 0;
		int remainingBossHp = 0;
		for(TaskItem task : totalTasks) {
			bossHp += task.getRank();
		}
		this.userMaxBossHp = bossHp;
		
		for(TaskItem task : completedTasks) {
			remainingBossHp += task.getRank();
		}
		this.reducedBossHp = remainingBossHp;
	}
	
	public int getMaxBossHp() {
		return this.userMaxBossHp;
	}
	
	public int getBossDmg() {
		return this.reducedBossHp;
	}
	
	public int getUserLvl() {
		return this.userLevel;
	}
	
	public int getUserXp() {
		return this.userXp;
	}
	
	public int getNextLvlXp() {
		return this.xpToNextLvl;
	}
	
	public int getUserID() {
		return this.userID;
	}

}
