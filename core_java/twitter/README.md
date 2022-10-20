# Introduction
This application is part of the core Java project. Developed a Twitter application, 
which was used to explore Twitter's REST API capabilities. The application was designed 
to allow users to post, show, and delete tweets in Twitter from the app's CLI. The application 
was programmed in Java and utilizes an HTTP client to run the HTTP requests made when an 
operation is executed for authentication. Maven was used to build the project and manage all 
the class dependencies in the package. The Spring framework was used in the app to simplify 
dependency management. JUnit4 and Mockito were used to test the app and Docker was used to 
containerize the project and push it to Docker Hub to allow it to run on any computer system.

Technologies used in the project are as follows:

- Twitter REST API
- Spring Framework
- SpringBoot
- Docker
- JUnit4
- Mockito
- Maven
- HTTP Client

# Quick Start
- To Package the app, the following commands are to be executed:
```
mvn clean package
```
- To Run the app using docker:
  - Build Docker Image:
  ```
  docker_user=username
  docker build -t ${docker_user}/twitter .
  ```
  - To post a tweet using docker:
  ```
  docker run --rm \
  -e consumerKey=YOUR_API_KEY \
  -e consumerSecret=YOUR_API_SECRET \
  -e accessToken=YOUR_ACCESS_TOKEN \
  -e tokenSecret=YOUR_TOKEN_SECRET \
  ${docker_user}/twitter post "tweet_text" longitude:latitude
  ```
  - To Display a tweet:
  ```
  docker run --rm \
  -e consumerKey=YOUR_API_KEY \
  -e consumerSecret=YOUR_API_SECRET \
  -e accessToken=YOUR_ACCESS_TOKEN \
  -e tokenSecret=YOUR_TOKEN_SECRET \
  ${docker_user}/twitter show tweetID [field1,field2,.....]
  ```
  - To Delete a tweet
  ```
  docker run --rm \
  -e consumerKey=YOUR_API_KEY \
  -e consumerSecret=YOUR_API_SECRET \
  -e accessToken=YOUR_ACCESS_TOKEN \
  -e tokenSecret=YOUR_TOKEN_SECRET \
  ${docker_user}/twitter delete tweetID
  ```
  
# Design
## UML diagram
![diagram](./assets/UMLDiagram.png)
## Project Component in details
- **App/main:** Twitter app was designed with the MVC architecture, which uses the main() 
function to call the controller with passed arguments and execute the run() method to run the app.

- **Controller:** This layer works with the CLI app and parses the user input given by the main. 
Then the controller layer calls the service layer and returns results.

- **Service:** The service layer controls the business logic of the app and calls the DAO layer 
to access the underlying Twitter REST API storage in the app.

- **Data Access Object (DAO):** The DAO layer interacts with the models of the Twitter app that 
were implemented with POJOs. The DAO can post, save, and delete tweets.
## Models
- The twitter app model consists of 3 object models:
  - Tweet Object
  ```
  {
     "created_at":"Mon Feb 18 21:24:39 +0000 2022",
     "id":1097607853932564480,
     "id_str":"1097607853932564480",
     "text":"test with loc223",
     "entities":{
        "hashtags":[],      
        "user_mentions":[]  
     },
     "coordinates":null,    
     "retweet_count":0,
     "favorite_count":0,
     "favorited":false,
     "retweeted":false
  }
  ```
  - Entity Object:
  ```
  "hashtags":[],      
  "user_mentions":[]  
  ```
  - Coordinates Object:
  ```
  "coordinates":null
  ```
## Spring
- Spring was used in this app to replace the traditional Dependency management method 
with the Inversion of Control (IoC) container and Beans. The Spring framework implements 
the Inversion of Control (IoC) principle which is a process that has objects define their 
dependencies through constructor arguments. The IoC container can then inject those 
dependencies when the bean is created, which inverts the process of the bean controlling 
the location of its dependencies. In the IoC container, there are multiple components 
that are managed. These components are called Beans in Spring since they are 
objects/classes created by the IoC. In this app, the Twitter classes are beans.

# Test
- The twitter app was tested using JUnit 4 by setting up the twitter class arguments and 
applying the @Before annotation in order to execute the setup method before every test to 
prepare the environment. Then the test methods were created and the @Test annotation 
was applied to them to identify them as tests. For every test method that returns objects, 
assert statements are used to test if the methods return the correct results.
- Mockito is used in the app to test the helping classes without actually testing the 
dependencies. It does this by creating mock objects of the dependency classes and passing 
them to the classes that depend on them. Mockito can also control what the mock object will 
return when they are called.

## Deployment
The app was deployed and containerized using Docker. Created a dockerfile that contains 
the commands to copy the jar file and place it at the entry point to create the docker image. 
Then the app was packaged using the `mvn clean package` command, and the docker image was 
built locally using the `docker build -t` command. Then `docker push` command so docker image 
can be stored in the Docker Hub to allow for easier access.

# Improvements
- Make more features like update, retweet.
- Build a user friendly interface for better user experience.
- Improvement in the restriction of twitter api so that user can able to add long tweet.