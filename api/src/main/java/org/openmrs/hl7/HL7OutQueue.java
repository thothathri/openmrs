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
package org.openmrs.hl7;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsObject;
import java.util.Date;


/**
 * Represents an hl7 message that is saved when a new Patient is saved or when new Encounter is saved.
 * 
 * @see HL7Service
 */
public class HL7OutQueue extends BaseOpenmrsObject implements Serializable {
	
	private static final long serialVersionUID = 8882704913734764446L;
	
	private Integer hl7OutQueueId;
	
	private String errorMessage;
	
	private Integer messageState;
	
	private String hl7Data;
	
	private Date dateCreated;
	
	private Date dateProcessed;
	
	private Integer hoqdId;	

	/**
	 * Default constructor
	 */
	public HL7OutQueue() {
	}
	
	/**
	 * @return Returns the hl7Data.
	 */
	public String getHL7Data() {
		return hl7Data;
	}
	/**
	 * @param hl7Data The hl7Data to set.
	 */
	public void setHL7Data(String hl7Data) {
		this.hl7Data = hl7Data;
	}
	

	/**
	 * @return Returns the dateCreated.
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * @param dateCreated The dateCreated to set.
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	/**
	 * @return Returns the dateCreated.
	 */
	public Date getDateProcessed() {
		return dateProcessed;
	}
	
	/**
	 * @param dateCreated The dateCreated to set.
	 */
	public void setDateProcessed(Date dateProcessed) {
		this.dateCreated = dateProcessed;
	}
	
	/**
	 * @return Returns the hl7OutQueueId.
	 */
	public Integer getHL7OutQueueId() {
		return hl7OutQueueId;
	}
	
	/**
	 * @param hl7OutQueueId The hl7OutQueueId to set.
	 */
	public void setHL7OutQueueId(Integer hl7OutQueueId) {
		this.hl7OutQueueId = hl7OutQueueId;
	}
	
	/**
	 * @return Returns the errorMessage.
	 * @since 1.5
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
	 * @param errorMessage The errorMessage to set.
	 * @since 1.5
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * Can be one of the states in the {@link HL7Constants} file.
	 * 
	 * @return Returns the message State.
	 * @see HL7Constants#HL7_STATUS_PENDING
	 * @see HL7Constants#HL7_STATUS_ERROR
	 * @see HL7Constants#HL7_STATUS_PROCESSED
	 * @see HL7Constants#HL7_STATUS_PROCESSING
	 * @see HL7Constants#HL7_STATUS_DELETED
	 */
	public Integer getMessageState() {
		return messageState;
	}
	
	/**
	 * @param messageState The message State to set.
	 */
	public void setMessageState(Integer messageState) {
		this.messageState = messageState;
	}
	
	/**
	 * @see org.openmrs.OpenmrsObject#getId()
	 * @since 1.5
	 */
	public Integer getId() {
		return getHL7OutQueueId();
	}
	
	/**
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 * @since 1.5
	 */
	public void setId(Integer id) {
		setHL7OutQueueId(id);
	}
	
    /**
     * @return the hoqdId
     */
    public Integer getHoqdId() {
    	return hoqdId;
    }

	
    /**
     * @param hoqdId the hoqdId to set
     */
    public void setHoqdId(Integer hoqdId) {
    	this.hoqdId = hoqdId;
    }
    
    

}
