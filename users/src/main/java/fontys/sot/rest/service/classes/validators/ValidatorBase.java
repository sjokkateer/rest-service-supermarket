package fontys.sot.rest.service.classes.validators;

import java.util.ArrayList;
import java.util.List;

abstract public class ValidatorBase {
    protected boolean valid = true;
    protected List<String> errors = new ArrayList<>();

    protected static boolean isNullOrEmpty(String value) {
        return value == null || value.equals("");
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isNotValid() {
        return !isValid();
    }

    public List<String> getErrors() {
        return errors;
    }
}
