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
	      <xsl:text>Users</xsl:text>
	    </h1>
	    <xsl:apply-templates select="users"/>
	    <a href="/user/new">
          <xsl:text>Create a user</xsl:text>
        </a>
	</xsl:template>
	<xsl:template match="users[not(user)]">
	    <p>
	      <xsl:text>There isn't a user registered for the moment.</xsl:text>
	    </p>
	</xsl:template>
	<xsl:template match="users[user]">
	    <table data-sortable="true">
	      <thead>
	        <tr>
	          <th>
	            <xsl:text>Name &amp; surname</xsl:text>
	          </th>
	          <th>
	            <xsl:text>Login</xsl:text>
	          </th>
	          <th></th>
	        </tr>
	      </thead>
	      <tbody>
	        <xsl:apply-templates select="user"/>
	      </tbody>
	    </table>
	</xsl:template>
	<xsl:template match="user">
	    <tr>
	      <xsl:if test="blocked = 'true'">
	        <xsl:attribute name="style">
	          <xsl:text>opacity:0.5;</xsl:text>
	        </xsl:attribute>
	      </xsl:if>
	      <td>
	        <xsl:value-of select="name"/>
	      </td>
	      <td>
	        <xsl:value-of select="login"/>
	      </td>
	      <td>
	        <a href="/user/visualize?login={login}">
	          <xsl:text>Visualize</xsl:text>
	        </a>
	      </td>
	    </tr>
	  </xsl:template>
</xsl:stylesheet>
