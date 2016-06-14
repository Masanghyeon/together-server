package kr.co.nexters.together.protocol;

public enum ArticleState {
    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE"),
    NONE("NONE");

    private String value;
    ArticleState(String value) {
        this.value = value;
    }
}
