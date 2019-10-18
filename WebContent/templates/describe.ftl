PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>
PREFIX dct: <http://purl.org/dc/terms/>
PREFIX schema: <http://schema.org/>
CONSTRUCT {<${resource}> ?p ?o. ?o ?p2 ?o2.}
WHERE {<${resource}> ?p ?o. ?o ?p2 ?o2. }
