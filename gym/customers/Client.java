package gym.customers;

import gym.notifications.Observer;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person implements Observer {

    private final List<String> notifications ;

    public Client(Person person) {
        super(person);
        this.notifications  = new ArrayList<>();
    }

    @Override
    public void update(String message) {
        this.notifications.add(message);
    }

    @Override
    public String getNotifications() {
        return notifications.toString();
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Gender: %s | Birthday: %s | Age: %s | Balance: %.0f",
                getId(), getName(), getGender(), getBirthDate(), getAge(), getBalance());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Client other = (Client) obj;
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
