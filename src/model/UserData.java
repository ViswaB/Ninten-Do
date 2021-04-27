package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.File;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * The following project Ninten-Do is an application that allows users to input tasks they must complete in a game like manner.
 * The goal of the application is to make daily chores fun and engaging for the user. The java Model application UserData.java
 * is created in order to set the logic, functions and calls needed to allow users to view and update
 * the data entered for the user data necessary for the Ninten-Do application.
 * 
 *
 */
public class UserData {
	private static UserData instance = new UserData();
	private static String usersFile = "resources/data/users/users.dat";
	private User loggedInUser;
	private ArrayList<User> allUsers;
	private Path filePath;

	private UserData() {}
	//logic for user data
	
	public boolean getUser(String userName, String password) {
		for(User user : allUsers) {
			if(user.getUserName().equals(userName) && user.getPassword().equals(password)) {
				this.loggedInUser = user;
				return true;
			}
		}
		return false;
	}
	
	public User retrieveUser() {
		return this.loggedInUser;
	}
	
	public boolean addUser(User user) {
		//logic to add users
		for(User u: allUsers) {
			if(u.getUserName().equals(user.getUserName())){
				return false;
			}
		}
		try {
			this.loggedInUser = user;
			this.allUsers.add(user);
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void loadUsers() {
		createIfNotExist();
		allUsers = new ArrayList<User>();
		filePath = FileSystems.getDefault().getPath(usersFile);
		
		try(ObjectInputStream uFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(filePath)))){
			boolean eof = false;
			while(!eof) {
				try {
					User user = (User) uFile.readObject();
					allUsers.add(user);
				}catch(EOFException e) {
					eof = true;
				}
			}
		}catch(EOFException e) {
		}catch(InvalidClassException e) {
		}catch(IOException e) {
		}catch(ClassNotFoundException e) {
		}
		//loads and adds users
	}
	
	public void saveUsers() {
		filePath = FileSystems.getDefault().getPath(usersFile);
		
		try(ObjectOutputStream uFile = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(filePath)))){
			Iterator<User> iter = allUsers.iterator();
			while(iter.hasNext()) {
				User user = iter.next();
				uFile.writeObject(user);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		//saves users
	}
	
	public void updateUser() {
		for(User user : allUsers) {
			if(user == this.loggedInUser) {
				user = this.loggedInUser;
			}
		}
		//updates users logged in data
	}
	
	public static UserData getInstance() {
		return instance;
	}
	//returns instance for userdata
	
	private void createIfNotExist() {
		File file = new File(usersFile);
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//created users file if it does not exist
	}

}
