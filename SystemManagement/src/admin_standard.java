import java.io.IOException;
import java.util.ArrayList;

public interface admin_standard {
	public void createCourse() throws IOException;
	public void deleteCourse() throws IOException;
	public void editCourse() throws IOException;
	public void displayCourse() throws IOException;
	public void registerStudent() throws IOException;
	
	public void viewAllCourse() throws IOException;
	public void viewFullCourse() throws IOException;
	public void exportFullCourse(ArrayList<String> full_course_names) throws IOException;
	public void viewStudentOfCourse() throws IOException;
	public void viewCourseOfStudent() throws IOException;
	public void sortByNumOfStudent(int i);
	
	public void backToMenu() throws IOException;
}
