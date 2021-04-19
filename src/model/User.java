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
	
	public void setCompletedTask(TaskItem task) {
		this.completedTasks.add(task);
		hitBoss(task.getRank());
		addXp(task.getRank()/10);
	}
	
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
	
	public int getMaxBossHp() {
		return this.userMaxBossHp;
	}
	
	public int getBossDmg() {
		return this.bossDamage;
	}
	
	private void hitBoss(int amount) {
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
	
	private void addXp(int xp) {
		
		if(this.userLevel != -1) {
			int newXp = this.userXp + xp;
			
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
	
	public void setNextLvlXp(int xp) {
		this.xpToNextLvl = xp;
	}
	
	public int getNextLvlXp() {
		return this.xpToNextLvl;
	}
	
	public int getUserID() {
		return this.userID;
	}

}
