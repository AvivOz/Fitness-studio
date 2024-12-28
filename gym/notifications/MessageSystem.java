package gym.notifications;

import gym.sessions.Session;
import gym.util.DateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageSystem {

    private static List<Observer> observers;

    public MessageSystem() {
        observers = new ArrayList<>();
    }

    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unregisterObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    public void sendMessageToAllClients(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void sendMessageToSessionClients(Session session, String message) {
        session.getParticipants().forEach(p -> p.update(message));
    }

    public void sendMessageToSessionClientsBySessionDate(List<Session> sessions, String date, String message) {

        LocalDateTime parsedDate = DateUtils.parseDate(date);

        sessions.stream()
                .filter(session -> session.getSessionDate().toLocalDate().equals(parsedDate.toLocalDate()))
                .forEach(session -> sendMessageToSessionClients(session, message));
    }

}
