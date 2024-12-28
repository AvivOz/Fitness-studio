package gym.strategy;

import gym.customers.Client;
import gym.sessions.Session;

public class PaymentValidationRule implements ValidationRule {

    @Override
    public boolean validate(Client client, Session session) {
        return client.getBalance() >= session.getPrice();
    }

    @Override
    public String getErrorMessage() {
        return "Client doesn't have enough balance";
    }
}
