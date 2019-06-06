/* LIST OF PUB/SUB
- init() - Listener creates instance of map
- geojson-selected(geosjson) - Listener adds submitted geojson (from feature URL) to map playground layer
- uri-selected(uri) - Emits selected map feature URI
*/

var mapViewer = (function(){
	
	var map;
	
//STYLES CREATION - Base Points Layer 
	
	var createTextStyle = function(feature, resolution, myPointStyleConf) {
        return new ol.style.Text({
          textAlign: myPointStyleConf.align,
          textBaseline: myPointStyleConf.baseline,
          font: myPointStyleConf.weight + ' ' + myPointStyleConf.size + ' ' + myPointStyleConf.font,
		  text: getText(feature, resolution, myPointStyleConf),
		  fill: new ol.style.Fill({color: myPointStyleConf.color}),
          stroke: new ol.style.Stroke({color: myPointStyleConf.outline, width: myPointStyleConf.outlineWidth}),
          offsetX: myPointStyleConf.offsetX,
          offsetY: myPointStyleConf.offsetY,		 
          rotation: myPointStyleConf.rotation
        });
	};
		
	var myPointStyleConf = {
		  text: 'shorten',
          align: 'center',
          baseline: 'middle',
          rotation: 0,
          font: 'Arial',
          weight: 'normal',
          size: '12px',
          offsetX: 0,
          offsetY: 12,
          color: '#aa3300',
          outline: '#ffffff',
          outlineWidth: 3,
          maxreso: 35
	};

	var getText = function(feature, resolution, myPointStyleConf) {
		var type = myPointStyleConf.text.value;
		var maxResolution = myPointStyleConf.maxreso;
		var text = feature.get('statid');

		if (resolution > maxResolution) {
			text = '';
		} else if (type == 'hide') {
          text = '';
        } else if (type == 'shorten') {
          text = text.trunc(12);
        } else if (type == 'wrap') {
          text = stringDivider(text, 16, '\n');
        }

        return text;
	};
	
	var pointStyleFeature = new ol.style.Circle({
            radius: 4,
            fill: new ol.style.Fill({color: 'rgba(255, 0, 0, 0.1)'}),
            stroke: new ol.style.Stroke({color: 'red', width: 1})
          });
	
	
	function pointStyleFunction(feature, resolution) {
        return new ol.style.Style({
          image: pointStyleFeature/*,
          text: createTextStyle(feature, resolution, myPointStyleConf)*/
        });
      };
	  
	  	
//LAYER CREATION - Base Points Layer
		/*
		var vectorPoints = new ol.layer.Vector({
			source: new ol.source.Vector({
			url: 'rich_v3.geojson',
			format: new ol.format.GeoJSON()
			}),
			style: pointStyleFunction
		});	
		vectorPoints.set('name','vectorPoints');

*/
		var vectorPolygons = new ol.layer.Vector({
			source: new ol.source.Vector({
			url: '../resources/catchment/catchments',
			format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
			  stroke: new ol.style.Stroke({
				//color: 'blue',
				color: 'rgba(51, 153, 255, 1)',
				//lineDash: [4],
				width: 2
			  }),
			  fill: new ol.style.Fill({
				color: 'rgba(0, 0, 255, 0.1)'
			  })
			})
		});
		vectorPolygons.set('name','vectorPolygons');

		

//STYLES CREATION - Playground Layer
		
		var image = new ol.style.Circle({
			radius: 5,
			fill: null,
			stroke: new ol.style.Stroke({color: 'red', width: 1})
		});
		
		var styles = {
			'Point': new ol.style.Style({
			  image: image
			}),
			'LineString': new ol.style.Style({
			  stroke: new ol.style.Stroke({
				color: 'green',
				width: 2
			  })
			}),
			'MultiLineString': new ol.style.Style({
			  stroke: new ol.style.Stroke({
				color: 'green',
				width: 2
			  })
			}),
			'MultiPoint': new ol.style.Style({
			  image: image
			}),
			'MultiPolygon': new ol.style.Style({
			  stroke: new ol.style.Stroke({
				color: 'red',
				width: 2
			  }),
			  fill: new ol.style.Fill({
				color: 'rgba(217, 137, 225, 0.1)'
			  })
			}),
			'Polygon': new ol.style.Style({
			  stroke: new ol.style.Stroke({
				color: 'blue',
				lineDash: [4],
				width: 2
			  }),
			  fill: new ol.style.Fill({
				color: 'rgba(0, 0, 255, 0.1)'
			  })
			}),
			'GeometryCollection': new ol.style.Style({
			  stroke: new ol.style.Stroke({
				color: 'magenta',
				width: 2
			  }),
			  fill: new ol.style.Fill({
				color: 'magenta'
			  }),
			  image: new ol.style.Circle({
				radius: 10,
				fill: null,
				stroke: new ol.style.Stroke({
				  color: 'magenta'
				})
			  })
			}),
			'Circle': new ol.style.Style({
			  stroke: new ol.style.Stroke({
				color: 'red',
				width: 2
			  }),
			  fill: new ol.style.Fill({
				color: 'rgba(255,0,0,0.2)'
			  })
			})
		};	
	
		 var styleFunction = function(feature) {
		   return styles[feature.getGeometry().getType()];
		 };
	
	
//LAYER CREATION - Playground Layer (Layer used for loading external LD GeoJSON)

		var vectorPlayground = new ol.layer.Vector({
			source:new ol.source.Vector(),
			style: styleFunction
		});
		
		vectorPlayground.set('name','vectorPlayground');
		
//Map creation	

	function init(){

	EVT.on("geojson-selected",playgroundUpdate);


      /**
       * Define a namespace for the application.
       */
      window.app = {};
      var app = window.app;

      /**
       * @constructor
       * @extends {ol.control.Control}
       * @param {Object=} opt_options Control options.
       */
      app.mapClickServiceControl = function(opt_options) {

		var options = opt_options || {};

        var button = document.createElement('button');
        button.innerHTML = 'H';

        var this_ = this;
        var handleMapClickService = function() {

          map.cmap.clickService = "chyf";

          /*if (map.cmap.clickService == "chyf"){
          	map.cmap.clickService = "";
          	
          }
          else{
          	map.cmap.clickService = "chyf";
          };*/
          //console.info(map.cmap.clickService);
        };

        button.addEventListener('click', handleMapClickService, false);
        button.addEventListener('touchstart', handleMapClickService, false);

        var element = document.createElement('div');
        element.className = 'chyf ol-unselectable ol-control';
        element.appendChild(button);

        ol.control.Control.call(this, {
          element: element,
          target: options.target
        });

      };

      ol.inherits(app.mapClickServiceControl, ol.control.Control);
    

	map = new ol.Map({
        layers: [
          /*new ol.layer.Tile({
            source: new ol.source.OSM()
          }),*/
          new ol.layer.Image({
          		//extent: [-13884991, 2870341, -7455066, 6338219],
          		source: new ol.source.ImageWMS({
            		url: 'http://geogratis.gc.ca/maps/CBMT',
            		params: {'LAYERS': 'CBMT'},
           			ratio: 1,
            		serverType: 'geoserver'
          		})
          }),
          /*
          new ol.layer.Tile({
              opacity: 1,
              source: new ol.source.WMTS((options))
          }),*/
		  //vectorPoints, vectorPlayground 
		  //vectorPolygons, vectorPlayground, vectorPoints
		  vectorPolygons, vectorPlayground
        ],
        target: 'map',
        controls: ol.control.defaults({
          attributionOptions: /** @type {olx.control.AttributionOptions} */ ({
            collapsible: false
          })
        }).extend([
          new app.mapClickServiceControl()
        ]),
        view: new ol.View({
		  center: [0, 0],
          zoom: 1
        })
	});

	
	/*vectorPoints.getSource().once('change',function(e){
		if(vectorPoints.getSource().getState() === 'ready') {
			var extent = vectorPoints.getSource().getExtent();
			map.getView().fit(extent, map.getSize());
		}
	});*/

	vectorPolygons.getSource().once('change',function(e){
		if(vectorPolygons.getSource().getState() === 'ready') {
			var extent = vectorPolygons.getSource().getExtent();
			map.getView().fit(extent, map.getSize());
		}
	});
	
	//Map behaviour

	
	//This is where we registerer mapclick event type. By default NULL.
	map.cmap = {};
    map.cmap.clickService = '';



	var element = document.getElementById('popup');
	
	var popup = new ol.Overlay({
        element: element,
        positioning: 'bottom-center',
        stopEvent: false,
        offset: [5, 0]
	});
    
	map.addOverlay(popup);
	
	map.on('click', function(evt) {
		var pixel = evt.pixel;
		//console.log('evt.pixel:');
		//console.log(evt.pixel);
		var clickCoordinates = evt.coordinate;

		var clickCoordinates_longlat = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
		//console.log('evt.coordinate:');
		//console.log(evt.coordinate);
		if(map.cmap.clickService != ''){
			if(map.cmap.clickService == 'chyf'){
				displayChyfInfo(pixel,clickCoordinates_longlat);
				map.cmap.clickService = '';
			};
		}
		else{
			displayFeatureInfo(pixel,clickCoordinates);
		}
	});
	
	var displayChyfInfo = function(pixel,clickCoordinates){

		$('#inputModal').attr('pixel',pixel);
		$('#inputModal').attr('clickCoordinates',clickCoordinates);

		$('#inputModal .modal-dialog .modal-content .modal-header .modal-title').html('Choose a query type');
		var htmlContent = '<div>Flowpath:<ul><li class="querytype" type="f_fp"><a href="">from point</a></li><li class="querytype" type="f_uop"><a href="">upstream of point</a></li><li class="querytype" type="f_dop"><a href="">downstream of point</a></li></ul>Catchments:<ul><li class="querytype" type="c_cp"><a href="">containing point</a></li><li class="querytype" type="c_uop"><a href="">upstream of point</a></li><li class="querytype" type="c_dop"><a href="">downstream of point</a></li></ul>Drainage Area:  <input type="checkbox" name="removeholes" id="removeholes" checked="checked"/> Remove Holes<ul><li class="querytype" type="d_uop"><a href="">upstream of poin</a>t</li><li class="querytype" type="d_dop"><a href="">downstream of point</a></li></ul></div>';

		$('#inputModal .modal-dialog .modal-content .modal-body').html(htmlContent);

		$('.querytype a').click(function(e){e.preventDefault();});

		$('.querytype').click(function(){
			//alert(this.type);
			queryChyf(this.type,clickCoordinates)
		})
		$('#inputModal').modal('show');

	};

	function queryChyf(type,coordinates){

		$('#inputModal').modal('hide');

		var removeHoles = '';
		if($('#removeholes').prop('checked')) {
    		removeHoles = true;
		} else {
    		removeHoles = false;
		};

		var parameter = '';
		if(type.indexOf("d_") >= 0){
			parameter = '&removeHoles=' +  removeHoles;
		}

		var queryTypes = {f_fp:"eflowpath/flowsFrom",f_uop:"eflowpath/upstreamOf",f_dop:"eflowpath/downstreamOf",c_cp:"ecatchment/containing",c_uop:"ecatchment/upstreamOf",c_dop:"ecatchment/downstreamOf",d_uop:"drainageArea/upstreamOf",d_dop:"drainageArea/downstreamOf"};

		var serviceUrl = "/gsip/data/api/CHYF/" + queryTypes[type] + "?f=geojson&point=" + coordinates + parameter;


		EVT.emit("geojson-selected",serviceUrl);

	};

	var displayFeatureInfo = function(pixel,clickCoordinates) {
		var features = [];
		map.forEachFeatureAtPixel(pixel,
			function(feature, layer) {
				features.push(feature);
			});			

		if (features.length > 0) {
			
          var info = [];
		  //console.log(features);
		  
		  if (features.length > 6){
			info.push( 'Found ' + features.length + ' features. <br/> Zoom in to query')
		  }else{
			for (var i = 0, ii = features.length; i < ii; ++i) {
				//info.push(' <a title="Get Resource" target="_blank" href="http://s-stf-ngwd.nrn.nrcan.gc.ca:8085/cocoon/devHeryk/lod/uri?uri=' + features[i].get('uri') + '">' + features[i].get('name') +'</a> ' + '<a href="" class="uriSelected" featureuri="' + features[i].get('uri') + '"><img src="img/linkedData.png" title="Linked data" alt="Linked data" width="15px"/></a>');
				info.push(' <a title="Get Resource" href="" class="geturiwebpage" featureuri="' + features[i].get('uri') + '">' + features[i].get('name') +'</a> ' + '<a href="" class="uriSelected" featureuri="' + features[i].get('uri') + '"><img src="img/linkedData.png" title="Linked data" alt="Linked data" width="15px"/></a>');
			}
		  }
		  
		  var coordinates = features[0].getGeometry().getCoordinates();
		  popup.setPosition(clickCoordinates);
		  
		  if($('.popover')){
			$(element).popover('dispose');
		  };
		  
		  $(element).popover({
				'placement': 'top',
				'html': true,
				'content': info.join('<br/>')
		  });
			
          $(element).popover('show');
		  
		  //Removes popover when "linked data" link clicked on
		  $('.uriSelected').on('click',function(){$(element).popover('dispose')});
		  
        } else {
          $(element).popover('dispose');
        }		
	};
	 
	$(document).on("click", ".geturiwebpage" , function(event){ 
			event.preventDefault();
			debug('on_click .getwebpage .this:');
			debug(this);
			//showModalwithFeature($(this).attr('featureuri'));
			//EVT.emit("uri-selected", $(this).attr('featureuri'));
			var featureuri = $(this).attr('featureuri');
			var myUrl = featureuri + '?f=jsonld';
			$.ajax({
				url: myUrl,
				dataType: "jsonp",
				//jsonpCallback: "showModal"
				//accepts: {json: "text/javascript"}
				success: function (data) {
					// Hash json data
					var resources = JSONLDHELPER.hashResource(data);
					// Get principal resource in Hash
					var o = resources[featureuri];
					// Get list os representations (See also)
					var reps = JSONLDHELPER.getRepresentation(o,resources);
					// Get representations of format text/html 
					var rep = JSONLDHELPER.getRep(reps,'text/html');
					// Link of page to open
					var urlOfHtmlFormat = featureuri;
					// If there are representations of format text/html, get the URL of the first one 
					if(rep[0]["@id"]!=null){
						urlOfHtmlFormat = rep[0]["@id"];
					};
					window.open(urlOfHtmlFormat,'_blank');
				}
			});
	});


	$(document).on("click", ".uriSelected" , function(event){ 
			event.preventDefault();
			debug('on_click.this:');
			debug(this);
			//showModalwithFeature($(this).attr('featureuri'));
			EVT.emit("uri-selected", $(this).attr('featureuri'));
	});
	
	// change feature style when over marker	  
	var highlightedFeatures = [],
	
	selectStyle_shadow = new ol.style.Style({
		image: new ol.style.Circle({
			radius: 7,
			fill: new ol.style.Fill({color: 'rgba(0, 0, 0, 0.2)'}),
			/*stroke: new ol.style.Stroke({
				color: '#ffffff',
				width: 2
			})*/
		}),  
		zIndex: 1
	});
	
	var selectStyle_main = new ol.style.Style({
		image: new ol.style.Circle({
			radius: 4,
			fill: new ol.style.Fill({color: 'rgba(255, 140, 0, 1)'}),
			stroke: new ol.style.Stroke({
				//color: '#ff8C00',
				color: '#000000',
				width: 1
			})
		}),  
		zIndex: 2
	});

	map.on('pointermove', function(e) {
		var i;
		for (i = 0; i < highlightedFeatures.length; i++) {
			highlightedFeatures[i].setStyle(pointStyleFunction)
		};
		highlightedFeatures = [];
		map.forEachFeatureAtPixel(e.pixel, 
			function(feature) {
				feature.setStyle([selectStyle_shadow,selectStyle_main]);
				highlightedFeatures.push(feature);
			},
			{layerFilter: 
				function (layer) {
				return layer.get('name') === 'vectorPoints';
				}
			}
		);
	});		
	
	// change mouse cursor when over marker
	map.on('pointermove', function(e) {
        if (e.dragging) {
          $(element).popover('dispose');
          return;
        }
		var pixel = map.getEventPixel(e.originalEvent);
		var hit = map.hasFeatureAtPixel(pixel);
		map.getTargetElement().style.cursor = hit ? 'pointer' : '';
	});

	
	};
	
	
//Map manipulation	

	function playgroundUpdate(geojsonUrl){
		
		var addNewSource = new ol.source.Vector({
			url: geojsonUrl,
			format: new ol.format.GeoJSON()
		});
		
		map.getLayers().forEach(function(el) {
			if (el.get('name') === 'vectorPlayground') {
				
				//*if(el.source !== "undefined"){el.source.clear(true)};
				el.setSource(addNewSource);
				//console.log(el);
			}
		});
		
	};
	

// Event Listners

	EVT.on('init',init);
	
// Public API

	return{
		init:init,
		playgroundUpdate:playgroundUpdate
	};
})();

//$(document).ready(mapViewer.init);