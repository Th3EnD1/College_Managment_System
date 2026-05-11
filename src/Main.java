import java.util.Scanner;

/*
 * Eden Moreno, ID: 324996016
 * Maor Opatovsky, ID: 211393871
 * */

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int lecturersCount = 0;
    String[] lecturers = new String[2];

    int committeesCount = 0;
    String[] committees = new String[2];

    int departmentsCount = 0;
    String[] departments = new String[2];

    System.out.print("Enter College Name: ");
    String collegeName = scanner.nextLine();

    int choice = -1;

    while (choice != 0) {
        System.out.println();
        System.out.println("--- Staff Management Menu - " + collegeName + " College ---");
        System.out.println("0- EXIT");
        System.out.println("1- Add Lecturer");
        System.out.println("2- Add Committee");
        System.out.println("3- Add Study Class");
        System.out.println("4- Assign a lecturer to a committee");
        System.out.println("5- Show the average salaries of all lecturers at the college");
        System.out.println("6- Display the average salaries of all lecturers in a specific department");
        System.out.println("7- View details of all lecturers");
        System.out.println("8- View details of all committees");
        System.out.print("Select an option: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 0: {
                System.out.println("Exiting program");
                break;
            }
            case 1: {
                String lecturerName = getUniqueName(lecturers, lecturersCount, "lecturer");

                if (lecturersCount == lecturers.length) {
                    lecturers = doubleArraySize(lecturers);
                }

                lecturers[lecturersCount] = lecturerName;
                lecturersCount++;

                System.out.println("Lecturer has been added.");
                break;
            }
            case 2: {
                String committeeName = getUniqueName(committees, committeesCount, "committee");

                if (committeesCount == committees.length) {
                    committees = doubleArraySize(committees);
                }

                committees[committeesCount] = committeeName;
                committeesCount++;

                System.out.println("Committee has been added.");
                break;
            }
            case 3: {
                String departmentName = getUniqueName(departments, departmentsCount, "study class");

                if (departmentsCount == departments.length) {
                    departments = doubleArraySize(departments);
                }

                departments[departmentsCount] = departmentName;
                departmentsCount++;

                System.out.println("Study class has been added.");
                break;
            }
            case 4: {
                System.out.print("Enter lecturer name: ");
                String searchLecturer = scanner.nextLine();
                System.out.print("Enter committee name: ");
                String searchCommittee = scanner.nextLine();

                boolean lecturerExists = isNameExists(lecturers, lecturersCount, searchLecturer);
                boolean committeeExists = isNameExists(committees, committeesCount, searchCommittee);

                if (!lecturerExists || !committeeExists) {
                    System.out.println("Error: The lecturer or committee does not exist in the system.");
                } else {
                    System.out.println("The absorption has been completed (the placement itself will be implemented in the following stages).");
                }
                break;
            }
            case 5: {
                System.out.println("This option will be implemented in the next stages of the project.");
                break;
            }
            case 6: {
                System.out.println("This option will be implemented in the next stages of the project.");
                break;
            }
            case 7: {
                printNames(lecturers, lecturersCount, "lecturers");
                break;
            }
            case 8: {
                printNames(committees, committeesCount, "committees");
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

public static boolean isNameExists(String[] arr, int logicalSize, String name) {
    for (int i = 0; i < logicalSize; i++) {
        if (arr[i].equals(name)) {
            return true;
        }
    }
    return false;
}

public static String getUniqueName(String[] arr, int logicalSize, String itemType) {
    String name;
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.print("Enter name for " + itemType + ": ");
        name = scanner.nextLine();

        if (isNameExists(arr, logicalSize, name)) {
            System.out.println("Error: The name already exists in the system. Please choose another name.");
        } else {
            break;
        }
    }
    return name;
}

public static String[] doubleArraySize(String[] oldArray) {
    String[] newArray = new String[oldArray.length * 2];
    for (int i = 0; i < oldArray.length; i++) {
        newArray[i] = oldArray[i];
    }
    return newArray;
}

public static void printNames(String[] arr, int logicalSize, String title) {
    System.out.println();
    System.out.println("--- List of " + title + " ---");
    if (logicalSize == 0) {
        System.out.println("No data available");
    } else {
        for (int i = 0; i < logicalSize; i++) {
            System.out.println((i + 1) + ". " + arr[i]);
        }
    }
}
