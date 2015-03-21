#!/bin/bash -xe

HOSTS=$1

for device in `adb devices | grep -v "List of devices" | awk '{print $1}'`
do
   echo "Configuring $device"
   adb -s $device remount
   adb -s $device push $HOSTS /system/etc/hosts
   echo "$device configured"
done