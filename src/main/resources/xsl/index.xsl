<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:include href="/xsl/layout.xsl"/>
	<xsl:template match="page" mode="head">
		<title>
			<xsl:text>Authenticator</xsl:text>
		</title>
	</xsl:template>
	<xsl:template match="page" mode="body">
	<h2 style="text-align: center;"><xsl:text>Authenticator</xsl:text></h2>
	<p align="justify">
		You are welcome ! This application is used to store credentials, authenticate users and validate authentication. We use <a href="https://fr.wikipedia.org/wiki/JSON_Web_Token" target="_blank">JSON Web Token (Jwt)</a> to secure exchanges as it is defined in <a href="https://tools.ietf.org/html/rfc7519" target="_blank">RFC 7519</a>.	
	</p>
	
	</xsl:template>
</xsl:stylesheet>
