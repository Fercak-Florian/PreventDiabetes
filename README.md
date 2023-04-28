# PreventDiabetes
This application has been developed to help doctors identify the patients most at risk of developing a form of diabetes  
This application is composed of 4 services.

## Prerequisite :

 - Java 11
 - Maven 3.8.6
 - Docker 20.10.23
 
## Installation :
 
#### Step 1 : download this project
 
#### Step 2 : package the application
Go to root directory of each module  
For each module, run the maven command : **mvn clean package**
 
#### Step 3 : build the docker images
In the root directory of each module, run the docker command : **docker build -t nom_du_conteneur .**  

## Run :
#### Step 4 : run the application stack
Go to the docker-compose.yml directory and run the docker command : **docker-compose up -d**

#### Step 5 : stop the application
To stop the application, run the docker command : **docker-compose stop**
