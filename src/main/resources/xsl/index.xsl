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
	<h3 style="text-align: center; margin-top: 0px; margin-bottom: 0px; color: green; font-weight: bold;"><xsl:text>Authenticator</xsl:text></h3>
	<p>
		Authentication for users by token. <br/>
		
		<p>User for test (Login / password) : <code>user / pwd1</code></p>
		
		<h4>Generate a token</h4>
		<code>
		<div>
			POST /token HTTP/1.1<br/>
			Host: https://authenticator.surati.io<br/>
			Content-Type: application/json<br/>
			{<br/>
			"login": "put_your_login_here",<br/>
			"password": "put_your_password_here"<br/>
			}<br/>
		</div>
		</code>
		
		<h4>Validate a token</h4>
		<code>
		<div>
			POST /token/validate HTTP/1.1<br/>
			Host: https://authenticator.surati.io<br/>
			Content-Type: application/json<br/>
			{<br/>
			"token": "put_your_jwt_token_here"<br/>
			}<br/>
		</div>
		</code>		
	</p>
	
	</xsl:template>
</xsl:stylesheet>
