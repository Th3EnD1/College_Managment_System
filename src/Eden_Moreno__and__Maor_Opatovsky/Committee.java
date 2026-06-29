package Eden_Moreno__and__Maor_Opatovsky;

import java.io.Serializable;
import java.util.ArrayList;

public class Committee implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private ArrayList<Lecturer> members;
    private Doctor chairman;
    private eCommitteeType type;

    public Committee(String name, Doctor chairman, eCommitteeType type) throws CollegeSystemException {
        setName(name);
        setChairman(chairman);
        setType(type);
        setMembers(new ArrayList<>());
    }

    public void addMember(Lecturer l) throws CollegeSystemException {
        if (l.equals(getChairman())) {
            throw new CollegeSystemException("Lecturer is the chairman and cannot be added as a regular member.");
        }
        if (getMembers().contains(l)) {
            throw new CollegeSystemException("Lecturer is already a member of this committee.");
        }

        // Enforce homogeneous degrees based on the committee type
        if (getType() == eCommitteeType.REGULAR && (l.getDegreeType() == eDegree.PHD || l.getDegreeType() == eDegree.PROFESSOR)) {
            throw new CollegeSystemException("This committee only accepts Regular (BA/MA) degrees.");
        }
        if (getType() == eCommitteeType.DOCTOR && l.getDegreeType() != eDegree.PHD) {
            throw new CollegeSystemException("This committee only accepts Doctor (PHD) degrees.");
        }
        if (getType() == eCommitteeType.PROFESSOR && l.getDegreeType() != eDegree.PROFESSOR) {
            throw new CollegeSystemException("This committee only accepts Professor degrees.");
        }

        members.add(l);
        l.addCommittee(this);
    }

    public void removeMember(Lecturer l) throws CollegeSystemException {
        if (members.remove(l)) {
            l.removeCommittee(this);
        } else {
            throw new CollegeSystemException("Lecturer is not a member of this committee.");
        }
    }

    public void setChairman(Doctor newChairman) throws CollegeSystemException {
        // If new chairman is a committee member, remove them from regular members
        try {
            removeMember(newChairman);
        } catch (CollegeSystemException e) {
            // It's okay if they weren't a member, ignore
        }
        this.chairman = newChairman;
    }

    @Override
    public Committee clone() {
        try {
            Committee clonedCommittee = (Committee) super.clone();
            clonedCommittee.setName(this.getName() + "-new");
            clonedCommittee.setMembers(new ArrayList<>());
            
            // Add the same members
            for (Lecturer l : this.getMembers()) {
                try {
                    clonedCommittee.addMember(l);
                } catch (CollegeSystemException e) {
                    // Logically shouldn't happen here as members are valid
                }
            }
            return clonedCommittee;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public Doctor getChairman() { return this.chairman; }
    public eCommitteeType getType() { return this.type; }
    public void setType(eCommitteeType type) { this.type = type; }
    public ArrayList<Lecturer> getMembers() { return this.members; }
    public void setMembers(ArrayList<Lecturer> members) { this.members = members; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Committee other = (Committee) obj;
        return this.getName().equals(other.getName());
    }

    @Override
    public String toString() {
        String res = "Committee: " + this.getName() + " (Type: " + this.getType() + ") | Chairman: " + this.getChairman().getName() + " | Number of members: " + this.getMembers().size();
        if (!this.getMembers().isEmpty()) {
            res += "\n  Committee members: ";
            for (int i = 0; i < this.getMembers().size(); i++) {
                res += this.getMembers().get(i).getName() + (i < this.getMembers().size() - 1 ? ", " : "");
            }
        }
        return res;
    }
}