<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Concept Map Types" otherwise="/login.htm" redirect="/admin/concepts/conceptMapTypeList.list" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2><spring:message code="ConceptMapType.title"/></h2>

<a href="conceptMapType.form"><spring:message code="ConceptMapType.add"/></a>

<br /><br />

<b class="boxHeader"><spring:message code="ConceptMapType.list.title"/></b>
<div class="box">
	<i style="font-size: small"><spring:message code="ConceptMapType.hidden.message"/></i><br/>
	<table cellpadding="3" cellspacing="3">
		<tr>
			<th><spring:message code="general.name"/></th>
			<th>&nbsp;</th>
			<th><spring:message code="general.description"/></th>
		</tr>
		<c:forEach var="conceptMapType" items="${conceptMapTypeList}">
			<tr <c:if test="${conceptMapType.isHidden == true }">style='color:red'</c:if>> 
				<td valign="top">
					<a href="conceptMapType.form?conceptMapTypeId=${conceptMapType.conceptMapTypeId}" 
					<c:if test="${conceptMapType.isHidden == true }">style='color:grey'</c:if>>
						<c:choose>
							<c:when test="${conceptMapType.retired}"><strike>${conceptMapType.name}</strike></c:when>
							<c:otherwise>${conceptMapType.name}</c:otherwise>
						</c:choose>
					</a>
				</td>
				<td>&nbsp;</td>
				<td valign="top" <c:if test="${conceptMapType.isHidden == true }">style='color:grey'</c:if>>${conceptMapType.description}</td>
			</tr>
		</c:forEach>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>