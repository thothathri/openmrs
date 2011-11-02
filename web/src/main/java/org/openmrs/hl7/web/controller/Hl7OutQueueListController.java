/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.hl7.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.hl7.HL7Constants;
import org.openmrs.hl7.HL7InQueue;
import org.openmrs.hl7.HL7OutQueue;
import org.openmrs.hl7.HL7Service;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Hl7OutQueueListController {
	
	/**
	 * Logger for this class and subclasses
	 */
	private static final Log log = LogFactory.getLog(Hl7OutQueueListController.class);
	
	/**
	 * Render the outQueue HL7 queue messages page
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/admin/hl7/hl7OutQueueList.htm")
	public String listOutQueueHL7s(ModelMap modelMap) {
		modelMap.addAttribute("messageState", HL7Constants.HL7_STATUS_PENDING);
		return "/admin/hl7/hl7OutQueueList";
	}	
	
	
	/**
	 * method for returning a batch of HL7s from the queue based on datatable parameters
	 * 
	 * @param iDisplayStart start index for search
	 * @param iDisplayLength amount of terms to return
	 * @param sSearch search term(s)
	 * @param sEcho check d
	 * igit for datatables
	 * @param messageState HL7OutQueue state to look up
	 * @return batch of HL7OutQueue objects to be converted to JSON
	 * @throws IOException
	 */
	@RequestMapping("/admin/hl7/hl7OutQueueList.json")
	public @ResponseBody
	Map<String, Object> getHL7OutQueueBatchAsJson(@RequestParam("iDisplayStart") int iDisplayStart,
	        @RequestParam("iDisplayLength") int iDisplayLength, @RequestParam("sSearch") String sSearch,
	        @RequestParam("sEcho") int sEcho, @RequestParam("messageState") int messageState) throws IOException {
		
		// get the data
		List<HL7OutQueue> hl7s = Context.getHL7Service().getHL7OutQueueBatch(iDisplayStart, iDisplayLength, messageState,
		    sSearch);
	
		// form the results dataset
		List<Object> results = new ArrayList<Object>();
		for (HL7OutQueue hl7 : hl7s)
			results.add(splitHL7OutQueue(hl7));
		
		// build the response
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("iTotalRecords", Context.getHL7Service().countHL7OutQueue(messageState, null));
		response.put("iTotalDisplayRecords", Context.getHL7Service().countHL7OutQueue(messageState, sSearch));
		response.put("sEcho", sEcho);
		response.put("aaData", results.toArray());
		
		// send it
		return response;
	}
	
	/**
	 * create an object array for a given HL7OutQueue
	 * 
	 * @param q HL7OutQueue object
	 * @return object array for use with datatables
	 */
	private Object[] splitHL7OutQueue(HL7OutQueue q) {
		// try to stick to basic types; String, Integer, etc (not Date)
		return new Object[] { q.getHL7OutQueueId().toString(), 
		     q.getHL7Data() };
	}
	
}
