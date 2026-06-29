package Eden_Moreno__and__Maor_Opatovsky;

import java.io.Serializable;
import java.util.ArrayList;

public class Doctor extends Lecturer implements Comparable<Doctor>, Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<String> articles;

    public Doctor(String name, String degreeName, double salary, Department department) {
        super(name, eDegree.PHD, degreeName, salary, department);
        setArticles(new ArrayList<>());
    }

    public int getArticlesCount() { return articles.size(); }
    public ArrayList<String> getArticles() { return this.articles; }
    public void setArticles(ArrayList<String> articles) { this.articles = articles; }

    public void addArticle(String articleName) {
        getArticles().add(articleName);
    }

    @Override
    public int compareTo(Doctor other) {
        return Integer.compare(getArticles().size(), other.getArticles().size());
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Doctor other = (Doctor) obj;
        return getArticles().size() == other.getArticles().size();
    }

    @Override
    public String toString() {
        return super.toString() + " | Number of Articles: " + getArticlesCount();
    }
}