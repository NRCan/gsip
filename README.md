# Groundwater Surface water Interoperability Pilot

Intro material on the GSIP project

Collaboration between [Environment and Climate Change Canada (ECCC)](https://www.ec.gc.ca) and [Natural Resources Canada (NRCan)](http://www.nrcan.gc.ca/).

[Geological Survey of Canada](http://www.nrcan.gc.ca/earth-sciences/science/geology/gsc/17100), [Canada Centre for Mapping and Earth Observation (CCMEO)](http://www.nrcan.gc.ca/earth-sciences/geomatics/10776), [Laboratoire de cartographie numérique et de photogrammétrie - Commission géologique du Canada -Québec](http://cgq-qgc.ca/en/facilities#LCNP) (part of NRCan)

[Meteorological Service of Canada](https://www.canada.ca/en/services/environment/weather.html), [Water Survey of Canada](https://www.canada.ca/en/environment-climate-change/services/water-overview/quantity/monitoring/survey.html) (part of ECCC)


## Building notes

sqllite support requires a .jar from https://github.com/benstadin/spatialite4-jdbc/blob/master/release/spatialite-jdbc-4.3.0a.jar

you need to download it and then register it in your local maven repo

```mvn install:install-file -Dfile=spatialite-jdbc-4.3.0a.jar -DgroupId=org.xerial -DartifactId=spatialite-jdbc -Dversion=4.3.0a -Dpackaging=jar -DgeneratePom=true```

## Documentation

Find documentation in `/doc` folder

## Demo application

There is an online demo application (somewhat behind the version on GitHub) at https://geoconnex.ca/gsip/app/index.html
