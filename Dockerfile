from maven:3.8.6-amazoncorretto-11

COPY ./ ./
WORKDIR ./

RUN mvn dependency:resolve -U
RUN mvn clean package

CMD ["java", "-jar", "target/add-gold-task-1.0-SNAPSHOT-jar-with-dependencies.jar"]