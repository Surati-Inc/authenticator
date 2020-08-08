<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:include href="/org/takes/rs/xe/memory.xsl"/>
    <xsl:include href="/org/takes/rs/xe/millis.xsl"/>
    <xsl:include href="/org/takes/facets/flash/flash.xsl"/>
	<xsl:template match="/page">
		<html lang="fr">
			<head>
				<meta charset="UTF-8"/>
				<meta name="viewport" content="width=device-width, initial-scale=1.0"/>		
				<link rel="shortcut icon" href="/img/logo.png"/>						
				<link rel="stylesheet" href="/css/tacit-css.min.css"/>			
				<link rel="stylesheet" href="/css/main.css"/>					
				<xsl:apply-templates select="." mode="head"/>
			</head>
			<body>
				<section>
				    
				    <xsl:if test="flash">
			            <xsl:call-template name="takes_flash_without_escaping">
			              <xsl:with-param name="flash" select="flash"/>
			            </xsl:call-template>
			        </xsl:if>
			        
					<header style="padding-top: 0px; padding-bottom: 0px">
						<nav>
							<ul>
								<li>									
									<a href="/">
										<img src="/img/logo.svg" style="width: 64px"/>
									</a>
								</li>
							</ul>
						</nav>
						<nav>
							<ul>
								<li>
									<xsl:if test="identity">
									    <xsl:text>@</xsl:text>
					                    <xsl:value-of select="identity/login"/>															                   
					                </xsl:if>
					                <xsl:if test="not(identity)">
					                    <a href="/login">
											Login
										</a>
					                </xsl:if>									
								</li>
							
								<xsl:if test="identity">								    
									<li>
										<xsl:choose>
									    	<xsl:when test="menu = 'users'">
									    	    <xsl:text>Users</xsl:text>
										    </xsl:when>
										    <xsl:otherwise>
										    	<a href="/users">										    
													<xsl:text>Users</xsl:text>
												</a>
										    </xsl:otherwise>
									    </xsl:choose>
									</li>
									<li>
										<a href="{links/link[@rel='takes:logout']/@href}">
											<xsl:text>Logout</xsl:text>
										</a>
									</li>
								</xsl:if>
							</ul>
						</nav>
					</header>
					<article>
						<xsl:apply-templates select="." mode="body"/>
					</article>
					<footer>
					   <nav>
							<ul class="bottom">
				                <li title="Currently deployed version">
				                  <xsl:text>v</xsl:text>
				                  <xsl:value-of select="version/name"/>
				                </li>
				                <li>
				                  <xsl:call-template name="takes_millis">
				                    <xsl:with-param name="millis" select="millis"/>
				                  </xsl:call-template>
				                </li>
				                <li>
				                  <xsl:call-template name="takes_memory">
				                    <xsl:with-param name="memory" select="memory"/>
				                  </xsl:call-template>
				                </li>
				                <li title="Current date/time">
				                  <xsl:value-of select="@date"/>
				                </li>
			               </ul>
			            </nav>
					</footer>
				</section>
			</body>
		</html>
	</xsl:template>
	<xsl:template name="takes_flash_without_escaping">
	    <xsl:param name="flash"/>
	    <xsl:if test="$flash/message">
	      <p>
	        <xsl:attribute name="class">
	          <xsl:text>flash</xsl:text>
	          <xsl:text> flash-</xsl:text>
	          <xsl:value-of select="$flash/level"/>
	        </xsl:attribute>
	        <xsl:value-of select="$flash/message" disable-output-escaping="yes"/>
	      </p>
	    </xsl:if>
	 </xsl:template>
</xsl:stylesheet>
