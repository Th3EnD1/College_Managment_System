package Eden_Moreno__and__Maor_Opatovsky;

public class Committee implements Cloneable {
    private String name;
    private Lecturer[] members;
    private Doctor chairman;
    private int membersCount;

    public Committee(String name, Doctor chairman) throws CollegeSystemException{
        setName(name);
        setChairman(chairman);
        setMembersCount(0);
        setMembers(new Lecturer[2]);
    }

    public void addMember(Lecturer l) throws CollegeSystemException {
        if (l.equals(getChairman())) {
            throw new CollegeSystemException("Lecturer is the chairman and cannot be added as a regular member.");
        }
        for (int i = 0; i < getMembersCount(); i++) {
            if (getMembers()[i].equals(l)) {
                throw new CollegeSystemException("Lecturer is already a member of this committee.");
            }
        }
        if (getMembersCount() == getMembers().length) {
            expandMembersArray();
        }
        getMembers()[getMembersCount()] = l;
        setMembersCount(getMembersCount() + 1);
        l.addCommittee(this);
    }

    public void removeMember(Lecturer l) throws CollegeSystemException {
        int index = -1;
        for (int i = 0; i < getMembersCount(); i++) {
            if (getMembers()[i].equals(l)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            l.removeCommittee(this);
            getMembers()[index] = getMembers()[getMembersCount() - 1];
            getMembers()[getMembersCount() - 1] = null;
            setMembersCount(getMembersCount() - 1);
        } else {
            throw new CollegeSystemException("Lecturer is not a member of this committee.");
        }
    }

    private void expandMembersArray() {
        Lecturer[] newArr = new Lecturer[getMembers().length * 2];
        for (int i = 0; i < getMembersCount(); i++) {
            newArr[i] = getMembers()[i];
        }
        setMembers(newArr);
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
            Committee cloned = (Committee) super.clone();
            cloned.setName(this.getName() + "-new");
            cloned.setMembers(new Lecturer[this.getMembers().length]);
            cloned.setMembersCount(0);
            
            // Add the same members
            for (int i = 0; i < this.getMembersCount(); i++) {
                try {
                    cloned.addMember(this.getMembers()[i]);
                } catch (CollegeSystemException e) {
                    // Ignore, logically shouldn't happen here
                }
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public Doctor getChairman() { return this.chairman; }
    public int getMembersCount() { return this.membersCount; }
    public void setMembersCount(int membersCount) { this.membersCount = membersCount; }
    public Lecturer[] getMembers() { return this.members; }
    public void setMembers(Lecturer[] members) { this.members = members; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Committee other = (Committee) obj;
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        String res = "Committee: " + name + " | Chairman: " + chairman.getName() + " | Number of members: " + getMembersCount();
        if (getMembersCount() > 0) {
            res += "\n  Committee members: ";
            for (int i = 0; i < getMembersCount(); i++) {
                res += getMembers()[i].getName() + (i < getMembersCount() - 1 ? ", " : "");
            }
        }
        return res;
    }
}