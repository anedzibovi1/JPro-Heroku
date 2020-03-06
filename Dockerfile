FROM adoptopenjdk:11-hotspot-bionic
RUN apt-get update
RUN apt-get -y install xorg gtk2-engines libasound2 libgtk2.0-0
WORKDIR /app
COPY . /app/
RUN ./gradlew jproRelease
CMD ./gradlew jproRun