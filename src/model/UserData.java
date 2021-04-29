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
 * Singleton class allows UserData
 * to be accessible throughout application
 * in a static manner
 * 
 * No need to instantiate instances of this class
 * whenever application needs access to Task data
 * 
 * Handles all logic related to User files data
 * 
 * @author Filiberto Rios
 *
 */
public class UserData {
	private static UserData instance = new UserData();
	private static String usersFile = "resources/data/users/users.dat";
	private User loggedInUser;
	private ArrayList<User> allUsers;
	private Path filePath;

	/**
	 * Private constructor doesn't allow instances of the class
	 */
	private UserData() {}
	
	/**
	 * Iterates through all Users and checks
	 * if requested user exists, if it does
	 * it sets loggedInUser to the User class
	 * associated with requested user for login
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean getUser(String userName, String password) {
		for(User user : allUsers) {
			if(user.getUserName().equals(userName) && user.getPassword().equals(password)) {
				this.loggedInUser = user;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return Logged in user
	 */
	public User retrieveUser() {
		return this.loggedInUser;
	}
	
	/**
	 * Adds user to database of users
	 * Fails if two users have the same UserName
	 * Called only when Users register a new account
	 * @param user
	 * @return
	 */
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
	
	/**
	 * Reads and stores all users from the users database file
	 */
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
	}
	
	/**
	 * Saves all users back to the database file
	 */
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
	}
	
	/**
	 * Updates logged in user in HashMap with any changes made throughout application
	 * Not entirely sure if this is required, but better safe than sorry
	 */
	public void updateUser() {
		for(User user : allUsers) {
			if(user == this.loggedInUser) {
				user = this.loggedInUser;
			}
		}
	}
	
	/**
	 * @return Singleton instance
	 */
	public static UserData getInstance() {
		return instance;
	}
	
	/**
	 * Creates user database file if it doesn't exist
	 */
	private void createIfNotExist() {
		File file = new File(usersFile);
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
