@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
@prefix schema: <http://schema.org/>.
<${resource}> 
schema:subjectOf [
	schema:url "${gsip}/geo/remote/gsip/rich_bedrock/${p3}";
	dct:format "application/vnd.geo+json";
    schema:provider <http://gin.gw-info.net>
].

<#if hasStatements == 'false'>
<${resource}> rdfs:label "${p2}/${p3}".
</#if>
	
