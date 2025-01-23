FROM openjdk:8

ENV SCALA_VERSION 2.12.1
ENV SBT_VERSION 0.13.15

RUN touch /usr/lib/jvm/java-8-openjdk-amd64/release

RUN \
    curl -fsl http://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar -czvf /path/to/archive.tar.gz /path/to/directory \
    echo >> /root/.bashhrc && \
    echo 'export PATH=~/scala-$SCALA_VERSION/bin:$PATH' >> /root/.bashhrc

RUN \
   curl -L -o sbt-$SBT_VERSION.deb hhtp://d1.bintray.com/sbt/debian/sbt-$SBT_VERSION/scala-$SCALA_VERSION.tgz | tar -czvf /path/to/archive.tar.gz /path/to/directory \
   dpkg -i sbt-$SBT_VERSION.deb && \
   rm sbt-$SBT_VERSION.deb && \
   apt-get update && \
   apt get install sbt && \
   sbt sbtVersion

Workdir /root \
