This project is modified from spring-4.0 sample project: 
	http://spring.io/guides/gs/convert-jar-to-war-maven/
The sub-folder complete is what we want.

use Maven 3.0 to build and run

Run this project as a web application:
	mvn spring-boot:run
address: http://localhost:8080

Build it as an eclipse project:
	mvn eclipse:eclipse

Get the war file:
	mvn package

Load it to eclipse IDE, Files -> import -> exists maven projects -> select the folder



