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
	var hl7OutQueueDestTable;
	
	$j(document).ready(function() {
	
		hl7OutQueueDestTable = $j('#hl7OutQueueDestTable').dataTable( { 
			"aoColumns": [  { "sName": "name", "bSortable": false, "bHidden": true},				
							{ "sName": "description", "bSortable": false},
							{ "sName": "destination", "bSortable": false}
					      ],
			"sDom": '<"fg-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix"flip>' + 
				'rt<"fg-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix"i>',
			"sPaginationType": "full_numbers",
			"bAutoWidth": false,
			"bLengthChange": true,
			"bProcessing": true,
			"bServerSide": true,
			"bStateSave": false,
			"sAjaxSource": "hl7OutQueueDestinationList.json",
			"bJQueryUI": true,
			"oLanguage": {
				"sInfoFiltered": "(_MAX_ in queue)"
			},
			"fnDrawCallback": function() {
				$j("#hl7OutQueueDestTable td").highlight(hl7OutQueueDestTable.fnSettings().oPreviousSearch.sSearch);
			}
		});
		hl7OutQueueDestTable.fnSetFilteringDelay(1000);
		
	} );
	
	function showMore(id) {
		$j('#hl7' + id).animate({height: "100%"}, "slow");
		$j('#show' + id).hide();
		$j('#hide' + id).show();
	}
	
	function hideMore(id) {
		$j('#hl7' + id).animate({height: "50px"}, "slow");
		$j('#hide' + id).hide();
		$j('#show' + id).show();
	}

	function setMessage(data) {
		$j("#message").fadeOut("fast", function() {
			if ("openmrs_msg" in data)
				$j("#message .content").html(data.openmrs_msg);
			else if ("openmrs_error" in data)
				$j("#message .content").html(data.openmrs_error);
			$j("#message").fadeIn("slow");
		});
	}
	
</script>

<style>
	.showmore { height: 50px; overflow: hidden; }
	#hl7OutQueueDestTable button { padding: 0.5em; }
</style>

<h2><spring:message code="HL7OutQueue.title" /></h2>
<a href="hl7OutQueueDestinationForm.htm"><spring:message code="Hl7Destination.add"/></a> 
<br/>
<div id="message" class="ui-widget"
	style="display: none; margin-bottom: 1em;">
	<div class="ui-state-highlight ui-corner-all" style="padding: 0.5em;">
		<span class="ui-icon ui-icon-info"
			style="float: left; margin-right: 0.3em;"></span> <span class="content"></span>
	</div>
</div>

<table cellpadding="5" cellspacing="0" id="hl7OutQueueDestTable" width="100%">
	<thead>
		<tr>
			
			
			<th><spring:message code="Hl7Destination.name" /></th>
			<th width="40%"><spring:message
				code="Hl7Destination.description" /></th>
				<th width="40%"><spring:message
				code="Hl7Destination.destination" /></th>
		</tr>
	</thead>
	<tbody></tbody>
</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>



