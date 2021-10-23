# Groundwater Surface water Interoperability Pilot

Intro material on the GSIP project

Collaboration between [Environment and Climate Change Canada (ECCC)](https://www.ec.gc.ca) and [Natural Resources Canada (NRCan)](http://www.nrcan.gc.ca/).

[Geological Survey of Canada](http://www.nrcan.gc.ca/earth-sciences/science/geology/gsc/17100), [Canada Centre for Mapping and Earth Observation (CCMEO)](http://www.nrcan.gc.ca/earth-sciences/geomatics/10776), [Laboratoire de cartographie numérique et de photogrammétrie - Commission géologique du Canada -Québec](http://cgq-qgc.ca/en/facilities#LCNP) (part of NRCan)

[Meteorological Service of Canada](https://www.canada.ca/en/services/environment/weather.html), [Water Survey of Canada](https://www.canada.ca/en/environment-climate-change/services/water-overview/quantity/monitoring/survey.html) (part of ECCC)

## Note

This version (merged from sqllite) now need to configure /META-INF/context.xml to add a database connection.  This is only important if you plan to use the demo dataset that comes with this application.
In the worst case, some links will 404.. this is how the web works after all.

## Documentation

Find documentation in `/docs` folder

[Architecture](docs/architecture.adoc)

[User Guide](docs/userguide.adoc)

## Build and use

Dockerfile is multistage, first part will build the code and the second part is a running instance

### Build it

`docker build -t gsip .`

### Run it

`docker run -d -p 8080:8080 --env-file local.env --name gsip gsip`

`local.env` contains values to run on localhost with port 8080.  You'll need to change those values if you change port and run another host

### Use it

A small demo app is available at 

http://localhost:8080/gsip/app/index.html


            

## Demo application

There is an online demo application (somewhat behind the version on GitHub) at https://geoconnex.ca/gsip/app/index.html
