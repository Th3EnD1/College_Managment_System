package Eden_Moreno__and__Maor_Opatovsky;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int numStudents;
    private ArrayList<Lecturer> lecturers;

    public Department(String name, int numStudents) {
        setName(name);
        setNumStudents(numStudents);
        setLecturers(new ArrayList<>());
    }

    public void addLecturer(Lecturer l) throws CollegeSystemException {
        if (getLecturers().contains(l)) {
            throw new CollegeSystemException("Lecturer already exists in this department.");
        }
        getLecturers().add(l);
        l.setDepartment(this);
    }

    public void removeLecturer(Lecturer l) throws CollegeSystemException {
        if (getLecturers().remove(l)) {
            l.setDepartment(null);
        } else {
            throw new CollegeSystemException("Lecturer not found in this department.");
        }
    }

    public ArrayList<Lecturer> getLecturers() { return this.lecturers; }
    public void setLecturers(ArrayList<Lecturer> lecturers) { this.lecturers = lecturers; }
    public int getNumStudents() { return this.numStudents; }
    public void setNumStudents(int numStudents) { this.numStudents = numStudents; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public double getAverageSalary() {
        if (getLecturers().isEmpty()) return 0;
        double sum = 0;
        for (Lecturer l : getLecturers()) {
            sum += l.getSalary();
        }
        return sum / getLecturers().size();
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
        return "Department: " + getName() + " | Number of students: " + getNumStudents() + " | Number of lecturers: " + getLecturers().size();
    }
}