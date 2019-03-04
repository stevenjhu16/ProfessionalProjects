import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable{
	private String username, password, first_name, last_name;
	private boolean user_type;
	private static ArrayList<User> user = new ArrayList<User>();

	
	public User() {
		
	}
	public User(String first_name, String last_name, boolean user_type) {
		this.setFirst_name(first_name);
		this.setLast_name(last_name);
		this.setUser_type(user_type);
	}
	public User(String first_name, String last_name, String username, String password, boolean user_type) {
		this.setFirst_name(first_name);
		this.setLast_name(last_name);
		this.setUsername(username);
		this.setPassword(password);
		this.setUser_type(user_type);
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(!password.equals("NOT ME")) {
			this.password = password;

		}
		else throw new IllegalArgumentException("This password cannot be used. Please change a password.");
	}

	public boolean isUser_type() {
		return user_type;
	}

	public void setUser_type(boolean user_type) {
		this.user_type = user_type;
	}

	public static ArrayList<User> getUser() {
		return user;
	}
	
	public static void setUser(ArrayList<User> user) {
		User.user = user;
	}
	
	/**
	 * Exit the program and serialize user-list and course-list
	 */
	public void logout()   {
		System.out.println("Logging out...");
		
		//Serialize objects as the user logging out
		try {
			FileOutputStream fos_user = new FileOutputStream("UserList.ser");
			ObjectOutputStream oos_user = new ObjectOutputStream(fos_user);
			FileOutputStream fos_course = new FileOutputStream("CourseList.ser");
			ObjectOutputStream oos_course = new ObjectOutputStream(fos_course);
			ArrayList userList = User.user;
			oos_user.writeObject(userList);
			ArrayList courseList = Course.getCourseList();
			oos_course.writeObject(courseList);
			
			fos_user.close();
			oos_user.close();
			fos_course.close();
			oos_course.close();
			
		}
		catch (IOException ioe){
			ioe.printStackTrace();
		}
		System.exit(0);
	}
	
}
