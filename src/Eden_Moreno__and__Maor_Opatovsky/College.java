package Eden_Moreno__and__Maor_Opatovsky;

public class College {
    private String name;
    private Lecturer[] lecturers;
    private int lecturersCount;
    private Department[] departments;
    private int departmentsCount;
    private Committee[] committees;
    private int committeesCount;

    public College(String name) {
        this.name = name;
        this.lecturers = new Lecturer[2];
        this.lecturersCount = 0;
        this.departments = new Department[2];
        this.departmentsCount = 0;
        this.committees = new Committee[2];
        this.committeesCount = 0;
    }

    public Lecturer findLecturerById(int id) {
        for (int i = 0; i < lecturersCount; i++) {
            if (lecturers[i].getId() == id) return lecturers[i];
        }
        return null;
    }

    public Committee findCommitteeByName(String name) {
        for (int i = 0; i < committeesCount; i++) {
            if (committees[i].getName().equals(name)) return committees[i];
        }
        return null;
    }

    public Department findDepartmentByName(String name) {
        for (int i = 0; i < departmentsCount; i++) {
            if (departments[i].getName().equals(name)) return departments[i];
        }
        return null;
    }

    // Additions to the main arrays
    public boolean addLecturer(Lecturer l) {
        if (findLecturerById(l.getId()) != null) return false;
        if (lecturersCount == lecturers.length) expandLecturers();
        lecturers[lecturersCount++] = l;
        return true;
    }

    public String addCommittee(String committeeName, int chairmanId) {
        if (findCommitteeByName(committeeName) != null) return "Committee name already exists";
        Lecturer chairman = findLecturerById(chairmanId);
        if (chairman == null) return "The lecturer (chairman) have not been found.";
        if (chairman.getDegreeType() != eDegree.PHD && chairman.getDegreeType() != eDegree.PROFESSOR) {
            return "Chairman of the committee must have a PhD or Professor degree.";
        }
        if (committeesCount == committees.length) expandCommittees();
        committees[committeesCount++] = new Committee(committeeName, chairman);
        return "Committee has been added.";
    }

    public boolean addDepartment(Department d) {
        if (findDepartmentByName(d.getName()) != null) return false;
        if (departmentsCount == departments.length) expandDepartments();
        departments[departmentsCount++] = d;
        return true;
    }

    // Actions and connections
    public String addMemberToCommittee(int lecturerId, String committeeName) {
        Lecturer l = findLecturerById(lecturerId);
        if (l == null) return "Lecturer not found.";
        Committee c = findCommitteeByName(committeeName);
        if (c == null) return "Committee not found.";

        boolean success = c.addMember(l);
        return success ? "Member added successfully to the committee." : "Add failed (may already be a member or chairman).";
    }

    public String updateCommitteeChairman(String committeeName, int newChairmanId) {
        Committee c = findCommitteeByName(committeeName);
        if (c == null) return "Committee not found.";
        Lecturer l = findLecturerById(newChairmanId);
        if (l == null) return "Lecturer not found.";

        boolean success = c.setChairman(l);
        return success ? "Chairman updated successfully." : "Update failed. Please ensure the lecturer has a PhD or Professor degree.";
    }

    public String removeMemberFromCommittee(int lecturerId, String committeeName) {
        Lecturer l = findLecturerById(lecturerId);
        if (l == null) return "Lecturer not found.";
        Committee c = findCommitteeByName(committeeName);
        if (c == null) return "Committee not found.";

        boolean success = c.removeMember(l);
        return success ? "Lecturer removed from the committee." : "Lecturer is not a member in the committee.";
    }

    public String addLecturerToDepartment(int lecturerId, String deptName) {
        Lecturer l = findLecturerById(lecturerId);
        if (l == null) return "Lecturer not found.";
        Department d = findDepartmentByName(deptName);
        if (d == null) return "Department not found.";

        boolean success = d.addLecturer(l);
        return success ? "Lecturer added to the department." : "Lecturer already exists in the department.";
    }

    // Queries and information
    public double getCollegeAverageSalary() {
        if (lecturersCount == 0) return 0;
        double sum = 0;
        for (int i = 0; i < lecturersCount; i++) sum += lecturers[i].getSalary();
        return sum / lecturersCount;
    }

    public double getDepartmentAverageSalary(String deptName) {
        Department d = findDepartmentByName(deptName);
        if (d == null) return -1;
        return d.getAverageSalary();
    }

    public String getAllLecturers() {
        if (lecturersCount == 0) return "There are no lecturers in the college.";
        String res = "";
        for (int i = 0; i < lecturersCount; i++) res += lecturers[i].toString() + "\n";
        return res;
    }

    public String getAllCommittees() {
        if (committeesCount == 0) return "There are no committees in the college.";
        String res = "";
        for (int i = 0; i < committeesCount; i++) res += committees[i].toString() + "\n";
        return res;
    }

    // Increasing arrays by 2 times
    private void expandLecturers() {
        Lecturer[] newArr = new Lecturer[lecturers.length * 2];
        for (int i = 0; i < lecturersCount; i++) newArr[i] = lecturers[i];
        this.lecturers = newArr;
    }

    private void expandDepartments() {
        Department[] newArr = new Department[departments.length * 2];
        for (int i = 0; i < departmentsCount; i++) newArr[i] = departments[i];
        this.departments = newArr;
    }

    private void expandCommittees() {
        Committee[] newArr = new Committee[committees.length * 2];
        for (int i = 0; i < committeesCount; i++) newArr[i] = committees[i];
        this.committees = newArr;
    }
}
