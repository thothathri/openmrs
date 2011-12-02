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
import java.lang.Boolean;


/**
 * Represents an hl7 message that is saved when a new Patient is saved or when new Encounter is saved.
 * 
 * @see HL7Service
 */
public class HL7OutQueueDestination extends BaseOpenmrsObject implements Serializable {
	
	private static final long serialVersionUID = 8882704913734764499L;
	
	private Integer hoqdId;
	
	private String name;
	
	private String description;
	
	private String destination;
	
	private Date dateCreated;
	
	private Integer createdBy;
	
	private Date dateChanged;
	
	private Integer changedBy;
	
	private Date dateRetired;
	
	private Boolean retired;
	
	private Integer retiredBy;
	
	private String retireReason;
	
	/**
	 * Default constructor
	 */
	public HL7OutQueueDestination() {
	}

	
	/**
	 * @see org.openmrs.OpenmrsObject#getId()
	 * @since 1.5
	 */
	public Integer getId() {
		return getHoqdId();
	}
	
	/**
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 * @since 1.5
	 */
	public void setId(Integer id) {
		setHoqdId(id);
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
    
    /**
     * @return the createdBy
     */
    public Integer getCreatedBy() {
    	return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(Integer createdBy) {
    	this.createdBy = createdBy;
    }
    
    /**
     * @return the name
     */
    public String getName() {
    	return name;
    }

	
    /**
     * @param name the name to set
     */
    public void setName(String name) {
    	this.name = name;
    }

	
    /**
     * @return the description
     */
    public String getDescription() {
    	return description;
    }

	
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
    	this.description = description;
    }

	
    /**
     * @return the destination
     */
    public String getDestination() {
    	return destination;
    }

	
    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
    	this.destination = destination;
    }

	
    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
    	return dateCreated;
    }

	
    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
    	this.dateCreated = dateCreated;
    }


	
    /**
     * @return the dateChanged
     */
    public Date getDateChanged() {
    	return dateChanged;
    }

	
    /**
     * @param dateChanged the dateChanged to set
     */
    public void setDateChanged(Date dateChanged) {
    	this.dateChanged = dateChanged;
    }

	
    /**
     * @return the changedBy
     */
    public Integer getChangedBy() {
    	return changedBy;
    }

	
    /**
     * @param changedBy the changedBy to set
     */
    public void setChangedBy(Integer changedBy) {
    	this.changedBy = changedBy;
    }

	
    /**
     * @return the dateRetired
     */
    public Date getDateRetired() {
    	return dateRetired;
    }

	
    /**
     * @param dateRetired the dateRetired to set
     */
    public void setDateRetired(Date dateRetired) {
    	this.dateRetired = dateRetired;
    }

	
    /**
     * @return the retired
     */
    public Boolean getRetired() {
    	return retired;
    }

	
    /**
     * @param retired the retired to set
     */
    public void setRetired(Boolean retired) {
    	this.retired = retired;
    }
	
    /**
     * @return the retiredBy
     */
    public Integer getRetiredBy() {
    	return retiredBy;
    }

	
    /**
     * @param retiredBy the retiredBy to set
     */
    public void setRetiredBy(Integer retiredBy) {
    	this.retiredBy = retiredBy;
    }

	
    /**
     * @return the retireReason
     */
    public String getRetireReason() {
    	return retireReason;
    }

	
    /**
     * @param retireReason the retireReason to set
     */
    public void setRetireReason(String retireReason) {
    	this.retireReason = retireReason;
    }
	
	
	
}
