<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:include href="/xsl/layout.xsl"/>
	<xsl:template match="page" mode="head">
		<title>
			<xsl:text>Surati - Utilisateur</xsl:text>
		</title>
	</xsl:template>
	<xsl:template match="page" mode="body">
	    <h1>
	      <xsl:text>Modifier le mot de passe</xsl:text>
	    </h1>
		<form action="/user/password/save" method="post">
	      <fieldset>
	        <label>
	          <xsl:text>Mot de passe</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="password" type="text" autocomplete="off" size="50" maxlength="50" required="" placeholder="mot de passe">
	        	<xsl:attribute name="value">
	              <xsl:value-of select="user/password"/>
	            </xsl:attribute>
	        </input>
	        <input name="login" type="hidden" value="{user/login}"/>
	        <div style="display: inline-block">
	        	<button type="submit">
		          <xsl:text>Modifier</xsl:text>
		        </button>
		        <button type="button" onclick="location.href='/user/visualize?login={user/login}'">
		          <xsl:text>Annuler</xsl:text>
		        </button>
	        </div>
	      </fieldset>
	    </form>
	</xsl:template>
</xsl:stylesheet>
