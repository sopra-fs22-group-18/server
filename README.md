# SoPra FS22 - Group 18 Roast me

## Introduction

“Roast Me!” is a game, where a user can host a roasting session and upload a picture, e.g. of a meal they cooked, a sweater they are wearing, or their pet. 

Other users can then join the roasting session as participants, and comment on the picture, making fun of it. 

The host will decide at the end of the session which participant wins the game.

## Technologies
- JSX, npm and React
- HTTP/Rest
- gradle, spring, java and mysql
- github, sonarqube and heroku

## High-level components
- Websocket: Socket.java uses the javax.websocket library to build a real time chat with different rooms
- TextApi: TextApi.java is hooked to a free API for text moderation. This component ensures that no blocked words are used in our chat rooms.
- Database: Application.java uses the jpa interface to store all kinds of information in a remote mysql server.

## Launch & Deployment
### First get to know Spring Boot
Before you set up the environment, get to know Spring Boot and what REST is
- [Spring-Boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/index.html)
- [Spring Guides](http://spring.io/guides)
    - [Building a RESTful Web Service](http://spring.io/guides/gs/rest-service/)
    - [Building REST services with Spring](http://spring.io/guides/tutorials/bookmarks/)

### Setup with your IDE of choice
Download your IDE of choice:
- [Eclipse](http://www.eclipse.org/downloads/)
- [IntelliJ](https://www.jetbrains.com/idea/download/)
- [Visual Studio Code](https://code.visualstudio.com/)
- Or any other IDE


Make Sure ```Java 15``` is installed on your system <br/>
	-> **For Windows Users:** please make sure your ```JAVA_HOME``` environment variable is set to the correct version of Java

1. File -> Open... -> server
2. Accept to import the project as a ``gradle project`` <br/><br/>
Then build the project in your IDE
3. Right click the ```build.gradle``` file and choose ```Run Build```

### Setup for VS Code
The following extensions will help you to run it more easily:
- `pivotal.vscode-spring-boot`
- `vscjava.vscode-spring-initializr`
- `vscjava.vscode-spring-boot-dashboard`
- `vscjava.vscode-java-pack`
- `richardwillis.vscode-gradle` <br/><br/>
**Note for VS Code Users:** You'll need to build the project first with Gradle, just click on the `build` command in the _Gradle Tasks_ extension. Then check the _Spring Boot Dashboard_ extension if it already shows `soprafs22` and hit the play button to start the server. If it doesn't show up, restart VS Code and check again.

### Building with Gradle
You can use the local Gradle Wrapper to build the application.
-   macOS: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

You can get more Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/) if you want.

### Build the application
Run in the server file in your terminal
```
./gradlew build
```

### Run the local deployment
```
./gradlew bootRun
```
### Run the tests

```
./gradlew test
```
### Database
You have to take no prearrangements for the database. The database will be running automatically.

## Roadmap
1. In the future, you could set up a lobby where you can wait for participants to enter after the game starts. 
2. In addition, it would be useful to be able to see your own post and comment history. 
3. It would also be interesting if users could send voice messages or videos while "roasting" pictures.
## Authors and acknowledgment

- [Benjamin Bajralija](https://github.com/bbajrari)
- [Carol Ernst](https://github.com/carolernst-uzh)
- [Niels Zweifel](https://github.com/itsniezwe)
- [Said Haji Abukar](https://github.com/awhoa)
- [Timon Fopp](https://github.com/trofej)

## License
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
