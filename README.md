# Locator

This service is to provide an api that will return users that are in London or currently located 
within a distance from a city

this project has only been configured to return results from London however more cities can be added
 with the following properties added to application.properites in the resources folder or 
 environmental variables
 
`city.coordinates.latitude.{city}={latitudeValue}
city.coordinates.longitude.{city}={longitudeValue}`
<p>where:
<ul>
<li>{city} is the name of the city you would like to add in lowercase</li>  
<li>{latitudeValue} is the latitude of the city in decimal degrees</li>  
<li>{longitudeValue} is the longitude of the city in decimal degrees</li>  
</ul>

## Start project
use `mvn clean package` to build the jar file
then use `docker-compose build` to build the project
then use `docker-compose up` to run the project

## Endpoints
`/haversine/people/city/{city}?distance={distance}`
<p>where:
<ul>
<li>{city} is the city which you would like to query for</li>
<li>{distance} is in miles and is optional and defaults to 50 miles</li>
</ul>

<p>The haversine endpoint returns people who live in the city or within a distance arround the city 
using the haversine method to calculate the distance.
This pulls all the users from bpdts-test-app.herokuapp.com/city/{city}/users and adds users from 
the bpdts-test-app.herokuapp.com/users endpoint filtered by users within the given distance from 
the city coordinates.  
As this filters through all users from the bpdts-test-app.herokuapp.com/users every time it is not 
the most efficient option but is valid if the user collection changes frequently.

`/mongo/people/city/{city}?distance={distance}`
<p>where:
<ul>
<li>{city} is the city which you would like to query for</li>
<li>{distance} is in miles and is optional and defaults to 50 miles</li>
</ul>

The mongo endpoint returns people who live in the city or within a distance around the city 
using mongo database to find users within a circle with the radius distance.
This pulls all the users from bpdts-test-app.herokuapp.com/city/{city}/users and adds users that 
are queried using mongo given distance from the city coordinates.  
This pulls all users from the bpdts-test-app.herokuapp.com/users and saves them into the database 
upon project startup. Therefore if the user collection is frequently changing this may not be the 
best option.
    
    

