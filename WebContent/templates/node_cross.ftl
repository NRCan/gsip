# get all triple that has a resource as an object and either subject or object are non local (not geoconnex)
CONSTRUCT {?s ?p ?o.}
WHERE {?s ?p ?o. 
FILTER (isIRI(?s) &&  isIRI(?o)
  && 
!(
	STRSTARTS(STR(?s),"https://geoconnex.ca") 
	&& 
	STRSTARTS(STR(?o),"https://geoconnex.ca")
 )
)
}