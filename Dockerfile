FROM adoptopenjdk:11-hotspot-bionic
RUN apt-get update
RUN apt-get -y install xorg gtk2-engines libasound2 libgtk2.0-0
RUN apt-get -y install unzip
WORKDIR /app
COPY . /app/
RUN ./gradlew jproRelease
RUN ls unzip build/distributions/
RUN unzip build/distributions/app-jpro
CMD ls build/distributions/
# CMD ./build/distributions/app-jpro/bin/start.sh