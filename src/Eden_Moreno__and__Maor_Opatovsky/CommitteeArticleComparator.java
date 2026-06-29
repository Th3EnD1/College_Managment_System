package Eden_Moreno__and__Maor_Opatovsky;

import java.util.Comparator;

public class CommitteeArticleComparator implements Comparator<Committee> {
    @Override
    public int compare(Committee c1, Committee c2) {
        return Integer.compare(getCommitteeArticlesCount(c1), getCommitteeArticlesCount(c2));
    }

    private int getCommitteeArticlesCount(Committee c) {
        int sum = 0;
        // Include chairman articles
        if (c.getChairman() != null) {
            sum += c.getChairman().getArticlesCount();
        }
        // Include regular members (if they are Doctors)
        for (Lecturer l : c.getMembers()) {
            if (l instanceof Doctor) {
                sum += ((Doctor) l).getArticlesCount();
            }
        }
        return sum;
    }
}