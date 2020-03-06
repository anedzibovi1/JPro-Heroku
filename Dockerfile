FROM adoptopenjdk:11-hotspot-bionic
RUN sudo apt-get install xorg gtk2-engines libasound2 libgtk2.0-0
RUN ls
RUN ./gradlew jproRun