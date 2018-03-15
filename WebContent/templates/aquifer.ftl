@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
<${resource}> 
rdfs:seeAlso <${baseUri}/data/aquifer/gwml/gwml/GIN/${p2}>;
<http://schema.org/name> "${p2}".
<${baseUri}/data/aquifer/gwml/gwml/GIN/${p2}>
	dct:format "text/html","application/vnd.geo+json".
<#if hasStatements == 'false'>
<${resource}> rdfs:label "${p2}".
</#if>
	
