package gym.management;

import gym.customers.*;
import gym.sessions.*;
import gym.notifications.MessageSystem;
import gym.strategy.*;

import java.util.ArrayList;
import java.util.List;

public class Gym {

    private static Gym instance = null;
    private String gymName;
    private Secretary currentSecretary;
    private final List<String> actionHistory;
    private final FinanceManager financeManager;
    private final MessageSystem messageSystem;
    private final List<Client> clientsList;
    private final List<Instructor> instructorsList;
    private final List<Employee> employeesList;
    private final List<Session> sessionsList;
    private final List<ValidationRule> validationRules;

    private Gym() {
        this.actionHistory = new ArrayList<>();
        this.financeManager = new FinanceManager();
        this.messageSystem = new MessageSystem();
        this.clientsList = new ArrayList<>();
        this.instructorsList = new ArrayList<>();
        this.employeesList = new ArrayList<>();
        this.sessionsList = new ArrayList<>();
        this.validationRules = List.of(
                new DateValidationRule(),
                new ForumValidationRule(),
                new CapacityChecker(),
                new PaymentValidationRule()
        );
    }

    public static Gym getInstance() {
        if (instance == null) {
            return new Gym();
        }
        return instance;
    }

    public void setSecretary(Person person, double salary) {

        if (currentSecretary != null) {
            currentSecretary.deActivate();
        }
        currentSecretary = new Secretary(person, salary,this);
        String str = "A new secretary has started working at the gym: " + person.getName();
        actionHistory.add(str);
    }

    /**
     * Get the current secretary of the GYM.
     * @return the current secretary of the GYM.
     */
    public Secretary getSecretary() {
        return currentSecretary;
    }

    /**
     * Set a name for the gym.
     * @param name the name to set for the gym.
     */
    public void setName(String name) {
        gymName = name;
    }

    public List<String> getActionHistory() {
        return actionHistory;
    }

    public FinanceManager getFinanceManager() {
        return financeManager;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public List<Client> getClientsList() {
        return clientsList;
    }

    public List<Instructor> getInstructorsList() {
        return instructorsList;
    }

    public List<Employee> getEmployeesList() {
        List<Employee> employees = new ArrayList<>();
        employees.addAll(instructorsList);

        if (currentSecretary != null) {
            employees.add(currentSecretary);
        }
        return employees;
    }

    public List<Session> getSessionsList() {
        return sessionsList;
    }

    public List<ValidationRule> getValidationRules() {
        return validationRules;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Gym Name: ").append(gymName).append("\n");
        sb.append("Gym Secretary: ").append(currentSecretary).append("\n");
        sb.append("Gym Balance: ").append((int) financeManager.getGymBalance()).append("\n\n");

        sb.append("Clients Data:\n");
        clientsList.forEach(client -> sb.append(client).append("\n"));
        sb.append("\n");

        sb.append("Employees Data:\n");
        getEmployeesList().forEach(employee -> sb.append(employee).append("\n"));
        sb.append("\n");

        sb.append("Sessions Data:\n");
        sessionsList.forEach(session -> sb.append(session).append("\n"));
        sb.append("\n");

        return sb.toString();
    }
}
