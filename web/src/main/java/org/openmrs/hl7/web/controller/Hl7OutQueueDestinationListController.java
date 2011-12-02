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
import org.openmrs.hl7.HL7OutQueueDestination;
import org.openmrs.hl7.HL7Service;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Hl7OutQueueDestinationListController {
	
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
	@RequestMapping("/admin/hl7/hl7OutQueueDestinationList.htm")
	public String listOutQueueDestinations(ModelMap modelMap) {
		return "/admin/hl7/hl7OutQueueDestinationList";
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
	@RequestMapping("/admin/hl7/hl7OutQueueDestinationList.json")
	public @ResponseBody
	List<HL7OutQueueDestination> getHL7OutQueueDestinationBatchAsJson() throws IOException {
		
		// get the data
		List<HL7OutQueueDestination> hl7OutQueueDestinations = Context.getHL7Service().getAllHL7OutQueueDestinations();

		// send it
		return hl7OutQueueDestinations;
	}
	

	
}
