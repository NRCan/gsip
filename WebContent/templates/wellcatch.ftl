@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
@prefix schema: <http://schema.org/>.
<${resource}> 
schema:subjectOf <${gsip}/resources/wells/${p2?replace("wellsIn","w")}>;
<http://schema.org/name> "Well collection ${p2?replace("wellsIn","")}".
 <${gsip}/resources/wells/${p2?replace("wellsIn","w")}>
    schema:provider <http://gin.gw-info.net> ;
   dct:conformsTo <https://www.opengis.net/def/gwml1> ;
	dct:format "application/vnd.geo+json".