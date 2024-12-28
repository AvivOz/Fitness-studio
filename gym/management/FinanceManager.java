package gym.management;

import gym.customers.Client;
import gym.customers.Person;
import gym.sessions.Session;

import java.util.List;

public class FinanceManager {

    private double gymBalance;

    public FinanceManager() {
        this.gymBalance = 0;
    }

    private void validateAmount(double amount) {
        if (Double.isNaN(amount)) {
            throw new IllegalArgumentException("Amount must be a valid number.");
        }
    }

    public void adjustGymBalance(double amount) {
        validateAmount(amount);
        gymBalance += amount;
    }

    public double getGymBalance() {
        return gymBalance;
    }

    public void updatePersonBalance(Person person, double amount) {
        validateAmount(amount);
        person.setBalance(person.getBalance() + amount);
    }

    public void chargeClientForSession(Client client, double amount) {
        validateAmount(amount);
        if (client.getBalance() < amount) {
            throw new IllegalArgumentException("Client does not have enough balance.");
        }
        updatePersonBalance(client, -amount);
        adjustGymBalance(amount);
    }


    public void paySecretarySalary(Secretary secretary) {
        double salary = secretary.getSalary();
        validateAmount(salary);
        adjustGymBalance(-salary);
        updatePersonBalance(secretary, salary);
    }
//
//    private double calculateInstructorSalary(Instructor instructor, List<Session> sessionsList) {
//
//    }

    public void payInstructorSalary(List<Instructor> instructorList) {
        double sumToPay = 0;
        for (Instructor i : instructorList) {
            double sum = i.getSessionsCount() * i.getSalaryPerHour();
            sumToPay += sum;
            updatePersonBalance(i, sum);
            i.resetSessionCount();
        }
        adjustGymBalance(-sumToPay);
    }


}
