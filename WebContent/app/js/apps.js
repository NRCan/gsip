/*(function(global){

	global.EVT = new EventEmitter2();

	$(document).ready(function(){
		
		mapViewer.init();
		uriModal.init();
		
	});
	
})(window);*/

var EVT = new EventEmitter2();

/* LIST OF PUB/SUB
- init() - Creates instance of map and modal html
- geojson-selected(geosjson) - Adds submitted geojson feature to map playground layer
- uri-selected(uri) - Displays URI in modal
*/

$(document).ready(function(){
	
	EVT.emit("init");
	
});