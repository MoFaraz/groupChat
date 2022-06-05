package thirdproject.groupchat.Controller;

import java.util.regex.Pattern;

public class RegEx {

    public static boolean usernameRegEx (String username) {
        return Pattern.matches("^[a-zA-Z0-9._-]{3,}$", username);
    }

    public static boolean passwordRegEx (String password) {
        return Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", password);
    }

    public static boolean emailRegEx (String email) {
        return Pattern.matches("^(.+)@(.+)$", email);
    }
}
