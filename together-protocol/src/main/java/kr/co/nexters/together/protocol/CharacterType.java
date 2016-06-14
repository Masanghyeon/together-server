package kr.co.nexters.together.protocol;

public enum CharacterType {
    NULBO("NULBO"),
    DONANG("DONANG"),
    NAMOONG("NAMOONG"),
    GGAPSOONG("GGAPSOONG"),
    UNKOWN("UNKOWN");

    private String value;
    CharacterType(String value) {
        this.value = value;
    }
}
