package gym.sessions;

import gym.management.Instructor;

public class SessionFactory {

    public static Session createSession(SessionType sessionType, String sessionDate, ForumType forumType, Instructor instructor) {
        return switch (sessionType) {
            case Pilates -> new PilatesSession(sessionType, sessionDate, forumType, instructor);
            case MachinePilates -> new MachinePilatesSession(sessionType, sessionDate, forumType, instructor);
            case ThaiBoxing -> new ThaiBoxingSession(sessionType, sessionDate, forumType, instructor);
            case Ninja -> new NinjaSession(sessionType, sessionDate, forumType, instructor);
        };
    }
}
