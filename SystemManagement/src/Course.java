import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Course implements Serializable,Comparable<Course>  {
	private String course_name, course_id, course_instructor, course_location; // store course name, instructor name, and course location
	private int max_student, current_student, section_number; // store maximum number of students, current number of students, and section number of a course
	private ArrayList<Student> student_list = new ArrayList <Student>(); // store the list of student enrolled
	private static String[][]firstTimeCourseList;
	private static ArrayList <Course> courseList = new ArrayList <Course>(); //store all courses info
	private static int numOfRows = 0; //indicate how many lines in the file

	//To handle csv FileNotFound scenario
	public Course(String course_name, String course_id, int max_student, int current_student, ArrayList<Student> student_list, String course_instructor, int section_number, String course_location) throws FileNotFoundException {
		this.course_name=course_name;
		this.course_id=course_id;
		this.max_student=max_student;
		this.current_student=current_student;
		this.student_list=student_list;
		this.course_instructor=course_instructor;
		this.section_number=section_number;
		this.course_location=course_location;
	}
	
	/**
	 * Load the object from serialized file, or read from the .csv file if it is the first time running the program
	 * @throws ClassNotFoundException
	 */
	public static void loadCourse() throws ClassNotFoundException{
		try {
			FileInputStream fis = new FileInputStream("CourseList.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			courseList=(ArrayList<Course>)ois.readObject();
			fis.close();
			ois.close();
		}
		catch(FileNotFoundException fnfe) {
			try {
				Course.firstTimeCourseList = Course.loadCSV("MyUniversityCourses.csv");
				for(String[] row: firstTimeCourseList) {
						String course_name=row[0], course_id=row[1], course_instructor=row[5], course_location=row[7];
						int max_student=Integer.parseInt(row[2]), current_student=Integer.parseInt(row[3]), section_number=Integer.parseInt(row[6]);
						ArrayList<Student> student_list = new ArrayList <Student>();
						Course.courseList.add(new Course(course_name, course_id, max_student, current_student, student_list, course_instructor, section_number, course_location));
				}
				
			}
			catch (FileNotFoundException fnfe_csv){
				fnfe_csv.printStackTrace();
			}
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	
	/**
	 * Load .csv file into 2d-array
	 * @param fileName name of the file processed
	 * @return courseArray the 2d-array containing the info from the .csv file
	 * @throws FileNotFoundException in case the csv is not found
	 */
	public static String[][] loadCSV(String fileName) throws FileNotFoundException {
		Scanner file = new Scanner (new File(fileName));
		String wholeFile = "";
		
		//separate data into 2D-array
		while(file.hasNextLine()) {
			String line = file.nextLine();
			wholeFile += (line + "\n");
			numOfRows++;
		}//while
		
		String [] arrayForLine = wholeFile.split("\n");
		String [][] courseArray = new String[numOfRows][];
		
		for(int i = 0; i< arrayForLine.length; i++) {
			String[] dataCell = arrayForLine[i].split(",");
			courseArray[i] = dataCell;
		}
		
		
		file.close();
		return courseArray;
	}//loadCourse

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getCourse_instructor() {
		return course_instructor;
	}

	public void setCourse_instructor(String course_instructor) {
		this.course_instructor = course_instructor;
	}

	public String getCourse_location() {
		return course_location;
	}

	public void setCourse_location(String course_location) {
		this.course_location = course_location;
	}

	public int getMax_student() {
		return max_student;
	}

	public void setMax_student(int max_student) {
		this.max_student = max_student;
	}

	public int getCurrent_student() {
		return current_student;
	}

	public void setCurrent_student(int current_student) throws IOException {
			this.current_student = current_student;
	}

	public int getSection_number() {
		return section_number;
	}

	public void setSection_number(int section_number) {
		this.section_number = section_number;
	}

	public ArrayList<Student> getStudent_list() {
		return student_list;
	}

	public void setStudent_list(ArrayList<Student> student_list) {
		this.student_list = student_list;
	}

	public static ArrayList<Course> getCourseList() {
		return courseList;
	}

	public static void setCourseList(ArrayList<Course> courseList) {
		Course.courseList = courseList;
	}


	@Override
	public int compareTo(Course o) {
		// TODO Auto-generated method stub
		return new Integer(o.getCurrent_student()).compareTo(new Integer(o.getCurrent_student()));
	}
	public static void sorting(Course[] sortedCourse) {
		Arrays.sort(sortedCourse, new Comparator<Course>() {
	        @Override
	        public int compare(Course o1, Course o2) {
	            return new Integer(o1.getCurrent_student()).compareTo(new Integer(o2.getCurrent_student()));
	        }
	    });
	}

	
}//class
