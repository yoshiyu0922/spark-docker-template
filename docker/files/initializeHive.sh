#!/bin/sh
cd `dirname ${0}`

hive -e 'create database sample;'

hive -f /sample/ddl/message.hql