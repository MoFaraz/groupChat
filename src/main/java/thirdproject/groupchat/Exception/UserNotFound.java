package thirdproject.groupchat.Exception;

public class UserNotFound extends Throwable {

    public UserNotFound () {
        super("User not found");
    }

    public UserNotFound (String message) {
        super(message);
    }
}
