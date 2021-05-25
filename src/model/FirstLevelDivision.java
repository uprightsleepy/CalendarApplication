package model;

public class FirstLevelDivision {
    private String name;
    private int countryID;

    public FirstLevelDivision(int countryID, String name) {
        this.name = name;
        this.countryID = countryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Override
    public String toString() {
        return name;
    }
}
