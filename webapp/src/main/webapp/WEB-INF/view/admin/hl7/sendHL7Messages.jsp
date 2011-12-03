<%@ include file="/WEB-INF/template/include.jsp" %>
<openmrs:require privilege="Manage Destination Types" otherwise="/login.htm" redirect="/admin/hl7/hl7OutQueueDestinationList.htm" />
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<openmrs:htmlInclude file="/scripts/jquery/highlight/jquery.highlight-3.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.filteringDelay.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css" />

<h2><spring:message code="Hl7Destination.manage.title"/></h2>
<script type="text/javascript">
	
	
        $j(document).ready(function() {
           
                
                alert("hi");
				var i = 0;
                    $j.getJSON('hl7OutQueueDestinationList.json' , function(json) {
                      var k = json.aaData.length;      
					
					  $j.each(json.aaData, function() {
					var rdb = "<tr><td><input id=RadioButton  type=radio name=name value=" + json.aaData[i][0] +" /><label for=RadioButton" + i + ">" + json.aaData[i][2] + "</label></td></tr>";
					i=i+1;
                            $j('#hl7OutQueueDestTable').append(rdb);	
					});							
                    });
			});
			
			function fun(){
			alert("Done!");
			}
 

	
</script>

<style>
	.showmore { height: 50px; overflow: hidden; }
	#hl7OutQueueDestTable button { padding: 0.5em; }
</style>
<table id="hl7OutQueueDestTable"></table>
<input type ="submit" value ="Send Messages" onClick="fun()" />



<%@ include file="/WEB-INF/template/footer.jsp"%>



