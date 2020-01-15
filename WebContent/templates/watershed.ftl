@prefix dc: <http://purl.org/dc/elements/1.1/> .
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix sesame: <http://www.openrdf.org/schema/sesame#> .
@prefix owl: <http://www.w3.org/2002/07/owl#> .
@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .
@prefix fn: <http://www.w3.org/2005/xpath-functions#> .
@prefix foaf: <http://xmlns.com/foaf/0.1/> .
@prefix dct: <http://purl.org/dc/terms/>.
@prefix hy: <http://geosciences.ca/def/hydraulic#>.
@prefix dcat: <http://www.w3.org/ns/dcat#>.
@prefix ex: <http://gin.gw-info.net/example#>.
@prefix schema: <http://schema.org/>.

<${resource}> a <http://geosciences.ca/def/hyf/1.0#Watershed>;
rdfs:label "Upstream watershed of ${p2}"@en,"Bassin versant en amont de ${p2}"@fr;
schema:subjectOf
	<${baseUri}/data/waterbody/CHYF/POS/CHYF/${p2}>.


<${baseUri}/data/waterbody/CHYF/POS/CHYF/${p2}>
rdfs:label  "CHyF upstream watershed representation upstream of ${p2}"@en,
					"Representation de CHyF en amont en amont de ${p2}"@fr;
    	dct:conformsTo <https://www.opengis.net/def/hyf> ;
    	schema:provider <https://chyf.ca/>;
		dct:format "application/vnd.geo+json","text/html";
		dct:language "en","fr".

		