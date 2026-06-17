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
        for (int i = 0; i < c.getMembersCount(); i++) {
            if (c.getMembers()[i] instanceof Doctor) {
                sum += ((Doctor) c.getMembers()[i]).getArticlesCount();
            }
        }
        return sum;
    }
}
