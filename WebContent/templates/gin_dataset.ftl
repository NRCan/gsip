@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
@prefix dcat: <http://www.w3.org/ns/dcat#>.
@prefix schema: <http://schema.org/>.
@prefix :      <http://base/#> .
<${resource}> 
schema:subjectOf <${baseUri}/data/dataset/gin/gin/GIN/${p2}>;
dcat:distribution :${p2}-shp,:${p2}-kml,:${p2}-geojson;
<http://schema.org/name> "${p2}".
<${baseUri}/data/dataset/gin/gin/GIN/${p2}>
	dct:format "text/html","application/vnd.geo+json","application/vnd.google-earth.kml+xml","application/x-esri-shape";
	rdfs:label "jeux de donn&eacute;es"@fr,"Dataset"@en;
	dct:conformsTo <https://www.opengis.net/def/gwml2> ;
	schema:provider <http://gin.gw-info.net>.
<#if hasStatements == 'false'>
<${resource}> rdfs:label "${p2}".
</#if>

:${p2}-shp a dcat:Distribution;
dcat:downloadURL <http://gin.gw-info.net/SpatialConversionEngine/gw_data/get/?${p2?replace("gin-","")}?f=ESRI%20Shapefile>;
dct:title "ESRI Shape file distribution"@en,"Distribution en format SHP de ESRI"@fr;
dcat:mediaType "application/x-esri-shape".

:${p2}-kml a dcat:Distribution;
dcat:downloadURL <http://gin.gw-info.net/SpatialConversionEngine/gw_data/get/?${p2?replace("gin-","")}?f=KML>;
dct:title "KML format distribution"@en,"Distribution en format KML"@fr;
dcat:mediaType "application/vnd.google-earth.kml+xml".

:${p2}-geojson a dcat:Distribution;
dcat:downloadURL <http://gin.gw-info.net/SpatialConversionEngine/gw_data/get/?${p2?replace("gin-","")}?f=GeoJSON>;
dct:title "GeoJSON file distribution"@en,"Distribution en format GeoJSON"@fr;
dcat:mediaType "application/vnd.geo+json".
