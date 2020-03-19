package fontys.sot.rest.service.classes.validators;

public class UserNameValidator extends ValidatorBase {
    private String name;

    public UserNameValidator(String name) {
        this.name = name;

        validate();
    }

    protected void validate() {
        if (isNullOrEmpty(name)) {
            valid = false;
            errors.add("Name is required.");
        }
    }
}
