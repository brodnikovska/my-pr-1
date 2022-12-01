package api_endpoints;

public enum User {
    USER("/v1/user/"),
    LOGIN("/v1/user/{login}");

    private final String name;

    User(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
