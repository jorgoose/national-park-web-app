# national-park-web-app
A web-based application meant to help people search for National Parks based on available activities. Created for the [Capital One Software Engineering Summit Challenge](https://www.mindsumo.com/contests/capital-one-engineering-summit-national-parks) (Fall 2021). 

This project was completed using the [Vaadin](https://vaadin.com/) development platform (as well as its associated libaries), and thus the vast majority of coding, for both frontend and backend, were done using Java (*Java classes can be found within this path -> src/main/java/org/vaadin/example*). Data for National Parks, National Park webcams, and other associated data was gathered using the [National Park Service API](https://www.nps.gov/subjects/developer/api-documentation.htm#/). The final product was deployed as a live web application on Heroku.


**Requirements / Objectives:**

[x] **Let visitors search from a list of activities to do at different National Parks**

Dropdown menu with autofill to let the user search from the given list of activities.

--

[x] **Visitors can click an activity and have the web app display all the National Parks tied to a specific activity**

After clicking an activity and hitting the search button, the website displays all the National Parks tied to the selected activity.

--


[x] **After selecting a specific park, the app should pull up an informational page so the visitor can learn more about the park.**

After selecting a park, the site opens an expanded view, which displays a description / summary of the park, an image taken from the park, the state(s) in which the park is located, the activities available at said park, and the ability to view a live webcam image from the park (if there is one available).

--

[x] **Have a feature on the web app where visitors can retrieve data from park web cams based on which National Park(s) the user selects. Specifically, this feature should be able to display the non-streaming images collected from park web cams so a visitor can view them with ease.**

After displaying more details about a specific park, if the park has an actively streaming webcam, the user is able to click a button to display the most recently collected image from said webcam. 

--


_**Known Issues**:_
- Some park images have problems with aspect ratio
- Slow image load times
