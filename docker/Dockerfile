FROM openjdk:8-jdk
MAINTAINER yoshiyu0922

### Versions
ENV HADOOP_MAJOR_VERSION 2.7
ENV HADOOP_VERSION $HADOOP_MAJOR_VERSION.7
ENV HIVE_VERSION 2.0.1
ENV SPARK_VERSION 2.4.4

# apt upgrade & Install netstat
RUN apt update
RUN apt install net-tools -y
RUN apt install vim -y

## Install Hadoop
ENV HADOOP_HOME /opt/hadoop-$HADOOP_VERSION
ENV HADOOP_CONF_DIR $HADOOP_HOME/conf
ENV PATH $PATH:$HADOOP_HOME/bin
RUN curl -sL \
  "https://archive.apache.org/dist/hadoop/common/hadoop-$HADOOP_VERSION/hadoop-$HADOOP_VERSION.tar.gz" \
    | gunzip \
    | tar -x -C /opt/ \
  && rm -rf $HADOOP_HOME/share/doc \
  && chown -R root:root $HADOOP_HOME \
  && mkdir -p $HADOOP_HOME/logs \
  && mkdir -p $HADOOP_CONF_DIR \
  && chmod 777 $HADOOP_CONF_DIR \
  && chmod 777 $HADOOP_HOME/logs

# Install MySQL
RUN apt install mysql-server -y
RUN service mysql start && \
    mysql -e'CREATE DATABASE metastore;' && \
    mysql -e'CREATE USER hive@localhost IDENTIFIED BY "hive";' && \
    mysql -e'GRANT ALL PRIVILEGES ON metastore.* TO hive@localhost;'

## install Hive
ENV HIVE_HOME /opt/apache-hive-${HIVE_VERSION}-bin
ENV HIVE_CONF_DIR $HIVE_HOME/conf
ENV PATH $PATH:$HIVE_HOME/bin
RUN curl -sL \
  "https://archive.apache.org/dist/hive/hive-$HIVE_VERSION/apache-hive-$HIVE_VERSION-bin.tar.gz" \
    | gunzip \
    | tar -x -C /opt/ \
  && chown -R root:root $HIVE_HOME \
  && mkdir -p $HIVE_HOME/hcatalog/var/log \
  && mkdir -p $HIVE_HOME/var/log \
  && mkdir -p /data/hive/ \
  && chmod 777 /data/hive/ \
  && mkdir -p /tmp/hive/ \
  && chmod -R 777 /tmp/hive/ \
  && mkdir -p $HIVE_CONF_DIR \
  && chmod 777 $HIVE_HOME/hcatalog/var/log \
  && chmod 777 $HIVE_HOME/var/log

RUN ln -s $HADOOP_HOME/share/hadoop/tools/lib/aws-java-sdk-1.7.4.jar $HIVE_HOME/lib/.
RUN ln -s $HADOOP_HOME/share/hadoop/tools/lib/hadoop-aws-2.7.3.jar $HIVE_HOME/lib/.

# install Spark
ENV SPARK_HOME=/opt/spark-$SPARK_VERSION-bin-hadoop2.7
ENV SPARK_CONF_DIR=$SPARK_HOME/conf
ENV PATH $PATH:$SPARK_HOME/bin
RUN curl -sL \
  "https://archive.apache.org/dist/spark/spark-$SPARK_VERSION/spark-$SPARK_VERSION-bin-hadoop$HADOOP_MAJOR_VERSION.tgz" \
    | gunzip \
    | tar -x -C /opt/ \
  && chown -R root:root $SPARK_HOME \
  && mkdir -p /data/spark/ \
  && mkdir -p $SPARK_HOME/logs \
  && mkdir -p $SPARK_CONF_DIR \
  && chmod 777 $SPARK_HOME/logs

# Install Readline Wrapper
RUN apt-get update && apt-get install -y rlwrap \
 && rm -rf /var/lib/apt/lists/*

RUN ln -s $HADOOP_HOME/share/hadoop/tools/lib/aws-java-sdk-1.7.4.jar $SPARK_HOME/jars/.
RUN ln -s $HADOOP_HOME/share/hadoop/tools/lib/hadoop-aws-2.7.3.jar $SPARK_HOME/jars/.

# Configure
ADD files/hive-site.xml $HIVE_CONF_DIR/
ADD files/hive-site.xml $SPARK_CONF_DIR/

# initialize schema in HIVE
ADD jars/*.jar $HIVE_HOME/lib/
RUN service mysql start && \
    schematool -dbType mysql -initSchema -verbose

ENV BASE_PATH=/sample/
RUN mkdir $BASE_PATH
RUN mkdir $BASE_PATH/shells
RUN mkdir $BASE_PATH/ddl
ADD files/*.sh $BASE_PATH/shells/
RUN chmod +x $BASE_PATH/shells/*.sh
ADD files/ddl/*.hql $BASE_PATH/ddl/

EXPOSE 10000

CMD ["/sample/shells/start.sh"]
