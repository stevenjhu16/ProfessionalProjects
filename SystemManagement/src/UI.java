import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The user-interface class
 * @author Steven Hu
 * @version 1.0
 *
 */
public abstract class UI {
	 private static User currentUser;
	
	public static void main(String[] args) throws IOException,ClassNotFoundException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		UI.loadUser(); //De-serialize user list
		Course.loadCourse(); //De-serialize course list
		
		
		System.out.println(	"Welcome to the Constantine Course Management System\n"
						+	"What would you like to do today:\n"
						+ 	"1. Login\n"
						+ 	"2. Set up student account");	
		boolean option_checker = false;
		while(!option_checker) {
			String answer = in.readLine();
			if (answer.equals("1")) {
				UI.login();
				option_checker = true;
			}
			else if (answer.equals("2")){
				UI.initAccount();
				option_checker = true;
			}
			else {
				System.out.println("Invalid input, please try again.");
			}
		}
		
		//after successful login
		if(currentUser.isUser_type()) { //Admin UI
			Admin admin = (Admin)currentUser;
			while(true) {
				UI.mainOptions(admin);
			}
		}
		else {//Student UI
			Student student = (Student)currentUser;
			while(true) {
				UI.mainOptions(student);
			}
		}
	}//main
	
	public static void mainOptions(Admin admin) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nMain functions:");
		System.out.println(Admin.getOptions());
		String ans = in.readLine();
		switch(ans) {
		case "1":
			adminManagementOptions(admin);
			break;
		case "2":
			adminReportOptions(admin);
			break;
		case "3":
			UI.currentUser.logout();
			break;
			default:
				System.out.println("Invalid input. Please try again.");
				UI.mainOptions(admin);
		}
		 
	}
	
	public static void adminManagementOptions(Admin admin) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Course Management Options: ");
		System.out.println(Admin.getManagementOptions());
		System.out.println("What would you like to do?");
		String ans = in.readLine();
		switch(ans) {
		case "1":
			admin.createCourse();
			break;
		case "2":
			admin.deleteCourse();
			break;
		case "3":
			admin.editCourse();
			break;
		case "4":
			admin.displayCourse();
			break;
		case "5":
			admin.registerStudent();
			break;
		case "6":
			UI.mainOptions(admin);
			break;
			default:
				System.out.println("Invalid input. Please try again.");			
		}
		UI.adminManagementOptions(admin);
		 
	}
	public static void adminReportOptions(Admin admin) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Report Options: ");
		System.out.println(Admin.getReportOptions());
		System.out.println("What would you like to do?");
		String ans = in.readLine();
		switch (ans) {
		case "1":
			admin.viewAllCourse();
			break;
		case "2":
			admin.viewFullCourse();
			break;
		case "3":
			admin.viewStudentOfCourse();
			break;
		case "4":
			admin.viewCourseOfStudent();
			break;
		case "5":
			admin.sortByNumOfStudent(0);
			break;
		case "6":
			UI.mainOptions(admin);
			break;
			default:
				break;
		}
		UI.adminReportOptions(admin);
	}
	
	public static void mainOptions(Student student) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nMain functions:");
		System.out.println(Student.getOptions());
		String ans = in.readLine();
		switch(ans) {
		case "1":
			student.viewAllCourse();
			break;
		case "2":
			student.registerCourse();
			break;
		case "3":
			student.withdrawCourse();
			break;
		case "4":
			student.viewEnrolledCourse();
			break;
		case "5":
			student.viewAvailableCourse();
			break;
		case "6":
			UI.currentUser.logout();
			break;
			default:
				System.out.println("Invalid input. Please try again.");
				UI.mainOptions(student);
		}
	}
	
	public static void login() throws IOException,ClassNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		boolean not_me_disabled = true;
		String username = "";
		
		System.out.println("Login to Constantine Course Management System");
		
		//check if username exists, loop until user input fits one of the username on record
		do {
			boolean username_status = false;
			boolean password_status = false;
			
			while (!username_status) {
				System.out.println(	"Please enter the following information:\n"
						+ 	"username: ");
				username = in.readLine();
		
				for (User temp: User.getUser()){
					if(temp.getUsername().equals(username)) {
						username_status = true;
						UI.currentUser = temp;
					}//if
				}//for
				if(username_status == false) {
					System.out.println("No such username found in record. Please try again.");
				} //if
			}//while
			
			//check if password is correct given the correct username
			while(!password_status) {
				System.out.println("Please enter password for account: "+ username + " . If this is not you, type \"NOT ME\" ");
				System.out.println("password: ");
				String password = in.readLine();
				if(password.equals("NOT ME")) {
					not_me_disabled = false;
					password_status = true;
				}else {
					for (User temp: User.getUser()){
						if(temp.getPassword().equals(password)) {
							password_status = true;
						}//if
					}//for
					if(password_status == false) {
						System.out.println("Password incorrect. Please try again.");
					} else {
						System.out.println("Login successful. Welcome, " + username);
						not_me_disabled = true;
					}
				}
				
				
			}//while
		}while(!not_me_disabled);
		
	}//login()


	
	public static void initAccount() throws IOException, ClassNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String first_name, last_name, username=null, password=null;
		System.out.println(	"Please enter the following information for account creation:\n"
						+ 	"First name: ");
		first_name = in.readLine();
		System.out.println("Last name: ");
		last_name = in.readLine();
		
		//Find the Student objects that the Admin has added to the system by checking names
		for(int i = 0;i<User.getUser().size();i++) { //assume that each student has unique name, this loop will only execute once
			User user = User.getUser().get(i);
			if(user instanceof Student) {
				Student student = (Student)user;
				//for not initialized student
				if(first_name.equals(student.getFirst_name())&&last_name.equals(student.getLast_name())&&!student.isInitialized()) { 
					System.out.println("Setting Username: ");
					username = in.readLine();
					student.setUsername(username);
					System.out.println("Setting Password: ");
					password = in.readLine();
					student.setPassword(password);
					student.setInitialized(true);
					System.out.println("Account change successful! Returning to login page...");
					login();
				}//if
				//for initialized student, prevent second time initialization
				else if(first_name.equals(student.getFirst_name())&&last_name.equals(student.getLast_name())&&student.isInitialized()) {
					System.out.println("This account is already initialized. Please login.");
					login();
				}
			}//if (instanceof)
		}
		
		 
	}
	
	public static void loadUser() throws ClassNotFoundException {
		//De-serialize user list
		try {
			FileInputStream fis = new FileInputStream("UserList.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			User.setUser(((ArrayList<User>)ois.readObject()));
			fis.close();
			ois.close();
		}
		catch(FileNotFoundException fnfe){
			//execute only when the program is runned by the user for the first time
			ArrayList<User> temp = User.getUser();
			temp.add(new Admin("Foo","Barista","Admin","Admin001")); // register the default admin in the system
//			temp.add(new Student("First","Second","Student","Student001")); //no default student, according to the professor
			return;
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
			return;
		}
				
	}

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User currentUser) {
		UI.currentUser = currentUser;
	}
}
