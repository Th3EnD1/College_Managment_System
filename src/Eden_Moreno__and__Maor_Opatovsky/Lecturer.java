package Eden_Moreno__and__Maor_Opatovsky;

import java.io.Serializable;
import java.util.ArrayList;

public class Lecturer implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int counter = 0; // Managed after serialization to continue correctly

    private int id;
    private String name;
    private String degreeName;
    private eDegree degreeType;
    private double salary;
    private Department department; // Maximum one department
    private ArrayList<Committee> committees;

    public Lecturer(String name, eDegree degreeType, String degreeName, double salary, Department department) {
        setName(name);
        setId(++counter);
        setDegreeType(degreeType);
        setDegreeName(degreeName);
        setSalary(salary);
        setDepartment(department); 
        setCommittees(new ArrayList<>());
    }

    public void addCommittee(Committee c) throws CollegeSystemException {
        if (getCommittees().contains(c)) {
            throw new CollegeSystemException("Lecturer is already a member of this committee.");
        }
        getCommittees().add(c);
    }

    public void removeCommittee(Committee c) throws CollegeSystemException {
        if (!getCommittees().remove(c)) {
            throw new CollegeSystemException("Lecturer is not a member of this committee.");
        }
    }
    public static void setCounter(int val) { counter = val; }
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public eDegree getDegreeType() { return this.degreeType; }
    public void setDegreeType(eDegree degreeType) { this.degreeType = degreeType; }
    public double getSalary() { return this.salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public String getDegreeName() { return this.degreeName; }
    public void setDegreeName(String degreeName) { this.degreeName = degreeName; }
    public void setDepartment(Department dept) { this.department = dept; }
    public Department getDepartment() { return this.department; }
    public ArrayList<Committee> getCommittees() { return this.committees; }
    public void setCommittees(ArrayList<Committee> committees) { this.committees = committees; }

    public String getCommitteesNames() {
        if (getCommittees().isEmpty()) return "No committees";
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < getCommittees().size(); i++) {
            names.append(getCommittees().get(i).getName());
            if (i < getCommittees().size() - 1) names.append(", ");
        }
        return names.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Lecturer other = (Lecturer) obj;
        return getId() == other.getId();
    }

    @Override
    public String toString() {
        String string = "Lecturer: " + getName() + " | ID: " + getId() + " | Degree: " + getDegreeType() + " (" + getDegreeName() + ") | Salary: "
                + getSalary() + " | Department: ";
        if (getDepartment() == null) { string += "No department | ";}
        else {string += getDepartment().getName() + " | ";}
        string += "Member in committees: ";
        if (getCommitteesNames().equals("")) { string += "No committees | ";}
        else {string += getCommitteesNames();}
        return string;
    }
}