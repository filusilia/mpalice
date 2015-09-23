<%@ taglib uri='/WEB-INF/tld/fmt.tld' prefix='fmt'%>
<%@ taglib uri='/WEB-INF/tld/c.tld' prefix='c'%>
<%@ taglib uri='/WEB-INF/tld/fn.tld' prefix='fn'%>
<%@ taglib uri='/WEB-INF/tld/turnPage.tld' prefix='page'%>
<%@ taglib uri='/WEB-INF/tld/datetag.tld' prefix='date'%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%
	request.setAttribute("rEnter", "\r\n");
%>