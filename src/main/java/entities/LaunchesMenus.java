package entities;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LaunchesMenus {
    NAME("NAME"),
    START_TIME("START TIME"),
    TOTAL("TOTAL"),
    PASSED("PASSED"),
    FAILED("FAILED"),
    SKIPPED("SKIPPED"),
    PRODUCT_BUG("PRODUCT BUG"),
    AUTO_BUG("AUTO BUG"),
    SYSTEM_ISSUE("SYSTEM ISSUE"),
    TO_INVESTIGATE("TO INVESTIGATE");

    private final String name;

    LaunchesMenus(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static List<String> values;

    public static List<String> getValues() {
        values = Stream.of(values())
                .map(LaunchesMenus::toString)
                .collect(Collectors.toList());
        return values;
    }
}
