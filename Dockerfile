# Build
FROM gradle:7.5.1-jdk11 AS build

COPY . .

RUN gradle assemble --no-daemon --info --parallel
RUN unzip /home/gradle/build/distributions/*.zip -d /app/

# Application
FROM amazoncorretto:11.0.18

COPY --from=build /app/ /app/

COPY docker-entrypoint.sh /

RUN chmod +x /docker-entrypoint.sh

ENTRYPOINT ["/docker-entrypoint.sh"]
