
./gradlew appengineStage
gcloud app deploy build/staged-app/app.yaml --project [app id] --version [some version]
