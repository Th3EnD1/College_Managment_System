package Eden_Moreno__and__Maor_Opatovsky;

public class Department {
    private String name;
    private int numStudents;
    private Lecturer[] lecturers;
    private int lecturersCount;

    public Department(String name, int numStudents) {
        setName(name);
        setNumStudents(numStudents);
        setLecturers(new Lecturer[2]);
        setLecturersCount(0);
    }

    public boolean addLecturer(Lecturer l) {
        for (int i = 0; i < getLecturersCount(); i++) {
            if (getLecturers()[i] == l) return false;
        }
        if (getLecturersCount() == getLecturers().length) {
            expandLecturersArray();
        }
        getLecturers()[getLecturersCount()] = l;
        setLecturersCount(getLecturersCount() + 1);
        l.setDepartment(this);
        return true;
    }

    public boolean removeLecturer(Lecturer l) {
        int index = -1;
        for (int i = 0; i < getLecturersCount(); i++) {
            if (getLecturers()[i] == l) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            l.setDepartment(null);
            getLecturers()[index] = getLecturers()[getLecturersCount() - 1];
            getLecturers()[getLecturersCount() - 1] = null;
            setLecturersCount(getLecturersCount() - 1);
            return true;
        }
        return false;
    }

    private void expandLecturersArray() {
        Lecturer[] newArr = new Lecturer[getLecturers().length * 2];
        for (int i = 0; i < getLecturersCount(); i++) {
            newArr[i] = getLecturers()[i];
        }
        setLecturers(newArr);
    }

    public Lecturer[] getLecturers() {return lecturers;}
    public void setLecturers(Lecturer[] lecturers) {this.lecturers = lecturers;}
    public int getNumStudents() {return numStudents;}
    public void setNumStudents(int numStudents) {this.numStudents = numStudents;}
    public String getName() { return name; }
    public void setName(String name) {this.name = name;}
    public int getLecturersCount() {return lecturersCount;}
    public void setLecturersCount(int lecturersCount) {this.lecturersCount = lecturersCount;}

    public double getAverageSalary() {
        if (getLecturersCount() == 0) return 0;
        double sum = 0;
        for (int i = 0; i < getLecturersCount(); i++) {
            sum += getLecturers()[i].getSalary();
        }
        return sum / getLecturersCount();
    }

    public String toString() {
        return "Department: " + getName() + " | Number of students: " + getNumStudents() + " | Number of lecturers: " + getLecturersCount();
    }
}
