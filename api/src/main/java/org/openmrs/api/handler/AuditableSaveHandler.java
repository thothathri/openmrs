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
package org.openmrs.api.handler;

import java.util.Date;

import org.openmrs.Auditable;
import org.openmrs.User;
import org.openmrs.annotation.Handler;
import org.openmrs.aop.RequiredDataAdvice;

/**
 * This class deals with any object that implements {@link Auditable}. When an {@link Auditable} is
 * saved (via a save* method in a service), this handler is automatically called by the
 * {@link RequiredDataAdvice} AOP class. <br/>
 * <br/>
 * This sets the changedBy and dateChanged on the object if it has been saved previously (aka, it
 * has an id).
 * 
 * @see RequiredDataHandler
 * @since 1.5
 */
@Handler(supports = Auditable.class)
public class AuditableSaveHandler implements SaveHandler<Auditable> {
	
	/**
	 * This sets the changedBy and dateChanged on the object if it has been saved already (aka, it
	 * has an id).
	 * 
	 * @see org.openmrs.api.handler.RequiredDataHandler#handle(org.openmrs.OpenmrsObject,
	 *      org.openmrs.User, java.util.Date, java.lang.String)
	 * @should set creator if null
	 * @should not set creator if non null
	 * @should set dateCreated if null
	 * @should not set dateCreated if non null
	 */
	public void handle(Auditable auditable, User currentUser, Date currentDate, String reason) {
		
		// only set the creator and date created if they weren't set by the user already
		if (auditable.getCreator() == null) {
			auditable.setCreator(currentUser);
		}
		
		if (auditable.getDateCreated() == null) {
			auditable.setDateCreated(currentDate);
		}
		
		// changedBy and dateChanged are no longer set here because the recursion logic 
		// actually caused problems (updates) on objects that should not have been updated
		// see TRUNK-1930 for more background info
		// Go look at the AuditableInterceptor for where this is implemented now
	}
	
}
