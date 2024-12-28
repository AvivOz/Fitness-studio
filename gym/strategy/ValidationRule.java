package gym.strategy;

import gym.customers.Client;
import gym.sessions.Session;

public interface ValidationRule  {

    boolean validate(Client client, Session session);

    String getErrorMessage();
}

