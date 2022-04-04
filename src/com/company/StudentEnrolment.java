package com.company;

public class StudentEnrolment {
    Student student;
    Course course;
    String semester;

    public StudentEnrolment(Student student, Course course, String semester) {
        this.student = student;
        this.course = course;
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getSemester() {
        return semester.replaceAll("\\s+","");
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return student.getSid().replaceAll("\\s+","") +
                ", " + student.getSname() +
                ", " + student.getBirthDate().replaceAll("\\s+","") +
                ", " + course.getCid().replaceAll("\\s+","") +
                ", " + course.getCname() +
                ", " + course.getCredit().replaceAll("\\s+","") +
                ", " + semester.replaceAll("\\s+","") + "\n" ;
    }
}
