FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/dscatalog-0.0.1-SNAPSHOT.jar dscatalog-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/dscatalog-0.0.1-SNAPSHOT.jar"]
