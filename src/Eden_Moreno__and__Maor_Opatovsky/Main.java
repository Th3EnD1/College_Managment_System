import Eden_Moreno__and__Maor_Opatovsky.*;

import java.util.Scanner;

/*
 * Eden Moreno, ID: 324996016
 * Maor Opatovsky, ID: 211393871
 * */

public static void main(String[] args) {
    // --- DATA FOR DEBUG ---
        College testCollege = new College("Test College");
        Lecturer lecturer1 = new Lecturer("lecturer1", eDegree.PHD, "Math", 30000, null);
        Lecturer lecturer2 = new Lecturer("lecturer2", eDegree.PROFESSOR, "Math", 20000, null);
        Lecturer lecturer3 = new Lecturer("lecturer3", eDegree.MA, "Computer Science", 10000, null);
        Department department1 = new Department("Math", 30);
        Department department2 = new Department("Computer Science", 30);
        Committee committee1 = new Committee("Tests", lecturer1);
        Committee committee2 = new Committee("Finance", lecturer2);
        testCollege.addLecturer(lecturer1);
        testCollege.addLecturer(lecturer2);
        testCollege.addLecturer(lecturer3);
        testCollege.addDepartment(department1);
        testCollege.addDepartment(department2);
        testCollege.addCommittee("Tests", 1);
        testCollege.addCommittee("Finance", 2);
        lecturer1.setDepartment(department1);
        testCollege.addMemberToCommittee(1, "Finance");
        testCollege.addMemberToCommittee(3, "Finance");
        testCollege.addMemberToCommittee(3, "Tests");
        System.out.println(testCollege.getAllLecturers());
        System.out.println(testCollege.getAllCommittees());
        lecturer1.setDepartment(department2);
        System.out.println(testCollege.getAllLecturers());
    // --- END OF DEBUG DATA ---

    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter College Name: ");
    String collegeName = scanner.nextLine();
    College college = new College(collegeName);

    int choice = -1;

    while (choice != 0) {
        System.out.println();
        System.out.println("--- Staff Management Menu - " + collegeName + " College ---");
        System.out.println("0- EXIT");
        System.out.println("1- Add lecturer");
        System.out.println("2- Add committee");
        System.out.println("3- Add member to committee");
        System.out.println("4- Update chairman to a committee");
        System.out.println("5- Remove member from a committee");
        System.out.println("6- Add department");
        System.out.println("7- Add lecturer to department");
        System.out.println("8- Show average salaries of all lecturers in the college");
        System.out.println("9- Show average salaries of all lecturers in a department");
        System.out.println("10- Show details of all lecturers in the college");
        System.out.println("11- Show details of all committees in the college");
        System.out.print("Select an option: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 0: {
                System.out.println("Exiting program");
                break;
            }
            case 1: {
                System.out.println("Enter lecturer name: ");
                String name = scanner.nextLine();
                while (college.findLecturerByName(name) != null) {
                    System.out.print("A lecturer with this name already exists. Please enter a different name: \n");
                    name = scanner.nextLine();
                }
                System.out.print("Degree Type (1-BA, 2-MA, 3-PHD, 4-PROFESSOR): ");
                int degreeChoice = scanner.nextInt();
                scanner.nextLine();
                eDegree degree = eDegree.BA;
                if (degreeChoice == 2) degree = eDegree.MA;
                else if (degreeChoice == 3) degree = eDegree.PHD;
                else if (degreeChoice == 4) degree = eDegree.PROFESSOR;
                System.out.println("Enter degree name: ");
                String degreeName = scanner.nextLine();
                System.out.println("Enter salary: ");
                double salary = scanner.nextDouble();
                scanner.nextLine();

                college.addLecturer(new Lecturer(name, degree, degreeName, salary, null));
                System.out.println("Lecturer has been added.");
                break;
            }
            case 2: {
                System.out.print("Enter committee name: ");
                String commName = scanner.nextLine();
                System.out.print("Enter chairman ID: ");
                int chairId = scanner.nextInt();
                System.out.println(college.addCommittee(commName, chairId));
                break;
            }
            case 3: {
                System.out.print("Enter committee name: ");
                String committeeNameAdd = scanner.nextLine();
                System.out.print("Enter lecturer's ID: ");
                int lecturerIdAdd = scanner.nextInt();
                System.out.println(college.addMemberToCommittee(lecturerIdAdd, committeeNameAdd));
                break;
            }
            case 4: {
                System.out.print("Enter committee name: ");
                String committeeNameUpdate = scanner.nextLine();
                System.out.print("Enter new chairman's ID: ");
                int lecturerIdUpdate = scanner.nextInt();
                System.out.println(college.updateCommitteeChairman(committeeNameUpdate, lecturerIdUpdate));
                break;
            }
            case 5: {
                System.out.print("Enter committee name: ");
                String committeeNameRemove = scanner.nextLine();
                System.out.print("Enter lecturer's ID for removal: ");
                int lecturerIdRemove = scanner.nextInt();
                System.out.println(college.removeMemberFromCommittee(lecturerIdRemove, committeeNameRemove));
                break;
            }
            case 6: {
                System.out.print("Enter department name: ");
                String departmentName = scanner.nextLine();
                while (college.findDepartmentByName(departmentName) != null) {
                    System.out.print("A department with this name already exists. Please enter a different name: ");
                    departmentName = scanner.nextLine();
                }
                System.out.print("Enter a maximum number for students in the department: ");
                int maxStudentsNum = scanner.nextInt();
                scanner.nextLine();

                college.addDepartment(new Department(departmentName, maxStudentsNum));
                System.out.println("Department has been added.");
                break;
            }
            case 7: {
                System.out.print("Enter department name: ");
                String departmentName = scanner.nextLine();
                System.out.print("Enter lecturer ID: ");
                int lecturerIdDepartment = scanner.nextInt();
                System.out.println(college.addLecturerToDepartment(lecturerIdDepartment, departmentName));
                break;
            }
            case 8: {
                System.out.printf("Average lecturer's salaries: %.2f\n", college.getCollegeAverageSalary());
                break;
            }
            case 9: {
                System.out.print("Enter department name: ");
                String departmentNameAvg = scanner.nextLine();
                double avg = college.getDepartmentAverageSalary(departmentNameAvg);
                if (avg == -1) System.out.println("Department not found.");
                else System.out.printf("Average salaries in department %s: %.2f\n", departmentNameAvg, avg);
                break;
            }
            case 10: {
                System.out.println("--- All Lecturers ---");
                System.out.println(college.getAllLecturers());
                break;
            }
            case 11: {
                System.out.println("--- All Committees ---");
                System.out.println(college.getAllCommittees());
                break;
            }
            default: {
                System.out.println("Incorrect selection, please try again.");
                break;
            }
        }
    }

    scanner.close();
}
