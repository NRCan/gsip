PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>
PREFIX dct: <http://purl.org/dc/terms/>
CONSTRUCT {<${resource}> ?p ?o. ?o rdfs:label ?o2. ?o dct:format ?o3}
WHERE {<${resource}> ?p ?o. OPTIONAL {?o rdfs:label ?o2}. OPTIONAL {?o dct:format ?o3}. }
LIMIT 100