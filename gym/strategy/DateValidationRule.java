package gym.strategy;

import gym.customers.Client;
import gym.sessions.Session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateValidationRule implements ValidationRule {

    @Override
    public boolean validate(Client client, Session session) {
        return LocalDateTime.now().isBefore(session.getSessionDate());
    }

    @Override
    public String getErrorMessage() {
        return "Session is not in the future";
    }
}
