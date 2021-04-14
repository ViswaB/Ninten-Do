package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
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

public class TaskData {
	private static TaskData instance = new TaskData();
	private static String filenameBase = "resources/data/tasks/";
	private static String filename;
	private ObservableList<TaskItem> tasks;
	private Path tasksPath;
	
	private TaskData() {}
	
	public ObservableList<TaskItem> getTasks(){
		return tasks;
	}
	
	public void setFilename(int uid) {
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
	}
	
	public void loadTasks() {
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
		}catch (InvalidClassException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
			e.printStackTrace();
		}
	}
}
