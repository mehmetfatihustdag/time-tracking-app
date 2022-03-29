# TimeTracking App
FoodTech time tracking app assignment


### Tech

Application uses a number of open source projects to work properly:

* Java 8- Primary Language
* Spring boot - An open source Java-based framework used to create a micro Service.
* Maven - Dependency Management
* h2- embedded database, and employing the integration tests to tests happy paths, and some exceptional cases

Project will run under the http://localhost:8083
Note : 8083 is configurable port it can be changed via application.yml file

All the flow is demonstrated via integration tests. PunchIn, PunchOut, and reports endpoints called , and tested. 

Factory pattern is employed to make the application easily extendable for the possible future cases (Vacation, and Break). 
New services can be easily derived from TimeTrackingHandler interface, and can be produced via TimeServiceFactory class without touching the core logic.

### Swagger UI :
Application Endpoints documented via Swagger . You can find the swagger ui below link, and test endpoints via browser as well
http://localhost:8083/swagger-ui.html

### Suggestion For Improvements :
- Write MORE Tests,  Unit tests can be employed. 
- Development, test , and production profiles can be used.
- Data model can be improved because of the quick prototype data model kept simply.

### Author
Fatih Ustdag
fatihustdag@gmail.com
 



