<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:include href="/xsl/layout.xsl"/>
	<xsl:template match="page" mode="head">
		<title>
			<xsl:text>Authenticator - Users</xsl:text>
		</title>
	</xsl:template>
	<xsl:template match="page" mode="body">
	    <h1>
	      <xsl:text>Modity Name &amp; surname</xsl:text>
	    </h1>
		<form action="/user/name/save" method="post">
	      <fieldset>
	        <label>
	          <xsl:text>Name &amp; surname</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="name" type="text" autocomplete="off" size="50" maxlength="50" required="" placeholder="Enter a username">
	        	<xsl:attribute name="value">
	              <xsl:value-of select="user/name"/>
	            </xsl:attribute>
	        </input>
	        <input name="login" type="hidden" required="">
	        	<xsl:attribute name="value">
	              <xsl:value-of select="user/login"/>
	            </xsl:attribute>
	        </input>
	        <div style="display: inline-block">
	        	<button type="submit">
		          <xsl:text>Apply</xsl:text>
		        </button>
		        <button type="button" onclick="location.href='/user/visualize?login={user/login}'">
		          <xsl:text>Cancel</xsl:text>
		        </button>
	        </div>
	      </fieldset>
	    </form>
	</xsl:template>
</xsl:stylesheet>
