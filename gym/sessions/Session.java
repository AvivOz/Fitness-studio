package gym.sessions;

import gym.customers.Client;
import gym.management.Instructor;
import gym.util.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class Session {

    private final SessionType sessionType;
    private final LocalDateTime sessionDate;
    private final ForumType forumType;
    private final Instructor instructor;
    private final List<Client> participants;

    public Session(SessionType sessionType, String sessionDate, ForumType forumType, Instructor instructor) {
        this.sessionType = sessionType;
        this.sessionDate = DateUtils.parseDate(sessionDate);
        this.forumType = forumType;
        this.instructor = instructor;
        this.participants = new ArrayList<>();
    }

    public abstract int getPrice();
    public abstract int getCapacity();

    public SessionType getSessionType() {
        return sessionType;
    }

    public LocalDateTime getSessionDate() {
        return sessionDate;
    }

    public ForumType getForumType() {
        return forumType;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void addParticipant(Client client) {
        participants.add(client);
    }

    public boolean isParticipant(Client client) {
        return participants.contains(client);
    }

    public List<Client> getParticipants() {
        return List.copyOf(participants);
    }

    @Override
    public String toString() {
        return String.format("Session Type: %s | Date: %s | Forum: %s | Instructor: %s | Participants: %d/%d",
                sessionType, DateUtils.formatDateWithTime(sessionDate), forumType, instructor.getName(), participants.size(), getCapacity());
    }
}

