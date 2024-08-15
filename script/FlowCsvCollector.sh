#!/bin/bash


CLASSPATH=.:$JAVA_HOME/jre/lib/*.jar:/data/NMS/java_lib/*
export CLASSPATH


PS_STATUS=$(/bin/ps -ef | grep -v grep | grep -v log | grep -v tail | grep FlowCollect.FlowCsvCollectorMain | grep -v .sh | wc -l)

if [ $PS_STATUS -lt 1 ]; then

        cd /data/NMS/bin
        java FlowCollect.FlowCsvCollectorMain &

fi



