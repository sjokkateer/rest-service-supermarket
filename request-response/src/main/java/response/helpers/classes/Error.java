package response.helpers.classes;

import java.util.List;

public class Error {
    private List<String> errors;

    public Error() {
        super();
    }

    public Error(List<String> errors) {
        this();
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getErrorMessage() {
        String result = "";

        for (String error: errors) {
            result += "- " + error + "\n";
        }

        return result;
    }
}
