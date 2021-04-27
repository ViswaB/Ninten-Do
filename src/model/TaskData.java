package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * The following project Ninten-Do is an application that allows users to input tasks they must complete in a game like manner.
 * The goal of the application is to make daily chores fun and engaging for the user. The java model application TaskData.java
 * is created in order to set the logic, functions and calls needed to allow users to update
 * the data entered for the tasks necessary for the Ninten-Do application.
 * 
 *
 */
public class TaskData {
	
	private static TaskData instance = new TaskData();
	private static String filenameBase = "resources/data/tasks/";
	private static String filename;
	private ObservableList<TaskItem> tasks;
	private Path tasksPath;
	
	private TaskData() {}
	//hashmap TaskItem
	public ObservableList<TaskItem> getTasks(){
		return tasks;
	}
	
	public void setFilename(int uid) {
		//created to set the filename and update
		StringBuffer fullFile = new StringBuffer();
		fullFile.append(filenameBase);
		fullFile.append("user");
		fullFile.append(uid);
		fullFile.append("tasks.dat");
		filename = fullFile.toString();
	}
	
	public static TaskData getInstance() {
		return instance;
	}
	
	public void addTask(TaskItem task) {
		tasks.add(task);
	}
	
	public void removeTask(TaskItem task) {
		tasks.remove(task);
	}
	
	public void clearTasks() {
		tasks.clear();
	}
	
	public void markComplete(TaskItem task) {
		for(TaskItem t : tasks) {
			if(t == task) {
				t.setCompleted(true);
			}
		}
		//created to mark a task completed
	}
	
	public void loadTasks() {
		createIfNotExist();
		tasks = FXCollections.observableArrayList();
		tasksPath = FileSystems.getDefault().getPath(filename);
		
		try (ObjectInputStream taskFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(tasksPath)))){
			boolean eof = false;
			while(!eof) {
				try {
					TaskItem taskItem = (TaskItem) taskFile.readObject();
					tasks.add(taskItem);
				}catch (EOFException e) {
					eof = true;
				}
			}
		}catch(EOFException e) {
		}catch (InvalidClassException e) {
		}catch (IOException e) {
		}catch (ClassNotFoundException e) {
		}
		//logic to load tasks and store
	}
	
	public void saveTasks() {
		tasksPath = FileSystems.getDefault().getPath(filename);
		
		try (ObjectOutputStream taskFile = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(tasksPath)))){
			Iterator<TaskItem> iter = tasks.iterator();
			while(iter.hasNext()) {
				TaskItem task = iter.next();
				taskFile.writeObject(task);
			}
		}catch (IOException e) {
		}
		//created to save the tasks
	}
	
	private void createIfNotExist() {
		File file = new File(filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		//created new file name if file does not exist
	}
	
	public String getFileName() {
		return filename;
	}
	//returns filename
}
