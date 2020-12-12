#FROM maven
#WORKDIR /app
#COPY . .
#RUN mvn package


# https://hub.docker.com/_/debian
FROM openjdk:8

ARG firefox_ver=83.0
ARG geckodriver_ver=0.28.0


RUN apt-get update \
 && apt-get upgrade -y \
 && apt-get install -y --no-install-recommends --no-install-suggests \
            ca-certificates \
 && update-ca-certificates \
    \
 # Install tools for building
 && toolDeps=" \
        curl bzip2 \
    " \
 && apt-get install -y --no-install-recommends --no-install-suggests \
            $toolDeps \
    \
 # Install dependencies for Firefox
 && apt-get install -y --no-install-recommends --no-install-suggests \
            `apt-cache depends firefox-esr | awk '/Depends:/{print$2}'` \
            # additional 'firefox-esl' dependencies which is not in 'depends' list
            libxt6 \
    \


 # Download and install Firefox
 && curl -fL -o /tmp/firefox.tar.bz2 \
         https://ftp.mozilla.org/pub/firefox/releases/${firefox_ver}/linux-x86_64/en-GB/firefox-${firefox_ver}.tar.bz2 \
 && tar -xjf /tmp/firefox.tar.bz2 -C /tmp/ \
 && mv /tmp/firefox /opt/firefox \
    \
 # Download and install geckodriver
 && curl -fL -o /tmp/geckodriver.tar.gz \
         https://github.com/mozilla/geckodriver/releases/download/v${geckodriver_ver}/geckodriver-v${geckodriver_ver}-linux64.tar.gz \
 && tar -xzf /tmp/geckodriver.tar.gz -C /tmp/ \
 && chmod +x /tmp/geckodriver \
 && mv /tmp/geckodriver /usr/local/bin/ \
    \
 # Cleanup unnecessary stuff
 && apt-get purge -y --auto-remove \
                  -o APT::AutoRemove::RecommendsImportant=false \
            $toolDeps \
 && rm -rf /var/lib/apt/lists/* \
           /tmp/*

EXPOSE 4444

ENV FIREFOX_HOME=/opt/firefox/firefox
ENV GECKODRIVER_HOME=/usr/local/bin/geckodriver

COPY ./target/firstcrawler-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "firstcrawler-0.0.1-SNAPSHOT.jar"]