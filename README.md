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

## Deployment

You can create a .war by running maven from the root of the project (where the .pom is)

`mvn package war:war`

You might want to edit the context file in /META-INF/context.xml to set database location first (or edit the file after it is deployed in Tomcat and restart the application)

To execute with local embedded database

Windows 

```
SET GSIP_APP=http://localhost:8080/gsip
SET GSIP_BASEURI=http://localhost:8080/gsip
SET GSIP_TRIPLESTORE=webapp:repos/gsip

mvn cargo:run
```

Linux

```
export GSIP_APP=http://localhost:8080/gsip
export GSIP_BASEURI=http://localhost:8080/gsip
export GSIP_TRIPLESTORE=webapp:repos/gsip

mvn cargo:run
```

            

## Demo application

There is an online demo application (somewhat behind the version on GitHub) at https://geoconnex.ca/gsip/app/index.html
