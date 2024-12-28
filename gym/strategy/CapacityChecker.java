package gym.strategy;

import gym.customers.Client;
import gym.sessions.Session;

public class CapacityChecker implements ValidationRule {

    @Override
    public boolean validate(Client client, Session session) {
        return session.getParticipants().size() < session.getCapacity();
    }

    @Override
    public String getErrorMessage() {
        return "No available spots for session";
    }
}

