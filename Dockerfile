FROM gcr.io/distroless/java:11@sha256:22e5e7f796ed60c7d325f794fca99bf85e10921138502a5491b15aff5575f9ee
COPY target/locator*.jar /locator.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/locator.jar"]