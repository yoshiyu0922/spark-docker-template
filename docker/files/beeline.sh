#!/bin/sh

rlwrap $HIVE_HOME/bin/beeline --showHeader=true -u jdbc:hive2://localhost:10000
