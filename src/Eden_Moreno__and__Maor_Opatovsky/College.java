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

    public int getCommitteesCount() { return this.committeesCount; }
    public void setCommitteesCount(int committeesCount) { this.committeesCount = committeesCount; }
    public Committee[] getCommittees() { return this.committees; }
    public void setCommittees(Committee[] committees) { this.committees = committees; }
    public int getDepartmentsCount() { return this.departmentsCount; }
    public void setDepartmentsCount(int departmentsCount) { this.departmentsCount = departmentsCount; }
    public Department[] getDepartments() { return this.departments; }
    public void setDepartments(Department[] departments) { this.departments = departments; }
    public int getLecturersCount() { return this.lecturersCount; }
    public void setLecturersCount(int lecturersCount) { this.lecturersCount = lecturersCount; }
    public Lecturer[] getLecturers() { return this.lecturers; }
    public void setLecturers(Lecturer[] lecturers) { this.lecturers = lecturers; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

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

    public void addLecturer(Lecturer l) throws CollegeSystemException {
        if (findLecturerById(l.getId()) != null) {
            throw new CollegeSystemException("Lecturer ID already exists.");
        }
        if (getLecturersCount() == getLecturers().length) expandLecturers();
        getLecturers()[getLecturersCount()] = l;
        setLecturersCount(getLecturersCount() + 1);
    }

    public void addCommittee(String committeeName, int chairmanId) throws CollegeSystemException {
        if (findCommitteeByName(committeeName) != null) {
            throw new CollegeSystemException("Committee name already exists.");
        }
        Lecturer chairman = findLecturerById(chairmanId);
        if (chairman == null) {
            throw new CollegeSystemException("The lecturer (chairman) has not been found.");
        }
        if (!(chairman instanceof Doctor)) {
            throw new CollegeSystemException("Chairman of the committee must be a PhD or Professor.");
        }
        if (getCommitteesCount() == getCommittees().length) expandCommittees();
        getCommittees()[getCommitteesCount()] = new Committee(committeeName, (Doctor) chairman);
        setCommitteesCount(getCommitteesCount() + 1);
    }

    public void addDepartment(Department d) throws CollegeSystemException {
        if (findDepartmentByName(d.getName()) != null) {
            throw new CollegeSystemException("Department name already exists.");
        }
        if (getDepartmentsCount() == getDepartments().length) expandDepartments();
        getDepartments()[getDepartmentsCount()] = d;
        setDepartmentsCount(getDepartmentsCount() + 1);
    }

    public void addMemberToCommittee(int lecturerId, String committeeName) throws CollegeSystemException {
        Lecturer l = findLecturerById(lecturerId);
        if (l == null) throw new CollegeSystemException("Lecturer not found.");
        Committee c = findCommitteeByName(committeeName);
        if (c == null) throw new CollegeSystemException("Committee not found.");

        c.addMember(l);
    }

    public void updateCommitteeChairman(String committeeName, int newChairmanId) throws CollegeSystemException {
        Committee c = findCommitteeByName(committeeName);
        if (c == null) throw new CollegeSystemException("Committee not found.");
        Lecturer l = findLecturerById(newChairmanId);
        if (l == null) throw new CollegeSystemException("Lecturer not found.");
        if (!(l instanceof Doctor)) {
            throw new CollegeSystemException("New chairman must be a PhD or Professor.");
        }

        c.setChairman((Doctor) l);
    }

    public void removeMemberFromCommittee(int lecturerId, String committeeName) throws CollegeSystemException {
        Lecturer l = findLecturerById(lecturerId);
        if (l == null) throw new CollegeSystemException("Lecturer not found.");
        Committee c = findCommitteeByName(committeeName);
        if (c == null) throw new CollegeSystemException("Committee not found.");

        c.removeMember(l);
    }

    public void addLecturerToDepartment(int lecturerId, String deptName) throws CollegeSystemException {
        Lecturer l = findLecturerById(lecturerId);
        if (l == null) throw new CollegeSystemException("Lecturer not found.");
        Department d = findDepartmentByName(deptName);
        if (d == null) throw new CollegeSystemException("Department not found.");

        d.addLecturer(l);
    }

    public void cloneCommittee(String committeeName) throws CollegeSystemException {
        Committee c = findCommitteeByName(committeeName);
        if (c == null) throw new CollegeSystemException("Committee not found.");
        Committee cloned = c.clone();
        if (cloned == null) throw new CollegeSystemException("Failed to clone committee.");
        
        if (getCommitteesCount() == getCommittees().length) expandCommittees();
        getCommittees()[getCommitteesCount()] = cloned;
        setCommitteesCount(getCommitteesCount() + 1);
    }

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
        for (int i = 0; i < getLecturersCount(); i++) res += getLecturers()[i].toString() + "\n\n";
        return res;
    }

    public String getAllCommittees() {
        if (getCommitteesCount() == 0) return "There are no committees in the college.";
        String res = "";
        for (int i = 0; i < getCommitteesCount(); i++) res += getCommittees()[i].toString() + "\n\n";
        return res;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        College other = (College) obj;
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return "College name: " + getName() + " | Number of lecturers: " + getLecturersCount() + " | "
                + "Number of departments: " + getDepartmentsCount() + " | Number of committees: " + getCommitteesCount();
    }
}