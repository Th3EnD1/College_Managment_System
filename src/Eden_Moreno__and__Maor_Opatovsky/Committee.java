package Eden_Moreno__and__Maor_Opatovsky;

public class Committee {
    private String name;
    private Lecturer[] members;
    private Lecturer chairman;
    private int membersCount;

    public Committee(String name, Lecturer chairman) {
        this.name = name;
        this.chairman = chairman;
        this.members = new Lecturer[2];
        this.membersCount = 0;
    }

    public boolean addMember(Lecturer l) {
        if (l == chairman) return false; // Chairman can't be a member
        for (int i = 0; i < membersCount; i++) {
            if (members[i] == l) return false; // Already exists
        }
        if (membersCount == members.length) {
            expandMembersArray();
        }
        members[membersCount++] = l;
        l.addCommittee(this);
        return true;
    }

    public boolean removeMember(Lecturer l) {
        int index = -1;
        for (int i = 0; i < membersCount; i++) {
            if (members[i] == l) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            l.removeCommittee(this);
            members[index] = members[membersCount - 1];
            members[membersCount - 1] = null;
            membersCount--;
            return true;
        }
        return false;
    }

    private void expandMembersArray() {
        Lecturer[] newArr = new Lecturer[members.length * 2];
        for (int i = 0; i < membersCount; i++) {
            newArr[i] = members[i];
        }
        this.members = newArr;
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

    public String getName() { return name; }
    public Lecturer getChairman() { return chairman; }

    public String toString() {
        String res = "Committee: " + name + " | Chairman: " + chairman.getName() + " | Number of members: " + membersCount;
        if (membersCount > 0) {
            res += "\n  Committee members: ";
            for (int i = 0; i < membersCount; i++) {
                res += members[i].getName() + (i < membersCount - 1 ? ", " : "");
            }
        }
        return res;
    }
}
