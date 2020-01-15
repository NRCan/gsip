// Enable or disable console.log debug mode
const DEBUG_MODE_ENABLED = false;
var debug;
if(DEBUG_MODE_ENABLED){
	debug = printDebugMessage
} else {
	debug = doNothing
};
function printDebugMessage(message){
	console.log(message)
};
function doNothing(){};
// End debug code


/* LIST OF PUB/SUB
- init() - Listener creates instance html modal
- geojson-selected(geosjson) - Emits geojson URL
- uri-selected(uri) - Listener create modal with JSON-LD URI
*/


//App script starts here!

var uriModal = (function(){
		
	var modalHtml = '<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalTitle" aria-hidden="true"><div class="modal-dialog" role="document"><div class="modal-content"><div class="modal-header"><h5 class="modal-title" id="myModalTitle">Modal title</h5><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body">Main content goes here!</div></div></div></div>';
		
	function init(){
	//uriSelected (Modal Trigger)
		
		$( modalHtml ).appendTo( "body" );
		EVT.on("uri-selected",updateModal);

		$('#myModal').draggable({
    		handle: ".modal-dialog"
  		});

		//console.log('GO');
	
		/*$(document).on("click", ".uriSelected" , function(){ 
			debug('on_click.this:');
			debug(this);
			showModalwithFeature($(this).attr('featureuri'));
		});*/
	};
	
	function updateModal(featureuri){
		showModalwithFeature(featureuri);
	};
		
	//Modal display with feature selected
	function showModalwithFeature(featureuri){
		//var myUrl = 'http://s-stf-gin.nrn.nrcan.gc.ca:8085/gsip/id/waterwell/ca.gc.gov.wells.' + featureID + '?f=jsonld';
		var myUrl = featureuri + '?f=jsonld';
		$.ajax({
			url: myUrl,
			dataType: "jsonp",
			//jsonpCallback: "showModal"
			//accepts: {json: "text/javascript"}
			success: function (data) {
				showModal(data,featureuri);
			}
		});
	};
		
	function addGeom2Map(geojsonUrl){
		debug('Add this to map:');
		//mapViewer.playgroundUpdate(geojsonUrl);
		EVT.emit("geojson-selected",geojsonUrl);
	};		
	
	function checkGeojson(arr){
		//var jsonOutput = getJson(z["@id"]);
		//debug('jsonOutput:');
		//debug(jsonOutput);
		//$('.geojsonLink').remove();
		console.info('here');
		console.info(arr);
		$.each(arr, function(i,z){
			console.info(i);
			console.info(z);
			var myUrl = z['@id'] + '?f=jsonld';
			debug(myUrl);
			var proplabel = "";
			if(z['proplabel']){
				proplabel = z['proplabel']
			};

			$.ajax({
				url: myUrl,
				dataType: "jsonp",
				//jsonpCallback: "showModal"
				//accepts: {json: "text/javascript"}
				success: function (data) {
					checkGeojson_reponse(data,z['@id'],proplabel,i);
				},
				error: function () {
					debug('error');
				}
			});

		});

	};

	function convertUri2Id(uri){
		/*
		var liId = uri.split('://')[1];
		var x = liId.search('/');
		var y = liId.substring(x);
		var z = y.replace(/\//g, '-');
		return z.replace(',','-');
		*/
		return hash(uri);
	};		
	
// turn a string into a hash
// taken from http://werxltd.com/wp/2010/05/13/javascript-implementation-of-javas-string-hashcode-method/
function hash(s) {
  var hash = 0, i, chr;
  if (s.length === 0) return hash;
  for (i = 0; i < s.length; i++) {
    chr   = s.charCodeAt(i);
    hash  = ((hash << 5) - hash) + chr;
    hash |= 0; // Convert to 32bit integer
  }
  return hash;
};

	
	function checkGeojson_reponse(data,uri,proplabel,i){
		debug('i:');debug(i);
		debug('data:');debug(data);
		debug('uri:');debug(uri);
		var resources = JSONLDHELPER.hashResource(data);
		var o = resources[uri];
		var reps = JSONLDHELPER.getRepresentation(o,resources);
		//debug('var_s See Also Resource:');
		//debug(s);
		
		
		var rep = JSONLDHELPER.getRep(reps,'application/vnd.geo+json');
		//var hasGeojson = JSONLDHELPER.hasFormat(rep,'application/vnd.geo+json');

		//console.warn('rep:');
		//console.warn(rep);

		rep.forEach(function(r,i){

			//console.warn('r:');
			//console.warn(r);
			//console.warn('i:');
			//console.warn(i);
			//console.warn('r[@id]');
			//console.warn(r['@id']);

			var uri_id = convertUri2Id(proplabel + uri);

			//console.warn('uri_id:');
			//console.warn(uri_id);

			var curentGeojsonURL = addUrlParameter(JSONLDHELPER.getUrl(r),'f','geojson');

			$('#' + uri_id).append( " <a geojson='" + curentGeojsonURL + "' class='geojsonLink' href=''> <img title='Get geometry' alt='Get geometry' src='img/mapicon.png' width='15px'/></a>");
			
			//$('#' + uri_id + ' a.geojsonLink').attr('geojson', addUrlParameter(r['@id'],'f','geojson'));



		});
		

		/*
		if(rep != undefined){
			$('#' + uri_id).append( " <a class='geojsonLink' href=''> <img title='Get geometry' alt='Get geometry' src='img/mapicon.png' width='15px'/></a>");
			
			$('#' + uri_id + ' a.geojsonLink').attr('geojson', addUrlParameter(rep['@id'],'f','geojson'));
		
		};*/
		
			
	};
	
	function addUrlParameter(url,name,value)
	{
		// already a separator
		if (url.endsWith('?') || url.endsWith('&'))
			return url + name + "=" + value;
		// if the url already contains a '?', it already contains parameters (at least one)
		if (url.includes('?')) return url + '&'+ name + "=" + value;
		// otherwise, no param, must use '?'
		return url + '?'+ name + "=" + value;
	}
	
	function getAssociations_Html(props_obj){
		var footer_html	= '';

		// Situation bizzare. Ici je suis obligé d'effacer les événements clicks pécédents si présents afin d'éviter d'empiler plusieurs fonctions onclick sur le même objet du DOM. Le problème se voyait lorsqu'on naviguait dans les ressources associées du Bootstrap Modal. 
		$(document).off("click", ".getLinkedData");	

			//console.warn('props_obj:');
			//console.warn(props_obj);
					
		props_obj.forEach(function(z)
		{
			if (typeof(z) == "undefined") return; // skip undefined variables
			

			//console.info('checkGeojson association');
			//console.info(z);
			//var geojsonConfirmation = checkGeojson(z);
		
			var current_id = convertUri2Id(z['proplabel'] + z['@id']);
		
			footer_html = footer_html +'<li id="' + current_id + '"><strong>' + z['proplabel'] + '</strong>: ' + JSONLDHELPER.getLabel(z,'en') + ' <a href="" class="getLinkedData" featureuri="' + z['@id'] + '"><img src="img/linkedData.png" title="Linked data" alt="Linked data" width="15px"/></a>' + '<a target="_blank" href="' + z['@id'] +'">' + '<img title="View linked data in web page" alt="View linked data in web page" src="img/htmllogo.png" width="25px"/></a>' + '</li>';
			
			console.warn(current_id);

			/*$(document).on("click", "#" + current_id +  " a[getLinkedData]" , function(event){ 
				event.preventDefault();
				debug('on_click.this:');
				debug(this);
				//showModalwithFeature($(this).attr('featureuri'));
				//console.warn($(this).attr('featureuri'));
				EVT.emit("uri-selected", $(this).attr('featureuri'));
			});	*/



			/*var li_id = z['@id'].split('://')[1];
		
			footer_html = footer_html +'<li id="' + li_id + '"><a target="_blank" href="' + z['@id'] +'">' + getLabel(z,'fr') + '</a></li>';*/
		});
		
		
		if(props_obj.length  == 0){
		 return ''
		}
		else{
		footer_html = '<hr/>Links: <ul>'+ footer_html + '</ul>'
		};
		return footer_html
	
	};
			
	function showModal(json,featureuri){


		function getPropsObjects(o,props){
			var propsObjCollection = [];
			props.forEach(function(x)
			{
				// x is the property name
			 JSONLDHELPER.castArray(o[x]).forEach(function(y)
			 {
				 
				if (typeof(y) === 'object') y = y["@id"]; // if no @id, not much we can do
			 	var tag = convertUri2Id(x+y);
				//var res = y["@id"];
				//propsObjCollection.push(resources[res]);
				if (typeof(resources[y]) == "undefined")
				{
					// I don't have a description of this object.  Make a dummy one
					// TODO: get the object by resolving it ? Need a service that return a brief version (?describe=true)
					var segments = y.split("/");
					var placeholder = {"@id":y, "label" : segments[segments.length-1],"tag":tag,"proplabel":x};
					propsObjCollection.push(placeholder);
				}
				else
					var placeholder = {"@id":y, "label" : resources[y].label,"tag":tag,"proplabel":x};
					propsObjCollection.push(placeholder);
			 });
			});
			return propsObjCollection;
		};	
		
		debug('JSON:');
		debug(json);
		var resources = JSONLDHELPER.hashResource(json);
		debug('var_resources Resources:');
		debug(resources);
		
		var o = resources[featureuri];
		debug('var_o Principal Resource:');
		debug(o);
		
		// aller chercher la liste des representation
		var s = JSONLDHELPER.getRepresentation(o,resources);
		debug('var_s See Also Resource:');
		debug(s);
		
		// afficher un label pour une resource
		debug('Principal Resource Label:');
		debug(JSONLDHELPER.getLabel(o,"en"));
		
		
		// aller chercher la liste des associations
		// ceci retourne un array de array (chaque propriété peux avoir plusieurs valeurs)
		/** [
			assoc1 : [v1,v2],
			assoc2: [v1,v2]
			]
		**/
		var props = JSONLDHELPER.filterAssociation(o);
		debug('Associations list:');
		debug(props);
		
		var props_collection = getPropsObjects(o,props);
		debug('Association collection:');
		debug(props_collection);
		
		/*
		// aller chercher la première propriété [0] et la première valeur de cette propriété [0] et aller chercher le @id
		// j'assume qu'il y a un @id - il est possible que ce soit un literal.
		var res = o[props[0]][0]["@id"];
		debug('Demo: aller chercher la première propriété [0] et la première valeur de cette propriété [0] et aller chercher le @id');
		debug(res);
		// aller chercher cette resource.
		debug('Demo - suite: aller chercher cette resource');
		debug(resources[res]);
		*/
		
		var conv_id = convertUri2Id(featureuri);
		//console.info(conv_id);

		var title_html = '<span id="'+ conv_id +'">' + JSONLDHELPER.getLabel(o,'en') + ' <a title="Get Resource" target="_blank" href="' + featureuri + '">' + '<img src="img/htmllogo.png" title="View linked data in web page" alt="View linked data in web page" width="25px"/> </a></span>';

		var associations_html = getAssociations_Html(props_collection);
		
		var modalBody_html = title_html + associations_html;
		
		$('#myModalTitle').html('Linked data');
		$('#myModal .modal-dialog .modal-content .modal-body').html(modalBody_html);
		//$('#myModal .modal-dialog .modal-content .modal-footer').html(modalFooter_html);
		

		
		var collection = props_collection;
		collection.push(o);
		console.log('All resources:');
		console.log(collection);
		checkGeojson(collection);
		/**/
		//checkGeojson(o);
		//console.info('checkGeojson priciaple resource');
		//console.info(o);

		addLinkedDataIconEvent();
		addGeojsonIconEvent();

		$('#myModal').modal('show');
			
	};

	function addGeojsonIconEvent(){
		/*$('a[geojsonLink]').click(function(event){
			event.preventDefault();	
			//addGeom2Map();		
			addGeom2Map($(this).attr('geojson'))
		});*/

		$(document).on("click",".geojsonLink",function(event){
			event.preventDefault();
			//console.log($(this).attr('geojson'));
			addGeom2Map($(this).attr('geojson'))
		});

	};

	function addLinkedDataIconEvent(){

			$(document).on("click", ".getLinkedData" , function(event){ 
				event.preventDefault();
				debug('on_click.this:');
				debug(this);
				//showModalwithFeature($(this).attr('featureuri'));

				//console.warn($(this).attr('featureuri'));

				EVT.emit("uri-selected", $(this).attr('featureuri'));
			});	

	};

// Event Listners

	EVT.on('init',init);
	
// Public API	

	return{
		init:init,
		updateModal:updateModal
	}
	
	})();	
		
//$(document).ready(uriModal.init);
	
	
