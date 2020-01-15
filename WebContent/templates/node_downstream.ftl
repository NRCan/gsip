# get all the triples that has drains-into relationship
@prefix hy: <http://geosciences.ca/def/hydraulic#>.
CONSTRUCT {?s hy:drains-into ?o.}
WHERE {?s hy:drains-into ?o}
