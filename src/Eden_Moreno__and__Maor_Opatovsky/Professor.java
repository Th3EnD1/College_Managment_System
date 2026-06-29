package Eden_Moreno__and__Maor_Opatovsky;

import java.io.Serializable;

public class Professor extends Doctor implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String awardingBody;

    public Professor(String name, String degreeName, double salary, Department department, String awardingBody){
        super(name, degreeName, salary, department);
        setAwardingBody(awardingBody);
        setDegreeType(eDegree.PROFESSOR); // Override degree type for Professor
    }

    public String getAwardingBody() { return this.awardingBody; }
    public void setAwardingBody(String awardingBody) {this.awardingBody = awardingBody;}

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Professor other = (Professor) obj;
        return getAwardingBody().equals(other.getAwardingBody());
    }

    @Override
    public String toString() {
        return super.toString() + " | Awarding Body: " + getAwardingBody();
    }
}