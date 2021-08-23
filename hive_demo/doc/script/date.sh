#!/bin/bash

EXPORT_START_DATE=$1
EXPORT_END_DATE=$2

i=$EXPORT_START_DATE
while [[ $i < `date -d "+1 day $EXPORT_END_DATE" +%Y-%m-%d` ]]
  do
    echo $i
    i=`date -d "+1 day $i" +%Y-%m-%d`
done
