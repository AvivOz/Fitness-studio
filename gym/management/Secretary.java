package gym.management;

import gym.exception.*;
import gym.customers.*;
import gym.strategy.*;
import gym.sessions.*;
import gym.notifications.MessageSystem;
import gym.util.DateUtils;

import java.util.List;

public class Secretary extends Person implements Employee{

    private double salary;
    private Gym gym;
    private boolean isActive = true;
    private static final int LEGAL_AGE = 18;
    private final MessageSystem messageSystem;
    private final FinanceManager financeManager;
    private final List<ValidationRule> ruleList;

    public Secretary(Person person, double salary, Gym gym) {
        super(person);
        this.salary = salary;
        this.gym = gym;
        this.messageSystem = gym.getMessageSystem();
        this.financeManager = gym.getFinanceManager();
        this.ruleList = gym.getValidationRules();
    }

    /**
     * Deactivate the current secretary to register a new one, it removes the permissions from the old secretary.
     */
    public void deActivate() {
        this.isActive = false;
    }

    /**
     * Check if the try of using the gym system is by the current secretary, if not throws exception.
     */
    public void isActive() {
        if (!this.isActive) {
            throw new NullPointerException("Error: Former secretaries are not permitted to perform actions");
        }
    }

    public Client registerClient(Person person) throws InvalidAgeException, DuplicateClientException {

        isActive();

        if (!isValidAge(person.getAge())) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }

        Client newClient = new Client(person);

        if (gym.getClientsList().contains(newClient)) {
            throw new DuplicateClientException("Error: The client is already registered");
        }

        gym.getClientsList().add(newClient);
        messageSystem.registerObserver(newClient);
        gym.getActionHistory().add("Registered new client: " + person.getName());

        return newClient;
    }

    public void unregisterClient(Client client) throws ClientNotRegisteredException {

        isActive();

        if (!gym.getClientsList().contains(client)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }

        gym.getClientsList().remove(client);
        messageSystem.unregisterObserver(client);
        gym.getActionHistory().add("Unregistered client: " + client.getName());
    }

    public Instructor hireInstructor(Person person, int hourlySalary, List<SessionType> certifiedSessionList) {

        isActive();

        Instructor newInstructor = new Instructor(person, hourlySalary, certifiedSessionList);
        gym.getInstructorsList().add(newInstructor);
        gym.getActionHistory().add("Hired new instructor: " + person.getName() + " with salary per hour: " + hourlySalary);

        return newInstructor;
    }

    public Session addSession(SessionType sessionType, String sessionDate, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException {

        isActive();

        isCertify(instructor, sessionType);

        Session newSession = SessionFactory.createSession(sessionType, sessionDate, forumType, instructor);
        gym.getSessionsList().add(newSession);
        instructor.increaseSessionCount();

        String formattedDate = DateUtils.formatISODate(DateUtils.parseDate(sessionDate));

        gym.getActionHistory().add("Created new session: " + sessionType + " on " + formattedDate + " with instructor: " + instructor.getName());

        return newSession;
    }

    public void isCertify(Instructor instructor, SessionType sessionType) throws InstructorNotQualifiedException {
         if (!instructor.getCertifiedSession().contains(sessionType)) {
             throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
         }
    }

    public void registerClientToLesson(Client client, Session session) throws ClientNotRegisteredException, DuplicateClientException {

        isActive();

        if (!gym.getClientsList().contains(client)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }

        if (session.isParticipant(client)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }

        for (ValidationRule rule : ruleList) {

            if (!rule.validate(client, session)) {
                gym.getActionHistory().add("Failed registration: " + rule.getErrorMessage());
                return;
            }
        }

        session.addParticipant(client);
        financeManager.chargeClientForSession(client, session.getPrice());
        gym.getActionHistory().add("Registered client: " + client.getName() + " to session: " + session.getSessionType() + " on " + session.getSessionDate() + " for price: " + session.getPrice());

    }

    /**
     * Valid the age of the person before registration as a client of the gym.
     * @param age the age of the person.
     * @return true if he old enough to register, otherwise false.
     */
    private boolean isValidAge(int age) {
        return age >= LEGAL_AGE;
    }

    /**
     * Send a message to all the clients.
     * @param message the message to send.
     */
    public void notify(String message) {
        isActive();

        gym.getMessageSystem().sendMessageToAllClients(message);
        gym.getActionHistory().add("A message was sent to all gym clients: " + message);
    }

    /**
     * Send a message to all the sessions in that date.
     * @param sessionDate the date of sessions.
     * @param message the message to send.
     */
    public void notify(String sessionDate, String message) {
        isActive();

        String formattedDate = DateUtils.formatISODateOnly(DateUtils.parseDate(sessionDate));

        gym.getMessageSystem().sendMessageToSessionClientsBySessionDate(gym.getSessionsList(), sessionDate, message);
        gym.getActionHistory().add("A message was sent to everyone registered for a session on " + formattedDate + " : " + message);
    }

    /**
     * Send a message to all the participants in a specific session.
     * @param session the session of the clients that will be notified.
     * @param message the message that will notify the clients.
     */
    public void notify(Session session, String message) {
        isActive();

        gym.getMessageSystem().sendMessageToSessionClients(session, message);
        gym.getActionHistory().add("A message was sent to everyone registered for session " + session.getSessionType() + " on " + session.getSessionDate() + " : " + message);
    }

    public void paySalaries() {
        isActive();
        financeManager.paySecretarySalary(this);
//        gym.getInstructorsList().forEach(instructor ->
//            financeManager.payInstructorSalary(instructor, gym.getSessionsList())
//        );
        financeManager.payInstructorSalary(gym.getInstructorsList());
        gym.getActionHistory().add("Salaries have been paid to all employees");
    }

    public void printActions() {
        isActive();
        gym.getActionHistory().forEach(System.out::println);
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %.0f | Role: %s | Salary per Month: %.0f",
                getId(), getName(), getGender(), getBirthDate(), getAge(), getBalance(), getRole(), getSalary());
    }

    @Override
    public String getRole() {
        return "Secretary";
    }
}
