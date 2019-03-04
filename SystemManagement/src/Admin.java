import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.*;
import com.opencsv.*;
import java.io.FileWriter;

public class Admin extends User implements admin_standard {
	
	private ArrayList<Course> sortedCourse = new ArrayList<Course>();
	private int size = Course.getCourseList().size();
	public Admin(String first_name, String last_name, String username, String password) {
		super(first_name, last_name, username, password,true);
	}
	
	//store the options for console display
	private static final String management_options =	
								"1. Create a new course\n"
							+ 	"2. Delete a course\n"
							+ 	"3. Edit a course\n"
							+ 	"4. Display information for a given course\n"
							+ 	"5. Register a student\n"
							+ 	"6. Back to main menu\n";
	private static final String report_options = 
							 	"1. View all courses\n"
							+ 	"	1.a. See students' details in each course\n"
							+ 	"2. View all courses that are FULL\n"
							+ 	"	2.a. Export the list\n"
							+ 	"3. View the names of students in a specific course\n"
							+	"4. View the list of courses that a given student is registered in\n"
							+	"5. Sort the courses by number of student enrolled\n"
							+	"6. Back to main menu\n"
							+	"* all sub-functionalities can be accessed within the main functionality";
	private static final String options = 
								"1. Course Management (Edit, create, delete, etc.)\n"
							+	"2. Course Reports (View, sort, and export)\n"
							+	"3. Logout\n";
	
	public void createCourse() throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<Student> student_list = new ArrayList<Student>();
//		Student student = null;
		boolean noStudent = true;
		
		System.out.println("Please enter:");
		System.out.println("Course Name: ");
		String course_name = in.readLine();
		System.out.println("Course ID: ");
		String course_id = in.readLine();
		System.out.println("Maximum Student: (Number only)");
		boolean maxIsValid = false;
		String max_student =null;
		do {
			max_student = in.readLine();
			if((Integer.parseInt(max_student)>0)) {
				maxIsValid =true;
			}
			else {
				System.out.println("Maximum Number of students cannot be negative. Please try again.");
			}
			
		}while(!maxIsValid);
		System.out.println("Current Student: (Number only)");
		boolean currentIsValid = false;
		String current_student = null;
		do {
			current_student = in.readLine();
			if(Integer.parseInt(current_student)<=Integer.parseInt(max_student)&& (Integer.parseInt(current_student)>=0)) {
				currentIsValid =true;
			}else {
				System.out.println("Current number of student bigger than Max Student or is negative. Please try again");
			}
			
		}while(!currentIsValid);
		
//		System.out.println("Students in class: (Format: First/Last,Bob/Richardson,... if no student is in that class, enter: none)");
//		String studentNames = in.readLine();
//		if(studentNames.equals("none")) {
//			noStudent = true;
//		}else {
//			String[] names = studentNames.split(",");
//			ArrayList <String[]> full_name_list = new ArrayList<String[]>(); // ArrayList full of names split into String array
//			for (String temp :names) {
//				String[] first_last_name = temp.split("/");
//				full_name_list.add(first_last_name);
//			}
//			for(int i =0; i< User.getUser().size();i++) {
//				User user = User.getUser().get(i);
//				for(String[] array : full_name_list){ // cycle through all names input and search for created account
//					if(user instanceof Student && user.getFirst_name().equals(array[0])&& user.getLast_name().equals(array[1])) {
//						student = (Student)user;
//						student_list.add(student);
//					}
//				}
//			}
//		}
		System.out.println("Course Instructor: ");
		String course_instructor = in.readLine();
		System.out.println("Section Number: (enter number only)");
		String section_number = in.readLine();
		System.out.println("Course Location: ");
		String course_location = in.readLine();
		
		Course newCourse = new Course(course_name, course_id, Integer.parseInt(max_student), Integer.parseInt(current_student), student_list, course_instructor, Integer.parseInt(section_number), course_location);
		Course.getCourseList().add(newCourse);
//		if(!noStudent) {
//			for(int i =0; i<student_list.size();i++) {
//				Student studentInList = student_list.get(i);
//				studentInList.getEnrolledList().add(newCourse);
//			}
//		}
		
		System.out.println("Course creation successful. Returning to the main menu...\n");
		 
	}
	public void deleteCourse() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter the course name: ");
		String course_name = in.readLine();
		System.out.println("Please enter the course section number: ");
		String section_number = in.readLine();
		Course selectedCourse = null;
		try {
		for(Course course : Course.getCourseList()) {
			if(course.getCourse_name().equals(course_name)&&course.getSection_number()==Integer.parseInt(section_number)) {
				selectedCourse = course;
				Course.getCourseList().remove(course);
			}
		}
		//remove from all students' enrolled classes arraylist
		
		//changing Student while looping through it will cause exception
			for(int i = 0; i<User.getUser().size();i++) {
				User user = User.getUser().get(i);
				if(user instanceof Student) {
					Student student = (Student)user;
					
						ArrayList<Course> temp = student.getEnrolledList();
						temp.remove(selectedCourse);
					
				}
			}//for
		}catch(ConcurrentModificationException cme) {
			//do nothing
		}
		System.out.println("Deletion successful.");
	}
	public void editCourse() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Course chosenCourse = null;
		System.out.println("Please enter following information to identify the intended course for editing:");
		System.out.println("Course Name: ");
		String course_name = in.readLine();
		System.out.println("Course ID: ");
		String course_id = in.readLine();
		for(int i = 0;i<Course.getCourseList().size();i++) {
			Course course = Course.getCourseList().get(i);
			if(course.getCourse_name().equals(course_name)&&course.getCourse_id().equals(course_id))
				chosenCourse = course;
		}
		
		
		System.out.println("Maximum Student: (Number only)"+"(Currently: "+ chosenCourse.getMax_student()+")");
		String max_student = in.readLine();
		chosenCourse.setMax_student(Integer.parseInt(max_student));
		System.out.println("Current Student: (Number only)"+"(Currently: "+ chosenCourse.getCurrent_student()+")");
		String current_student = in.readLine();
		chosenCourse.setCurrent_student(Integer.parseInt(current_student));
		System.out.println("Course Instructor: "+"(Currently: "+ chosenCourse.getCourse_instructor()+")");
		String course_instructor = in.readLine();
		chosenCourse.setCourse_instructor(course_instructor);
		System.out.println("Section Number: (enter number only)"+"(Currently: "+ chosenCourse.getSection_number()+")");
		String section_number = in.readLine();
		chosenCourse.setSection_number(Integer.parseInt(section_number));
		System.out.println("Course Location: "+"(Currently: "+ chosenCourse.getCourse_location()+")");
		String course_location = in.readLine();
		chosenCourse.setCourse_location(course_location);
		
		System.out.println("Course editing successful.");
	}
	public void displayCourse() throws IOException {
		System.out.println("Please enter the course ID:");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String course_id = in.readLine();
		boolean courseFound = false;
		for(Course course : Course.getCourseList()) {
			if(course.getCourse_id().equals(course_id)) {
				System.out.printf("%-50s|%-30s|%-20s|%-30s|%-50s\n", "Course Name","Course ID","Section Number","Instructor","Location");
				System.out.printf("%-50s|%-30s|%-20s|%-30s|%-50s\n", course.getCourse_name(),course.getCourse_id(),course.getSection_number(),course.getCourse_instructor(),course.getCourse_location());
				courseFound = true;
			}
		}
		if(!courseFound) {
			System.out.println("No such course found.");
		}
		 
	}
	public void registerStudent() throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter the following information of the student: \n"
							+ "*Student will need to set their own username and password.");
		System.out.println("First name: ");
		String first_name = in.readLine();
		System.out.println("Last name: ");
		String last_name = in.readLine();
		System.out.println("ID: ");
		String ID = in.readLine();
		User newStudent = new Student(first_name,last_name,String.valueOf(Math.random()*300),String.valueOf(Math.random()*300),ID);
		User.getUser().add(newStudent);
		 
	}
	
	public void viewAllCourse() throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("All course listings: ");
		System.out.printf("%-50s|%-20s|%30s\n", "Course Name","Class Limit", "Registered # of Student");
		for(Course course : Course.getCourseList()) {
			System.out.printf("%-50s|%-20d|%30d\n", course.getCourse_name(),course.getMax_student(),course.getCurrent_student());
		}
		boolean repeat_checker = false;
		
		do {
			System.out.println("Would you like see the details of students in each course? (Y/N)");
			String ans = in.readLine();
			switch(ans) {
			case "y":
			case "Y":
				this.courseStudentDetail();
				repeat_checker = true;
				break;
			case "n":
			case "N":
				UI.adminReportOptions((Admin)UI.getCurrentUser());
				repeat_checker = true;
				break;
				default:
					System.out.println("Invalid input. Please try again.");
			}
		}while(!repeat_checker);
		
		 
	}
	public void courseStudentDetail() {
		for(Course course: Course.getCourseList()) {
			System.out.println("Course name: "+course.getCourse_name());
			System.out.printf("%-30s|%-30s|%-10s\n","First Name","Last Name", "ID");
			for(Student student : course.getStudent_list()) {
				System.out.printf("%-30s|%-30s|%-10s\n", student.getFirst_name(),student.getLast_name(),student.getID());
			}
		}
	}
	
	public void viewFullCourse() throws IOException {
		ArrayList<String> full_course_names = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Courses that are full: ");
		for(Course course : Course.getCourseList()) {
			if(course.getMax_student()==course.getCurrent_student()) {
				System.out.println(course.getCourse_name());
				try {
					full_course_names.add(course.getCourse_name());
				}catch (NullPointerException npe){
					System.out.println("NULL");
				}
				
			}
		}
		boolean repeat_checker = false;
		
		do {
			System.out.println("Would you like to export the list? (Y/N)");
			String ans = in.readLine();
			switch(ans) {
			case "y":
			case "Y":
				this.exportFullCourse(full_course_names);
				repeat_checker = true;
				break;
			case "n":
			case "N":
				UI.adminReportOptions((Admin)UI.getCurrentUser());;
				repeat_checker = true;
				break;
				default:
					System.out.println("Invalid input. Please try again.");
			}
		}while(!repeat_checker);
		 		
	}
	public void exportFullCourse(ArrayList<String> full_course_names) throws IOException{
		String csv = "FullCourseList.csv";
		CSVWriter writer = new CSVWriter(new FileWriter(csv));
		String[] header = {"List of Full Course"};
		writer.writeNext(header);
		for(String courseName : full_course_names) {
			String[] courseNameInArray = new String[1];
			courseNameInArray[0] = courseName;
			writer.writeNext(courseNameInArray);
		}
		
		System.out.println("Export complete.");
		writer.close();
		
	}
	public void viewStudentOfCourse() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter:\n"
							+ "Course name:");
		String course_name = in.readLine();
		System.out.println("Section number: ");
		String section_number = in.readLine();
		for(Course course : Course.getCourseList()) {
			if(course.getCourse_name().equals(course_name)&&course.getSection_number()==Integer.parseInt(section_number)) {
				System.out.println("These students are enrolled in "+ course_name + ": "); 
				for(Student student : course.getStudent_list()) {
					System.out.println(student.getFirst_name()+" "+student.getLast_name());
				}
			}
		}
		System.out.println();
	}
	public void viewCourseOfStudent() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter the following information of this student:\n"
							+ "First name: ");
		String first_name = in.readLine();
		System.out.println("Last name: ");
		String last_name = in.readLine();
		for(User user : super.getUser()) {
			if(user instanceof Student) {
				Student student = (Student) user;
				if(student.getFirst_name().equals(first_name)&&student.getLast_name().equals(last_name)) {
					System.out.printf("%-50s|%-20s\n", "Course Name","Section Number");
					for(Course course : student.getEnrolledList()) {
						System.out.printf("%-50s|%-20s\n",course.getCourse_name(),course.getSection_number());
					}
					
				}
			}
		}
		 
	}
	
	
	public void sortByNumOfStudent(int i) {
//		int[] sortBy = new int[sortedCourse.length];
		
		if(i==0) {//execute once per sort
//			int index=0;
			for(Course course: Course.getCourseList()) {
//				String[] courseInfo = {course.getCourse_name(),Integer.toString(course.getMax_student()),Integer.toString(course.getCurrent_student())};
//				this.sortedCourse[index]= courseInfo;
//				sortBy[index] = course.getCurrent_student();
//				index++;
				sortedCourse.add(course);
			}
			
		}
		//Course[] courseArray = (Course[]) sortedCourse.toArray();
		Course[] courseArray = new Course[sortedCourse.size()];
		for(int p = 0;p<sortedCourse.size();p++) {
			courseArray[p] = sortedCourse.get(p);
		}
		
		Course.sorting(courseArray);
		displaySortedCourse(courseArray);
		
		
//		if(i==this.sortedCourse.size()-1) {
//			return;
//		}
//		else { 
//			int size = Integer.valueOf(sortedCourse.size());
//			for(int a = 0; a<size-1;a++) {
//				for(int b = 1;b<size;b++) {
//					Course a_course = sortedCourse.get(a);
//					Course b_course = sortedCourse.get(b);
//					if(a_course.getCurrent_student()>b_course.getCurrent_student()) {
//						sortedCourse.add(b+1, a_course);
//						sortedCourse.remove(a_course);//only removes the first occurrence, by ascending order always the smaller one
//						
//						//sortedCourse.add(b+1,sortedCourse.get(a+1));
//						//sortedCourse.remove(a);
//					}
//				}
//			
//			}
//		}
		
	}
	public void displaySortedCourse(Course[] sortedCourse) {
		System.out.printf("%-50s|%-20s|%30s\n", "Course Name","Class Limit", "Registered # of Student");
		for(Course course : sortedCourse) {
			System.out.printf("%-50s|%-20d|%30d\n",course.getCourse_name(),course.getMax_student(),course.getCurrent_student());
		}
//		for(Course course: sortedCourse) {
//			System.out.printf("%-50s|%-20d|%30d\n", course.getCourse_name(),course.getMax_student(),course.getCurrent_student());
//		}
	}
	
	public void backToMenu() throws IOException{
		Admin admin = (Admin)UI.getCurrentUser();
		UI.mainOptions(admin);
	}
	
	public static String getOptions() {
		return options;
	}
	public static String getManagementOptions() {
		return management_options;
	}
	public static String getReportOptions() {
		return report_options;
	}
					
	
}
