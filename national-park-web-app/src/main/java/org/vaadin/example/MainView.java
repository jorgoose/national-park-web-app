package org.vaadin.example;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;

/**
 * Vaadin MainView class.
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */

@PageTitle("National Park Search Tool")
@Route(value = "")
public class MainView extends VerticalLayout {

    /**
     * A newly created APIFetcher object that pulls can be used to pull in API data.
     */
    private static APIFetcher fetcher = new APIFetcher();

    /**
     * A list of all activities currently available at national parks.
     */
    private static ArrayList<String> currentActivities = fetcher.getAllActivities();

    /**
     * A list of all national parks.
     */
    private static ArrayList<Park> everyPark = fetcher.getAllParks();

    /**
     * A filtered list of parks (filtered based on user search / selection).
     */
    private static ArrayList<Park> filteredParks = everyPark;

    /**
     * A list of all webcams found across national parks.
     */
    private static ArrayList<Webcam> allWebcams;

    /**
     * A list of page detail segments, which are page elements created for each national park.
     */
    private static ArrayList<Details> parkInfoSegments = new ArrayList<Details>();


    public MainView() {
// Reset the list of informational segments of parks. Mainly in place for when the user refreshes the page.
        parkInfoSegments.clear();

        // Create a new image element that contains a image of Rocky Mountain National Park for decorative / aesthetic purposes.
        Image styleImage = new Image("https://upload.wikimedia.org/wikipedia/commons/1/14/Rockies_in_the_morning.jpg", "Image of Rocky Mountain National Park");

        // Add decorate green bar at the top of the page.
        HorizontalLayout top = new HorizontalLayout();
        top.getStyle().set("width", "100%");
        top.getStyle().set("height", "50px");
        top.getStyle().set("background", "#33691e");
        add(top);

        // Adjust styling of main page header image.
        styleImage.getStyle().set("width", "100%");
        styleImage.getStyle().set("height", "350px");
        styleImage.getStyle().set("object-fit", "cover");
        styleImage.getStyle().set("object-position", "50% 15%");
        add(styleImage);

        // Create a new header that will serve as the primary title for the webpage.
        H1 intro = new H1("Welcome to the National Park Activity Search Tool!");
        intro.getStyle().set("margin-left", "auto");
        intro.getStyle().set("margin-right", "auto");
        intro.getStyle().set("padding-bottom", "10px");
        add(intro);

        // Create a new header element meant to briefly explain to the user
        H2 helper1 = new H2("Get started browsing parks by selecting an activity below.");
        helper1.getStyle().set("margin-top", "0px");
        add(helper1);

        // Create new ComboBox object containing a list of all activities. Add this component into a newly created div,
        //  and add it to the page.
        ComboBox<String> selectParkActivities = new ComboBox<>();
        selectParkActivities.setItems(currentActivities);
        selectParkActivities.setPlaceholder("Select an activity");
        Div selectBox = new Div(selectParkActivities);
        add(selectBox);

        // Create a new button that contains the text "Search", which will be used to filter the parks into only one
        //  that matches the user's chosen activity.
        Button searchForParks = new Button("Search", buttonClickEvent -> {

            // Remove helper1, as the user should now understand how the search component operates.
            remove(helper1);

            // Clear current parkSegments from page and reset parkInfoSegments.
            for (int i = 0; i < parkInfoSegments.size(); i++) {
                remove(parkInfoSegments.get(i));
            }
            parkInfoSegments.clear();

            // Reset filteredParks to all original parks, before beginning filtering.
            filteredParks = everyPark;
            ArrayList<Park> temp = new ArrayList<Park>();

            // Filter parks based on "Search" ComboBox input.
            for (int i = 0; i < filteredParks.size(); i++) {
                boolean activityPresent = false;
                for (int activity = 0; activity < filteredParks.get(i).getParkActivities().size(); activity++) {
                    if (filteredParks.get(i).getParkActivities().get(activity).equals(selectParkActivities.getValue())) {
                        activityPresent = true;
                        break;
                    }
                }
                if (activityPresent) {
                    temp.add(filteredParks.get(i));
                }
            }
            filteredParks = temp;


            // Rebuild bpage based on filtered data. For each Park object in filteredParks, created a Details component
            //  ,fill with related information and images, and add to the page.
            for (int i = 0; i < filteredParks.size(); i++) {
                Details parkSegment = new Details();

                // Get the current parks' name
                parkSegment.setSummaryText(filteredParks.get(i).getName());

                // Create a new header element that will contain the current park's name.
                H2 parkName = new H2(filteredParks.get(i).getName());
                parkName.getStyle().set("color", "White");
                parkName.getStyle().set("text-align", "center");
                parkName.getStyle().set("padding", "10px");

                // Create a new div for the current park's name.
                Div parkNameDiv = new Div(parkName);
                parkNameDiv.getStyle().set("background-color", "#558b2f");
                parkNameDiv.getStyle().set("margin-left", "20%");
                parkNameDiv.getStyle().set("margin-right", "20%");
                parkNameDiv.getStyle().set("border-radius", "5px");
                parkSegment.addContent(parkNameDiv);

                // Create a new text element that will contain what state(s) the park is located in.
                Paragraph states = new Paragraph("State(s): " + filteredParks.get(i).getStates());
                states.getStyle().set("font-style", "italic");
                states.getStyle().set("margin-left", "auto");
                states.getStyle().set("margin-right", "auto");
                states.getStyle().set("text-align", "center");
                parkSegment.addContent(states);

                // Get a new image element containing an image taken at the given park, format it, and add it to the page.
                Image imageToAdd = new Image(filteredParks.get(i).getParkImageLinks().get(0), "Image of Park");
                imageToAdd.setWidth(500, Unit.PIXELS);
                imageToAdd.setHeight(300, Unit.PIXELS);
                imageToAdd.getStyle().set("border", "6px solid #558b2f");
                imageToAdd.getStyle().set("display", "block");
                imageToAdd.getStyle().set("margin-left", "auto");
                imageToAdd.getStyle().set("margin-right", "auto");
                parkSegment.addContent(imageToAdd);

                // Create a new header and paragraph element which will contain basic information about the current park.
                H3 parkDescHeader = new H3("Park Description:");
                Paragraph parkDesc = new Paragraph(filteredParks.get(i).getParkDescription());
                parkDescHeader.getStyle().set("margin-left", "10px");
                parkDesc.getStyle().set("margin-left", "10px");
                parkSegment.addContent(parkDescHeader, parkDesc);

                // Create a new string that will contain all activities for the given park.
                String activitiesString = "";

                // Add all activities associated with the current park to activitiesString.
                for (int activities = 0; activities < filteredParks.get(i).getParkActivities().size(); activities++) {
                    if (activities != filteredParks.get(i).getParkActivities().size() - 1) {
                        activitiesString += filteredParks.get(i).getParkActivities().get(activities) + ", ";
                    }
                    else {
                        activitiesString += filteredParks.get(i).getParkActivities().get(activities);
                    }
                }

                // Create a header for the activities list and add it to the page.
                H3 activitiesHead = new H3("Available Activities:");
                activitiesHead.getStyle().set("margin-left", "10px");
                parkSegment.addContent(activitiesHead);

                // Create a new paragraph text element that contains the list of activities for the current park,
                //  and add it to the page
                Paragraph activities = new Paragraph(activitiesString);
                activities.getStyle().set("margin-left", "10px");
                parkSegment.addContent(activities);

                // Get updated webcam image link based on the given current park's name.
                String imageLink = APIFetcher.getCurrentWebcamLink(filteredParks.get(i).getName());
                Image updatedPhoto = new Image("//:0", "");


                int currentPark = i;

                // If the image link is available (or rather, the webcam is active):
                if (!imageLink.equals("")) {
                    // Create button that can collect up-to-date images from webcams upon user input.
                    Button getWebcamImages = new Button("Click to show an image from a park web cam", updateImages -> {

                        allWebcams = fetcher.getAllWebcams();
                        String newLink = APIFetcher.getCurrentWebcamLink(filteredParks.get(currentPark).getName());
                        if (updatedPhoto.getSrc() == "//:0") {
                            updatedPhoto.setSrc("https://" + newLink);
                            updatedPhoto.setWidth(1000, Unit.PIXELS);
                            updatedPhoto.setHeight(600, Unit.PIXELS);
                            updatedPhoto.getStyle().set("border", "6px solid ##558b2f");
                            updatedPhoto.getStyle().set("display", "block");
                            updatedPhoto.getStyle().set("margin-left", "auto");
                            updatedPhoto.getStyle().set("margin-right", "auto");
                            updatedPhoto.getStyle().set("border", "6px solid #558b2f");
                            parkSegment.addContent(updatedPhoto);
                        }
                        else {
                            updatedPhoto.setSrc("https://" + newLink);
                        }
                    });
                    getWebcamImages.getStyle().set("margin-left", "auto");
                    getWebcamImages.getStyle().set("margin-right", "auto");
                    getWebcamImages.getStyle().set("color", "White");
                    getWebcamImages.getStyle().set("background", "#558b2f");
                    parkSegment.addContent(getWebcamImages);
                }
                parkInfoSegments.add(parkSegment);
                parkSegment.getStyle().set("padding-left", "0");
                parkSegment.getStyle().set("margin-left", "10px");
                add(parkSegment);
            }
        });

        // Adjust styling of "Search" button, and add it to the page
        searchForParks.getStyle().set("color", "White");
        searchForParks.getStyle().set("background", "#558b2f");
        searchForParks.getStyle().set("text-align", "center");
        add(searchForParks);

        // Set default page layout and styling options
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "start");
        getStyle().set("padding", "0");
        getStyle().set("padding-bottom", "40x");
        getStyle().set("margin-top", "0");
    }

}

