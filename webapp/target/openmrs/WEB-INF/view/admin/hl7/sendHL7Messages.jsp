<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="View HL7 Outbound Queue"
	otherwise="/login.htm" redirect="/admin/hl7/hl7OutQueueList.htm" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localHeader.jsp"%>


<%@ include file="/WEB-INF/template/footer.jsp"%>
