# telechargement de l'image de base
FROM eclipse-temurin:11-jdk-alpine

# creation d'un repertoire "app"
RUN mkdir /app

# copie du fichier jar dans mon image docker, dans le repertoire "app"
COPY ./target/view-0.0.1-SNAPSHOT.jar /app/

# aller dans le repertoire "app"
WORKDIR /app

# on expose le port 8085 de l'image docker
EXPOSE 8085

# commande qui est lancee au demarage du conteneur
CMD "java" "-jar" view-0.0.1-SNAPSHOT.jar