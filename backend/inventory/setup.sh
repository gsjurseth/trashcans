#!/usr/local/bin/bash

curl http://start.spring.io/starter.tgz -d type=gradle-project -d language=java -d bootVersion=1.5.9.RELEASE \
  -d baseDir=trashcansInventory -d groupId=com.apigee.trashcans -d artifactId=trashcansInventory -d name=trashcansInventory \
  -d description=Demo+project+for+Spring+Boot -d packageName=com.apigee.trashcans.trashcansInventory -d packaging=jar \
  -d javaVersion=1.8 -d autocomplete= -d generate-project= -d style=web-services | tar -xzvf -
