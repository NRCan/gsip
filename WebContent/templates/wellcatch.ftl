@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
<${resource}> 
rdfs:seeAlso <${gsip}/resources/wells/${p2?replace("wellsIn","w")}>;
<http://schema.org/name> "Well collection ${p2?replace("wellsIn","")}".
 <${gsip}/resources/wells/${p2?replace("wellsIn","w")}>
	dct:format "application/vnd.geo+json".