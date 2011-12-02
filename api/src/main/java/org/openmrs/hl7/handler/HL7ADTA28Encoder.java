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
package org.openmrs.hl7.handler;

import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonName;
import org.openmrs.User;
import org.openmrs.api.PatientIdentifierException;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.validator.PatientIdentifierValidator;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.app.Application;
import ca.uhn.hl7v2.app.ApplicationException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v25.datatype.CX;
import ca.uhn.hl7v2.model.v25.datatype.ID;
import ca.uhn.hl7v2.model.v25.datatype.TS;
import ca.uhn.hl7v2.model.v25.datatype.XPN;
import ca.uhn.hl7v2.model.v25.message.ADT_A05;
import ca.uhn.hl7v2.model.v25.message.ADT_A01;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.parser.DefaultXMLParser;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;

/* HL7 using HAPI to encode into ADT A05 Messages
 * 
 * ADT/ACK - Add person or patient information (Event A28)
 * 
 * FYI:  The 3rd field of MSH contains the sending application.  
 * For example, the Rwanda lab system uses 'neal_lims'.  
 * If neal_lims exists as an OpenMRS username, then this handler
 * will use that user as the creator for patients it creates.
 * If the sending application isn't setup as an OpenMRS user,
 * the creator will default to the user running this task.
 *  
 * TODO: You may wonder why the createPatient, validate, getMSH, 
 * getPIH and tsToDate code is duplicated in this file (and the R01
 * message handler file? It would be more useful to have these in the 
 * HL7 Utility file.  It's a good question, and it will happen 
 * soon.
 * 
 * The HL7 v2.5 manual table 0354 (section 2.17.3) describes A28.
 *   
 * There are many cases in HL7 where events (like A05, A14, A28, and A31) 
 * share a common structure.  This table also represented in HL7APIs 
 * eventmap properties file (http://tinyurl.com/2almfx)  -- describes 
 * exactly which events share which structures.
 *
 * So the answer to the A28 event is to use the ADT_A05 message 
 * structure from within the v2.5 object hierarchy.  Without going 
 * to the table, you can see this relationship in the description 
 * of the A28 event message structure (3.3.28), which is labeled as 
 * ADT^A28^ADT_A05.  This represents the message type (ADT), 
 * event (A28), and message structure (ADT_A05).
 * 
 * TODO: This ADT A28 handler does NOT currently handle ALL possible segments.
 * 		 Some of the segments that are not handled include these:
 * 			
 * 			EVN (Event type) - required to be backwardly compatible
 * 			SFT (Software segment)
 * 			PD1 (Additional demographics) (*)
 * 			ROL (Role)
 * 			NK1 (Next of Kin / associated parties) (*)
 * 			PV1/2 (Patient visit - additional information) (*)
 * 			DB1 (Disability information)
 * 			OBX (Observation / result)  (***)
 * 			AL1 (Allergy information)
 * 			DG1 (Diagnosis information)
 * 			DRG (Diagnosis related group)
 * 			PR1	(Procedures)
 * 			GT1 (Guarantor)
 * 			IN1 (Insurance)
 * 			ACC (Accident information)
 * 			UB1/2 (Universal Bill Information)
 * 
 *  NOTE:  The ones with (*) could be useful in the near future.
 */

/**
 * 
 * Encodes the HL7 messages(ADT_A28) using V25 of HAPI library
 *
 */
public class HL7ADTA28Encoder {
	
	private static int sequenceNumber = 0;
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	public  HL7ADTA28Encoder() {
	/* Default Constructor*/	
	}
	/**
	 * @param mshSegment the MSH object, which is the Message Header of the ADT_A05 message
	 * @param msgType the message code, in this case "ADT"
	 * @param trigEvent the trigger event, in this case "A05"
	 */
	public void getGeneralMshSeg(MSH mshSegment,String msgType, String trigEvent) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmm");
		String dateNow = formatter.format(currentDate.getTime());
		System.out.println("Now the date is :=>  " + dateNow);
		// Populate the MSH Segment
		try{
        mshSegment.getFieldSeparator().setValue("|");
        mshSegment.getEncodingCharacters().setValue("^~\\&");
        mshSegment.getDateTimeOfMessage().getTime().setValue(dateNow);
        mshSegment.getSendingApplication().getNamespaceID().setValue("OpenMrs");
        mshSegment.getReceivingApplication().getNamespaceID().setValue("XRayDiv");
        mshSegment.getSequenceNumber().setValue(Integer.toString(sequenceNumber++));
        mshSegment.getMessageType().getMessageCode().setValue(msgType);
        mshSegment.getMessageType().getTriggerEvent().setValue(trigEvent);
        mshSegment.getMessageType().getMessageStructure().setValue(msgType+" "+trigEvent);
		}
		catch(Exception e) {
			System.out.println("Exception Encountered!");
		}
	}

	/**
	 * @param pid the PID header
	 * @param person a Person object
	 * @param patientId the ID of the Patient
	 */
	public void getPatientInfo(PID pid,Person person) {
		Integer patientId = person.getPersonId();
		
 		try {
 		    pid.getPatientName(0).getFamilyName().getSurname().setValue(person.getFamilyName());
 		    pid.getPatientName(0).getGivenName().setValue(person.getGivenName());
 		    pid.getPatientIdentifierList(0).getIDNumber().setValue(Context.getPatientService().getPatient(patientId).getPatientIdentifier().getIdentifier());
 		}
 		catch(Exception e) {
 			log.error("Exception in HL7ADTA28Encoder#getPatientInfo",e);
 		}
	}
	
	/**
	 * @param patientId the ID of the Patient
	 * @return String with the encoded message
	 */
	public String HL7EncodeAdtA01Msg(Integer patientId) throws HL7Exception {

        ADT_A01 adt = new ADT_A01();

        // Populate the MSH Segment
        MSH mshSegment = adt.getMSH();
        getGeneralMshSeg(mshSegment,"ADT","A01");
        
       // Populate the PID Segment		
 		PersonName newPersonName = new PersonName();
 		
 		try {
 		   newPersonName = Context.getPersonService().getPersonName(patientId);
 		   
 		} 		
 		catch(Exception e)  {
 			System.out.println("Exception in HL7ADTA28Encoder");
 		}
 		
 		getPatientInfo(adt.getPID(),newPersonName.getPerson());
 		
        Parser parser = new PipeParser();
        String encodedMessage = parser.encode(adt);
 
        return encodedMessage;
    }

	/**
	 * @param patientId the ID of the Patient object
	 * @return String with the encoded message
	 */
	public String HL7EncodeAdtA05Msg(Patient patient) throws HL7Exception {

        ADT_A05 adt = new ADT_A05();
        Integer patientId = patient.getPatientId();
        // Populate the MSH Segment
        MSH mshSegment = adt.getMSH();
        getGeneralMshSeg(mshSegment,"ADT","A05");

       // Populate the PID Segment		
 		PersonName newPersonName = new PersonName();
 		
 		try {
 		   newPersonName = Context.getPersonService().getPersonName(patientId);
 		}
 		
 		catch(Exception e)  {
 			System.out.println("Exception in HL7ADTA28Encoder");
 		}
 		
 		getPatientInfo(adt.getPID(),newPersonName.getPerson());
		
        Parser parser = new PipeParser();
        String encodedMessage = parser.encode(adt);

        return encodedMessage;
    }
}


