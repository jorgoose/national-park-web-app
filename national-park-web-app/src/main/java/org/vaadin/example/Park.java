package org.vaadin.example;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Represents a singular National Park
 * @author Logan Jorgensen
 */
public class Park {

    /**
     * A String to contain the park's name
     */
    private String name;

    /**
     * A String to contain the park's
     */
    private String parkCode;

    /**
     * A String to contain a brief description of the park
     */
    private String parkDescription;

    /**
     * An ArrayList to contain a list of the different activities available at the park, where each activity is denoted
     * as an individual String
     */
    private ArrayList<String> parkActivities = new ArrayList<String>();

    /**
     * A String to contain the states that the park occupies
     */
    private String states;

    /**
     * A ArrayList to contain the park's associated image URls, where each URL is denoted as an individual string
     */
    private ArrayList<String> parkImageLinks = new ArrayList<String>();


    /**
     * Creates a new Park object using provided objects passed into method.
     * @param  parkName A string of the park's name
     * @param parkCode A string of the park's 4 digit abbreviation code
     * @param parkDescription A string of the park's basic description
     * @param parkActivities An ArrayList of the park's available activities
     * @param states An ArrayList of the state(s) the park is located within
     * @param parkImageLinks An ArrayList of image links for the park
     */
    public Park(String parkName, String parkCode, String parkDescription, ArrayList<String> parkActivities, String states, ArrayList<String> parkImageLinks) {
        setName(parkName);
        setParkCode(parkCode);
        setParkDescription(parkDescription);
        setParkActivities(parkActivities);
        setStates(states);
        setParkImageLinks(parkImageLinks);
    }

    /**
     * Gets the Park object's name
     * @return name The parks' name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Park object's name
     * @param givenName The givenName as provided in the Park objects constructor
     */
    public void setName(String givenName) {
        name = givenName;
    }

    /**
     * Gets the Park object's park code
     * @return parkCode A four letter code associated with the park
     */
    public String getParkCode() {
        return parkCode;
    }

    /**
     * Sets the Park object's park code
     * @param givenParkCode A provided four letter String passed into the Park objects constructor
     */
    public void setParkCode(String givenParkCode) {
        parkCode = givenParkCode;
    }

    /**
     *  Gets the Park's description
     * @return parkDescription A string that contains a description of the park
     */
    public String getParkDescription() {
        return parkDescription;
    }

    /**
     * Set the Park's description
     * @param givenParkDesc
     */
    public void setParkDescription(String givenParkDesc) {
        parkDescription = givenParkDesc;
    }

    /**
     * Get the list of the activities available at the current park
     * @return givenParkDesc
     */
    public ArrayList<String> getParkActivities() {
        return parkActivities;
    }

    /**
     * Sets the park's list of activities
     * @param givenParkActivities
     */
    public void setParkActivities(ArrayList<String> givenParkActivities) {
        parkActivities = givenParkActivities;
    }

    /**
     * Gets the park's String of states that it occupies
     * @return states A string which contains all the states the current Park object occupies
     */
    public String getStates() {
        return states;
    }

    /**
     * Sets the parks String of states that it occupies
     * @param givenStates
     */
    public void setStates(String givenStates) {
        states = givenStates;
    }

    /**
     * Gets a list of the Park's image links
     * @return parkImageLinks
     */
    public ArrayList<String> getParkImageLinks() {
        return parkImageLinks;
    }

    /**
     * Sets the array of the Park's image links
     * @param givenParkImageLinks A list of given image links for the park, passed into the Park constructor
     */
    public void setParkImageLinks(ArrayList<String> givenParkImageLinks) {
        parkImageLinks = givenParkImageLinks;
    }

}
