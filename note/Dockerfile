# telechargement de l'image de base
FROM eclipse-temurin:11-jdk-alpine

# creation d'un repertoire "app"
RUN mkdir /app

# copie du fichier jar dans mon image docker, dans le repertoire "app"
COPY ./target/note-0.0.1-SNAPSHOT.jar /app/

# aller dans le repertoire "app"
WORKDIR /app

# on expose le port 8082 de l'image docker
EXPOSE 8082

# commande qui est lancee au demarage du conteneur
CMD "java" "-jar" note-0.0.1-SNAPSHOT.jar