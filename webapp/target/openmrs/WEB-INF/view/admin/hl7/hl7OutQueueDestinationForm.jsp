<%@ include file="/WEB-INF/template/include.jsp" %>
<openmrs:require privilege="Manage Destination Types" otherwise="/login.htm" redirect="/admin/hl7/hl7OutQueueDestinationList.htm" />
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>
<h2><spring:message code="Hl7Destination.manage.title"/></h2>
<br />
<br />
<b class="boxHeader"><spring:message code="Hl7Destination.list.title"/></b>
<form method="POST" action = "hl7OutQueueDestinationForm.htm">

	Enter Name:
	<input type = "string" name="name" />
	<br/>
	Enter Description:
	<input type = "string" name="description" />
	<br/>
	Enter Destination:
	<input type = "string" name="destination" />
	<br/>
	<input type="submit"/>

</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>