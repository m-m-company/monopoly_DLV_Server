# Monopoly DLV AI

This is a monopoly javascript version that has artificial intelligence powered by [DLV2.0](http://www.dlvsystem.com/) system.
The web server is built with [Spring Boot](https://spring.io/) and [EmbASP 3.1.0](https://github.com/DeMaCS-UNICAL/EmbASP).

The whole front-end code is forked from https://github.com/intrepidcoder/monopoly (Thanks!).

In the front-end we worked only on the ai.js file. 

This is project is built for educational-only purpose as part of the Artificial Intelligence exam at [UniCal Department of Mathematics and Computer Science](https://www.mat.unical.it/demacs).
#Build

First of all clone this repository in your preferred folder.

Put your dlv2 executable in the `src/main/resources/libs` folder. 

###Please, rename the executable "dlv2". _This is important_.

Execute `mvn clean install` in the root directory (where the pom.xml file is).

#Run

Just run `mvn spring-boot:run` in the root directory.

Your server is running on `localhost:8080`.

###Enjoy!


#How it works

We used Synchronous AJAX (jQuery) calls to communicate with the server the state of the game.

We built the web server using Spring Boot and only one class handles all the ajax calls (ServletCalls.java).

In the DTO package you'll find all the domain classes.

The most important class is the wrapper DLVHandler that's a Singleton that exposes only the
```java
    List<AnswerSet> startGuess(Collection<Object> facts, String encoding);
```
method that receive a `java.util.Collection` of objects as facts and the name of the encoding (extension included, ie. "bid.dlv").

In the end, all the other methods of EmbASP (that we needed) are reduced to template code.

####Contributors
[Matteo Notaro](https://github.com/MattNot) & [Matteo Loria](https://github.com/MatteoLoria/)