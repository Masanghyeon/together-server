package kr.co.nexters.together.protocol;

public enum FacebookStatus {
    NONE("NONE"),
    OPEN("OPEN"),
    CLOSE("CLOSE");

    private final String value;
    FacebookStatus(String value) {
        this.value= value;
    }
}
