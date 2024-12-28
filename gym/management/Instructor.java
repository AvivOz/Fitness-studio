package gym.management;

import gym.customers.*;
import gym.sessions.SessionType;

import java.util.List;

public class Instructor extends Person implements Employee {

    private double SalaryPerHour;
    private final List<SessionType> certifiedSessionTypes;
    private int sessionsCount = 0;

    public Instructor(Person person, double SalaryPerHour, List<SessionType> certifiedSessionTypes) {
        super(person);
        this.SalaryPerHour = SalaryPerHour;
        this.certifiedSessionTypes = certifiedSessionTypes;
    }

    public double getSalaryPerHour() {
        return this.SalaryPerHour;
    }

    public List<SessionType> getCertifiedSession() {
        return this.certifiedSessionTypes;
    }

    public void increaseSessionCount(){
        this.sessionsCount++;
    }

    public void resetSessionCount(){
        this.sessionsCount = 0;
    }

    public int getSessionsCount() {
        return this.sessionsCount;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Gender: %s | Birthday: %s | Age: %s | Balance: %.0f | Role: %s | Salary per hour: %.0f | Certified Classes: %s",
                getId(), getName(), getGender(), getBirthDate(), getAge(), getBalance(), getRole(), getSalaryPerHour(), getCertifiedSession().toString().replaceAll("[\\[\\]]", ""));
    }

    @Override
    public String getRole() {
        return "Instructor";
    }
}

