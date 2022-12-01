package api_endpoints;

public enum Dashboard {
    DASHBOARD("/v1/{projectName}/dashboard"),
    DASHBOARD_ID("/v1/{projectName}/dashboard/{dashboardId}");

    private final String name;

    Dashboard(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
