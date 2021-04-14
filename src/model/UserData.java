package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class UserData {
	private static UserData instance = new UserData();
	private static String usersFile = "users.dat";
	private User loggedInUser;
	private ArrayList<User> allUsers;
	private Path filePath;

	private UserData() {}
	
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
	
	public void  addUser(User user) {
		this.loggedInUser = user;
		this.allUsers.add(user);
	}
	
	public void loadUsers() {
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
		}catch(InvalidClassException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
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
	}
	
	public UserData getInstance() {
		return instance;
	}

}
