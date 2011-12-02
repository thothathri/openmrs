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
package org.openmrs.web.controller.hl7;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.hl7.HL7OutQueueDestination;
import org.openmrs.hl7.HL7Source;
import org.openmrs.api.APIException;
import org.openmrs.hl7.HL7Service;
import org.openmrs.api.context.Context;
import org.openmrs.web.WebConstants;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;
//import org.openmrs.User;
import java.lang.Integer;
import java.lang.Boolean;




/**
 * This is the controlling class for hl7SourceForm.jsp page. It initBinder and formBackingObject
 * are called before page load. After submission,The onSubmit function receives the form/command object that was modified by the input form
 * and saves it to the db
 *
 */
@Controller
public class HL7OutQueueDestinationFormController extends SimpleFormController {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(value = "/admin/hl7/hl7OutQueueDestinationForm.htm", method = RequestMethod.POST)
		public void afterPageSubmission(
				ModelMap map,
				@RequestParam("name") String name,
				@RequestParam("description") String description,
				@RequestParam("destination") String destination,
				HttpSession httpSession) {
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$IN THIS METHOD!$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$IN THIS METHOD!$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		}
	
	/**
	 * The onSubmit function receives the form/command object that was modified by the input form
	 * and saves it to the db
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object obj,
	        BindException errors) throws Exception {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String destination = request.getParameter("destination");
		//Integer id = Context.getAuthenticatedUser().getId();
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmm");
		String dateNow = formatter.format(currentDate.getTime());
		Date date = formatter.parse(dateNow);
		Integer i = 1, id=1;
		Boolean b = false;
		
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$WOWOWOWW$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+name);
		System.out.println("Id USer"+id);
		log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$WOWOWOWW$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+name);
		HttpSession httpSession = request.getSession();
		
		String view = getFormView();
		
		if (Context.isAuthenticated()) {
			System.out.println("*********AUTHENTICATED***************");
			log.info("*********AUTHENTICATED***************");
	//		HL7OutQueueDestination hl7OutQueueDestination = (HL7OutQueueDestination) obj;
			HL7OutQueueDestination hl7OutQueueDestination = new HL7OutQueueDestination();
			hl7OutQueueDestination.setName(name);
			hl7OutQueueDestination.setDescription(description);
			hl7OutQueueDestination.setDestination(destination);
			hl7OutQueueDestination.setCreatedBy(id);
			hl7OutQueueDestination.setDateCreated(date);
			hl7OutQueueDestination.setHoqdId(i);
			hl7OutQueueDestination.setRetired(b);
			HL7Service es = Context.getHL7Service();

			
		//	if (request.getParameter("save") != null) {
				System.out.println("******* PHASE 1 *******");
			    hl7OutQueueDestination = es.saveHL7OutQueueDestination(hl7OutQueueDestination);
			    System.out.println("******* PHASE 2 *******");
				view = getSuccessView();
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "HL7OutQueueDestination.saved");
		//	}
/*
			// if the user is retiring out the VisitType
			else if (request.getParameter("retire") != null) {
				String retireReason = request.getParameter("retireReason");
				if (visitType.getVisitTypeId() != null && !(StringUtils.hasText(retireReason))) {
					errors.reject("retireReason", "general.retiredReason.empty");
					return showForm(request, response, errors);
				}
				
				es.retireVisitType(visitType, retireReason);
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "VisitType.retiredSuccessfully");
				
				view = getSuccessView();
			}

			// if the user is purging the visitType
			else if (request.getParameter("purge") != null) {
				
				try {
					es.purgeVisitType(visitType);
					httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "VisitType.purgedSuccessfully");
					view = getSuccessView();
				}
				catch (DataIntegrityViolationException e) {
					httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "error.object.inuse.cannot.purge");
					view = "visitType.form?visitTypeId=" + visitType.getVisitTypeId();
				}
				catch (APIException e) {
					httpSession.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "error.general: " + e.getLocalizedMessage());
					view = "visitType.form?visitTypeId=" + visitType.getVisitTypeId();
				}
			}
			*/
			
		}
		if(view == null)
		{
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$NULL$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$NULL$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		}
		
		return new ModelAndView(new RedirectView(view));
	}
	
	/**
	 * This is called prior to displaying a form for the first time. It tells Spring the
	 * form/command object to load into the request
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		HL7OutQueueDestination hl7OutQueueDestination = null;
		
		if (Context.isAuthenticated()) {
			HL7Service os = Context.getHL7Service();
			String hoqdId = request.getParameter("hoqdId");
			if (hoqdId != null)
				hl7OutQueueDestination = os.getHL7OutQueueDestination(Integer.valueOf(hoqdId));
		}
		
		if (hl7OutQueueDestination == null)
			hl7OutQueueDestination = new HL7OutQueueDestination();
		
		return hl7OutQueueDestination;
	}
	
}
