package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TaskItem implements Serializable{
	private static final long serialVersionUID = 1L;
	private String shortDesc;
	private String longDesc;
	private LocalDate deadline;
	private boolean completed;
	private int rank;
	
	public TaskItem(String shortDesc, String longDesc, LocalDate deadline) {
		this.shortDesc = shortDesc;
		this.longDesc = longDesc;
		this.deadline = deadline;
		this.completed = false;
		calculateRank();
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getlongDesc() {
		return longDesc;
	}
	public void setDetails(String longDesc) {
		this.longDesc = longDesc;
	}
	public LocalDate getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}
	public boolean getCompleted() {
		return this.completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public int getRank() {
		return this.rank;
	}
	
	private void calculateRank() {
		long daysLong = ChronoUnit.DAYS.between(LocalDate.now(), this.deadline);
		int days = (int)daysLong;
		
		if(days == 0) {
			this.rank = 500;
		}else if(days > 20 && days <= 30) {
			switch(days) {
			case 21:
				this.rank = (days * 13) + 7;
				break;
			case 22:
				this.rank = (days * 13) - 6;
				break;
			case 23:
				this.rank = (days * 11) + 7;
				break;
			case 24:
				this.rank = (days * 11) - 4;
				break;
			case 25:
				this.rank = (days * 9) + 15;
				break;
			case 26:
				this.rank = (days * 9) + 6;
				break;
			case 27:
				this.rank = (days * 8) + 4;
				break;
			case 28:
				this.rank = (days * 8) - 4;
				break;
			case 29:
				this.rank = (days * 7) - 3;
				break;
			case 30:
				this.rank = (days * 6) + 20;
				break;
			}
		}else if(days > 10 && days <=20) {
			switch(days) {
			case 11:
				this.rank = (days * 35) - 5;
				break;
			case 12:
				this.rank = (days * 32) - 4;
				break;			
			case 13:
				this.rank = (days * 28) - 4;
				break;
			case 14:
				this.rank = (days * 26) - 4;
				break;
			case 15:
				this.rank = (days * 23) - 5;
				break;
			case 16:
				this.rank = (days * 21) + 4;
				break;			
			case 17:
				this.rank = (days * 19) - 3;
				break;
			case 18:
				this.rank = (days * 18) - 4;
				break;			
			case 19:
				this.rank = (days * 16) - 4;
				break;
			case 20:
				this.rank = (days * 15);
				break;
			}
		}else if(days <= 10) {
			switch(days) {
			case 1:
				this.rank = (days * 480);
				break;
			case 2:
				this.rank = (days * 240);
			case 3:
				this.rank = (days * 154) - 2;
				break;
			case 4:
				this.rank = (days * 115);
			case 5:
				this.rank = (days * 88);
				break;
			case 6:
				this.rank = (days * 74) - 4;
			case 7:
				this.rank = (days * 60);
				break;
			case 8:
				this.rank = (days * 53) - 4;
			case 9:
				this.rank = (days * 45) - 5;
				break;
			case 10:
				this.rank = (days * 40);
			}
		}else {
			this.rank = 180;
		}
	}
	
//	@Override
//	public String toString() {
//		return this.shortDesc;
//	}
	
}
