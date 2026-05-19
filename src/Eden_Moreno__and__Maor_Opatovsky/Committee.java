package Eden_Moreno__and__Maor_Opatovsky;

public class Committee {
    private String name;
    private Lecturer[] members;
    private Lecturer chairman;
    private int membersCount;

    public Committee(String name, Lecturer chairman) {
        setName(name);
        setChairman(chairman);
        setMembersCount(0);
        setMembers(new Lecturer[2]);
    }

    public boolean addMember(Lecturer l) {
        if (l == getChairman()) return false; // Chairman can't be a member
        for (int i = 0; i < getMembersCount(); i++) {
            if (getMembers()[i] == l) return false; // Already exists
        }
        if (getMembersCount() == getMembers().length) {
            expandMembersArray();
        }
        getMembers()[getMembersCount()] = l;
        setMembersCount(getMembersCount() + 1);
        l.addCommittee(this);
        return true;
    }

    public boolean removeMember(Lecturer l) {
        int index = -1;
        for (int i = 0; i < getMembersCount(); i++) {
            if (getMembers()[i] == l) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            l.removeCommittee(this);
            getMembers()[index] = getMembers()[getMembersCount() - 1];
            getMembers()[getMembersCount() - 1] = null;
            setMembersCount(getMembersCount() - 1);
            return true;
        }
        return false;
    }

    private void expandMembersArray() {
        Lecturer[] newArr = new Lecturer[getMembers().length * 2];
        for (int i = 0; i < getMembersCount(); i++) {
            newArr[i] = getMembers()[i];
        }
        setMembers(newArr);
    }

    public boolean setChairman(Lecturer newChairman) {
        if (newChairman.getDegreeType() != eDegree.PHD && newChairman.getDegreeType() != eDegree.PROFESSOR) {
            return false;
        }
        // If new chairman is a committee member, remove it
        removeMember(newChairman);
        this.chairman = newChairman;
        return true;
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public Lecturer getChairman() { return this.chairman; }
    public int getMembersCount() {return this.membersCount;}
    public void setMembersCount(int membersCount) {this.membersCount = membersCount;}
    public Lecturer[] getMembers() { return this.members; }
    public void setMembers(Lecturer[] members) { this.members = members; }


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
