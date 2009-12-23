<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<html>
	<head>
		<title><decorator:title default="Mysterious page..." /></title>
		
		<link href="<%= request.getContextPath() %>/decorators/main.css" rel="stylesheet" type="text/css">
			
		<link href="<%= request.getContextPath() %>/css/global.css" type="text/css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/css/tabs.css" />
    <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/tabs.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/scriptaculous/prototype.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/scriptaculous/scriptaculous.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/secure/dwr/engine.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/secure/dwr/util.js"></script>
    				
		<decorator:head />
	</head>

	<body>
		<!--<table width="100%" height="100%">
			<tr>
				<page:applyDecorator page="/html/Header.html" name="panel" />
			</tr>
			<tr>-->
				<table width="100%" height="100%">
					<tr>
						<td valign="top">
							<page:applyDecorator page="/html/myProject/MyprojectMenu.html" name="panel" />
						</td>
						<td width="100%">
							<table width="100%" height="100%">
								<tr>
									<td id="pageTitle">
										<decorator:title />
									</td>
								</tr>
								<tr>
									<td valign="top" height="100%">
										<decorator:body />
									</td>
								</tr>
								<tr>
									<td id="footer">
										<b>Disclaimer:</b>
										This site is an example site to demonstrate Sadbel. It serves no other purpose.
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>				
		<!--	</tr>
		</table>-->


		<%-- Construct URL from page request and append 'printable=true' param --%>
		<decorator:usePage id="p" />
		<%
			HttpServletRequest req = p.getRequest();
			StringBuffer printUrl = new StringBuffer();
			printUrl.append( req.getRequestURI() );
			printUrl.append("?printable=true");
			if (request.getQueryString()!=null) {
				printUrl.append('&');
				printUrl.append(request.getQueryString());
			}
		%>
		<p align="right">[ <a href="<%= printUrl %>">printable version</a> ]</p>

	</body>
</html>