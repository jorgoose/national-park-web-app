package org.vaadin.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Represents a singular instance of an API Fetcher tool, which is used to retrieve data from the National Park Service
 * API
 * @author Logan Jorgensen
 */
public class APIFetcher {

    /** An HTTP Url connection that will be used to read in park activities from the NPS API. */
    private static HttpURLConnection activityConnection;

    /** An HTTP Url connection that will be used to read in all national parks from the NPS API. */
    private static HttpURLConnection parkConnection;

    /** An HTTP Url connection that will be used to read basic webcam data from the NPS API. */
    private static HttpURLConnection webcamConnection;

    /** An HTTP Url connection that will be used to read updated webcam data from the NPS API. */
    private static HttpURLConnection cameraIndividualConnection;

    /** A list of all possible activities at a national park, with each activity denoted as a String object */
    private static ArrayList<String> allActivities = new ArrayList<String>();

    /** A list of all national parks, denoted as Park objects */
    private static ArrayList<Park> allParks = new ArrayList<Park>();

    /** A list of all park webcams, denoted as WebCam objects */
    private static ArrayList<Webcam> webCams = new ArrayList<Webcam>();

    /**
     * Create an APIFetcher object by calling each "gather" methods (gatherActivites, gatherParks, gatherWebcams)
     * to gather up-to-date API data from the NPS API service using provided links in each method.
     */
    public APIFetcher() {
        gatherActivities();
        gatherParks();
        gatherWebcams();
        this.allActivities = allActivities;
        this.allParks = allParks;
    }

    /**
     * Returns the ArrayList allActivities, which is a list of every possible activity
     * @return allActivities An ArrayList of Strings of all the activities any given National Park could possibly
     * offer, where each string is the name of a single activity
     */
    public static ArrayList<String> getAllActivities() {
        return allActivities;
    }

    /**
     * Get a list of all parks
     * @return allParks, a list of all parks denoted as Park objects
     */
    public static ArrayList<Park> getAllParks() {
        return allParks;
    }

    /**
     * Get a list of all webcams
     * @return webCams, a list of all webCams denoted as WebCam objects
     */
    public static ArrayList<Webcam> getAllWebcams() {
        return webCams;
    }

    /**
     * Create a string and -- using a provided API url -- read in the raw, unparsed API data and assign this data
     * to said string. Then, feed this content in the parseActivities method, and assign the returned value to a new
     * array of Strings. Lastly, loop through this new array and add each activity in the array to allActivities,
     * and remove the first element, as it is an empty activity.
     */
    public static void gatherActivities() {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL activitiesURL = new URL("https://developer.nps.gov/api/v1/activities?limit=50&api_key=wO2UZtSoETP755sF8T0D2yU6vglXg7qInbUxIjfr");
            activityConnection = (HttpURLConnection) activitiesURL.openConnection();

            // Request setup
            activityConnection.setRequestMethod("GET");
            activityConnection.setConnectTimeout(5000);
            activityConnection.setReadTimeout(5000);

            int status = activityConnection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(activityConnection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(activityConnection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
            }
            String[] activitiesList = parseActivities(responseContent.toString());
            for (int i = 0; i < activitiesList.length; i++) {
                allActivities.add(activitiesList[i]);
            }
            allActivities.remove(0);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            activityConnection.disconnect();
        }
    }

    /** Creates an empty String array by splitting the given string, and then parse through said array and
     * adapt each section to proper formatting and truncation criteria.
     * @param rawString A String which contains un-parsed data from the API accessed within the gatherActivities
     * method.
     * @return activities A list that contains every activity, as found by the parsing process within the method.
     */
    public static String[] parseActivities(String rawString) {
        String[] activities = rawString.split("\"name\":\"");

        for (int activityIndex = 0; activityIndex < activities.length; activityIndex++) {
            int currentLetterIndex = 0;
            String truncatedActivity = "";
            while (activities[activityIndex].charAt(currentLetterIndex) != '\"') {
                truncatedActivity += activities[activityIndex].charAt(currentLetterIndex);
                currentLetterIndex++;
            }
            activities[activityIndex] = truncatedActivity;
        }
        return activities;
    }

    /**
     * Create a string and -- using a provided API url -- read in the raw, unparsed API data and assign this data
     * to said string. Then, feed this content in the parseParks method.
     */
    public static void gatherParks() {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL parksURL = new URL("https://developer.nps.gov/api/v1/parks?limit=500&api_key=wO2UZtSoETP755sF8T0D2yU6vglXg7qInbUxIjfr");
            parkConnection = (HttpURLConnection) parksURL.openConnection();

            // Request setup
            parkConnection.setRequestMethod("GET");
            parkConnection.setConnectTimeout(5000);
            parkConnection.setReadTimeout(5000);

            int status = parkConnection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(parkConnection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(parkConnection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
            }

            parseParks(responseContent.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            parkConnection.disconnect();
        }
    }

    /** Creates an empty String array by splitting the given string, and then parse through said array and
     * adapt each section to proper formatting and truncation criteria. Use specific information gathered from
     * relevant Strings to create Park objects, and then add these Park objects to the list of all parks (allParks)
     * @param rawString A String which contains un-parsed data from the API, which contains data for all national parks.
     * method.
     */
    public static void parseParks(String rawString) {
        String[] parksLines = rawString.split("\"fullName\":");

        for (int parkIndex = 1; parkIndex < parksLines.length; parkIndex++) {

            // Get name of the park from current parkLine
            String newParkName = "";

            int letterIndex = 0;
            while (parksLines[parkIndex].charAt(letterIndex) != ',') {
                newParkName += parksLines[parkIndex].charAt(letterIndex);
                letterIndex++;
            }
            newParkName = newParkName.substring(1, newParkName.length() - 4);

            parksLines[parkIndex] = parksLines[parkIndex].substring(letterIndex + 1);
            String[] newParkData = parksLines[parkIndex].split("\",\"");

            // Get image URLs for current park at index
            ArrayList<String> newImageURLs = new ArrayList<String>();
            for (int i = 0; i < newParkData.length; i++) {
                if (newParkData[i].substring(0, 3).equals("url")) {
                    String newUrl = "";
                    int startQuoteIndex = 6;
                    while (newParkData[i].charAt(startQuoteIndex) != '\"') {
                        newUrl += newParkData[i].charAt(startQuoteIndex);
                        startQuoteIndex++;
                    }
                    newImageURLs.add(newUrl);
                }
            }

            // Get park code from parkData for park at index.
            String newParkCode = newParkData[0].substring(12);

            // Get park description from current parkLine for park at index.
            String newParkDesc = newParkData[1].substring(14);
            String temp = "";
            for (int i = 0; i < newParkDesc.length(); i++) {
                if (newParkDesc.charAt(i) != '\\') {
                    temp += newParkDesc.charAt(i);
                }
            }
            newParkDesc = temp;


            // Get string with park activities from current parkLine, and parse through to add each activity to new activities list
            ArrayList<String> newParkActivities = new ArrayList<String>();

            // Start at index 4, which will always be the location of the first activity for each park
            int currentActivityInd = 4;
            int futureStateIndex = 0;
            while (newParkData[currentActivityInd].substring(0, 4).equals("name")) {
                String activityToAdd = "";
                int charIndex = 7;
                while (newParkData[currentActivityInd].charAt(charIndex) != '\"') {
                    activityToAdd += newParkData[currentActivityInd].charAt(charIndex);
                    charIndex++;
                }

                // Add new activity to list of activities for current park
                newParkActivities.add(activityToAdd);

                currentActivityInd++;
                futureStateIndex = charIndex + 13;
            }



            // Get park states from current parkLine
            String newParkStates = newParkData[currentActivityInd - 1].substring(futureStateIndex + 1);
            if (newParkStates.length() > 2) {
                newParkStates = newParkStates.substring(newParkStates.length() - 2);
            }

            Park parkToAdd = new Park(newParkName, newParkCode, newParkDesc, newParkActivities, newParkStates, newImageURLs);
            allParks.add(parkToAdd);

        }
    }

    /**
     * Create a string and -- using a provided API url -- read in the raw, un-parsed API data and assign this data
     * to said string. Because this method is meant to build out the webCams array, the method parses through the
     * gathered un-parsed string, and creates a set of webCam objects, each of which are added to the webCams array
     * after construction.
     */
    public static void gatherWebcams() {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL webcamsURL = new URL("https://developer.nps.gov/api/v1/webcams?limit=200&api_key=wO2UZtSoETP755sF8T0D2yU6vglXg7qInbUxIjfr");
            webcamConnection = (HttpURLConnection) webcamsURL.openConnection();

            // Request setup
            webcamConnection.setRequestMethod("GET");
            webcamConnection.setConnectTimeout(5000);
            webcamConnection.setReadTimeout(5000);

            int status = webcamConnection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(webcamConnection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(webcamConnection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
            }

            webCams.clear();

            String[] unparsedCamsWebsites = responseContent.toString().split("\"id\"");
            // Create webcam objects by parsing through each line of data and reading information into
            //  Webcam constructor.
            for (int i = 1; i < unparsedCamsWebsites.length; i++) {
                int parseStartIndex = 47;
                String newCamWebsiteUrl = "";
                while (unparsedCamsWebsites[i].charAt(parseStartIndex) != '\"') {
                    newCamWebsiteUrl += unparsedCamsWebsites[i].charAt(parseStartIndex);
                    parseStartIndex++;
                }
                while (unparsedCamsWebsites[i].charAt(parseStartIndex) != ':') {
                    parseStartIndex++;
                }
                parseStartIndex += 2;

                String newWebcamName = "";
                while (unparsedCamsWebsites[i].charAt(parseStartIndex) != '\"') {
                    newWebcamName += unparsedCamsWebsites[i].charAt(parseStartIndex);
                    parseStartIndex++;
                }

                parseStartIndex = unparsedCamsWebsites[i].indexOf("parkCode") + 11;
                String newCamParkCode = unparsedCamsWebsites[i].substring(parseStartIndex, parseStartIndex + 4);

                String newCamParkName = "";
                parseStartIndex = unparsedCamsWebsites[i].indexOf("fullName\":") + 11;
                while (unparsedCamsWebsites[i].charAt(parseStartIndex) != '\"') {
                    newCamParkName += unparsedCamsWebsites[i].charAt(parseStartIndex);
                    parseStartIndex++;
                }

                String newCamStatus = "";
                parseStartIndex = unparsedCamsWebsites[i].indexOf("status\":") + 9;
                while (unparsedCamsWebsites[i].charAt(parseStartIndex) != '\"') {
                    newCamStatus += unparsedCamsWebsites[i].charAt(parseStartIndex);
                    parseStartIndex++;
                }

                Webcam camToAdd = new Webcam(newCamWebsiteUrl, newWebcamName, newCamParkCode, newCamParkName, newCamStatus);
                webCams.add(camToAdd);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            webcamConnection.disconnect();
        }
    }

    /**
     * Given the name of a specified national park, the method created a filteredImageLink String that will contain
     * the final filtered link to the desired image captured from the given national park. The method iterates
     * through the webCams array and attempts to find a WebCam object that both is active and is associated with
     * the given park.
     * @param givenParkName The name of a specified national park.
     * @return filteredImageLink A String which contains the parsed, filtered version of webcam associated with the
     * given park
     */
    public static String getCurrentWebcamLink(String givenParkName) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        String filteredImageLink = "";
        try {
            String parkCamUrl = "";
            int webCamSetNum = 0;

            for (int webcamNum = 0; webcamNum < webCams.size(); webcamNum++) {
                if (webCams.get(webcamNum).getRelatedPark().equals(givenParkName) && (!webCams.get(webcamNum).getWebcamStatus().equals("Inactive"))) {
                    parkCamUrl = webCams.get(webcamNum).getImageLink();
                    webCamSetNum = webcamNum;
                    break;
                }
            }
            if ((!parkCamUrl.equals("")) && (!webCams.get(webCamSetNum).getWebcamStatus().equals("Inactive"))) {
                URL parksURL = new URL(parkCamUrl);
                cameraIndividualConnection = (HttpURLConnection) parksURL.openConnection();

                // Request setup
                cameraIndividualConnection.setRequestMethod("GET");
                cameraIndividualConnection.setConnectTimeout(5000);
                cameraIndividualConnection.setReadTimeout(5000);

                int status = cameraIndividualConnection.getResponseCode();

                if (status > 299) {
                    reader = new BufferedReader(new InputStreamReader(cameraIndividualConnection.getErrorStream()));
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                    reader.close();
                } else {
                    reader = new BufferedReader(new InputStreamReader(cameraIndividualConnection.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                }

                int startLinkParseIndex = responseContent.toString().indexOf("webcamRefreshImage") + 24;
                String unfilteredImageLink = "";
                while (responseContent.toString().charAt(startLinkParseIndex) != '\"') {
                    unfilteredImageLink += responseContent.toString().charAt(startLinkParseIndex);
                    ++startLinkParseIndex;
                }

                unfilteredImageLink = unfilteredImageLink.substring(5);
                for (int i = 0; i < unfilteredImageLink.length(); i++) {
                    if (unfilteredImageLink.charAt(i) == '&') {
                        i += 5;
                        filteredImageLink += '/';
                        continue;
                    }
                    if (filteredImageLink.charAt(0) == '/') {
                        filteredImageLink = filteredImageLink.substring(1);
                    }
                    filteredImageLink += unfilteredImageLink.charAt(i);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            parkConnection.disconnect();
            return filteredImageLink;
        }
    }
}
