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

    public Lecturer(String name, eDegree degreeType, String degreeName, double salary) {
        this.name = name;
        this.id = ++counter;
        this.degreeType = degreeType;
        this.degreeName = degreeName;
        this.salary = salary;
        this.department = null; // No default department
        this.committees = new Committee[2]; // Initial size
        this.committeesCount = 0;
    }

    public void addCommittee(Committee c) {
        if (committeesCount == committees.length) {
            expandCommitteesArray();
        }
        committees[committeesCount++] = c;
    }

    public void removeCommittee(Committee c) {
        int index = -1;
        for (int i = 0; i < committeesCount; i++) {
            if (committees[i] == c) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            committees[index] = committees[committeesCount - 1]; // דריסה עם האחרון
            committees[committeesCount - 1] = null;
            committeesCount--;
        }
    }

    private void expandCommitteesArray() {
        Committee[] newArr = new Committee[committees.length * 2];
        for (int i = 0; i < committeesCount; i++) {
            newArr[i] = committees[i];
        }
        this.committees = newArr;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public eDegree getDegreeType() { return degreeType; }
    public double getSalary() { return salary; }
    public void setDepartment(Department dept) { this.department = dept; }

    public String getCommitteesNames() {
        if (committeesCount == 0) return "No committees";
        String names = "";
        for (int i = 0; i < committeesCount; i++) {
            names += committees[i].getName();
            if (i < committeesCount - 1) names += ", ";
        }
        return names;
    }

    public String toString() {
        String deptName = (department != null) ? department.getName() : "No departments";
        return String.format("Lecturer: %s | ID: %s | Degree: %s (%s) | Salary: %.2f | Department: %s | Member in committees: %s",
                name, this.id, degreeType, degreeName, salary, deptName, getCommitteesNames());
    }

}
