#!/bin/bash
java -Xms50m -Xmx50m -XX:MaxMetaspaceSize=50m -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:CompressedClassSpaceSize=1m -jar target/nikeloader-0.0.1-SNAPSHOT.jar --spring.config.location=configurations/
