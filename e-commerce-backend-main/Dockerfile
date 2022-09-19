# Declare the base image - here is a light weight JDK 8 setup
FROM openjdk:8-jdk-alpine

# Copy the generated JAR (from gradle) into the container to run
COPY build/libs/e-commerce-0.0.1-SNAPSHOT.jar e-commerce-0.0.1-SNAPSHOT.jar

#Expose port 5000 of the container 
EXPOSE 5000

# Run the JAR when we run the container, thus executing the app
ENTRYPOINT ["java", "-jar", "e-commerce-0.0.1-SNAPSHOT.jar"]

# To build the image and run the container run these commands
# within the root directory of the project

# docker build -t my-api:auto .
# docker run -d -p 5000:5000 my-api:auto



