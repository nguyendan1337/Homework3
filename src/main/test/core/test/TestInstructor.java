package core.test;

import core.api.impl.Admin;
import core.api.IInstructor;
import core.api.impl.Instructor;
import org.junit.Before;
import org.junit.Test;
import core.api.impl.Student;

import static org.junit.Assert.*;

public class TestInstructor {
	private IInstructor instructor;
	
	@Before
	public void setup() {
		this.instructor = new Instructor();
	}
	
	@Test
	public void validHomeworkAdd() {
		Admin a = new Admin();
		a.createClass("class", 2017, "teacher", 15);
		this.instructor.addHomework("teacher", "class", 2017, "hw1");
		assertTrue(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	@Test
	public void diffHWNameHomeworkAdd() {
		Admin a = new Admin();
		a.createClass("class", 2017, "teacher", 15);
		this.instructor.addHomework("teacher", "class", 2017, "hw1");
		assertFalse(this.instructor.homeworkExists("class", 2017, "hw2"));
	}
	
	@Test
	public void diffYearHomeworkAdd() {
		Admin a = new Admin();
		a.createClass("class", 2017, "teacher", 15);
		this.instructor.addHomework("teacher", "class", 2016, "hw1");
		assertFalse(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	@Test
	public void noClassHomeworkAdd() {
		this.instructor.addHomework("imposter", "class", 2017, "hw1");
		assertFalse(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	@Test
	public void noTeacherHomeworkAdd() {
		Admin a = new Admin();
		a.createClass("class", 2017, "teacher", 15);
		this.instructor.addHomework("imposter", "class", 2017, "hw1");
		assertFalse(this.instructor.homeworkExists("class", 2017, "hw1"));
	}
	
	@Test
	public void validGradeAssign() {
		Admin a = new Admin();
		Student s = new Student();
		a.createClass("class", 2017, "teacher", 15);
		this.instructor.addHomework("teacher", "class", 2017, "hw1");
		s.submitHomework("student1", "hw1", "hello world", "class", 2017);
		this.instructor.assignGrade("teacher", "class", 2017, "hw1", "student1", 4);
		assertNotNull(this.instructor.getGrade("class", 2017, "hw1", "student1"));
	}
	
	@Test
	public void wrongTeacherGradeAssign() {
		Admin a = new Admin();
		Student s = new Student();
		a.createClass("class", 2017, "teacher", 15);
		this.instructor.addHomework("imposter", "class", 2017, "hw1");
		s.submitHomework("student1", "hw1", "hello world", "class", 2017);
		this.instructor.assignGrade("teacher", "class", 2017, "hw1", "student1", 4);
		assertNull(this.instructor.getGrade("class", 2017, "hw1", "student1"));
	}
	
	@Test
	public void homeworkUnassignedGradeAssign() {
		Admin a = new Admin();
		Student s = new Student();
		a.createClass("class", 2017, "teacher", 15);
		s.submitHomework("student1", "hw1", "hello world", "class", 2017);
		this.instructor.assignGrade("teacher", "class", 2017, "hw1", "student1", 4);
		assertNull(this.instructor.getGrade("class", 2017, "hw1", "student1"));
	}
	
	@Test
	public void noSubmissionGradeAssign() {
		Admin a = new Admin();
		a.createClass("class", 2017, "teacher", 15);
		this.instructor.addHomework("teacher", "class", 2017, "hw1");
		this.instructor.assignGrade("teacher", "class", 2017, "hw1", "student1", 4);
		assertNull(this.instructor.getGrade("class", 2017, "hw1", "student1"));
	}
}
