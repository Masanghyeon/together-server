package kr.co.nexters.together.protocol;

public enum ResponseStatus {
    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    private final String status;
    ResponseStatus(String status) {
        this.status = status;
    }
}
