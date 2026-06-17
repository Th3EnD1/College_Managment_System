package Eden_Moreno__and__Maor_Opatovsky;

public class Doctor extends Lecturer implements Comparable<Doctor> {
    private String[] articles;
    private int articlesCount;

    public Doctor(String name, String degreeName, double salary, Department department) {
        super(name, eDegree.PHD, degreeName, salary, department);
        this.articles = new String[2];
        articlesCount = 0;
    }

    public void addArticle(String articleName) {
        if (articlesCount == articles.length) {
            expandArticlesArray();
        }
        articles[articlesCount++] = articleName;
    }

    private void expandArticlesArray() {
        String[] newArr = new String[articles.length * 2];
        for (int i = 0; i < articlesCount; i++) {
            newArr[i] = articles[i];
        }
        articles = newArr;
    }

    public int getArticlesCount() {
        return this.articlesCount;
    }

    @Override
    public int compareTo(Doctor other) {
        return Integer.compare(this.articlesCount, other.articlesCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Doctor other = (Doctor) obj;
        return this.articlesCount == other.articlesCount;
    }

    @Override
    public String toString() {
        return super.toString() + " | Number of Articles: " + getArticlesCount();
    }
}
