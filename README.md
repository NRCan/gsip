# Groundwater Surface water Interoperability Pilot

Intro material on the GSIP project

Collaboration between [https://www.ec.gc.ca] (Environment and Climate Change Canada) (ECCC), [http://www.nrcan.gc.ca/] (Natural Resources Canada) (NRCan).

[http://www.nrcan.gc.ca/earth-sciences/science/geology/gsc/17100](Geological Survey of Canada) (part of NRCan)

[http://cgq-qgc.ca/en/facilities#LCNP] (Laboratoire de cartographie numérique et de photogrammétrie - CGC-Québec)


## GSIP Mediation Layer

This application provides description and resolution service for non information URI based on the architecture designed for the GSIP pilot.

### Description (a.k.a /info/ page)

Returns an hypermedia describing the resource.  It provides 2 types of informations

   * available representations
   * other linked resources
   
   
   
## Resource structure

It's type and labels and other useful attributes
~~~~
<https://geoconnex.ca/id/catchment/02OJ*CD>  a hy:HY_Catchment; 
 rdfs:label "Bassin versant: Ruisseau Landry - Riviere Richelieu"@fr,"Watershed: Ruisseau Landry - Riviere Richelieu"@en.

~~~~


representations and their formats


~~~~
<https://geoconnex.ca/id/catchment/02OJ*CD> 
	dfs:seeAlso <https://geoconnex.ca/data/catchment/HYF/WSCSSSDA/NRCAN/02OJ*CD>.

<https://geoconnex.ca/data/catchment/HYF/WSCSSSDA/NRCAN/02OJ*CD>
	dct:format "text/html","application/vnd.geo+json".
~~~~

links to other resources

~~~~
<https://geoconnex.ca/id/catchment/02OJ*CD> hy:drains-into <https://geoconnex.ca/id/catchment/02OJ*CE>.
<https://geoconnex.ca/id/catchment/02OJ*CD> hy:overlaps <https://geoconnex.ca/id/hydrogeounits/Richelieu1>.
<https://geoconnex.ca/id/catchment/02OJ*CD> hy:contains <https://geoconnex.ca/id/featureCollection/wellsIn02OJ_CD>;
~~~~

