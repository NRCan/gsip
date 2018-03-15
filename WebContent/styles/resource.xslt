<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:j.0="http://schema.org/" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:sesame="http://www.openrdf.org/schema/sesame#" xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:xsd="http://www.w3.org/2001/XMLSchema#" xmlns:gwml="http://geosciences.ca/def/gwml/2.1#" xmlns:dct="http://purl.org/dc/terms/" xmlns:ex="http://gin.gw-info.net/example#" xmlns:hy="http://geosciences.ca/def/hydraulic#" xmlns:g="urn:x-gin:func" xmlns:p="urn:x-gsip:1.0">
	<xsl:output method="html" version="4.0" encoding="UTF-8" indent="yes"/>
	<xsl:param name="resource">https://geoconnex.ca/id/waterbody/9483b203be4111d892e2080020a0f4c9</xsl:param>
	<xsl:param name="host"/>
	<xsl:param name="jsonld"/>
	<xsl:param name="locale">en</xsl:param>
	<xsl:variable name="mime-types">
		<p:types>
			<p:type mime-type="application/vnd.geo+json" formats="geojson"/>
			<p:type mime-type="text/csv" formats="csv"/>
			<p:type mime-type="text/xml; subtype=gml/3.2.1" formats="gml"/>
			<p:type mime-type="text/xml" formats="xml"/>
			<p:type mime-type="application/rdf+xml" formats="rdf;rdf+xml"/>
			<p:type mime-type="application/x-turtle" formats="ttl;turtle"/>
			<p:type mime-type="text/turtle" sameAs="application/x-turtle"/>
			<p:type mime-type="text/plain" formats="txt"/>
			<p:type mime-type="text/html" formats=""/>
			<p:type mime-type="application/vnd.google-earth.kml+xml" formats="kml"/>
			<p:type mime-type="application/json" formats="json"/>
		</p:types>
	</xsl:variable>
	<xsl:template match="/">
		<html lang="en">
			<head>
				<meta charset="utf-8"/>
				<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
				<meta name="description" content=""/>
				<meta name="author" content=""/>
				<link rel="icon" href="{$host}/app/img/favicon.ico"/>
				<title>
					<xsl:choose>
						<xsl:when test="//*[@rdf:about=$resource]">
							<xsl:choose>
								<xsl:when test="//*[@rdf:about=$resource]/rdf:type/*/rdfs:label">
									<xsl:choose>
										<xsl:when test="//*[@rdf:about=$resource]/rdf:type/*/rdfs:label[@xml:lang=$locale]">
											<xsl:value-of select="//*[@rdf:about=$resource]/rdf:type/*/rdfs:label[@xml:lang=$locale]"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="//*[@rdf:about=$resource]/rdf:type/*/rdfs:label"/>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:when>
								<xsl:when test="//*[@rdf:about=$resource]/rdfs:label">
									<xsl:value-of select="//*[@rdf:about=$resource]/rdfs:label"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="//*[@rdf:about=$resource]/j.0:name"/>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>Resource not found</xsl:otherwise>
					</xsl:choose>
				</title>
				<!-- Bootstrap core CSS -->
				<link href="{$host}/app/css/bootstrapmin.css" rel="stylesheet"/>
				<!-- Custom styles for this template -->
				<link href="{$host}/app/css/navbartopfixed.css" rel="stylesheet"/>
				<!--<link rel="alternate" type="application/rdf+xml" href="{replace($resource,'/id/','/info/')}"/>
				<link rel="alternate" type="application/x-turtle" href="{replace($resource,'/id/','/info/')}"/>-->
				<style>
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
					<img src="{$host}/app/img/logominwhiteback25px.png"/>
					<a class="navbar-brand" href="#" style="padding-left: 4px;">GSIP Linked Data Demonstration</a>
				</nav>
				<div class="container">
					<!--				<div class="row">
  <div class="col-12 col-md-8">.col-12 .col-md-8</div>
  <div class="col-6 col-md-4">.col-6 .col-md-4</div>
</div>-->
					<div class="row">
						<div class="col-sm-12">
							<h1>
								<xsl:choose>
									<xsl:when test="//*[@rdf:about=$resource]/rdfs:label[@xml:lang=$locale]">
										<xsl:value-of select="//*[@rdf:about=$resource]/rdfs:label[@xml:lang=$locale]"/>
									</xsl:when>
									<xsl:when test="//*[@rdf:about=$resource]/rdfs:label[1]">
										<xsl:value-of select="//*[@rdf:about=$resource]/rdfs:label[1]"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="$resource"/>
									</xsl:otherwise>
								</xsl:choose>
							</h1>
							<blockquote class="blockquote container">
								<div class="row">
									<div class="col-sm-12 col-md-9">
										<p class="mb-0">
											<strong>Type: </strong>
											<samp>
												<xsl:choose>
													<xsl:when test="//*[@rdf:about=$resource]">
														<xsl:value-of select="local-name(//*[@rdf:about=$resource])"/>
													</xsl:when>
													<xsl:otherwise>Resource not found</xsl:otherwise>
												</xsl:choose>
											</samp>
										</p>
										<p class="mb-0">
											<strong>Identifier: </strong>
											<samp>
												<a href="{$resource}">
													<xsl:value-of select="$resource"/>
												</a>
											</samp>
										</p>
									</div>
									<div class="col-sm-12 col-md-3">
										<a href="{$resource}?f=rdf" target="_blank">
											<img class="img-fluid" title="Display page in RDF/XML format" alt="View in RFF/XML format" src="{$host}/app/img/rdfxmlicon.png" style="max-width:35px; padding: 10px 5px 0 5px"/>
										</a>
										<a href="{$resource}?f=json" target="_blank">
											<img class="img-fluid" title="Display page in JSON-LD format" alt="View in JSON-LD format" src="{$host}/app/img/jsonicon.png" style="max-width:35px; padding: 10px 5px 0 5px"/>
										</a>
										<a href="{$resource}?f=ttl" target="_blank">
											<img class="img-fluid" title="Display page in TTL format" alt="View in TTL format" src="{$host}/app/img/ttlicon.png" style="max-width:35px; padding: 10px 5px 0 5px"/>
										</a>
									</div>
								</div>
							</blockquote>
							<div class="container">
								<div class="row">
									<xsl:choose>
										<xsl:when test="//*[@rdf:about=$resource]/j.0:image">
											<div class="col-sm-12 col-md-8">
												<xsl:call-template name="representations"/>
												<xsl:call-template name="associations"/>
											</div>
											<div class="col-sm-12 col-md-4">
												<xsl:apply-templates select="//*[@rdf:about=$resource]/j.0:image"/>
											</div>
										</xsl:when>
										<xsl:otherwise>
											<div class="col-sm-12 col-md-12">
												<xsl:call-template name="representations"/>
												<xsl:call-template name="associations"/>
											</div>
										</xsl:otherwise>
									</xsl:choose>
								</div>
							</div>
						</div>
					</div>
				</div>
				<xsl:call-template name="footer"/>
				<xsl:call-template name="scripts"/>
			</body>
			<xsl:if test="$jsonld">
				<script language="" type="application/ld+json">
					<!--  remove extra line feed -->
					<xsl:value-of select="translate($jsonld,'&#xD;',' ')"/>
				</script>
			</xsl:if>
		</html>
	</xsl:template>
	<xsl:template name="footer">
		<footer class="mastfoot mt-auto">
			<div class="inner">
				<img class="img-fluid" alt="Government of Canada logo" src="{$host}/app/img/GOCcolouren.png" style=" max-height: 35px;"/>
				<!--<p>Cover template for <a href="https://getbootstrap.com/">Bootstrap</a>, by <a href="https://twitter.com/mdo">@mdo</a>.</p>-->
			</div>
		</footer>
	</xsl:template>
	<xsl:template name="representations">
		<xsl:if test="//*[@rdf:about=$resource]/rdfs:seeAlso">
			<h3>Representation<xsl:choose>
					<xsl:when test="count(//*[@rdf:about=$resource]/rdfs:seeAlso)&gt;1">s</xsl:when>
					<xsl:otherwise/>
				</xsl:choose>:
			</h3>
		</xsl:if>
		<ul>
			<xsl:apply-templates select="//*[@rdf:about=$resource]/rdfs:seeAlso"/>
		</ul>
	</xsl:template>
	<xsl:template name="associations">
		<h3>Related Features:</h3>
		<ul class="nav nav-tabs" role="tablist">
			<li class="nav-item">
				<a class="nav-link active" href="#g_type" role="tab" data-toggle="tab">Grouped by relations</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#g_res" role="tab" data-toggle="tab">Grouped by features</a>
			</li>
		</ul>
		<!-- Tab panes -->
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane active" id="g_type">
				<ul>
					<xsl:for-each-group select="//*[@rdf:about=$resource]/*" group-by="name(.)">
						<xsl:if test="(name()!='rdfs:label') and (name()!='rdf:type') and (name()!='owl:sameAs') and (name()!='j.0:name')and (name()!='rdfs:seeAlso') and (name()!='j.0:image')">
							<li>
								<strong>
									<xsl:value-of select="fn:substring-after(current-grouping-key(),':')"/>: </strong>
								<xsl:for-each select="current-group()">
									<a href="{@rdf:resource|*/@rdf:about}" title="{@rdf:resource|*/@rdf:about}">
										<xsl:call-template name="assoc_label"/>
									</a>
									<xsl:if test="position()!=last()">, </xsl:if>
								</xsl:for-each>
							</li>
						</xsl:if>
					</xsl:for-each-group>
				</ul>
			</div>
			<div role="tabpanel" class="tab-pane" id="g_res">
				<ul>
					<xsl:for-each-group select="//*[@rdf:about=$resource]/*" group-by="(*/@rdf:about)|(@rdf:resource)">
						<xsl:if test="(name()!='rdfs:label') and (name()!='rdf:type') and (name()!='owl:sameAs') and (name()!='j.0:name')and (name()!='rdfs:seeAlso') and (name()!='j.0:image')">
							<li>
								<strong>
									<a href="{current-grouping-key()}">
										<xsl:call-template name="assoc_label"/>
									</a>: </strong>
								<xsl:for-each select="current-group()">
									<xsl:value-of select="local-name()"/>
									<xsl:if test="position()!=last()">, </xsl:if>
								</xsl:for-each>
							</li>
						</xsl:if>
					</xsl:for-each-group>
				</ul>
			</div>
		</div>
	</xsl:template>
	<xsl:template name="assoc_label">
		<xsl:choose>
			<xsl:when test="*/rdfs:label">
				<xsl:choose>
					<xsl:when test="*/rdfs:label[@xml:lang=$locale]">
						<xsl:value-of select="*/rdfs:label[@xml:lang=$locale]"/>
					</xsl:when>
					<xsl:when test="*/rdfs:label">
						<xsl:value-of select="*/rdfs:label[1]"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="*/@rdf:about"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="@rdf:resource">
				<xsl:variable name="currentResource">
					<xsl:value-of select="@rdf:resource"/>
				</xsl:variable>
				<xsl:choose>
					<xsl:when test="//*[@rdf:about=$currentResource]/rdfs:label[@xml:lang=$locale]">
						<xsl:value-of select="//*[@rdf:about=$currentResource]/rdfs:label[@xml:lang=$locale]"/>
					</xsl:when>
					<xsl:when test="//*[@rdf:about=$currentResource]/rdfs:label">
						<xsl:value-of select="//*[@rdf:about=$currentResource]/rdfs:label[1]"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$currentResource"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<!--<xsl:value-of select="*/@rdf:about"/>-->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="j.0:image">
		<a href="{@rdf:resource}" target="_blank">
			<img src="{@rdf:resource}" class="img-thumbnail img-fluid"/>
		</a>
	</xsl:template>
	<xsl:template name="scripts">
		<!-- Bootstrap core JavaScript
    ================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<!--<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"/>
		-->
		<script src="https://code.jquery.com/jquery-3.2.1.min.js" crossorigin="anonymous"><xsl:text> </xsl:text></script>
		<!--<script>
			<xsl:text><![CDATA[window.jQuery || document.write('<script src="http://s-stf-ngwd.nrn.nrcan.gc.ca/cocoon/devHeryk/rdf2Html/bootstrap/assets/js/vendor/jquery.min.js"><\/script>')]]></xsl:text>
		</script>-->
		<script src="{$host}/app/js/poppermin.js"><xsl:text> </xsl:text></script>
		<script src="{$host}/app/js/bootstrapmin.js"><xsl:text> </xsl:text></script>
		<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
		<script src="{$host}/app/js/ieworkaround.js"><xsl:text> </xsl:text></script>
	</xsl:template>
	<!--<xsl:template match="hy:*">
		<li>
			<a href="{@rdf:resource|*/@rdf:about}" title="{@rdf:resource|*/@rdf:about}">
				<xsl:value-of select="fn:local-name(.)"/>
			</a>
		</li>
	</xsl:template>-->
	<xsl:template match="rdfs:seeAlso">
		<xsl:variable name="link">
			<xsl:value-of select="@rdf:resource|*/@rdf:about"/>
		</xsl:variable>
		<xsl:variable name="format">
			<xsl:choose>
				<xsl:when test="*/@rdf:about">
					<xsl:copy-of select="*[@rdf:about]/dct:format"/>
				</xsl:when>
				<xsl:when test="@rdf:resource">
					<xsl:copy-of select="//*[@rdf:about=$link]/dct:format"/>
				</xsl:when>
				<xsl:otherwise>undefined</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="containsFormatLinks">
			<xsl:choose>
				<xsl:when test="contains(*/@rdf:about,'/data/')">true</xsl:when>
				<xsl:when test="contains(@rdf:resource,'/data/')">true</xsl:when>
				<xsl:otherwise>false</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="label">
			<xsl:choose>
				<xsl:when test="*/rdfs:label[@xml:lang=$locale]">
					<xsl:value-of select="*/rdfs:label[@xml:lang=$locale]"/>
				</xsl:when>
				<xsl:when test="*/rdfs:label[1]">
					<xsl:value-of select="*/rdfs:label[1]"/>
				</xsl:when>
				<xsl:otherwise>No label</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<li>
			<samp>
				<xsl:choose>
					<xsl:when test="($format='undefined')or($format='')">
						<a href="{$link}">
							<xsl:value-of select="$label"/>
						</a>
					</xsl:when>
					<xsl:otherwise>
						<strong>
							<xsl:value-of select="$label"/>: </strong>
						<xsl:for-each select="$format/dct:format">
							<xsl:variable name="ext">
								<xsl:value-of select="."/>
							</xsl:variable>
							<xsl:choose>
								<xsl:when test="$mime-types/p:types/p:type/@mime-type=$ext">
									<xsl:variable name="anchor">
										<xsl:value-of select="$link"/>?f=<xsl:value-of select="$mime-types/p:types/p:type[@mime-type=$ext]/@formats"/>
									</xsl:variable>
									<a href="{$anchor}">
										<xsl:value-of select="$ext"/>
									</a>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="$ext"/>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:if test="position()!=last()">, </xsl:if>
						</xsl:for-each>
					</xsl:otherwise>
				</xsl:choose>
			</samp>
		</li>
	</xsl:template>

</xsl:stylesheet>
