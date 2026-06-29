package Eden_Moreno__and__Maor_Opatovsky;

import java.util.Comparator;

public class CommitteeMemberComparator implements Comparator<Committee> {
    @Override
    public int compare(Committee c1, Committee c2) {
        return Integer.compare(c1.getMembers().size(), c2.getMembers().size());
    }
}