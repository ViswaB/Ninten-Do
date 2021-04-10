package model;

import java.io.Serializable;
import java.time.LocalDate;

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
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	@Override
	public String toString() {
		return this.shortDesc;
	}
	
}
