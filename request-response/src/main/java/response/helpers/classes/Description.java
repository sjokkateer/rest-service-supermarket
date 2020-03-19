package response.helpers.classes;

public class Description {
    private String description;

    public Description() {
        super();
    }

    public Description(String content) {
        this();
        description = content;
    }

    public static Description createSuccess(String resource, int id, String action) {
        return create(resource + " with id (" + id + ") was successfully " + action + ".");
    }

    public static Description createNotFound(String resource, String field, int value) {
        return create(resource + " with " + field + " (" + value + ") was not found.");
    }

    public static Description create(String descriptionContent) {
        return new Description(descriptionContent);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
