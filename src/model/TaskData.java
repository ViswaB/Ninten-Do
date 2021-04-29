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
 * Singleton class allows Task data
 * to be accessible all throughout application
 * in a static manner.
 * 
 * No need to instantiate instances of this class
 * whenever application needs access to Task data
 * 
 * Handles all logic related to processing TaskItems
 * 
 * @author Filiberto Rios
 *
 */
public class TaskData {
	
	private static TaskData instance = new TaskData();
	private static String filenameBase = "resources/data/tasks/";
	private static String filename;
	private ObservableList<TaskItem> tasks;
	private Path tasksPath;

	/**
	 * Private constructor doesn't allow for instances to be made
	 */
	private TaskData() {}

	/**
	 * Retrieves a list of Tasks to be used throughout application
	 * @return
	 */
	public ObservableList<TaskItem> getTasks(){
		return tasks;
	}
	
	/**
	 * Sets User's task filename based on User ID
	 * User IDs are unique, therefore Tasks won't
	 * get mixed up with other user's taks files
	 * 
	 * @param uid
	 */
	public void setFilename(int uid) {
		//created to set the filename and update
		StringBuffer fullFile = new StringBuffer();
		fullFile.append(filenameBase);
		fullFile.append("user");
		fullFile.append(uid);
		fullFile.append("tasks.dat");
		filename = fullFile.toString();
	}
	
	/**
	 * Returns instance of singleton
	 * @return
	 */
	public static TaskData getInstance() {
		return instance;
	}
	
	/**
	 * Adds task to list of user tasks
	 * 
	 * @param task
	 */
	public void addTask(TaskItem task) {
		tasks.add(task);
	}
	
	/**
	 * Removes task from list of user tasks
	 * 
	 * @param task
	 */
	public void removeTask(TaskItem task) {
		tasks.remove(task);
	}
	
	/**
	 * Clears all user's tasks
	 */
	public void clearTasks() {
		tasks.clear();
	}
	
	/**
	 * Marks given task as complete
	 * 
	 * @param task
	 */
	public void markComplete(TaskItem task) {
		for(TaskItem t : tasks) {
			if(t == task) {
				t.setCompleted(true);
			}
		}
	}
	
	/**
	 * Creates unique user Task file if it doesn't exist
	 * Reads all Tasks stored in unique file and stores in HashMap
	 * 
	 */
	public void loadTasks() {
		createIfNotExist();
		tasks = FXCollections.observableArrayList();
		tasksPath = FileSystems.getDefault().getPath(filename);
		
		// Creates Stream to read TaskItem Object binary data from file
		try (ObjectInputStream taskFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(tasksPath)))){
			boolean eof = false;
			
			// Continues to read until End of File is reached
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
	}
	
	/**
	 * Saves all TaskItems stored for Users in unqie User Task file
	 */
	public void saveTasks() {
		tasksPath = FileSystems.getDefault().getPath(filename);
		
		// Creates Stream to write TaskItem Object binary data to file
		try (ObjectOutputStream taskFile = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(tasksPath)))){
			Iterator<TaskItem> iter = tasks.iterator();
			while(iter.hasNext()) {
				TaskItem task = iter.next();
				taskFile.writeObject(task);
			}
		}catch (IOException e) {
		}
	}
	
	/**
	 * Creates unique User Task file
	 */
	private void createIfNotExist() {
		File file = new File(filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * Returns User specific file name for debugging
	 * 
	 * @return
	 */
	public String getFileName() {
		return filename;
	}
}
