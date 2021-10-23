@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
@prefix schema: <https://schema.org/>.
<${resource}> 
schema:subjectOf <${baseUri}/data/gwml/gwml1/gsip/gin/${p2}>;
<https://schema.org/name> "${p2}"; 
<https://schema.org/image> <https://gin.gw-info.net/Reference/uri-cgi/feature/gsc/waterwell/${p2?replace("qc.","ca.qc.gov.wells.")}?format=png>.
<${baseUri}/data/gwml/gwml1/gsip/gin/${p2}>
   rdfs:label "Information depuis RIES"@fr,"Information from GIN"@en;
   schema:provider <http://gin.gw-info.net> ;
   dct:conformsTo <https://www.opengis.net/def/gwml1> ;
	dct:format "text/xml","text/html","application/vnd.geo+json".
<${resource}> 
schema:subjectOf [
    rdfs:label "Information depuis la Cache RIES"@fr,"Information from GIN Cache"@en;
   schema:provider <http://gin.gw-info.net> ;
   dct:conformsTo <https://www.opengis.net/def/gwml2> ;
	dct:format "text/xml";
   schema:url "https://gin.geosciences.ca/GinService/rs/FeatureTypes/query?uri=${resource}"
],
[
   rdfs:label "Page depuis la Cache RIES"@fr,"Page from GIN Cache"@en;
   schema:provider <http://gin.gw-info.net> ;
   dct:conformsTo <https://www.opengis.net/def/gwml2> ;
	dct:format "text/html";
   schema:url "https://gin.geosciences.ca/GinService/rs/view/gin?uri=${resource}"
]
.
<#if hasStatements == 'false'>
<${resource}> rdfs:label "${p2}".
</#if>
	
	