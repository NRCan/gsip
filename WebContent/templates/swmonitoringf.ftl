@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
<${resource}> 
rdfs:seeAlso <${baseUri}/data/swmonitoring/WML2/historical/WSC/${p2}>;
<http://schema.org/name> "${p2}".
<${baseUri}/data/swmonitoring/WML2/historical/WSC/${p2}>
   rdfs:label "Donnees historiques"@fr,"Historical data"@en;
	dct:format "application/vnd.geo+json","text/html".
<#if hasStatements == 'false'>
<${resource}> rdfs:label "${p2}".
</#if>
	
	