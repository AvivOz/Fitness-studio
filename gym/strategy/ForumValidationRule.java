package gym.strategy;


import gym.customers.*;
import gym.sessions.*;


public class ForumValidationRule implements ValidationRule {

    private String errorMessage;

    @Override
    public boolean validate(Client client, Session session) {

        ForumType forumType = session.getForumType();

        switch (forumType) {

            case Male:
                if (client.getGender() != Gender.Male) {
                    errorMessage = "Client's gender doesn't match the session's gender requirements";
                    return false;
                }
                break;

            case Female:
                if (client.getGender() != Gender.Female) {
                    errorMessage = "Client's gender doesn't match the session's gender requirements";
                    return false;
                }
                break;

            case Seniors:
                if (client.getAge() < 65) {
                    errorMessage = "Client doesn't meet the age requirements for this session (Seniors)";
                    return false;
                }
                break;
        }

        errorMessage = null;
        return true;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage == null ? "" : errorMessage;
    }
}
