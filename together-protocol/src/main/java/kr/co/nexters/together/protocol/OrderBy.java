package kr.co.nexters.together.protocol;

public enum OrderBy {
    RECENTLY_CREATED("RECENTLY_CREATED"),
    REVERSE_RECENTLY_CREATED("REVERSE_RECENTLY_CREATED");


    private String value;
    OrderBy(String value) {
        this.value = value;
    }
}
