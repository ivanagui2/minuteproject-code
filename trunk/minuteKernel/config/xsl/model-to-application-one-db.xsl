<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/" >
	    <liste-items>
	        <xsl:apply-templates/>
	    </liste-items>
	</xsl:template>
	
	<xsl:template match="generator-config/configuration" >
	    <item>
	        <num>
	            t
	        </num>
	    </item>
	    <xsl:copy-of select="." />
	</xsl:template>

	<!-- ADD NODE -->
</xsl:stylesheet>