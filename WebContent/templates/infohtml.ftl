<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<link rel="icon" href="${host}/app/img/favicon.ico" />
<link rel="canonical" href="${model.getContextResourceUri()}"/>
<title>Resource</title>
<link href="${host}/app/css/bootstrapmin.css" rel="stylesheet" />
<link href="${host}/app/css/navbartopfixed.css" rel="stylesheet" />
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
<style type="text/css">
/* Footer */
.mastfoot {
	color: rgba(255, 255, 255, .5);
	display: -ms-flexbox;
	display: -webkit-box;
	display: flex;
	-ms-flex-pack: center;
	-webkit-box-pack: center;
	justify-content: center;
	color: #fff;
	/*text-shadow: 0 .05rem .1rem rgba(0, 0, 0, .5);*/
	box-shadow: inset 0 0 5rem rgba(0, 0, 0, .1);
	padding: 10px 10px 10px 10px;
}



      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

/* Sticky footer styles
-------------------------------------------------- */
html {
  position: relative;
  min-height: 100%;
}
body {
  /* Margin bottom by footer height */
  margin-bottom: 60px;
}
.footer {
  position: absolute;
  bottom: 0;
  width: 100%;
  /* Set the fixed height of the footer here */
  height: 60px;
  line-height: 60px; /* Vertically center the text there */
  background-color: #f5f5f5;
}



</style>
<script language="" type="application/ld+json">
${model.encode("JSON-LD")}
</script>

    <!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/4.5/examples/sticky-footer-navbar/sticky-footer-navbar.css" rel="stylesheet">


</head>
<body>
	<header>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<img src="${host}/app/img/logominwhiteback25px.png"
			alt="" /> <a class="navbar-brand" href="#" style="padding-left: 4px;">GSIP
			Linked Data Demonstration</a>
	</nav>
	</header>

	<main role="main" class="container">
		<div class="row">
			<div class="col-sm-12">
				<h1>${model.getPreferredLabel("en","Unamed")}</h1>
				<blockquote class="blockquote container">
					<div class="row">
						<div class="col-sm-12 col-md-9">
							<p class="mb-0">
								<strong>Type: </strong>
								<samp>${model.getTypeLabel()}</samp>
							</p>
							<p class="mb-0">
								<strong>Identifier: </strong>
								<samp>
									<a href="${model.getContextResourceUri()}">${model.getContextResourceUri()}</a>
								</samp>
							</p>
							<p><small>(Unclassified information)</small></p>
						</div>
						<div class="col-sm-12 col-md-3">
							<a href="${model.getContextResourceUri()}?f=rdf"
								target="_blank"><img class="img-fluid"
								title="Display page in RDF/XML format"
								alt="View in RDF/XML format"
								src="${host}/app/img/rdfxmlicon.png"
								style="max-width: 35px; padding: 10px 5px 0 5px" />
							</a>
							<a
								href="${model.getContextResourceUri()}?f=json"
								target="_blank"><img class="img-fluid"
								title="Display page in JSON-LD format"
								alt="View in JSON-LD format"
								src="${host}/app/img/jsonicon.png"
								style="max-width: 35px; padding: 10px 5px 0 5px" /></a>
							<a
								href="${model.getContextResourceUri()}?f=ttl"
								target="_blank"><img class="img-fluid"
								title="Display page in TTL format" alt="View in TTL format"
								src="${host}/app/img/ttlicon.png"
								style="max-width: 35px; padding: 10px 5px 0 5px" /></a>

							<a href="${model.getNonInfoUri()}LOD_Node/CAN_Hydro_LOD_Node" 
								target="_blank"> <img class="img_fluid"  
								title="This node" alt="Access this node" src="${host}/app/img/node.png" 
								style="max-width: 35px; padding: 10px 5px 0 5px" /></a>
						</div>
					</div>
				</blockquote>
				<div class="container">
					<div class="row">
						<#assign image = model.getPropertyResource("https://schema.org/image")>
						<#if image?has_content>
						<div class="col-sm-12 col-md-8">
							<#else>
						<div class="col-sm-12 col-md-12">
							</#if>
							<!--<small>(unclassified - non classifié)</small>-->
							<h3>Available Representations:</h3>
							<!--<i class="material-icons">arrow_right</i><i class="material-icons">arrow_drop_down</i>-->

							<#assign collapsableId = 0>
							<#assign collapsableShow = ''>
							<#assign collapsableShow_arrow = 'arrow_drop_down'>
							<div class="accordion" id="accordionExample">
	
							<#list model.getRepresentations() as r>

							<#if collapsableId == 0>
								<#assign collapsableShow = 'show'>
								<#assign collapsableShow_arrow = 'arrow_drop_down'>
							<#else>
								<#assign collapsableShow = ''>
								<#assign collapsableShow_arrow = 'arrow_right'>
							</#if>


							<div class="card">
								<div class="card-header" id="heading_${collapsableId}">
								<h2 class="mb-0">
									<button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#collapse_${collapsableId}" aria-expanded="true" aria-controls="collapse_${collapsableId}">
									${model.getPreferredLabel(r, "en", "No label")}<sub><i class="material-icons"> ${collapsableShow_arrow}</i>
									</sub></button>
								</h2>
								</div>
								
								<div id="collapse_${collapsableId}" class="collapse ${collapsableShow}" aria-labelledby="heading_${collapsableId}" data-parent="#accordionExample">
								<div class="card-body">

								<div class="representation">
								<table width="100%">

									<tr><td><b>Conforms to : </b> <a href="${model.getConformsTo(r)}"><#switch model.getConformsTo(r)>
										<#case "https://www.opengis.net/def/gwml2">GroundwaterML 2<#break>
										<#case "https://opengeospatial.github.io/SELFIE/">Second Environmental Linked Features Interoperability Experiment<#break>

										<#default>${model.getConformsTo(r)}</#switch></a></td></tr>
									<tr><td><b>Provider : </b> <a href="${model.getProvider(r)}"><#switch model.getProvider(r)>
										<#case "http://gin.gw-info.net">Groundwater Information Network<#break>
										<#case "http://gin.geosciences.ca">Groundwater Information Network<#break>
										<#default>${model.getProvider(r)}</#switch></a></td></tr>

									<#assign links = []>
									<#list model.getUrls(r,true) as url>
									<#assign link><a href="${url.getUrl()}"><#switch url.getLabel()>
												<#case "application/rdf+xml"><img class="img-fluid" title="Display in RDF/XML format" alt="Display in RDF/XML format" src="${host}/app/img/rdfxmlicon.png" style="max-width: 35px; padding: 10px 5px 0 5px"/><#break>
												<#case "application/ld+json"><img class="img-fluid" title="Display in JSON format" alt="Display in JSON format" src="${host}/app/img/jsonicon.png" style="max-width: 35px; padding: 10px 5px 0 5px"/><#break>
												<#case "application/x-turtle"><img class="img-fluid" title="Display in TTL (Turtle) format" alt="Display in TTL (Turtle) format" src="${host}/app/img/ttlicon.png" style="max-width: 35px; padding: 10px 5px 0 5px"/><#break>
												<#case "text/html"><img class="img-fluid" title="Display web page" alt="Display web page" src="${host}/app/img/htmlicon.png" style="max-width: 35px; padding: 10px 5px 0 5px"/><#break>
												<#case "text/plain"><img class="img-fluid" title="Display in plain text format" alt="Display in plain text format" src="${host}/app/img/txticon.png" style="max-width: 35px; padding: 10px 5px 0 5px"/><#break>
												<#case "image/jpeg"><img class="img-fluid" title="Display in jpeg" alt="Display in jpeg" src="${host}/app/img/jpg-outline.png" style="max-width: 35px;padding: 10px 5px 0 5px"/><#break>
												<#case "application/vnd.geo+json"><img class="img-fluid" title="Display in GeoJSON format" alt="Display in GeoJSON format" src="${host}/app/img/geojsonicon.png" style="max-width: 35px; padding: 10px 5px 0 5px"/><#break>
												<#default><img class="img-fluid" title="Display in ${url.getLabel()} format" alt="Display in ${url.getLabel()} format" src="${host}/app/img/othericon.png" style="max-width: 35px; padding: 10px 5px 0 5px"/></#switch></a></#assign>
									<#assign links = links + [link]>
									</#list>
									<tr><td colspan=2>
									<b>Formats :</b>  ${links?join(" ")}
									</td></tr>

								</table>
								</div>

								</div>
								</div>
							</div>
							<#assign collapsableId = collapsableId + 1> 
								
							</#list>
							</div>

							<br/>
							<h3>Related Features:</h3>
							<ul class="nav nav-tabs" role="tablist">
								<li class="nav-item"><a class="nav-link active"
									href="#g_type" role="tab" data-toggle="tab">Grouped by
										relations</a></li>
								<li class="nav-item"><a class="nav-link" href="#g_res"
									role="tab" data-toggle="tab">Grouped by features</a></li>
							</ul>
							<div class="tab-content">
								<div role="tabpanel" class="tab-pane active" id="g_type">
									<ul>
									<#assign grp = model.getRelevantLinkByProperty()>
									<#list grp?keys as p>
										<li><strong>${p}:</strong>
										<#list grp[p] as link>
										<a
											href="${link.getUrl()!'none'}"
											title="${link.getUrl()!'none'}">${link.getResLabel()}</a>
										</#list>

										</li>
									</#list>
									</ul>
								</div>
								<div role="tabpanel" class="tab-pane" id="g_res">
									<ul>
									<#assign res = model.getRelevantLinkByResource()>
									<#list res?keys as r>
										<li>
										<#list res[r] as l>
										<a
											href="${l.getUrl()}"
											title="${l.getUrl()}">${l.getResLabel()}</a> : <strong>${l.getLabel()}</strong>
										</#list>

										</li>
									</#list>


									</ul>
								</div>
							</div>
						</div>
						<#if image?has_content>
						<div class="col-sm-12 col-md-4">
							<a href="${image[0]}" target="_blank">
								<img src="${image[0]}" class="img-thumbnail img-fluid"/>
							</a>
						</div>
						</#if>
					</div>
				</div>
			</div>
		</div>
		<a href="https://www.nrcan.gc.ca/terms-and-conditions/10847" target="_blank"><small>[Terms and conditions of use]</small></a>  <a href="https://www.rncan.gc.ca/avis/10848" target="_blank"><small>[Conditions régissant l'utilisation]</small></a>
		
	</main>

	<!---<footer class="mastfoot mt-auto">
		<div class="inner">
			<img class="img-fluid" alt="Government of Canada logo"
				src="${host}/app/img/GOCcolouren.png"
				style="max-height: 35px;" />
		</div>
	</footer>--->
	<br/>
    <footer class="footer">
      <div class="container">
        <span class="text-muted"><img class="img-fluid" alt="Government of Canada logo"
				src="${host}/app/img/GOCcolouren.png"
				style="max-height: 35px;" /></span>
      </div>
    </footer>


	<script src="https://code.jquery.com/jquery-3.2.1.min.js"
		crossorigin="anonymous" type="text/javascript">

	</script>
	<script src="${host}/app/js/poppermin.js"
		type="text/javascript">

	</script>
	<script src="${host}/app/js/bootstrapmin.js"
		type="text/javascript">

	</script>
	<script src="${host}/app/js/ieworkaround.js"
		type="text/javascript">

	</script>

	<script type="text/javascript">
		$('.collapse').on('hide.bs.collapse', function () {
			var getallcardheadericons = '.card-header ' + ' h2' + ' button' + ' .material-icons' ;
			$(getallcardheadericons).text('arrow_right');

		});
		$('.collapse').on('show.bs.collapse', function () {
			var getallcardheadericons = '.card-header ' + ' h2' + ' button' + ' .material-icons' ;
			$(getallcardheadericons).text('arrow_right');
		});

		$('.collapse').on('shown.bs.collapse', function () {
			var parts = this.id.split('_');
			var idnumber = parts[parts.length - 1];
			var getcurrentcollapse = '#collapse_' +  idnumber;
			var getcurrentcardheadericon = '#heading_' + idnumber + ' h2' + ' button' + ' .material-icons' ;
			$(getcurrentcardheadericon).text('arrow_drop_down');
		});
		
	</script>
</body>

</html>

