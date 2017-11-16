package core.test;


import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.IStudent;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent {
	
	private IStudent student;
	
	@Before
	public void setup() {
		this.student = new Student();
	}
	
	@Test
	public void validRegistration() {
		Admin a = new Admin();
		a.createClass("class", 2017, "mr. teach", 3);
		this.student.registerForClass("billy", "class", 2017);
		assertTrue(this.student.isRegisteredFor("billy", "class", 2017));
	}
	
	@Test
	public void classDoesNotExistRegistration() {
		this.student.registerForClass("billy", "class", 2017);
		assertFalse(this.student.isRegisteredFor("billy", "class", 2017));
	}
	
	@Test
	public void overCapacityRegistration() {
		Admin a = new Admin();
		a.createClass("class", 2017, "mr. teach", 3);
		Student s1 = new Student();
		s1.registerForClass("kid1", "class", 2017);
		Student s2 = new Student();
		s2.registerForClass("kid2", "class", 2017);
		Student s3 = new Student();
		s3.registerForClass("kid3", "class", 2017);
		this.student.registerForClass("billy", "class", 2017);
		assertFalse(this.student.isRegisteredFor("billy", "class", 2017));
	}
	
	@Test
	public void atCapacityRegistration() {
		Admin a = new Admin();
		a.createClass("class", 2017, "mr. teach", 3);
		Student s1 = new Student();
		s1.registerForClass("kid1", "class", 2017);
		Student s2 = new Student();
		s2.registerForClass("kid2", "class", 2017);
		this.student.registerForClass("billy", "class", 2017);
		assertTrue(this.student.isRegisteredFor("billy", "class", 2017));
	}
	
	@Test
	public void validDrop() {
		Admin a = new Admin();
		a.createClass("class", 2017, "mr. teach", 3);
		this.student.registerForClass("billy", "class", 2017);
		this.student.dropClass("billy", "class", 2017);
		assertFalse(this.student.isRegisteredFor("billy", "class", 2017));
	}
	
	@Test
	public void studentNotRegisteredDrop() {
		Admin a = new Admin();
		a.createClass("class", 2017, "mr. teach", 3);
		this.student.dropClass("billy", "class", 2017);
		assertFalse(this.student.isRegisteredFor("billy", "class", 2017));
	}
	
	@Test
	public void classEndedDrop() {
		Admin a = new Admin();
		a.createClass("class", 2016, "mr. teach", 3);
		this.student.registerForClass("billy", "class", 2016);
		this.student.dropClass("billy", "class", 2016);
		assertTrue(this.student.isRegisteredFor("billy", "class", 2016));
	}
	
	@Test
	public void validSubmission() {
		Admin a = new Admin();
		Instructor i = new Instructor();
		a.createClass("class", 2017, "mr. teach", 3);
		this.student.registerForClass("billy", "class", 2017);
		i.addHomework("mr. teach", "class", 2017, "hw1");
		this.student.submitHomework("billy", "hw1", "hello world", "class", 2017);
		assertTrue(this.student.hasSubmitted("billy", "hw1", "class", 2017));
		
	}
	
	@Test
	public void badSubmissionStudentNotRegistered() {
		Admin a = new Admin();
		Instructor i = new Instructor();
		a.createClass("class", 2017, "mr. teach", 3);
		i.addHomework("mr. teach", "class", 2017, "hw1");
		this.student.submitHomework("billy", "hw1", "hello world", "class", 2017);
		assertFalse(this.student.hasSubmitted("billy", "hw1", "class", 2017));
		
	}
	
	@Test
	public void badSubmissionHWDoesntExist() {
		Admin a = new Admin();
		a.createClass("class", 2017, "mr. teach", 3);
		this.student.registerForClass("billy", "class", 2017);
		this.student.submitHomework("billy", "hw1", "hello world", "class", 2017);
		assertFalse(this.student.hasSubmitted("billy", "hw1", "class", 2017));
	}
	
	//fix
	@Test
	public void badSubmissionpPastYear() {
		Admin a = new Admin();
		Instructor i = new Instructor();
		a.createClass("class", 2016, "mr. teach", 3);
		this.student.registerForClass("billy", "class", 2016);
		i.addHomework("mr. teach", "class", 2016, "hw1");
		this.student.submitHomework("billy", "hw1", "hello world", "class", 2016);
		assertFalse(this.student.hasSubmitted("billy", "hw1", "class", 2016));
	}
	
	@Test
	public void badSubmissionpFutureYear() {
		Admin a = new Admin();
		Instructor i = new Instructor();
		a.createClass("class", 2019, "mr. teach", 3);
		this.student.registerForClass("billy", "class", 2019);
		i.addHomework("mr. teach", "class", 2019, "hw1");
		this.student.submitHomework("billy", "hw1", "hello world", "class", 2019);
		assertFalse(this.student.hasSubmitted("billy", "hw1", "class", 2019));
	}
}
