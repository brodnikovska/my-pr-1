FROM maven:3.8.5-jdk-8

COPY src /home/SeleniumTestFramework/src

COPY pom.xml /home/SeleniumTestFramework

RUN mvn -f /home/SeleniumTestFramework/pom.xml clean test -DskipTests=true