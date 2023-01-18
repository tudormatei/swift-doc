package ro.tudormatei.backend.model;

public class UserData {
    private final String email;
    private final String jsonPath;

    public UserData(String email, String jsonPath) {
        this.email = email;
        this.jsonPath = jsonPath;
    }

    public String getEmail() {
        return email;
    }

    public String getJsonPath() {
        return jsonPath;
    }
}
