#!/bin/sh

echo "### start start.sh ###"

service mysql start

$HIVE_HOME/hcatalog/sbin/hcat_server.sh start

$SPARK_HOME/sbin/start-thriftserver.sh --master "local[4]" --conf "spark.sql.shuffle.partitions=4"

/sample/shells/initializeHive.sh

echo "### end start.sh ###"

/usr/bin/tail -f /dev/null
