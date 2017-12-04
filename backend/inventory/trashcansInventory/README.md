# Trashcan Inventory SOAP Backend
This is a simple springboot spring-ws project build to use datastore as a data backend.

## Assumptions 
The whole idea behing this little endeavor is to build a microservice, appengine based,
contract first spring environment for a traditional webservce.

## Requirements
To build these you'll need a java SDK setup ('m using v8). You'll also need gradle and the gcloud command tools setup and ready. Finally, you'll need to set up APPENGINE home as I did myself... Mine is set like so:

```
APPENGINE_HOME=/Users/me/GoogleShtuff/google-cloud-sdk/platform/google_appengine/google/appengine/tools/java
```

With this in place you'll be able to run all of the tests and even run a local version of appengine on your laptop including a locally running datastore backend which is pretty cool.

## How I built it
Mostly I followed this guide:

https://spring.io/guides/gs/producing-web-service/

I then made a number of specific changes to get this to work with my own dependency set

## Changes
* Given that I was working with appengine I had to make some dependency changes in my gradle file. Notably I had to add some exclusions.
* Also, given that I was working with datastore I had to add some additional dependencies to get those pieces inside of it
* Objectify :) .. Objectify is a datastore library I'm using here that usually likes to work with a filter. Unfortunately, I'm working with spring-ws and therefore don't have a web.xml to deal with and so I've added it as a bean to WebServiceConfig.

## How do I test this?
Well, you should be able to simply do the following:

```bash
gradle appengineRun
```
That *should* build it and spin up a locally running instance. If you hit this url: `http://localhost:8085/ws/trashcans.wsdl` then you should get a nice wsdl output that you can imput into SOAPUI if you like.

## How do I deploy this
This assumes you ave a complete environment ready including a gcloud project... But what I do deploy is the following:

```bash
gradle appengineStage
gcloud app deploy build/staged-app/app.yaml --project <insert your project id here> --version <insert a version number here>
```

That should deploy and end give you a url whicn you can hit and append `/ws/trashcans.wsdl` against again... That service can be added to appige for a smashing demo.
