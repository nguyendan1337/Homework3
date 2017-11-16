package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;
import core.api.impl.Student;

import static org.junit.Assert.*;

public class TestAdmin{
	private IAdmin admin;
	
	@Before
    public void setup() {
        this.admin = new Admin();
    }
	
	@Test
	public void invalidCapacity() {
		this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
	}
	
	@Test
	public void zeroCapacity() {
		this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
	}
	
	@Test
    public void testMakeClassValid() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClassPastYear() {
        this.admin.createClass("Test", 2015, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2015));
    }

    
	@Test
	public void validCapacityChange() {
		this.admin.createClass("Test", 2017, "Instructor", 5);
		Student s1 = new Student();
		s1.registerForClass("student1", "Test", 2017);
		Student s2 = new Student();
		s2.registerForClass("student2", "Test", 2017);
		Student s3 = new Student();
		s3.registerForClass("student3", "Test", 2017);
		this.admin.changeCapacity("Test", 2017, 4);
		assertTrue(this.admin.getClassCapacity("Test", 2017) >= 3);
	}
	
	@Test
	public void CapacityChangeToNumberRegistered() {
		this.admin.createClass("Test", 2017, "Instructor", 5);
		Student s1 = new Student();
		s1.registerForClass("student1", "Test", 2017);
		Student s2 = new Student();
		s2.registerForClass("student2", "Test", 2017);
		Student s3 = new Student();
		s3.registerForClass("student3", "Test", 2017);
		this.admin.changeCapacity("Test", 2017, 3);
		assertTrue(this.admin.getClassCapacity("Test", 2017) == 3);
	}
	
	@Test
	public void invalidCapacityChange() {
		this.admin.createClass("Test", 2017, "Instructor", 5);
		Student s1 = new Student();
		s1.registerForClass("student1", "Test", 2017);
		Student s2 = new Student();
		s2.registerForClass("student2", "Test", 2017);
		Student s3 = new Student();
		s3.registerForClass("student3", "Test", 2017);
		this.admin.changeCapacity("Test", 2017, 1);
		assertFalse(this.admin.getClassCapacity("Test", 2017) >= 3);
	}
	
	@Test
	public void instructorCourseLimitCornerCase() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.createClass("Test2", 2017, "Instructor", 15);
		assertTrue(this.admin.classExists("Test2", 2017));
	}
	
	@Test
	public void differentYearSameClass() {
		this.admin.createClass("Test", 2016, "Instructor", 15);
		this.admin.createClass("Test", 2017, "Instructor", 15);
		assertTrue(this.admin.classExists("Test", 2017));
	}
	
	@Test
	public void instructorCourseLimitViolated() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.createClass("Test2", 2017, "Instructor", 15);
		this.admin.createClass("Test3", 2017, "Instructor", 15);
		assertFalse(this.admin.classExists("Test3", 2017));
	}
	
	@Test
	public void twoTeachersTeachSameClass() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.createClass("Test", 2017, "Teacher", 20);
		System.out.println(this.admin.getClassCapacity("Test", 2017));
		assertFalse(this.admin.classExists("Test", 2017));
	}
	
	@Test
	public void checkUniqueTeacher() {
		this.admin.createClass("Test", 2016, "Instructor", 15);
		this.admin.createClass("Test", 2017, "Teacher", 15);
		assertTrue(this.admin.classExists("Test", 2017));
	}
	
	@Test
	public void notUnique() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.createClass("Test", 2017, "Instructor", 20);
		System.out.print(this.admin.getClassCapacity("Test", 2017));
		assertNotEquals(20, this.admin.getClassCapacity("Test", 2017));
	}
	
}
