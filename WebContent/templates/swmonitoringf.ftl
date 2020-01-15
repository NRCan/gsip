@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
@prefix hy: <http://geosciences.ca/def/hydraulic#>.
@prefix schema: <http://schema.org/>.
<${resource}> 
schema:subjectOf <${baseUri}/data/swmonitoring/WML2/historical/WSC/${p2}>,<${baseUri}/data/swmonitoring/WFS3/hydrometric-stations/WSC/${p2}>,
<${baseUri}/data/swmonitoring/WFS3/hydrometric-annual-peaks/WSC/${p2}>,
<${baseUri}/data/swmonitoring/WFS3/hydrometric-annual-statistics/WSC/${p2}>,
<${baseUri}/data/swmonitoring/WFS3/hydrometric-monthly-mean/WSC/${p2}>,
<${baseUri}/data/swmonitoring/WFS3/hydrometric-daily-mean/WSC/${p2}>;
<http://schema.org/name> "${p2}";
a hy:HY_HydrometricStation.
<${baseUri}/data/swmonitoring/WML2/historical/WSC/${p2}>
   rdfs:label "Donnees historiques"@fr,"Historical data"@en;
   dct:conformsTo <https://www.opengis.net/def/gwml2> ;
   schema:provider <http://gin.gw-info.net>;
	dct:format "application/vnd.geo+json","text/html".
<${baseUri}/data/swmonitoring/WFS3/hydrometric-stations/WSC/${p2}>
   rdfs:label "Information sur la station"@fr,"Information about the station"@en;
   dct:conformsTo <https://www.opengis.net/def/gwml2> ;
   schema:provider <http://gin.gw-info.net>;
	dct:format "application/vnd.geo+json","text/html".
	
<${baseUri}/data/swmonitoring/WFS3/hydrometric-annual-peaks/WSC/${p2}>
   rdfs:label "Pointes annuelles"@fr,"Annual peaks"@en;
   dct:conformsTo <https://www.opengis.net/def/gwml2> ;
   schema:provider <http://gin.gw-info.net>;
	dct:format "application/vnd.geo+json","text/html".
<${baseUri}/data/swmonitoring/WFS3/hydrometric-annual-statistics/WSC/${p2}>
   rdfs:label "Statistiques annuelles"@fr,"Annual statistics"@en;
   dct:conformsTo <https://www.opengis.net/def/gwml2> ;
   schema:provider <http://gin.gw-info.net>;
	dct:format "application/vnd.geo+json","text/html".
<${baseUri}/data/swmonitoring/WFS3/hydrometric-monthly-mean/WSC/${p2}>
   rdfs:label "Moyennes mensuelles"@fr,"Monthly means"@en;
   dct:conformsTo <https://www.opengis.net/def/gwml2> ;
   schema:provider <http://gin.gw-info.net>;
	dct:format "application/vnd.geo+json","text/html".
<${baseUri}/data/swmonitoring/WFS3/hydrometric-daily-mean/WSC/${p2}>
   rdfs:label "Moyennes quotidiennes"@fr,"Daily means"@en;
   dct:conformsTo <https://www.opengis.net/def/gwml2> ;
   schema:provider <http://gin.gw-info.net>;
	dct:format "application/vnd.geo+json","text/html".

<#if hasStatements == 'false'>
<${resource}> rdfs:label "${p2}".
</#if>
	
	