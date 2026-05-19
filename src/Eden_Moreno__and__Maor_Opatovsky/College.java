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
        setName(name);
        setLecturers(new Lecturer[2]);
        setCommitteesCount(0);
        setCommittees(new Committee[2]);
        setDepartmentsCount(0);
        setDepartments(new Department[2]);
    }

    public int getCommitteesCount() {return this.committeesCount;}
    public void setCommitteesCount(int committeesCount) {this.committeesCount = committeesCount;}
    public Committee[] getCommittees() {return this.committees;}
    public void setCommittees(Committee[] committees) {this.committees = committees;}
    public int getDepartmentsCount() {return this.departmentsCount;}
    public void setDepartmentsCount(int departmentsCount) {this.departmentsCount = departmentsCount;}
    public Department[] getDepartments() {return this.departments;}
    public void setDepartments(Department[] departments) {this.departments = departments;}
    public int getLecturersCount() {return this.lecturersCount;}
    public void setLecturersCount(int lecturersCount) {this.lecturersCount = lecturersCount;}
    public Lecturer[] getLecturers() {return this.lecturers;}
    public void setLecturers(Lecturer[] lecturers) {this.lecturers = lecturers;}
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public Lecturer findLecturerById(int id) {
        for (int i = 0; i < getLecturersCount(); i++) {
            if (getLecturers()[i].getId() == id) return getLecturers()[i];
        }
        return null;
    }

    public Lecturer findLecturerByName(String name) {
        for (int i = 0; i < getLecturersCount(); i++) {
            if (getLecturers()[i].getName().equals(name)) return getLecturers()[i];
        }
        return null;
    }

    public Committee findCommitteeByName(String name) {
        for (int i = 0; i < getCommitteesCount(); i++) {
            if (getCommittees()[i].getName().equals(name)) return getCommittees()[i];
        }
        return null;
    }

    public Department findDepartmentByName(String name) {
        for (int i = 0; i < getDepartmentsCount(); i++) {
            if (getDepartments()[i].getName().equals(name)) return getDepartments()[i];
        }
        return null;
    }

    // Additions to the main arrays
    public boolean addLecturer(Lecturer l) {
        if (findLecturerById(l.getId()) != null) return false;
        if (getLecturersCount() == getLecturers().length) expandLecturers();
        getLecturers()[getLecturersCount()] = l;
        setLecturersCount(getLecturersCount() + 1);
        return true;
    }

    public String addCommittee(String committeeName, int chairmanId) {
        if (findCommitteeByName(committeeName) != null) return "Committee name already exists";
        Lecturer chairman = findLecturerById(chairmanId);
        if (chairman == null) return "The lecturer (chairman) have not been found.";
        if (chairman.getDegreeType() != eDegree.PHD && chairman.getDegreeType() != eDegree.PROFESSOR) {
            return "Chairman of the committee must have a PhD or Professor degree.";
        }
        if (getCommitteesCount() == getCommittees().length) expandCommittees();
        getCommittees()[getCommitteesCount()] = new Committee(committeeName, chairman);
        setCommitteesCount(getCommitteesCount() + 1);
        return "Committee has been added.";
    }

    public boolean addDepartment(Department d) {
        if (findDepartmentByName(d.getName()) != null) return false;
        if (getDepartmentsCount() == getDepartments().length) expandDepartments();
        getDepartments()[getDepartmentsCount()] = d;
        setDepartmentsCount(getDepartmentsCount() + 1);
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
        if (getLecturersCount() == 0) return 0;
        double sum = 0;
        for (int i = 0; i < getLecturersCount(); i++) sum += getLecturers()[i].getSalary();
        return sum / getLecturersCount();
    }

    public double getDepartmentAverageSalary(String deptName) {
        Department d = findDepartmentByName(deptName);
        if (d == null) return -1;
        return d.getAverageSalary();
    }

    public String getAllLecturers() {
        if (getLecturersCount() == 0) return "There are no lecturers in the college.";
        String res = "";
        for (int i = 0; i < getLecturersCount(); i++) res += getLecturers()[i].toString() + "\n";
        return res;
    }

    public String getAllCommittees() {
        if (getCommitteesCount() == 0) return "There are no committees in the college.";
        String res = "";
        for (int i = 0; i < getCommitteesCount(); i++) res += getCommittees()[i].toString() + "\n";
        return res;
    }

    // Increasing arrays by 2 times
    private void expandLecturers() {
        Lecturer[] newArr = new Lecturer[getLecturers().length * 2];
        for (int i = 0; i < getLecturersCount(); i++) newArr[i] = getLecturers()[i];
        setLecturers(newArr);
    }

    private void expandDepartments() {
        Department[] newArr = new Department[getDepartments().length * 2];
        for (int i = 0; i < getDepartmentsCount(); i++) newArr[i] = getDepartments()[i];
        setDepartments(newArr);
    }

    private void expandCommittees() {
        Committee[] newArr = new Committee[getCommittees().length * 2];
        for (int i = 0; i < getCommitteesCount(); i++) newArr[i] = getCommittees()[i];
        setCommittees(newArr);
    }

    public String toString() {
        return "College name: " + getName() + " | Number of lecturers: " + getLecturersCount() + " | "
                + "Number of departments: " + getDepartmentsCount() + " | Number of committees: " + getCommitteesCount();
    }
}
