<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:include href="/xsl/layout.xsl"/>
	<xsl:template match="page" mode="head">
		<title>
			<xsl:text>Authenticator - Visualize a user</xsl:text>
		</title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<h1>
	      <xsl:value-of select="user/name"/>
	      <xsl:if test="user/blocked = 'true'">
		    	<xsl:text> (Blocked)</xsl:text>
		    </xsl:if>
		    <xsl:if test="user/blocked = 'false'">
		    	<xsl:text> (Unblocked)</xsl:text>
		    </xsl:if>
	    </h1>
	    <xsl:apply-templates select="user"/>
	</xsl:template>
	<xsl:template match="user">
	    <p>
	    Login : <code><xsl:value-of select="login"/></code><br/> 	    
	    Password : <code><xsl:value-of select="password"/></code>
	    </p>
	    <nav>
		    <ul style="text-align: left">		    	
		    	<li>
		    		<a href="/user/name/edit?login={login}">
			          <xsl:text>Modify Name &amp; surname</xsl:text>
			        </a>
		    	</li>
		    	<li>
		    		<a href="/user/password/edit?login={login}">
			          <xsl:text>Change password</xsl:text>
			        </a>
		    	</li>
		    	<li>
		    		<xsl:if test="blocked = 'true'"> 
				    	<a href="/user/block?login={login}&amp;enable=false">
				          <xsl:text>Unblock</xsl:text>
				        </a>
				    </xsl:if>
				    <xsl:if test="blocked = 'false'">
				    	<a href="/user/block?login={login}&amp;enable=true">
				          <xsl:text>Block</xsl:text>
				        </a>
				    </xsl:if>
		    	</li>
		    	<li>
		    		<a href="/users">
			          <xsl:text>Return</xsl:text>
			        </a>
		    	</li>
		    </ul>		    
	    </nav>
	</xsl:template>
</xsl:stylesheet>
