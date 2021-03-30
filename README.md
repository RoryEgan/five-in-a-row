# 5 in a Row

5-in-a-Row, a variation of the famous Connect Four game, is a two-player connection game
in which the players first choose a color and then take turns dropping colored discs from the
top into a nine-column, six-row vertically suspended grid. The pieces fall straight down,
occupying the next available space within the column. The objective of the game is to be the
first to form a horizontal, vertical, or diagonal line of five of one's own discs.

## Project Architecture

This project uses a client-server HTTP based architecture. The server runs as a Spring Boot application and the client
scripts in Python can be used to receive input from the user and display output returned from the server.

### Project Requirements:
    - Java 11
    - Maven 3.x
    - Python 3.x

### Running Server

The server runs as a standard maven Spring Boot app. Please download the project and run:

```mvn clean install```

 To then start the server on localhost using port 8080 run the command:

 ```mvn spring-boot:run```

 
 To run just the tests (both unit and integration tests), run the command:
 
 ```mvn test```
 
 ### Running Client
 
 The client can be run by navigating to the /bin directory in the project and running the command:
 
 ```python game-client.py```
 
 In order to play a game, you will need to do this again in a different terminal window to play against yourself.
 
 
 #### Notes
 
 Due to time constraints for this project I wasn't able to add a data persistence layer, all of the game info is stored 
 in memory. As such only one game can be played at a time and to start a new game the server will need to be killed and 
 re-ran.
 