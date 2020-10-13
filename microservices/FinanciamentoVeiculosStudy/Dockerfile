FROM openjdk:8-jre
RUN mkdir app
ARG JAR_FILE
ADD target/${JAR_FILE} /app/
WORKDIR /app
ENTRYPOINT java -jar FinanciamentoVeiculosStudy-v1.jar