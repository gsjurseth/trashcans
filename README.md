# Complete project for a complex apigee setup
The idea here is to build a system that requires several disparate backends: SOAP, REST, and something weird (TBD).

The mission is to create an automated setup that though relying on GCP for the backend creates what looks like a
real customer setup.

# The contents
* ## backend
  The backend contains all of the backend pieces. Each one is stored here as it's own project
* ## apigee
  The apigee portion contains all of the api-specific pieces that make this an easily consumable api
* ## frontend
  The frontend pieces containing a consumer frontend as well as an administrator system for adding stock and so on.
