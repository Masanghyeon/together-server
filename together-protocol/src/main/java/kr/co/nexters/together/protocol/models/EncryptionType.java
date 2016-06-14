package kr.co.nexters.together.protocol.models;

public enum EncryptionType {
    PBKDF2("PBKDF2");

    private final String type;
    EncryptionType(String type) {
        this.type = type;
    }
}
