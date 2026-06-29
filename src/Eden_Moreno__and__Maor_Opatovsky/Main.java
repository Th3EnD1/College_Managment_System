package Eden_Moreno__and__Maor_Opatovsky;

import java.io.*;
import java.util.Scanner;

/*
 * Eden Moreno, ID: 324996016
 * Maor Opatovsky, ID: 211393871
 */

public class Main {
    private static final String DATA_FILE = "college_data.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        College college = loadCollegeData();

        if (college == null) {
            System.out.print("Enter College Name: ");
            String collegeName = scanner.nextLine();
            college = new College(collegeName);
            // Setup test data only if starting fresh
            setupTestData(college);
        } else {
            System.out.println("\nSuccessfully loaded college data from previous session!");
            // Restore static ID counter for Lecturers
            int maxId = 0;
            for (Lecturer l : college.getLecturers()) {
                if (l.getId() > maxId) {
                    maxId = l.getId();
                }
            }
            Lecturer.setCounter(maxId);
        }

        int choice = -1;

        while (choice != 0) {
            System.out.println("\n--- Staff Management Menu - " + college.getName() + " College ---");
            System.out.println("0- EXIT (And save data)");
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
            System.out.println("12- Compare Doctors/Professors by articles count");
            System.out.println("13- Compare Committees (by size or articles)");
            System.out.println("14- Clone a committee");
            System.out.print("Select an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please enter a numeric value.");
                continue;
            }

            try {
                switch (choice) {
                    case 0:
                        saveCollegeData(college);
                        System.out.println("Exiting program and saving data.");
                        break;
                    case 1:
                        addNewLecturerToCollege(college, scanner);
                        break;
                    case 2:
                        addNewCommitteeToCollege(college, scanner);
                        break;
                    case 3:
                        addMemberToCommitteeInCollege(college, scanner);
                        break;
                    case 4:
                        updateCommitteeChairmanInCollege(college, scanner);
                        break;
                    case 5:
                        removeMemberFromCommitteeInCollege(college, scanner);
                        break;
                    case 6:
                        addNewDepartmentToCollege(college, scanner);
                        break;
                    case 7:
                        addLecturerToDepartmentInCollege(college, scanner);
                        break;
                    case 8:
                        System.out.printf("Average lecturer's salaries: %.2f\n", college.getCollegeAverageSalary());
                        break;
                    case 9:
                        showLecturersSalariesAverageInDepartment(college, scanner);
                        break;
                    case 10:
                        showAllLecturersInCollege(college, scanner);
                        break;
                    case 11:
                        showAllCommitteesInCollege(college, scanner);
                        break;
                    case 12:
                        compareDoctorsByArticles(college, scanner);
                        break;
                    case 13:
                        compareCommittees(college, scanner);
                        break;
                    case 14:
                        cloneCommitteeInCollege(college, scanner);
                        break;
                    default:
                        System.out.println("Incorrect selection, please try again.");
                        break;
                }
            } catch (CollegeSystemException e) {
                System.out.println("Action Failed: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void saveCollegeData(College college) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(college);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    private static College loadCollegeData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (College) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load previous data. Starting fresh...");
            return null;
        }
    }

    public static void setupTestData(College college) {
        try {
            Doctor doc1 = new Doctor("Dr. Cohen", "Computer Science", 15000, null);
            doc1.addArticle("Java Basics");
            Doctor doc2 = new Doctor("Dr. Levi", "Mathematics", 16000, null);
            doc2.addArticle("Calculus I");
            Doctor doc3 = new Doctor("Dr. Ben-David", "Physics", 15500, null);
            doc3.addArticle("Quantum Mechanics");

            Professor prof1 = new Professor("Prof. Goldstein", "Software Engineering", 20000, null, "Afeka College of Engineering");
            prof1.addArticle("Advanced Design Patterns");

            college.addLecturer(doc1);
            college.addLecturer(doc2);
            college.addLecturer(doc3);
            college.addLecturer(prof1);

            Department csDept = new Department("Computer Science", 500);
            Department mathDept = new Department("Mathematics", 300);

            college.addDepartment(csDept);
            college.addDepartment(mathDept);

            college.addLecturerToDepartment(doc1.getId(), "Computer Science");
            college.addLecturerToDepartment(prof1.getId(), "Computer Science");
            college.addLecturerToDepartment(doc2.getId(), "Mathematics");
            college.addLecturerToDepartment(doc3.getId(), "Mathematics");

            college.addCommittee("Curriculum", prof1.getId(), eCommitteeType.DOCTOR);
            college.addCommittee("Admissions", doc1.getId(), eCommitteeType.DOCTOR);

            college.addMemberToCommittee(doc2.getId(), "Curriculum");
            college.addMemberToCommittee(doc3.getId(), "Admissions");

            System.out.println("\nTest data setup successfully.");
        } catch (CollegeSystemException e) {
            System.out.println("\nError setting up test data: " + e.getMessage());
        }
    }

    public static void addNewLecturerToCollege(College college, Scanner scanner) throws CollegeSystemException {
        System.out.println("Enter lecturer name: ");
        String name = scanner.nextLine();
        while (college.findLecturerByName(name) != null) {
            System.out.println("A lecturer with this name already exists. Please enter a different name: ");
            name = scanner.nextLine();
        }
        System.out.print("Degree Type (1-BA, 2-MA, 3-PHD, 4-PROFESSOR): ");
        int degreeChoice;
        try {
            degreeChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new CollegeSystemException("Invalid input: Expected a numeric value for degree type.");
        }
        
        eDegree degree = eDegree.BA;
        if (degreeChoice == 2) degree = eDegree.MA;
        else if (degreeChoice == 3) degree = eDegree.PHD;
        else if (degreeChoice == 4) degree = eDegree.PROFESSOR;
        
        System.out.println("Enter degree name: ");
        String degreeName = scanner.nextLine();
        System.out.println("Enter salary: ");
        double salary;
        try {
            salary = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new CollegeSystemException("Invalid input: Expected a numeric value for salary.");
        }

        if (degree == eDegree.PHD || degree == eDegree.PROFESSOR) {
            Doctor doctor;
            if (degree == eDegree.PROFESSOR) {
                System.out.print("Enter awarding body for the professorship: ");
                String awardingBody = scanner.nextLine();
                doctor = new Professor(name, degreeName, salary, null, awardingBody);
            } else {
                doctor = new Doctor(name, degreeName, salary, null);
            }
            
            System.out.print("How many articles do you want to add now? ");
            int numArticles;
            try {
                numArticles = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                throw new CollegeSystemException("Invalid input: Expected a numeric value for number of articles.");
            }
            for (int i = 0; i < numArticles; i++) {
                System.out.print("Enter article name: ");
                String articleName = scanner.nextLine();
                doctor.addArticle(articleName);
            }
            college.addLecturer(doctor);
        } else {
            college.addLecturer(new Lecturer(name, degree, degreeName, salary, null));
        }
        System.out.println("\nLecturer " + name + " has been added.");
    }

    public static void addNewCommitteeToCollege(College college, Scanner scanner) throws CollegeSystemException {
        System.out.print("Enter committee name: ");
        String commName = scanner.nextLine();
        System.out.print("Enter chairman ID (Must be PhD or Professor): ");
        int chairId;
        try {
            chairId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new CollegeSystemException("Invalid input: Expected a numeric value for chairman ID.");
        }

        System.out.println("Select regular members degree level (1- Regular [BA/MA], 2- Doctor [PHD], 3- Professor [PROFESSOR]): ");
        int typeChoice;
        try {
            typeChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new CollegeSystemException("Invalid input: Expected a numeric value.");
        }

        eCommitteeType type;
        switch (typeChoice) {
            case 1:
                type = eCommitteeType.REGULAR;
                break;
            case 2:
                type = eCommitteeType.DOCTOR;
                break;
            case 3:
                type = eCommitteeType.PROFESSOR;
                break;
            default:
                throw new CollegeSystemException("Invalid committee type selection.");
        }

        college.addCommittee(commName, chairId, type);
        System.out.println("\nCommittee " + commName + " has been added.");
    }

    public static void addMemberToCommitteeInCollege(College college, Scanner scanner) throws CollegeSystemException {
        System.out.print("Enter committee name: ");
        String committeeNameAdd = scanner.nextLine();
        System.out.print("Enter lecturer's ID: ");
        int lecturerIdAdd;
        try {
            lecturerIdAdd = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new CollegeSystemException("Invalid input: Expected a numeric value for lecturer ID.");
        }
        college.addMemberToCommittee(lecturerIdAdd, committeeNameAdd);
        System.out.println("\nMember " + college.findLecturerById(lecturerIdAdd).getName() + " has been added to the committee " + committeeNameAdd + ".");
    }

    public static void updateCommitteeChairmanInCollege(College college, Scanner scanner) throws CollegeSystemException {
        System.out.print("Enter committee name: ");
        String committeeNameUpdate = scanner.nextLine();
        System.out.print("Enter new chairman's ID: ");
        int lecturerIdUpdate;
        try {
            lecturerIdUpdate = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new CollegeSystemException("Invalid input: Expected a numeric value for chairman ID.");
        }
        college.updateCommitteeChairman(committeeNameUpdate, lecturerIdUpdate);
        System.out.println("\nChairman " + college.findCommitteeByName(committeeNameUpdate).getChairman().getName() + " has been updated in the committee " + committeeNameUpdate + ".");
    }

    public static void removeMemberFromCommitteeInCollege(College college, Scanner scanner) throws CollegeSystemException {
        System.out.print("Enter committee name: ");
        String committeeNameRemove = scanner.nextLine();
        System.out.print("Enter lecturer's ID for removal: ");
        int lecturerIdRemove;
        try {
            lecturerIdRemove = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new CollegeSystemException("Invalid input: Expected a numeric value for lecturer ID.");
        }
        college.removeMemberFromCommittee(lecturerIdRemove, committeeNameRemove);
        System.out.println("\nLecturer " + college.findLecturerById(lecturerIdRemove).getName() + " has been removed from the committee " + committeeNameRemove + ".");
    }

    public static void addNewDepartmentToCollege(College college, Scanner scanner) throws CollegeSystemException {
        System.out.print("Enter department name: ");
        String departmentName = scanner.nextLine();
        while (college.findDepartmentByName(departmentName) != null) {
            System.out.print("A department with this name already exists. Please enter a different name: ");
            departmentName = scanner.nextLine();
        }
        System.out.print("Enter a maximum number for students in the department: ");
        int maxStudentsNum;
        try {
            maxStudentsNum = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new CollegeSystemException("Invalid input: Expected a numeric value for maximum students.");
        }

        college.addDepartment(new Department(departmentName, maxStudentsNum));
        System.out.println("\nDepartment " + departmentName + " has been added.");
    }

    public static void addLecturerToDepartmentInCollege(College college, Scanner scanner) throws CollegeSystemException {
        System.out.print("Enter department name: ");
        String departmentName = scanner.nextLine();
        System.out.print("Enter lecturer ID: ");
        int lecturerIdDepartment;
        try {
            lecturerIdDepartment = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new CollegeSystemException("Invalid input: Expected a numeric value for lecturer ID.");
        }
        college.addLecturerToDepartment(lecturerIdDepartment, departmentName);
        System.out.println("\nLecturer " + college.findLecturerById(lecturerIdDepartment).getName() + " has been added to the department " + departmentName + ".");
    }

    public static void cloneCommitteeInCollege(College college, Scanner scanner) throws CollegeSystemException {
        System.out.print("Enter committee name to clone: ");
        String committeeName = scanner.nextLine();
        college.cloneCommittee(committeeName);
        System.out.println("\nCommittee " + committeeName + " has been cloned.");
    }

    public static void compareDoctorsByArticles(College college, Scanner scanner) {
        System.out.print("Enter first Doctor/Professor ID: ");
        int id1;
        try {
            id1 = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Expected a numeric value for ID.");
            return;
        }
        System.out.print("Enter second Doctor/Professor ID: ");
        int id2;
        try {
            id2 = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Expected a numeric value for ID.");
            return;
        }

        Lecturer l1 = college.findLecturerById(id1);
        Lecturer l2 = college.findLecturerById(id2);

        if (l1 instanceof Doctor && l2 instanceof Doctor) {
            Doctor d1 = (Doctor) l1;
            Doctor d2 = (Doctor) l2;
            int result = d1.compareTo(d2);
            if (result > 0) System.out.println(d1.getName() + " has more articles.");
            else if (result < 0) System.out.println(d2.getName() + " has more articles.");
            else System.out.println("Both have the same number of articles.");
        } else {
            System.out.println("One or both IDs do not belong to a Doctor/Professor.");
        }
    }

    public static void compareCommittees(College college, Scanner scanner) {
        System.out.print("Enter first committee name: ");
        String c1Name = scanner.nextLine();
        System.out.print("Enter second committee name: ");
        String c2Name = scanner.nextLine();

        Committee c1 = college.findCommitteeByName(c1Name);
        Committee c2 = college.findCommitteeByName(c2Name);

        if (c1 == null || c2 == null) {
            System.out.println("One or both committees not found.");
            return;
        }

        System.out.println("Compare by: 1- Total members count, 2- Total articles count");
        int criteria;
        try {
            criteria = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Expected a numeric value for criteria.");
            return;
        }

        int result = 0;
        if (criteria == 1) {
            result = new CommitteeMemberComparator().compare(c1, c2);
        } else if (criteria == 2) {
            result = new CommitteeArticleComparator().compare(c1, c2);
        } else {
            System.out.println("Invalid selection.");
            return;
        }

        if (result > 0) System.out.println(c1.getName() + " is larger by this criteria.");
        else if (result < 0) System.out.println(c2.getName() + " is larger by this criteria.");
        else System.out.println("Both committees are equal by this criteria.");
    }

    public static void showLecturersSalariesAverageInDepartment(College college, Scanner scanner) {
        System.out.print("Enter department name: ");
        String departmentNameAvg = scanner.nextLine();
        double avg = college.getDepartmentAverageSalary(departmentNameAvg);
        if (avg == -1) System.out.println("Department not found.");
        else System.out.printf("Average salaries in department %s: %.2f\n", departmentNameAvg, avg);
    }

    public static void showAllLecturersInCollege(College college, Scanner scanner) {
        System.out.println("\n--- All Lecturers ---");
        System.out.println(college.getAllLecturers());
    }

    public static void showAllCommitteesInCollege(College college, Scanner scanner) {
        System.out.println("\n--- All Committees ---");
        System.out.println(college.getAllCommittees());
    }
}