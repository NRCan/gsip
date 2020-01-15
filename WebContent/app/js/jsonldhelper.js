var JSONLDHELPER = (function(){
	
	//INTERNAL CONSTANTS
	//const SEEALSO = "http://www.w3.org/2000/01/rdf-schema#seeAlso";
	//const SUBJECTOF = "http://schema.org/subjectOf"
	//const LABEL = "http://www.w3.org/2000/01/rdf-schema#label";
	//const FORMAT = "http://purl.org/dc/terms/format";
	//TODO: use a JSON-LD library to do proper mapping between property URIs and local names.  
	const SUBJECTOF = "subjectOf";
	const LABEL = "label";
	const FORMAT = "format";
	const SAMEAS = "sameAs";
	const IMAGE = "image";
	const NAME = "name";
	
	
	//INTERNAL FUNCTIONS
	function hashResource(res_array)
	{	

		// check if the object contains a graph, if so, the objects are in the graph
		if (res_array.hasOwnProperty("@graph"))
			res_array = res_array["@graph"];
		// loop in all object, find their id and load into an object.  id must be unique
		var map = {};
		res_array.forEach(
			function(r) 
			{
				if (r["@id"] !== undefined)
				{
					var id = r["@id"];
					map[id] = r;
					
				}
			});
		return map;
	};
	
	// make sure a property is always an array by converting non array into array of 1, or 0 if undefined
	function castArray(v)
	{
		
		if(v == undefined) return [];
		if (Array.isArray(v))
			return v;
		else
		{
			var a = [];
			a.push(v);
			return a;
		}
	}
	
	function isBlank(r)
	{
		// check if a node is a blank node
		// r is a string, can be either a http:// or a urn:  or a _: something.
		// the latter is a blank node
		return r.startsWith("_:");
	}
	
	function getRepresentation(o,collection)	
	// get the representations 
	// o is the resource, collection is the list of available resources
	// returns an array of resources
	{
		var subjectOf = castArray(o[SUBJECTOF]);
		// find all the see also
		var subjectOfResource = [];
		// subjectOf is not always an array
		
		subjectOf.forEach(
			function(x)
			{ 
			// when there is a proper context, the subjectOf is just a literal value (the id)
			//var id = x["@id"];
			if (collection[x] !== undefined) 
				subjectOfResource.push(collection[x]);
			}
		);
		return subjectOfResource;
	};
	
	// goddam javascript, sometimes string are string, but sometimes they are objects.
	// see https://stackoverflow.com/a/9436948 
	// INTERNAL
	function isString(v)
	{
		return (typeof v === 'string' || v instanceof String);
	}
	
	
	function getLabel(o,lang)
	// get the label, preferably in a given language.  If not found, use a label without any language
	//o is the resource, lang is the preferred language ("fr" or "en")
	{
		var noLang = undefined;
		var thisLang = undefined;
		
		
		var labels = castArray(o[LABEL]);
		labels.forEach(function(x)
		{
			//special case.  if it's a string (not an object), it's the same as "no lang"
			if (isString(x)) 
					noLang = x;
			else{
			if (x["@language"] !== undefined && x["@language"] == lang) thisLang = x["@value"];
			if (x["@language"] == undefined) noLang=x["@value"];
			}
		}
		);

		if (thisLang == undefined) return noLang; else return thisLang;
	};
		
	function filterAssociation(o)
	// get associations array
	//o is the resource
	{
		var props = [];
		for (var property in o) {
			if (o.hasOwnProperty(property)) {
			if (LABEL == property) continue;
			if ("@type" == property) continue;
			if (SUBJECTOF == property) continue;
			if ("@id" == property) continue;
			if (SAMEAS == property) continue;
			if (IMAGE == property) continue;
			if (NAME == property) continue;
			// amélioration ? prendre seulement ceux qui ont des valeurs qui ont un @id
			// (en assumant que toutes les valeurs sont du même type)
			
			props.push(property);
			}
		}
		return props;
	};
	
	
	// return the first resource that has format
	function getRep(o,format)
	{
		
		//var returnRep = undefined;
		var returnRep = [];
		castArray(o).forEach(function(r)
		{
			var f = castArray(r[FORMAT]);
			if(jQuery.inArray(format,f) != -1){
				//returnRep = r;
				returnRep.push(r);
				//return false; // return just return from the forEach.. not the darn function  I hate javascript
		}
		});
		return returnRep;
	}
	
	// return the URL of the resource (subjectOf)
	// if this resource is blank, look for a URL, otherwise, look for @id
	function getUrl(o)
	{
		var id = o["@id"];
		if (isBlank(id))
			return o["url"];
		else
			return id;
		
	}
	
	function hasFormat(o,formatCheck)
	// check if geojson format available in resource
	
	{
		
		var formats = castArray(o[FORMAT]);
		var formatsArray = [];
		jQuery.each(formats, function( i, val ) {
			formatsArray.push(val);
		});
		if(jQuery.inArray('application/vnd.geo+json',formatsArray) != -1){
			return true
		}
		else{
			return false
		};	
	};
	
	//PUBLIC API
	return{
		hashResource: hashResource,
		getRepresentation: getRepresentation,
		getLabel: getLabel,
		filterAssociation: filterAssociation,
		hasFormat: hasFormat,
		castArray: castArray,
		getRep: getRep,
		getUrl: getUrl
	}
	
})();