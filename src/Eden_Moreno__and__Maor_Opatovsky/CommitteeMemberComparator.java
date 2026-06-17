package Eden_Moreno__and__Maor_Opatovsky;

import java.util.Comparator;

public class CommitteeMemberComparator implements Comparator<Committee> {
    @Override
    public int compare(Committee c1, Committee c2) {
        if (c1.getMembersCount() == c2.getMembersCount())
            return 0;
        else if (c1.getMembersCount() > c2.getMembersCount())
            return 1;
        else
            return -1;
    }

}
