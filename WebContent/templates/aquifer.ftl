@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
@prefix schema: <http://schema.org/>.
@prefix gw: <https://www.opengis.net/def/gwml2#> .

<${resource}> 
schema:subjectOf <${baseUri}/data/aquifer/gwml/gwml/GIN/${p2}>;
<http://schema.org/name> "${p2}".

<${baseUri}/data/aquifer/gwml/gwml/GIN/${p2}>
	dct:format "text/html","application/vnd.geo+json";
	dct:conformsTo <http://www.opengis.net/def/gwml2>;
	schema:provider <http://gin.gw-info.net> .
<#if hasStatements == 'false'>
<${resource}> rdfs:label "${p2}".
</#if>
	
