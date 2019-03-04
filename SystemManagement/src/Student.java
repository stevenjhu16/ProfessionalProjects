import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Student extends User implements student_standard {
	private String first_name, last_name, username, password, ID;
	private boolean initialized = false;
	private ArrayList<Course> enrolledList = new ArrayList<Course>();
	/**
	 * Course.getCourseList() a reference to Course.courseList;
	 */
	private ArrayList<Course> courseList = Course.getCourseList();
	
	
	public Student(String first_name, String last_name, String username, String password, String ID) {
		super(first_name, last_name, username, password,false);
		super.setFirst_name(first_name);
		super.setLast_name(last_name);
		super.setUsername(username);
		super.setPassword(password);
		super.setUser_type(false);
		this.ID = ID;
	}
	
	//store the options for console display
	private static final String options =
								"1. View all courses\n"
							+	"2. Register in a course\n"
							+	"3. Withdraw from a course\n"
							+	"4. View all courses registered\n"
							+	"5. View all courses that are not full\n"
							+	"6. Logout";
	
	public void viewAllCourse() throws IOException{
		System.out.println("Following courses are provided:");
		for(Course course : Course.getCourseList()) {
			System.out.println(course.getCourse_name());
		}
	}
	public void viewAvailableCourse() throws IOException {
		System.out.println("Following courses are available for register:");
		for(Course course : Course.getCourseList()) {
			if(course.getMax_student()>course.getCurrent_student()) {
				System.out.println(course.getCourse_name());
			}
		}
		UI.mainOptions((Student)UI.getCurrentUser());
	}
	public void registerCourse() throws IOException {
		//boolean courseRegistration_status= false;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Which course would you like to register?");
		String answer = in.readLine();
		
		ArrayList<Course> sectionList = new ArrayList<Course>(); //store the desired course object
		int[] sectionIndex = new int[10]; //store the index of the desired courses within the Course.courseList, so can be used out of the for loop below
		int storeIndex = 0;
		
		
		System.out.println("Following is/are the class(es) that are available: ");
		System.out.printf("%-50s|%-30s|%-20s|%-30s|%-50s\n", "Course Name","Course ID","Section Number","Instructor","Location");
		for(int i = 0; i< courseList.size();i++) {
			Course thisCourse = courseList.get(i);
			boolean courseNotFull = thisCourse.getMax_student()>thisCourse.getCurrent_student();
			if(thisCourse.getCourse_name().equals(answer)&&courseNotFull&&!checkIfRegistered(this,thisCourse)&&(thisCourse.getCurrent_student()+1<=thisCourse.getMax_student()) ) {
				sectionIndex[storeIndex] = i;
				storeIndex++;
				sectionList.add(thisCourse);
				System.out.printf("%-50s|%-30s|%-20s|%-30s|%-50s\n", thisCourse.getCourse_name(),thisCourse.getCourse_id(),thisCourse.getSection_number(),thisCourse.getCourse_instructor(),thisCourse.getCourse_location());
			}
		}
		if(sectionList.size()==0) {
			System.out.println("No class fits the criteria.");
		}else if(sectionList.size()==1) {
			boolean check = false;
			do {
				System.out.println("\nWould you like to enroll in this class? (Y/N)");
				String ans = in.readLine();
				Course thisCourse = courseList.get(sectionIndex[0]);
				switch (ans) {
				case "y":
				case "Y":
					ArrayList<Student> temp = thisCourse.getStudent_list();
					temp.add((Student)UI.getCurrentUser()); //since Admin is never given the option to register in a course, the casting is safe
					//thisCourse.setStudent_list(temp);
					thisCourse.setCurrent_student(thisCourse.getCurrent_student()+1);
					Course.setCourseList(courseList);
					this.enrolledList.add(thisCourse);
					System.out.println(thisCourse.getCourse_name() + " registration successful.");
					check = true;
					break;
				case "n":
				case "N":
					check = true;
					break;
					default:
						System.out.println("Invalid input. Please try again.");
				}
			}while(!check);
			
		}else {// when there are multiple sections
			boolean regSuccess =false;
			do {
				System.out.println("Which section would you like to be enrolled in? (Enter section number only)");
				String ans = in.readLine();
				for(int i =0; i< sectionList.size();i++) {
					Course course = courseList.get(sectionIndex[i]);
					if(Integer.parseInt(ans)==course.getSection_number()) {
						ArrayList<Student> studentList = course.getStudent_list();
						studentList.add((Student)UI.getCurrentUser());
						//course.setStudent_list(studentList);
						course.setCurrent_student(course.getCurrent_student()+1);
						this.enrolledList.add(course);
						regSuccess = true;
						System.out.println(course.getCourse_name() + " registration successful.");
					}
				}
				if(!regSuccess){
					System.out.println("Invalid input. Please try again.");
				}
			}while(!regSuccess);
			
		}
		
		 
	}
	public void withdrawCourse() throws IOException {
		boolean wthdSuccess = false;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		viewEnrolledCourse();
		try {
		do {
			System.out.println("Which class would you like to withdraw?");
			String ans = in.readLine();
			for(Course enr_course : this.enrolledList) {
				if(ans.equals(enr_course.getCourse_name())) {
					Course thisCourse = this.courseList.get(checkCourseIndex(enr_course));
					ArrayList<Student>studentList = thisCourse.getStudent_list();
					studentList.remove((Student)UI.getCurrentUser());
					thisCourse.setCurrent_student(thisCourse.getCurrent_student()-1);
					
					this.enrolledList.remove(enr_course);
					System.out.println(thisCourse.getCourse_name()+" withdrawn.");
					Course.setCourseList(courseList);
					wthdSuccess = true;
				}
			}
			if(!wthdSuccess) {
				System.out.println("Invalid input. Please try again.");
			}
		}while(!wthdSuccess);
		}catch(ConcurrentModificationException cme) {
			
		}
		
	}
	public int checkCourseIndex(Course course) {
		int index = 0;
		for(int i = 0; i<Course.getCourseList().size();i++) {
			Course sample = courseList.get(i);
			if(sample.getCourse_name().equals(course.getCourse_name())&&sample.getSection_number()==course.getSection_number()) {
				index = i;
			}
		}
		return index;
	}
	public void viewEnrolledCourse() {
		System.out.println("This is/are the class(es) that you are currently enrolled in:");
		for (Course thisCourse : this.enrolledList) {
			System.out.printf("%-50s|%-30s|%-20s|%-30s|%-50s\n", "Course Name","Course ID","Section Number","Instructor","Location");
			System.out.printf("%-50s|%-30s|%-20s|%-30s|%-50s\n", thisCourse.getCourse_name(),thisCourse.getCourse_id(),thisCourse.getSection_number(),thisCourse.getCourse_instructor(),thisCourse.getCourse_location());
		}
	}
	public boolean checkIfRegistered(Student student, Course course){
		boolean registered = false;
		for(Course enrolled : student.enrolledList) {
			
				if(enrolled.getCourse_name().equals(course.getCourse_name())) {
					registered = true;
				}
			
		}
		return registered;
	}
	
	@Override
	public String getFirst_name() {
		return first_name;
	}
	@Override
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	@Override
	public String getLast_name() {
		return last_name;
	}
	@Override
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public void setPassword(String password) {
		if(!password.equals("NOT ME")) {
		this.password = password;
		}
		else throw new IllegalArgumentException("This password cannot be used. Please change a password.");
	}
	public ArrayList<Course> getEnrolledList() {
		return enrolledList;
	}
	public void setEnrolledList(ArrayList<Course> enrolledList) {
		this.enrolledList = enrolledList;
	}
	public static String getOptions() {
		return options;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public boolean isInitialized() {
		return initialized;
	}
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
}//class
