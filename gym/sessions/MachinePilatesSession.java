package gym.sessions;

import gym.management.Instructor;

import java.time.LocalDateTime;

public class MachinePilatesSession extends Session {

    private static final int PRICE = 80;
    private static final int CAPACITY = 10;

    public MachinePilatesSession(SessionType sessionType, String sessionDate, ForumType forumType, Instructor instructor) {
        super(sessionType, sessionDate, forumType, instructor);
    }

    @Override
    public int getPrice() {
        return PRICE;
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

}
