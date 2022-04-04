package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {
//    private static Student student;
//    private static Course course;
    private static MainSystem enrolmentList;

    @BeforeEach
    public void setUpMain() {
        enrolmentList = new MainSystem();
    }

    @Test
    public void testWhenAddNew() {
        Student student = new Student("s3753740", "Tran Duy Khang", "31/08/2000");
        Course course= new Course("c0001", "Further Programming", "24");
        String semester = "2022A";
        enrolmentList.equals(new StudentEnrolment(student, course, semester));
        assertEquals("s3753740", student.getSid());
        assertEquals("c0001", course.getCid());
    }

    @Test
    public void getAllTest() {
        Student student1 = new Student("s0001", "Tran Duy Khang", "31/08/2000");
        Student student2 = new Student("s0002", "Gabriel Belmont", "03/03/1999");

        Course course1 = new Course("c001", "SADI", "12");
        Course course2 = new Course("c002", "Further Programming", "24");

        String semester1 = "2022A";
        String semester2 = "2022B";

        ArrayList<StudentEnrolment> newList = new ArrayList<>();

        int i = 0;
        String testSId = "";
        String testCId = "";

        newList.add(new StudentEnrolment(student1, course1, semester1));
        newList.add(new StudentEnrolment(student2, course2,semester2));

        for (StudentEnrolment oneRecord : newList) {
            if (i == 0) {
                testSId = "s0001";
                testCId = "c001";
            }

            if (i == 1) {
                testSId = "s0002";
                testCId = "c002";
            }

            assertEquals(testSId, oneRecord.getStudent().getSid());
            assertEquals(testCId, oneRecord.getCourse().getCid());

            i++;
        }
    }

    @Test
    public void testWhenUpdate() {
        Student student = new Student("s3753740", "Tran Duy Khang", "31/08/2000");

        Course course= new Course("c0001", "Further Programming", "24");
        Course course2 = new Course("c002", "Further Programming", "24");

        String semester = "2022A";
        String semester2 = "2022B";

        StudentEnrolment newEnrolment = new StudentEnrolment(student, course, semester);

        assertEquals("s3753740", newEnrolment.getStudent().getSid());
        assertEquals("c0001", newEnrolment.getCourse().getCid());
        assertEquals(semester, newEnrolment.getSemester());

        newEnrolment.setCourse(course2);
        assertEquals("c002", newEnrolment.getCourse().getCid());

        newEnrolment.setSemester(semester2);
        assertEquals(semester2, newEnrolment.getSemester());
    }
}
