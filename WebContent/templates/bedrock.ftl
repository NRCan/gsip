@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dct: <http://purl.org/dc/terms/>.
@prefix schema: <http://schema.org/>.
<${resource}> 
schema:subjectOf <${gsip}/geo/remote/gsip/rich_bedrock/${p3}>;
<http://schema.org/name> "${p2}".
<${gsip}/geo/remote/gsip/rich_bedrock/${p3}>
	dct:format "application/vnd.geo+json".
<#if hasStatements == 'false'>
<${resource}> rdfs:label "${p2}/${p3}".
</#if>
	
