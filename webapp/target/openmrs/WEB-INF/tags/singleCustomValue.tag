<%@tag import="org.openmrs.customdatatype.CustomDatatype"%>
<%@tag import="org.openmrs.customdatatype.CustomDatatypeHandler"%>
<%@tag import="org.openmrs.customdatatype.CustomDatatypeUtil"%>
<%@tag import="org.openmrs.web.attribute.handler.FieldGenDatatypeHandler"%>
<%@tag import="org.openmrs.web.attribute.handler.WebDatatypeHandler"%>
<%@tag import="java.util.Map"%>
<%@tag import="org.openmrs.api.context.Context"%>

<%@ include file="/WEB-INF/template/include.jsp" %>
<%--
You must specify either customValueDescriptor or value.
You must specify formFieldName
--%>
<%@ attribute name="customValueDescriptor" required="false" type="org.openmrs.customdatatype.CustomValueDescriptor" %>
<%@ attribute name="value" required="false" type="org.openmrs.customdatatype.SingleCustomValue" %>
<%@ attribute name="formFieldName" required="true" type="java.lang.String" %>
<%
if (value != null) {
	customValueDescriptor = value.getDescriptor();
}
CustomDatatypeHandler handler = CustomDatatypeUtil.getHandler(customValueDescriptor);

if (handler instanceof FieldGenDatatypeHandler) {
    FieldGenDatatypeHandler h = (FieldGenDatatypeHandler) handler;
    String widgetName = h.getWidgetName();
    Map<String, Object> widgetConfig = h.getWidgetConfiguration();
%>
    <openmrs:fieldGen
        formFieldName="${ formFieldName }"
        type="<%= widgetName %>"
        parameterMap="<%= widgetConfig %>"
        val="${ value.value }"/>
<% } else if (handler instanceof WebDatatypeHandler) {
	WebDatatypeHandler h = (WebDatatypeHandler) handler;
	CustomDatatype dt = CustomDatatypeUtil.getDatatype(customValueDescriptor);
%>

	<%= h.getWidgetHtml(dt, formFieldName, value.getValue()) %>

<% } else {
	String valueAsString = "";
	if (value != null)
		valueAsString = value.getValueReference();
%>
    <input type="text" name="${ formFieldName }" value="<%= valueAsString %>"/>
<% } %>