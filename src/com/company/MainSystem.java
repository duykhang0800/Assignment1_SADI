package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainSystem implements EnrolmentManager {

    static Scanner input = new Scanner(System.in);
    static String[] semester = {"2022A", "2022B", "2022C", "2023A", "2023B", "2023C"};
    static ArrayList semesterList = new ArrayList(Arrays.asList(semester));

    static ArrayList<Student> studentLists = new ArrayList<Student>();
    static ArrayList<Course> courseList = new ArrayList<Course>();
    static ArrayList<StudentEnrolment> enrolmentList = new ArrayList<>();

    //This function will print out all available options for users to choose from
    public static void menu() {
        System.out.println("\n Which operation do you want to perform?");
        System.out.println("\n Choose one of the following: \n");
        System.out.println("1. Enrol in courses \n");
        System.out.println("2. Display one specific record \n");
        System.out.println("3. Display all records \n");
        System.out.println("4. Update or Delete one record \n");
        System.out.println("5. Export all records \n");
        System.out.println("6. Print all records \n");
        System.out.println("7. Exit the application");
    }

    //Called when we need to get a user input, only integers will be accepted
    public static int getUsersOption() {
        int option = -1;
        Scanner optionIn = new Scanner(System.in);
        System.out.println("Enter your choice: ");
        try {
            option = optionIn.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Your input does not match required value");
        }
        return option;
    }

    //Read all records from the csv file
    public static String readFile() {
        boolean eof = false;
        String fileName = "";

        while (!eof) {
            //Trying to get the file's name
            String defaultFile = "src/com/company/default.csv";

            System.out.println("Enter the name of file you want to read from");
            System.out.println("Leave a blank line and hit enter to read the default file");

//            try {
//                fileName = input.nextLine();
//            } catch (InputMismatchException e) {
//                System.out.println("You entered the wrong format!");
//            }

//            if(!fileName.equals("")) {
//                defaultFile = fileName;
//            }

            //Reading of the file starts here, split string when encounter a ","
            String line;
            String splitAnnotation = ",";

            try {
                fileName = input.nextLine();
                if(!fileName.equals("")) {
                    defaultFile = fileName;
                }
                BufferedReader readfile = new BufferedReader(new FileReader(defaultFile));
                while ((line = readfile.readLine()) != null) {
                    boolean studentAdded = false;
                    boolean courseAdded = false;

                    String[] data = line.split(splitAnnotation);

                    //Check if a student or a course is added in the file to prevent duplicates
                    for(Student student: studentLists) {
                        if(student.getSid().equals(data[0])) {
                            studentAdded = true;
                            break;
                        }
                    }

                    for(Course course: courseList) {
                        if(course.getCid().equals(data[3])) {
                            courseAdded = true;
                            break;
                        }
                    }

                    if(!studentAdded) {
                        studentLists.add(new Student(data[0], data[1], data[2]));
                    }

                    if(!courseAdded) {
                        courseList.add(new Course(data[3], data[4], data[5]));
                    }

                    enrolmentList.add(new StudentEnrolment(new Student(data[0], data[1], data[2]), new Course(data[3], data[4], data[5]), data[6]));
                }
                System.out.println("Successfully retrieved information");
                eof = true;
            } catch (IOException e) {
//                e.printStackTrace();
                System.out.println("An error occured. Entered file name does not exist");
                eof = false;
            }
        }
        return fileName;
    }

    public static void menuOperations() {
        String defaultLocation = readFile();
        int inputOpt;

        do {
            menu();
            inputOpt = getUsersOption();

            switch (inputOpt) {
                case 1:
                    add();
                    break;
                case 2:
                    getOne();
                    break;
                case 3:
                    getAllRecords();
                    break;
                case 4:
                    update();
                    break;
                case 5:
                    saveFile(defaultLocation);
                    break;
                case 6:
                    printRecords();
                    break;
                case 7:
                    System.out.println("The system will now close");
                    return;
                default:
                    System.out.println("Choice not found");
                    break;
            }
        }
        while (inputOpt != 0);
    }

    //This method here will allow users to choose student's name, preferred course and semester
    //then add them into the list of all enrolments.
    //Give a warning if duplicated
    public static void add() {
        Student firstExpr = null;
        Course secondExpr = null;

        //Starting to print out all students' names and IDs for user to choose from
        System.out.println("List of available students:");
        for(Student student : studentLists) {
            System.out.println(student.getSid() + " " + student.getSname());
        }

        System.out.print("Enter the student's ID: ");
        String studentID = input.nextLine();
        System.out.println();

        for(Student student : studentLists) {
            if(student.getSid().equalsIgnoreCase(studentID)) {
                firstExpr = student;
            }
        }

        if(firstExpr == null) {
            System.out.println("No student is found with this ID");
            return;
        }

        //Starting to print out all courses' names and IDs
        System.out.println("List of available courses:");
        for(Course course : courseList) {
            System.out.println(course.getCid() + ", " + course.getCname() + ", " + course.getCredit());
        }

        System.out.print("Enter the course's ID: ");
        String courseID = input.nextLine();
        System.out.println();

        for(Course course : courseList) {
            if(course.getCid().equalsIgnoreCase(courseID)) {
                secondExpr = course;
            }
        }

        if(secondExpr == null) {
            System.out.println("Not any course is found with this ID");
            return;
        }

        //Starting to print out all semesters available within a 2-year period
        System.out.println("List of available semesters: ");
        for (Object semester : semesterList) {
            System.out.println(semester);
        }

        System.out.println("Enter semester: ");
        String semester = input.nextLine().toUpperCase();
        System.out.println();

        if(!semesterList.contains(semester)) {
            System.out.println("Semester is not available");
            return;
        }

        StudentEnrolment newRecord = new StudentEnrolment(firstExpr, secondExpr, semester);
        if(enrolmentList.contains(newRecord)) {
            System.out.println("Enrolment already existed, please perform a different action");
        } else {
            enrolmentList.add(newRecord);
            System.out.println("Successfully added");
            System.out.println(newRecord.toString());
        }

    }

    public static void getOne() {}

    public static void getAllRecords() {}

    public static void update() {}

    public static void saveFile(String defaultLocation) {}

    public static void printRecords() {}

    public static void main(String[] args) {
	// write your code here

        menuOperations();
    }
}
