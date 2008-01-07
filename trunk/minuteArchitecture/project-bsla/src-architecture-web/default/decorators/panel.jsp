<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%--<body onload="<decorator:getProperty name="body.onload" />">--%>
<p>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<th class="panelTitle">
				<decorator:title default="Unknown panel" />
			</th>
		</tr>
		<tr>
			<td class="panelBody">
				<decorator:body />
			</td>
		</tr>
	</table>
</p>
<%--</body>--%>
