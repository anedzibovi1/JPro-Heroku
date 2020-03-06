FROM adoptopenjdk:11-hotspot-bionic
RUN apt-get install xorg gtk2-engines libasound2 libgtk2.0-0
RUN echo 1
RUN ls
CMD ./gradlew jproRun