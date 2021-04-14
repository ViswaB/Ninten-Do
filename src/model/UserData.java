package model;

import java.nio.file.Path;
import java.util.HashMap;

public class UserData {
	private static UserData instance = new UserData();
	private static String usersFile;
	private HashMap<Integer, User> allUsers;
	private Path filePath;
	
	private UserData() {}
	
	public User getUser(int uid) {
		return this.allUsers.get(uid);
	}
	
	public void addUser(User user, int uid) {
		this.allUsers.put(uid, user);
	}
	
	public UserData getInstance() {
		return instance;
	}

}
