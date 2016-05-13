FROM maven:latest

RUN git clone http://github.com/babanomania/citizen-hub.git 
WORKDIR citizen-hub/citizen-hub-bus
RUN mvn camel:run