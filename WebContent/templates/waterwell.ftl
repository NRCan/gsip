@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
@prefix schema: <http://schema.org/>.
<${resource}> 
schema:subjectOf <${baseUri}/data/gwml/gwml1/gsip/gin/${p2}>;
<http://schema.org/name> "${p2}";
<http://schema.org/image> <http://ngwd-bdnes.cits.nrcan.gc.ca/Reference/uri-cgi/feature/gsc/waterwell/${p2?replace("qc.","ca.qc.gov.wells.")}?format=png>.
<${baseUri}/data/gwml/gwml1/gsip/gin/${p2}>
   rdfs:label "Information depuis RIES"@fr,"Information from GIN"@en;
	dct:format "text/xml","text/html","application/vnd.geo+json".
<#if hasStatements == 'false'>
<${resource}> rdfs:label "${p2}".
</#if>
	
	