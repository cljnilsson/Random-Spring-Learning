#### Todo list application with spring as backend.

##### Dev Commands
mvn spring-boot:run  
mvn clean package  
java -jar ./target/demo-0.0.1-SNAPSHOT.jar  


##### Features
* Database, CRUD for the todo model.
* Oauth2 login with Github and Google, automatic account creation.
* Websocket support

##### Dependencies
* Java 8
* Spring Boot
* Maven
* Check pom.xml for java dependencies
* bootstrap
* jquery
* Font Awesome

##### Known Issues
* The required Whitelist role does not get added properly when you log in the first time. This requires you to re-login, alternatively just comment out the antMachers() that handles in the "/" route to disable the whitelist requirement.