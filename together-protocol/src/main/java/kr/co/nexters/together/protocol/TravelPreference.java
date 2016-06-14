package kr.co.nexters.together.protocol;

public enum TravelPreference {
    SPORTS("SPORTS"),
    PHOTO("PHOTO"),
    CONCERT("CONCERT"),
    FOOD("FOOD"),
    REST("REST"),
    DRINK("DRINK"),
    MUSEUM("MUSEUM"),
    ENTERTAINMENT("ENTERTAINMENT");

    private final String value;
    TravelPreference(String value) {
        this.value= value;
    }
}
