package Eden_Moreno__and__Maor_Opatovsky;

public class Department {
    private String name;
    private int numStudents;
    private Lecturer[] lecturers;
    private int lecturersCount;

    public Department(String name, int numStudents) {
        this.name = name;
        this.numStudents = numStudents;
        this.lecturers = new Lecturer[2];
        this.lecturersCount = 0;
    }

    public boolean addLecturer(Lecturer l) {
        for (int i = 0; i < lecturersCount; i++) {
            if (lecturers[i] == l) return false;
        }
        if (lecturersCount == lecturers.length) {
            expandLecturersArray();
        }
        lecturers[lecturersCount++] = l;
        l.setDepartment(this);
        return true;
    }

    private void expandLecturersArray() {
        Lecturer[] newArr = new Lecturer[lecturers.length * 2];
        for (int i = 0; i < lecturersCount; i++) {
            newArr[i] = lecturers[i];
        }
        this.lecturers = newArr;
    }

    public String getName() { return name; }

    public double getAverageSalary() {
        if (lecturersCount == 0) return 0;
        double sum = 0;
        for (int i = 0; i < lecturersCount; i++) {
            sum += lecturers[i].getSalary();
        }
        return sum / lecturersCount;
    }

    public String toString() {
        return "Department: " + name + " | Number of students: " + numStudents + " | Number of lecturers: " + lecturersCount;
    }
}
