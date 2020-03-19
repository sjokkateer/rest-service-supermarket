package fontys.sot.rest.service.classes.validators;

import java.util.ArrayList;
import java.util.List;

public class UserValidator extends ValidatorBase {
    private UserNameValidator userNameValidator;
    private PasswordValidator passwordValidator;

    public UserValidator(String name, String password, String confirmPassword) {
        userNameValidator = new UserNameValidator(name);
        passwordValidator = new PasswordValidator(password, confirmPassword);
    }

    public boolean isValid() {
        return userNameValidator.isValid() && passwordValidator.isValid();
    }

    public List<String> getErrors() {
        List<String> collectiveOfErrors = new ArrayList<>();
        collectiveOfErrors.addAll(userNameValidator.getErrors());
        collectiveOfErrors.addAll(passwordValidator.getErrors());

        return collectiveOfErrors;
    }
}
