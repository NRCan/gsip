<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<link rel="icon" href="${host}/app/img/favicon.ico" />
<title>Geological Units</title>
<link href="${host}/app/css/bootstrapmin.css" rel="stylesheet" />
<link href="${host}/app/css/navbartopfixed.css" rel="stylesheet" />
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
</style>
</head>
<body>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<img src="${host}/app/img/logominwhiteback25px.png"
			alt="" /> <a class="navbar-brand" href="#" style="padding-left: 4px;">GSIP
			Linked Data Demonstration</a>
	</nav>
	<div class="container">
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
						</div>
						<div class="col-sm-12 col-md-3">
							<a href="${model.getContextResourceUri()}?f=rdf"
								target="_blank"><img class="img-fluid"
								title="Display page in RDF/XML format"
								alt="View in RFF/XML format"
								src="${host}/app/img/rdfxmlicon.png"
								style="max-width: 35px; padding: 10px 5px 0 5px" /></a><a
								href="${model.getContextResourceUri()}?f=json"
								target="_blank"><img class="img-fluid"
								title="Display page in JSON-LD format"
								alt="View in JSON-LD format"
								src="${host}/app/img/jsonicon.png"
								style="max-width: 35px; padding: 10px 5px 0 5px" /></a><a
								href="${model.getContextResourceUri()}?f=ttl"
								target="_blank"><img class="img-fluid"
								title="Display page in TTL format" alt="View in TTL format"
								src="${host}/app/img/ttlicon.png"
								style="max-width: 35px; padding: 10px 5px 0 5px" /></a>
						</div>
					</div>
				</blockquote>
				<div class="container">
					<div class="row">
						<div class="col-sm-12 col-md-12">
							<h3>Representation:</h3>
							<ul>
							<#list model.getRepresentations() as r>
								<li><samp>
										<strong>${model.getPreferredLabel(r, "en", "No label")}</strong>
										<#list model.getFormats(r) as f>
											<a href="${model.getFormatOverride(r,f)}">${f!unknown}</a> 
										</#list>
									</samp>
								</li>
							</#list>
							</ul>
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
											href="${link.getUrl()}"
											title="${link.getUrl()}">${link.getResLabel()}</a>
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
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer class="mastfoot mt-auto">
		<div class="inner">
			<img class="img-fluid" alt="Government of Canada logo"
				src="${host}/app/img/GOCcolouren.png"
				style="max-height: 35px;" />
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
</body>
<script language="" type="application/ld+json">
${model.encode("JSON-LD")}
</script>
</html>
<html></html>
