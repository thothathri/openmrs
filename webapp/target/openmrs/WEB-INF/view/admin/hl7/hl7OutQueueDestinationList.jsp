<%@ include file="/WEB-INF/template/include.jsp" %>
<openmrs:require privilege="Manage Destination Types" otherwise="/login.htm" redirect="/admin/hl7/hl7OutQueueDestinationList.htm" />
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>
<h2><spring:message code="Hl7Destination.manage.title"/></h2>
<a href="hl7OutQueueDestinationForm.htm"><spring:message code="Hl7Destination.add"/></a> 
<br />
<br />
<b class="boxHeader"><spring:message code="Hl7Destination.list.title"/></b>
<form method="post" class="box">
<table>
<tr>
<th> <spring:message code="Hl7Destination.name" /> </th>
<th> <spring:message code="Hl7Destination.description" /> </th>
<th> <spring:message code="Hl7Destination.destination" /> </th>
</tr>
<c:forEach var="Hl7Destination" items="${hl7OutQueueDestinations}">
<tr>
<td valign="top">${Hl7Destination.name}</td>
<td valign="top">${Hl7Destination.description}</td>
<td valign="top">${Hl7Destination.destination}</td>
</tr>
</c:forEach>
</table>
</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>
