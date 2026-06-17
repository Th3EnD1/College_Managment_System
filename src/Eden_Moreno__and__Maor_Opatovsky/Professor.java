package Eden_Moreno__and__Maor_Opatovsky;

public class Professor extends Doctor{
    private String awardingBody;

    public Professor(String name, String degreeName, double salary, Department department, String awardingBody){
        super(name, degreeName, salary, department);
        this.awardingBody = awardingBody;
        setDegreeType(eDegree.PROFESSOR); // Override degree type for Professor
    }

    public String getAwardingBody() {
        return this.awardingBody;
    }

    public void setAwardingBody(String awardingBody) {
        this.awardingBody = awardingBody;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Professor other = (Professor) obj;
        return this.awardingBody.equals(other.awardingBody);
    }

    @Override
    public String toString() {
        return super.toString() + " | Awarding Body: " + awardingBody;
    }
}
