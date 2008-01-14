<%@page isErrorPage="true" %>
<%@ include file="/WEB-INF/jsp/common/common-include.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>error page</title>
</head>
<body>
an error has occured!
<Br/>
<jsp:useBean id="now" class="java.util.Date"/>
${now}
<br/>
<h3 color="red">
${exception.message}
</h3>
</body>
</html>