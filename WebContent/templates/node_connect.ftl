# get all the triples that has drains-into relationship
CONSTRUCT {?s <https://geoconnex.ca/id/connectedTo> ?o.}
WHERE {?s <https://geoconnex.ca/id/connectedTo> ?o}
