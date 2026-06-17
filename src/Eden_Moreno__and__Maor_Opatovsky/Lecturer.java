package Eden_Moreno__and__Maor_Opatovsky;

public class Lecturer {
    private static int counter;
    private int id;
    private String name;
    private String degreeName;
    private eDegree degreeType;
    private double salary;
    private Department department; // Maximum one department

    private Committee[] committees;
    private int committeesCount;

    public Lecturer(String name, eDegree degreeType, String degreeName, double salary, Department department) {
        setName(name);
        setId(++counter);
        setDegreeType(degreeType);
        setDegreeName(degreeName);
        setSalary(salary);
        setDepartment(department); 
        setCommittees(new Committee[2]); // Initial size of 2
        setCommitteesCount(0);
    }

    public void addCommittee(Committee c) throws CollegeSystemException {
        for (int i = 0; i < getCommitteesCount(); i++) {
            if (getCommittees()[i].equals(c)) {
                throw new CollegeSystemException("Lecturer is already a member of this committee.");
            }
        }
        if (getCommitteesCount() == getCommittees().length) {
            expandCommitteesArray();
        }
        getCommittees()[getCommitteesCount()] = c;
        setCommitteesCount(getCommitteesCount() + 1);
    }

    public void removeCommittee(Committee c) throws CollegeSystemException {
        int index = -1;
        for (int i = 0; i < getCommitteesCount(); i++) {
            if (getCommittees()[i].equals(c)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            getCommittees()[index] = getCommittees()[getCommitteesCount() - 1]; // Override with last element
            getCommittees()[getCommitteesCount() - 1] = null;
            setCommitteesCount(getCommitteesCount() - 1);
        } else {
            throw new CollegeSystemException("Lecturer is not a member of this committee.");
        }
    }

    private void expandCommitteesArray() {
        Committee[] newArr = new Committee[getCommittees().length * 2];
        for (int i = 0; i < getCommitteesCount(); i++) {
            newArr[i] = getCommittees()[i];
        }
        setCommittees(newArr);
    }

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
    public int getCommitteesCount() { return this.committeesCount; }
    public void setCommitteesCount(int committeesCount) { this.committeesCount = committeesCount; }
    public Committee[] getCommittees() { return this.committees; }
    public void setCommittees(Committee[] committees) { this.committees = committees; }

    public String getCommitteesNames() {
        if (getCommitteesCount() == 0) return "No committees";
        String names = "";
        for (int i = 0; i < getCommitteesCount(); i++) {
            names += getCommittees()[i].getName();
            if (i < getCommitteesCount() - 1) names += ", ";
        }
        return names;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Lecturer other = (Lecturer) obj;
        return this.id == other.id;
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