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

    public void addLecturer(Lecturer l) throws CollegeSystemException {
        for (int i = 0; i < getLecturersCount(); i++) {
            if (getLecturers()[i].equals(l)) {
                throw new CollegeSystemException("Lecturer already exists in this department.");
            }
        }
        if (getLecturersCount() == getLecturers().length) {
            expandLecturersArray();
        }
        getLecturers()[getLecturersCount()] = l;
        setLecturersCount(getLecturersCount() + 1);
        l.setDepartment(this);
    }

    public void removeLecturer(Lecturer l) throws CollegeSystemException {
        int index = -1;
        for (int i = 0; i < getLecturersCount(); i++) {
            if (getLecturers()[i].equals(l)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            l.setDepartment(null);
            getLecturers()[index] = getLecturers()[getLecturersCount() - 1];
            getLecturers()[getLecturersCount() - 1] = null;
            setLecturersCount(getLecturersCount() - 1);
        } else {
            throw new CollegeSystemException("Lecturer not found in this department.");
        }
    }

    private void expandLecturersArray() {
        Lecturer[] newArr = new Lecturer[getLecturers().length * 2];
        for (int i = 0; i < getLecturersCount(); i++) {
            newArr[i] = getLecturers()[i];
        }
        setLecturers(newArr);
    }

    public Lecturer[] getLecturers() { return lecturers; }
    public void setLecturers(Lecturer[] lecturers) { this.lecturers = lecturers; }
    public int getNumStudents() { return numStudents; }
    public void setNumStudents(int numStudents) { this.numStudents = numStudents; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getLecturersCount() { return lecturersCount; }
    public void setLecturersCount(int lecturersCount) { this.lecturersCount = lecturersCount; }

    public double getAverageSalary() {
        if (getLecturersCount() == 0) return 0;
        double sum = 0;
        for (int i = 0; i < getLecturersCount(); i++) {
            sum += getLecturers()[i].getSalary();
        }
        return sum / getLecturersCount();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Department other = (Department) obj;
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return "Department: " + getName() + " | Number of students: " + getNumStudents() + " | Number of lecturers: " + getLecturersCount();
    }
}