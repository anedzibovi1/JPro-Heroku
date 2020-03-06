FROM adoptopenjdk:11-hotspot-bionic
RUN apt-get update
RUN apt-get -y install xorg gtk2-engines libasound2 libgtk2.0-0
RUN echo 1
RUN ls
WORKDIR /app
COPY . /app/
CMD ls