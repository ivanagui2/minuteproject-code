<%@ attribute name="role" required="true" %>

<%
     String [] roles = role.split(",");
     int length = roles.length;
     boolean isInRole = false;
     for (int i = 0; i < length;i++) {
         if(((javax.servlet.http.HttpServletRequest)((PageContext)jspContext).getRequest()).isUserInRole(roles[i])) {
             isInRole = true;
             break;
         }
         if(((javax.servlet.http.HttpServletRequest)((PageContext)jspContext).getRequest()).isUserInRole("ROLE_"+roles[i].toUpperCase())) {
             isInRole = true;
             break;
         }         
     }
    if(isInRole) {
%>
        <jsp:doBody/>
<%        
    }
%>