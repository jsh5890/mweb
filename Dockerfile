FROM node:16.17.0
#ENV NODE_OPTIONS --openssl-legacy-provider
RUN mkdir /mweb
COPY . /mweb
WORKDIR /mweb/front
#RUN apt install -y yarn
#RUN yarn && yarn build --spa
RUN npm install && npm run build
RUN mkdir -p ../src/main/resources/static
RUN cp -R dist/* ../src/main/resources/static

FROM gradle:jdk11
RUN mkdir /mweb
WORKDIR /mweb
COPY --from=0 /mweb .
RUN gradle bootjar


FROM openjdk:11-jre
COPY --from=1 /mweb/build/libs/mweb-*.jar mweb.jar
VOLUME [ "/root/data" ]
EXPOSE 8080
#CMD [ "java", "-Xshareclasses", "-Xquickstart", "-jar", "mweb.jar"]
CMD [ "java", "-jar", "mweb.jar"]