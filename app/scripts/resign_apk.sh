#!/bin/bash -xe

APK_NAME=$1

echo $KEY_STORE_PASS | jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore $KEY_STORE_PATH $APK_NAME $KEY_ALIAS

