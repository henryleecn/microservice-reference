# Placebid service

This service uses Socket.js to publish bid updates to users who are viewing the relevant Sale details page

### Getting Started

This service get the user bid requests from JQuery. This service needs the Java web app to be up as it uses the same for posting the bids.
On a successful bid it generates a socketjs event to update the clients watching the same sale details. 

### Prerequisites

NodeJS
At least 1 running instances of all components in the microservice accelerator.

### Installing

Edit the config.json file to update the location of the auction webapp.
Execute ``` node install ``` to download the dependencies.
Execute ```node placebid.js``` to start this service.

### Deployment

This Node.JS module can be zipped and distributed or uploaded to npmjs

### Reference deployment

A reference deployment is done at  http://bangvmpltesappsrv.sapient.com:3000 and is in use for demo.