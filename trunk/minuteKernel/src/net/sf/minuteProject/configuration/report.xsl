<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:output method="html" encoding="ISO-8859-1" />
<xsl:template match="loadTestConfiguration">
	<xsl:variable name="redThreshold">5</xsl:variable>
	<html>	
		<head>
			<title>Load test report</title>
		</head>
		<body>
		<br></br>
		<h1>Configuration 
		<xsl:choose>
			<xsl:when test="string-length(@link) != 0"><a href="{@link}"><xsl:value-of select="@name"/></a></xsl:when>
			<xsl:otherwise><xsl:value-of select="@name"/></xsl:otherwise>
		</xsl:choose>				
		</h1>		
		<xsl:for-each select="loadTestSuite">			
		<h2>Load report for "<xsl:value-of select="@name"/>"</h2>		
		<table width="80%" cellspacing="2" cellpadding="5">
			<tbody>
			   <tr>
					<!--<th>test class</th>-->
				    <th>description</th>
				    <th>test method</th>
					<th>concurrent users</th>
					<th>iterations</th>
					<th>start Time</th>
				    <th>duration (s)</th>
				    <th>average (s)</th>
				</tr>	
				<xsl:for-each select="loadTestCase">
				<xsl:sort order="ascending" select="@method"/>
			    <xsl:if test="@average &lt; $redThreshold">
					<tr bgcolor="white">
					<th> <xsl:value-of select="@description"/></th>
					<th> <xsl:value-of select="@method"/></th>
					<th> <xsl:value-of select="@nbuser"/></th>
					<th> <xsl:value-of select="@iteration"/></th>
					<th> <xsl:value-of select="@timeStart"/></th>
					<th> <xsl:value-of select="@duration"/></th>	
					<th> <xsl:value-of select="@average"/></th>	
					</tr>
				</xsl:if>
			    <xsl:if test="@average &gt; $redThreshold">
					<tr bgcolor="red">
					<th> <xsl:value-of select="@description"/></th>
					<th> <xsl:value-of select="@method"/></th>
					<th> <xsl:value-of select="@nbuser"/></th>
					<th> <xsl:value-of select="@iteration"/></th>
					<th> <xsl:value-of select="@timeStart"/></th>
					<th> <xsl:value-of select="@duration"/></th>	
					<th> <xsl:value-of select="@average"/></th>	
					</tr>
				</xsl:if>					
				</xsl:for-each>		
			</tbody>
			</table>
		</xsl:for-each>				
		</body>		
	</html>		
</xsl:template>	

</xsl:stylesheet>
