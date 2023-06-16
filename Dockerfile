FROM reghbpr01.dc1.true.th/mse/java/openjdk:8-jre-alpine
# Set timezone to Bangkok
ENV TZ Asia/Bangkok

# Add timezone data
RUN apk add --no-cache --update curl busybox-extras tzdata \
 && cp /usr/share/zoneinfo/$TZ /etc/localtime \
 && echo $TZ > /etc/timezone \
#
# Cleanup
 && rm -rf /var/cache/apk/* && rm -rf /usr/share/zoneinfo

# Set volume point to /tmp
VOLUME /tmp

ENV JAVA_OPTS '-server -noverify'
ENV RUN_ARGS '-Djava.security.egd=file:/dev/./urandom'
ENV JAVA_MEM_OPTS '-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1'
ENV MONITOR_OPTS=''

WORKDIR /app

# Add src to working directory
COPY target/*.jar /app/api.jar

# Start app
ENTRYPOINT java $JAVA_OPTS $RUN_ARGS $JAVA_MEM_OPTS $MONITOR_OPTS -jar /app/api.jar