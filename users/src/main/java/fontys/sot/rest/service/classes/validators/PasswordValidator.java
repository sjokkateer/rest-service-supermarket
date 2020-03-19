package fontys.sot.rest.service.classes.validators;

public class PasswordValidator extends ValidatorBase {
    private static final int MINIMUM_SIZE = 4;

    private String password;
    private String confirmPassword;

    public PasswordValidator(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;

        validate();
    }

    protected void validate() {
        if (isNullOrEmpty(password)) {
            valid = false;
            errors.add("Password is required.");
        }

        if (tooShort(password)) {
            valid = false;
            errors.add("Password needs to be >= "+ MINIMUM_SIZE +" characters.");
        }

        if (areNotEqual(password, confirmPassword)) {
            valid = false;
            errors.add("Passwords did not match.");
        }
    }

    private boolean tooShort(String password) {
        return password.length() < 4;
    }

    private boolean areNotEqual(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }
}
