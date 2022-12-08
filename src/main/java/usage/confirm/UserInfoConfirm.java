package usage.confirm;

import java.util.Objects;

public class UserInfoConfirm {

    private static final UserInfoConfirm INSTANCE = new UserInfoConfirm();

    public static UserInfoConfirm getInstance() {
        return INSTANCE;
    }

    private UserInfoConfirm() {
    }

    private static final String USERNAME_FORMAT_REGEX = "[a-z]{4,15}";
    private static final String NAME_FORMAT_REGEX = ".{2,30}";
    private static final String EMAIL_FORMAT_REGEX = "[a-z][[a-z][0-9][-][_]]{3,25}[@][a-z]{2,10}[.][a-z]{2,4}";
    private static final String PASSWORD_FORMAT_REGEX = ".{4,28}";

    public boolean validate(String email) {
        return email.matches(EMAIL_FORMAT_REGEX);
    }

    public boolean validate(String username, String password) {
//        return username.matches(USERNAME_FORMAT_REGEX) &&
//                password.matches(PASSWORD_FORMAT_REGEX);
        return username.matches(USERNAME_FORMAT_REGEX) &&
                Objects.nonNull(password) && !password.isEmpty();
    }

    public boolean validate(String firstName, String lastName, String email) {
        return firstName.matches(NAME_FORMAT_REGEX) &&
                lastName.matches(NAME_FORMAT_REGEX) &&
                email.matches(EMAIL_FORMAT_REGEX);
    }

    public boolean validate(String username,
                            String firstName,
                            String lastName,
                            String email,
                            String password
            , String confirmedPassword) {
        return username.matches(USERNAME_FORMAT_REGEX) &&
                firstName.matches(NAME_FORMAT_REGEX) &&
                lastName.matches(NAME_FORMAT_REGEX) &&
                email.matches(EMAIL_FORMAT_REGEX) &&
                password.matches(PASSWORD_FORMAT_REGEX) &&
                password.equals(confirmedPassword);
    }

    public boolean validatePasswords(String currentPassword, String newPassword, String confirmedPassword) {
        return currentPassword.matches(PASSWORD_FORMAT_REGEX) &&
                newPassword.matches(PASSWORD_FORMAT_REGEX) &&
                confirmedPassword.matches(PASSWORD_FORMAT_REGEX) &&
                newPassword.equals(confirmedPassword);
    }
}

