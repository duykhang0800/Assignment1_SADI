package com.company;

import java.io.*;
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
        System.out.println("4. Update one record \n");
        System.out.println("5. Delete one record \n");
        System.out.println("6. Export all records \n");
        System.out.println("7. Print all records \n");
        System.out.println("8. Exit the application");
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

                    enrolmentList.add(new StudentEnrolment(new Student(data[0], data[1], data[2]), new Course(data[3],
                            data[4], data[5]), data[6]));
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
                    delete();
                    break;
                case 6:
                    saveFile(defaultLocation);
                    break;
                case 7:
                    printRecords();
                    break;
                case 8:
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

    //This method will allow users to check information about one single specific enrolment
    public static void getOne() {
        Student firstExpr = null;
        Course secondExpr = null;

        //Starting to print out all students' names and IDs for user to choose from
        System.out.println("List of available students:");
        for(Student student : studentLists) {
            System.out.println(student.getSid() + " " + student.getSname());
        }

        System.out.print("Whose ID do you want to find?: ");
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

        System.out.print("Which course's ID is this student associated to?: ");
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
//        System.out.println("List of available semesters: ");
//        for (Object semester : semesterList) {
//            System.out.println(semester);
//        }
//
//        System.out.println("In which semester was this student enrolled?: ");
//        String semester = input.nextLine().toUpperCase();
//        System.out.println();
//
//        if(!semesterList.contains(semester)) {
//            System.out.println("Semester is not available");
//            return;
//        }

        for (StudentEnrolment studentEnrolment : enrolmentList) {
            if(studentEnrolment.getStudent().getSid().equalsIgnoreCase(studentID) &&
                    studentEnrolment.getCourse().getCid().equalsIgnoreCase(courseID)
//                    && studentEnrolment.getSemester().equalsIgnoreCase(semester)
            ) {
                System.out.println(studentEnrolment.toString());
                return;
            } else {
                System.out.println("There is no matching enrolment");
                return;
            }
        }
    }

    public static void getAllRecords() {
        if(enrolmentList.isEmpty()) {
            System.out.println("There are currently no records to print");
        } else {
            for(StudentEnrolment studentEnrolment : enrolmentList) {
                System.out.println(studentEnrolment.toString());
            }
        }
    }

    public static void update() {
        Student firstExpr = null;
        Course secondExpr = null;
        StudentEnrolment oneEnrolment = null;
        boolean hasChanged = false;

        //Show the users all current enrolment information
        getAllRecords();

        //Starting to print out all students' names and IDs for user to choose from
        System.out.println("List of available students:");
        for(Student student : studentLists) {
            System.out.println(student.getSid() + " " + student.getSname());
        }

        System.out.print("Whose ID do you want to find?: ");
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

        System.out.print("Which course's ID is this student associated to?: ");
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

        for (StudentEnrolment studentEnrolment : enrolmentList) {
            if(studentEnrolment.getStudent().getSid().equalsIgnoreCase(studentID) &&
                    studentEnrolment.getCourse().getCid().equalsIgnoreCase(courseID)
//                    && studentEnrolment.getSemester().equalsIgnoreCase(semester)
            ) {
                System.out.println(studentEnrolment.toString());
                oneEnrolment = studentEnrolment;
                break;
            }
        }

        if (oneEnrolment == null) {
            System.out.println("No matching enrolment is found");
            return;
        }

        Course newCourse;
        String newSemester;

        do {
            System.out.println("Starting to change enrolment information");
            System.out.println("________________________________________");

            newCourse = null;
            newSemester = null;

//            Course newCourse = null;
//            String newSemester = null;

            System.out.println("List of available courses:");
            for(Course course : courseList) {
                System.out.println(course.getCid() + ", " + course.getCname() + ", " + course.getCredit());
            }

            System.out.print("Which course's ID do you want to enrol?: ");
            courseID = input.nextLine();
            System.out.println();

            for(Course course : courseList) {
                if(course.getCid().equalsIgnoreCase(courseID)) {
                    newCourse = course;
                }
            }

            if(newCourse == null) {
                System.out.println("Not any course is found with this ID");
            }

            System.out.println("List of available semesters: ");
            for (Object semester : semesterList) {
                System.out.println(semester);
            }

            System.out.println("In which semester was this student enrolled?: ");
            String semester = input.nextLine().toUpperCase();
            System.out.println();

            if(!semesterList.contains(semester)) {
                System.out.println("Semester is not available");
            } else {
                newSemester = semester;
            }

            if (newCourse != null && newSemester != null) {
                hasChanged = true;
            }

        } while (!hasChanged);

//        if(newCourse != null && newSemester != null) {
//
//        } else {
//            System.out.println("Failed to update enrolment information");
//        }

        oneEnrolment.setCourse(newCourse);
        oneEnrolment.setSemester(newSemester);
        System.out.println(oneEnrolment.toString());
    }

    public static void delete() {
        StudentEnrolment oneEnrolment = null;
        boolean isFound = false;
        boolean foundStudent = false;
        boolean foundCourse = false;
        String studentID = null;
        String courseID = null;

        do {
            getAllRecords();
            System.out.println("Choose one record you want to delete from the list above: ");
            System.out.println("Enter student's ID: ");
            studentID = input.nextLine();
            System.out.println();

            System.out.println("Enter course's ID: ");
            courseID = input.nextLine();
            System.out.println();

            for(Student student : studentLists) {
                if(student.getSid().equalsIgnoreCase(studentID)) {
                    foundStudent = true;
                    break;
                }
            }

            for(Course course : courseList) {
                if(course.getCid().equalsIgnoreCase(courseID)) {
                    foundCourse = true;
                    break;
                }
            }

            if(foundCourse && foundStudent) {
                isFound = true;
            }
        } while (!isFound);



        for(StudentEnrolment studentEnrolment : enrolmentList) {
            if(studentEnrolment.getStudent().getSid().equalsIgnoreCase(studentID) &&
                studentEnrolment.getCourse().getCid().equalsIgnoreCase(courseID)
            ) {
                oneEnrolment = studentEnrolment;
            }
        }

        if(oneEnrolment != null) {
            enrolmentList.remove(oneEnrolment);
            System.out.println("Successfully deleted, please check the list again");
            getAllRecords();
        } else {
            System.out.println("No such enrolment is found, please start the operation again");
        }
    };

    public static void saveFile(String filename) {
        String defaultLocation = "src/com/company/default.csv";

        if(!filename.equals("")) {
            defaultLocation = filename;
        }

        try {
            FileWriter fw = new FileWriter(defaultLocation);
            for (StudentEnrolment studentEnrolment : enrolmentList) {
                fw.write(studentEnrolment.toString());
            }
            fw.close();
            System.out.println("Write to file successfully executed");
        } catch (IOException e) {
            System.out.println("There is no such file");
            e.printStackTrace();
        }
    }

    public static void printRecords() {
        Student student = null;
        Course course = null;
        String semester;
        String choice;
        int option;
        ArrayList<StudentEnrolment> listForPrinting = new ArrayList<>();

        System.out.println("Choose one from the following options:");
        System.out.println("______________________________________");
        System.out.println("1. Print all courses of 1 student in 1 semester");
        System.out.println("2. Print all students enrolled in 1 course ");
        System.out.println("3. Print all available courses in 1 semester");

        //Create a new Scanner to prevent previous inputs from interfering with the switch case
        Scanner userInput = new Scanner(System.in);

        option = input.nextInt();

        switch (option) {
            case 1: {
                System.out.println("List of available students:");
                for(Student oneStudent : studentLists) {
                    System.out.println(oneStudent.getSid() + " " + oneStudent.getSname());
                }

//                String studentID = "S103723";
//                System.out.println(studentID);
                System.out.print("Enter the student's ID: ");
                String studentID = userInput.nextLine();
//                System.out.println(studentID);
//                System.out.println();

                for(Student oneStudent : studentLists) {
                    if(oneStudent.getSid().equalsIgnoreCase(studentID)) {
                        student = oneStudent;
                    }
                }

                if(student == null) {
                    System.out.println("No student is found with this ID");
//                    return;
                }

                System.out.println("List of available semesters: ");
                for (Object oneSemester : semesterList) {
                    System.out.println(oneSemester);
                }

                System.out.println("Enter the semester:");
                semester = userInput.nextLine().toUpperCase();

                if(!semesterList.contains(semester)) {
                    System.out.println("Semester is not available");
                    return;
                }

                for (StudentEnrolment studentEnrolment : enrolmentList) {
                    if (studentEnrolment.getStudent().getSid().equalsIgnoreCase(studentID) &&
                    studentEnrolment.getSemester().equalsIgnoreCase(semester)) {
                        System.out.println(studentEnrolment.toString());
                        listForPrinting.add(studentEnrolment);
                    }
                }

                System.out.println("Do you want to print all the records?");
                System.out.println("[Y] Yes");
                System.out.println("[N] No");

                choice = userInput.nextLine();

                if (choice.equalsIgnoreCase("Y")) {
                    if (!listForPrinting.isEmpty()) {
                        String filepath = "src/com/company/print.csv";
                        try {
                            FileWriter fw = new FileWriter(filepath);
                            for (StudentEnrolment oneRecord : listForPrinting) {
                                fw.write(oneRecord.toString());
                            }
                            fw.close();
                            System.out.println("Records have successfully been printed");
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("No such file exist");
                        }
                    } else {
                        System.out.println("There is nothing to print");
                    }
                } else if(choice.equalsIgnoreCase("N")) {
                    System.out.println("The operation will now commence exit");
                }
                break;
            }
            case 2: {
                System.out.println("List of available courses:");
                for(Course oneCourse : courseList) {
                    System.out.println(oneCourse.getCid() + ", " + oneCourse.getCname() + ", " + oneCourse.getCredit());
                }

                System.out.println("Enter course's ID:");
                String courseID = userInput.nextLine();
                for (Course oneCourse : courseList) {
                    if (oneCourse.getCid().equalsIgnoreCase(courseID)) {
                        course = oneCourse;
                    }
                }

                if (course == null) {
                    System.out.println("No such course is available");
                    return;
                }

                System.out.println("List of available semesters: ");
                for (Object oneSemester : semesterList) {
                    System.out.println(oneSemester);
                }
                System.out.println("Enter the semester:");
                semester = userInput.nextLine().toUpperCase();

                if(!semesterList.contains(semester)) {
                    System.out.println("Semester is not available");
                    return;
                }

                for (StudentEnrolment studentEnrolment : enrolmentList) {
                    if(studentEnrolment.getCourse().getCid().equalsIgnoreCase(courseID) &&
                    studentEnrolment.getSemester().equalsIgnoreCase(semester)) {
                        System.out.println(studentEnrolment.toString());
                        listForPrinting.add(studentEnrolment);
                    }
                }

                System.out.println("Results are retrieved successfully");
                System.out.println("Do you want to print all the records?");
                System.out.println("[Y] Yes");
                System.out.println("[N] No");

                choice = userInput.nextLine();

                if (choice.equalsIgnoreCase("Y")) {
                    if (!listForPrinting.isEmpty()) {
                        String filepath = "src/com/company/print.csv";
                        try {
                            FileWriter fw = new FileWriter(filepath);
                            for (StudentEnrolment oneRecord : listForPrinting) {
                                fw.write(oneRecord.toString());
                            }
                            fw.close();
                            System.out.println("Records have successfully been printed");
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("No such file exist");
                        }
                    } else {
                        System.out.println("There is nothing to print");
                    }
                } else if(choice.equalsIgnoreCase("N")) {
                    System.out.println("The operation will now commence exit");
                }
                break;
            }

            case 3: {
                System.out.println("List of available semesters: ");
                for (Object oneSemester : semesterList) {
                    System.out.println(oneSemester);
                }

                System.out.println("Enter the semester:");
                semester = userInput.nextLine().toUpperCase();

                if(!semesterList.contains(semester)) {
                    System.out.println("No such semester available");
                    return;
                }

                for (StudentEnrolment studentEnrolment : enrolmentList) {
                    if(studentEnrolment.getSemester().equalsIgnoreCase(semester)) {
                        System.out.println(studentEnrolment.toString());
                        listForPrinting.add(studentEnrolment);
                    }
                }

                System.out.println("Do you want to print all the records?");
                System.out.println("[Y] Yes");
                System.out.println("[N] No");

                choice = userInput.nextLine();

                if (choice.equalsIgnoreCase("Y")) {
                    if (!listForPrinting.isEmpty()) {
                        String filepath = "src/com/company/print.csv";
                        try {
                            FileWriter fw = new FileWriter(filepath);
                            for (StudentEnrolment oneRecord : listForPrinting) {
                                fw.write(oneRecord.toString());
                            }
                            fw.close();
                            System.out.println("Records have successfully been printed");
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("No such file exist");
                        }
                    } else {
                        System.out.println("There is nothing to print");
                    }
                } else if(choice.equalsIgnoreCase("N")) {
                    System.out.println("The operation will now commence exit");
                }
                break;
            }
        }
    }

    public static void main(String[] args) {
	// write your code here
        menuOperations();
    }
}
