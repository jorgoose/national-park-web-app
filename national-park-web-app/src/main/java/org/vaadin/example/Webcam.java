package org.vaadin.example;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Represents a singular park webcam
 * @author Logan Jorgensen
 */
public class Webcam {
    /**
     * A string to contain the webcam's name
     */
    private String webcamName;

    /**
     * A string to contain the webcam's associated park code
     */
    private String parkCode;

    /**
     * A string to contain the name of the webcam's associated park
     */
    private String relatedPark;

    /**
     * A string to contain the link to the webcam's associated image
     */
    private String imageLink;

    /**
     * A String to contain the status of the webcam (must be "Active" or "Inactive")
     */
    private String webcamStatus;

    /**
     * Creates a new Webcam object
     * @param givenImageLink
     * @param givenWebcamName
     * @param givenParkCode
     * @param givenRelatedPark
     * @param givenWebcamStatus
     */
    public Webcam(String givenImageLink, String givenWebcamName, String givenParkCode, String givenRelatedPark, String givenWebcamStatus) {
        setImageLink(givenImageLink);
        setName(givenWebcamName);
        setParkCode(givenParkCode);
        setRelatedPark(givenRelatedPark);
        setWebcamStatus(givenWebcamStatus);
    }

    /**
     * Get the webcam's name
     * @return webcamName A String containing the name of the webcam
     */
    public String getName() {
        return webcamName;
    }

    /**
     * Set the webcam's name
     * @param  givenName A String containing a name to be set at the webcam's name
     */
    public void setName(String givenName) {
        webcamName = givenName;
    }

    /**
     * Get the park code of the webcam's associated park
     * @return parkCode A String containing the webcam's associated park code.
     */
    public String getParkCode() {
        return parkCode;
    }

    /**
     * Get the park code of the webcam's associated park
     * @param givenCode A String containing a park code to be set as the webcam's new associated park code.
     */
    public void setParkCode(String givenCode) {
        parkCode = givenCode;
    }

    /**
     * Get the name of the webcam's associated park
     * @return relatedPark A String containing the name of the webcam's associated park
     */
    public String getRelatedPark() {
        return relatedPark;
    }

    /**
     * Sets the name of the webcam's associated park
     * @param givenRelatedPark A String which contains the name of a park to be set to the webcam's associated park
     */
    public void setRelatedPark(String givenRelatedPark) {
        relatedPark = givenRelatedPark;
    }

    /**
     * Gets the image link for the Webcam Object
     * @return imageLink A String which contains the URL for the webcam's associated image
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * Sets the image link for the Webcam Object
     * @param givenImageLink A String which contains a URL to an image to be set as the webcam's
     * new associated image
     */
    public void setImageLink(String givenImageLink) {
        imageLink = givenImageLink;
    }

    /**
     * Gets the status of the Webcam object, either "Active" or "Inactive"
     * @return webcamStatus A String, either "Active" or "Inactive", that denotes the status of the current Webcam
     * Object
     */
    public String getWebcamStatus() {
        return webcamStatus;
    }

    /**
     * Set the status of the Webcam object, which is either "Active" or "Inactive"
     * @param givenStatus A String, either "Active" or "Inactive", that denotes the status of the webcam (fed into the
     * constructor)
     */
    public void setWebcamStatus(String givenStatus) {
        webcamStatus = givenStatus;
    }
}