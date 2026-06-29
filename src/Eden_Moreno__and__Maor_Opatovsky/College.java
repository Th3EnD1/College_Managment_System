package Eden_Moreno__and__Maor_Opatovsky;

import java.io.Serializable;
import java.util.ArrayList;

public class College implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private ArrayList<Lecturer> lecturers;
    private ArrayList<Department> departments;
    private ArrayList<Committee> committees;

    public College(String name) {
        setName(name);
        setLecturers(new ArrayList<>());
        setDepartments(new ArrayList<>());
        setCommittees(new ArrayList<>());
    }

    public ArrayList<Committee> getCommittees() { return this.committees; }
    public void setCommittees(ArrayList<Committee> committees) { this.committees = committees; }
    public ArrayList<Department> getDepartments() { return this.departments; }
    public void setDepartments(ArrayList<Department> departments) { this.departments = departments; }
    public ArrayList<Lecturer> getLecturers() { return this.lecturers; }
    public void setLecturers(ArrayList<Lecturer> lecturers) { this.lecturers = lecturers; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public Lecturer findLecturerById(int id) {
        for (Lecturer l : getLecturers()) {
            if (l.getId() == id) return l;
        }
        return null;
    }

    public Lecturer findLecturerByName(String name) {
        for (Lecturer l : getLecturers()) {
            if (l.getName().equals(name)) return l;
        }
        return null;
    }

    public Committee findCommitteeByName(String name) {
        for (Committee c : getCommittees()) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }

    public Department findDepartmentByName(String name) {
        for (Department d : getDepartments()) {
            if (d.getName().equals(name)) return d;
        }
        return null;
    }

    public void addLecturer(Lecturer l) throws CollegeSystemException {
        if (findLecturerById(l.getId()) != null) {
            throw new CollegeSystemException("Lecturer ID already exists.");
        }
        getLecturers().add(l);
    }

    public void addCommittee(String committeeName, int chairmanId, eCommitteeType type) throws CollegeSystemException {
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
        getCommittees().add(new Committee(committeeName, (Doctor) chairman, type));
    }

    public void addDepartment(Department d) throws CollegeSystemException {
        if (findDepartmentByName(d.getName()) != null) {
            throw new CollegeSystemException("Department name already exists.");
        }
        getDepartments().add(d);
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
        
        getCommittees().add(cloned);
    }

    public double getCollegeAverageSalary() {
        if (getLecturers().isEmpty()) return 0;
        double sum = 0;
        for (Lecturer l : getLecturers()) {
            sum += l.getSalary();
        }
        return sum / getLecturers().size();
    }

    public double getDepartmentAverageSalary(String deptName) {
        Department d = findDepartmentByName(deptName);
        if (d == null) return -1;
        return d.getAverageSalary();
    }

    public String getAllLecturers() {
        if (getLecturers().isEmpty()) return "There are no lecturers in the college.";
        StringBuilder res = new StringBuilder();
        for (Lecturer l : getLecturers()) {
            res.append(l.toString()).append("\n\n");
        }
        return res.toString();
    }

    public String getAllCommittees() {
        if (getCommittees().isEmpty()) return "There are no committees in the college.";
        StringBuilder res = new StringBuilder();
        for (Committee c : getCommittees()) {
            res.append(c.toString()).append("\n\n");
        }
        return res.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        College other = (College) obj;
        return getName().equals(other.getName());
    }

    @Override
    public String toString() {
        return "College name: " + getName() + " | Number of lecturers: " + getLecturers().size() + " | "
                + "Number of departments: " + getDepartments().size() + " | Number of committees: " + getCommittees().size();
    }
}