# PreventDiabetes
This application has been developed to help doctors identify the patients most at risk of developing a form of diabetes.
This application is composed of 4 services.

Spring Boot Project with :

 - Java 11
 - Maven
 - Docker
 
To use this project, download the git .zip package. 

You can run this project int two ways :
 - with IntelliJ
 - with Docker

 =====================================================================================
 
 Running with IntelliJ:
 - Import one sub-project, the one as you want, patient project for example, using File/New/Project From Existing Sources
 - Import the others sub-project, one by one, using File/New/Modules From Existing Sources
 
 Now that you have the 4 modules imported, you have set profile into IntelliJ
 Actually, if you run the 4 modules, you will get errors during Spring initilization because Spring properties are defined to run into docker configuration
 The default activated profile is the 'prod' profile
 But, we want to run in the 'dev' profile
 To achieve this, you have to add the line below in Run/Edit Configuration/Environnement variables :
 Add this line to patient, report and view Run configuration
 spring.profiles.active=dev
 
 Now that the 'dev' profile is set, you can run the 4 modules with IntelliJ and go to http://localhost:8085 to use the prevent diabetes application
 
 
 =====================================================================================
 
 
 Running with docker:
  - To run this application with docker, docker has to be already installed on your PC.

 
  Step 1 : building the jar for each of the 4 module
   - go to PreventDiabetes/patient and run maven command : mvn clean package
   - do the same for the note, report and view module
  Now we have 4 jar application
  
  Step 2 : Using Docker to build 4 images
           Docker has to run before following the next steps
   - go to directory PreventDiabetes/patient/target and run this docker command : docker build -t <nameOfService>-0.01 .
     To build the image from jar the docker command will be : docker build -t patient-0.01 .
   - do the same for the note, report and view jar
   Now we have 4 docker images
  
  Step 3 : Using docker-compose to run the 4 container simultaneously
   - got to directory PreventDiabetes, we will use the docker-compose.yml file to run the 4 containers simultaneously
   - use the docker command : docker-compose up -d
  Now that the 4 container run simultaneously in one stack, go to http://localhost:8085 to use the prevent diabetes application
  
 
 =====================================================================================
 
 
  Swagger UI documentation :
  You can use links below to access the swagger ui documentation for patient, note and report, once this services are up.
  - Patient API documentation : http://localhost:8081/swagger-ui/
  - Note API documentation : http://localhost:8082/swagger-ui/
  - Report API documentation : http://localhost:8083/swagger-ui/

