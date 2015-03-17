#!/bin/bash -xe

export ENV=$1

./gradlew :app:repackApk

echo $KEY_STORE_PASS | jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore $KEY_STORE_PATH app/build/outputs/apk/app-debug-unsigned.apk $KEY_ALIAS

zipalign -v 4 app/build/outputs/apk/app-debug-unsigned.apk app/build/outputs/apk/app-debug.apk