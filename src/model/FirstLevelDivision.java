package model;

/**
 * The type First level division.
 */
public class FirstLevelDivision {
    private String name;
    private int countryID;

    /**
     * Instantiates a new First level division.
     *
     * @param countryID the country id
     * @param name      the name
     */
    public FirstLevelDivision(int countryID, String name) {
        this.name = name;
        this.countryID = countryID;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets country id.
     *
     * @param countryID the country id
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Override
    public String toString() {
        return name;
    }
}
