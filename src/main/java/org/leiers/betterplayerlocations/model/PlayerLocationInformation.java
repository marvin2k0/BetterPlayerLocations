package org.leiers.betterplayerlocations.model;

public class PlayerLocationInformation {
    private final String continent;
    private final String country;

    public PlayerLocationInformation(String continent, String country) {
        this.continent = continent;
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public String getCountry() {
        return country;
    }
}
