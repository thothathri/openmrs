package org.openmrs.validator;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.ConceptMap;
import org.openmrs.ConceptMapType;
import org.openmrs.ConceptName;
import org.openmrs.ConceptReferenceTerm;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.DuplicateConceptNameException;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.Verifies;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

/**
 * Tests methods on the {@link ConceptValidator} class.
 */
public class ConceptValidatorTest extends BaseContextSensitiveTest {
	
	/**
	 * @verifies {@link ConceptValidator#validate(Object,Errors)} test = should fail if there is a
	 *           duplicate unretired concept name in the locale
	 */
	@Test(expected = DuplicateConceptNameException.class)
	@Verifies(value = "should fail if there is a duplicate unretired concept name in the locale", method = "validate(Concept)")
	public void validate_shouldFailIfThereIsADuplicateUnretiredConceptNameInTheLocale() throws Exception {
		Context.setLocale(new Locale("en"));
		Concept concept = Context.getConceptService().getConcept(5497);
		String duplicateName = concept.getFullySpecifiedName(Context.getLocale()).getName();
		
		ConceptName newName = new ConceptName(duplicateName, Context.getLocale());
		newName.setDateCreated(Calendar.getInstance().getTime());
		newName.setCreator(Context.getAuthenticatedUser());
		concept.addName(newName);
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Verifies(value = "should fail if the object parameter is null", method = "validate(Concept)")
	public void validate_shouldFailIfTheObjectParameterIsNull() throws Exception {
		Errors errors = new BindException(null, "concept");
		new ConceptValidator().validate(null, errors);
		Assert.assertTrue(errors.hasErrors());
	}
	
	@Test
	@Verifies(value = "should pass if the concept is being updated with no name change", method = "validate(Concept)")
	public void validate_shouldPassIfTheConceptIsBeingUpdatedWithNoNameChange() throws Exception {
		
		Concept conceptToUpdate = Context.getConceptService().getConcept(5497);
		conceptToUpdate.setCreator(Context.getAuthenticatedUser());
		Errors errors = new BindException(conceptToUpdate, "concept");
		new ConceptValidator().validate(conceptToUpdate, errors);
		Assert.assertFalse(errors.hasErrors());
		
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if any name is an empty string", method = "validate(Object,Errors)")
	public void validate_shouldFailIfAnyNameIsAnEmptyString() throws Exception {
		Concept concept = new Concept();
		concept.addName(new ConceptName("name", Context.getLocale()));
		concept.addName(new ConceptName("", Context.getLocale()));
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		Assert.assertEquals(true, errors.hasErrors());
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "fail if any name is a null value", method = "validate(Object,Errors)")
	public void validate_shouldFailIfAnyNameIsANullValue() throws Exception {
		Concept concept = new Concept();
		concept.addName(new ConceptName("name", Context.getLocale()));
		concept.addName(new ConceptName(null, Context.getLocale()));
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		Assert.assertEquals(true, errors.hasErrors());
	}
	
	/**
	 * Tests for duplicate names for the same concept and not with names for other concepts
	 * 
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test(expected = DuplicateConceptNameException.class)
	@Verifies(value = "should fail if any names in the same locale for this concept are similar", method = "validate(Object,Errors)")
	public void validate_shouldFailIfAnyNamesInTheSameLocaleForThisConceptAreSimilar() throws Exception {
		Concept concept = new Concept();
		concept.addName(new ConceptName("same name", Context.getLocale()));
		concept.addName(new ConceptName("same name", Context.getLocale()));
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
	}
	
	/**
	 * Tests for uniqueness amongst all fully specified names in the same locale
	 * 
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test(expected = DuplicateConceptNameException.class)
	@Verifies(value = "should fail if there is a duplicate unretired fully specified name in the same locale", method = "validate(Object,Errors)")
	public void validate_shouldFailIfThereIsADuplicateUnretiredFullySpecifiedNameInTheSameLocale() throws Exception {
		Context.setLocale(new Locale("en"));
		Concept concept = Context.getConceptService().getConcept(5497);
		Assert.assertEquals(true, concept.getFullySpecifiedName(Context.getLocale()).isFullySpecifiedName());
		String duplicateName = concept.getFullySpecifiedName(Context.getLocale()).getName();
		
		Concept anotherConcept = Context.getConceptService().getConcept(5089);
		anotherConcept.getFullySpecifiedName(Context.getLocale()).setName(duplicateName);
		Errors errors = new BindException(anotherConcept, "concept");
		new ConceptValidator().validate(anotherConcept, errors);
	}
	
	/**
	 * Tests for uniqueness amongst all preferred names in the same locale
	 * 
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test(expected = DuplicateConceptNameException.class)
	@Verifies(value = "should fail if there is a duplicate unretired preferred name in the same locale", method = "validate(Object,Errors)")
	public void validate_shouldFailIfThereIsADuplicateUnretiredPreferredNameInTheSameLocale() throws Exception {
		Context.setLocale(new Locale("en"));
		Concept concept = Context.getConceptService().getConcept(5497);
		ConceptName preferredName = new ConceptName("preferred name", Context.getLocale());
		concept.setPreferredName(preferredName);
		Context.getConceptService().saveConcept(concept);
		Assert.assertEquals("preferred name", concept.getPreferredName(Context.getLocale()).getName());
		
		Concept anotherConcept = Context.getConceptService().getConcept(5089);
		anotherConcept.getFullySpecifiedName(Context.getLocale()).setName("preferred name");
		
		Errors errors = new BindException(anotherConcept, "concept");
		new ConceptValidator().validate(anotherConcept, errors);
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if there is no name explicitly marked as fully specified", method = "validate(Object,Errors)")
	public void validate_shouldFailIfThereIsNoNameExplicitlyMarkedAsFullySpecified() throws Exception {
		Concept concept = Context.getConceptService().getConcept(5497);
		for (ConceptName name : concept.getNames())
			name.setConceptNameType(null);
		
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		Assert.assertEquals(true, errors.hasErrors());
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass if the concept has atleast one fully specified name added to it", method = "validate(Object,Errors)")
	public void validate_shouldPassIfTheConceptHasAtleastOneFullySpecifiedNameAddedToIt() throws Exception {
		Concept concept = new Concept();
		concept.addName(new ConceptName("one name", Context.getLocale()));
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		Assert.assertEquals(false, errors.hasErrors());
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass if the duplicate ConceptName is neither preferred nor fully Specified", method = "validate(Object,Errors)")
	public void validate_shouldPassIfTheDuplicateConceptNameIsNeitherPreferredNorFullySpecified() throws Exception {
		Context.setLocale(new Locale("en"));
		Concept concept = Context.getConceptService().getConcept(5497);
		//use a synonym as the duplicate name
		ConceptName duplicateName = concept.getSynonyms(Context.getLocale()).iterator().next();
		Assert.assertEquals(true, duplicateName.isSynonym());
		
		Concept anotherConcept = Context.getConceptService().getConcept(5089);
		anotherConcept.getFullySpecifiedName(Context.getLocale()).setName(duplicateName.getName());
		Errors errors = new BindException(anotherConcept, "concept");
		new ConceptValidator().validate(anotherConcept, errors);
		Assert.assertEquals(false, errors.hasErrors());
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass if the concept with a duplicate name is retired", method = "validate(Object,Errors)")
	public void validate_shouldPassIfTheConceptWithADuplicateNameIsRetired() throws Exception {
		Context.setLocale(new Locale("en"));
		Concept concept = Context.getConceptService().getConcept(5497);
		concept.setRetired(true);
		Context.getConceptService().saveConcept(concept);
		String duplicateName = concept.getFullySpecifiedName(Context.getLocale()).getName();
		
		Concept anotherConcept = Context.getConceptService().getConcept(5089);
		anotherConcept.getFullySpecifiedName(Context.getLocale()).setName(duplicateName);
		Errors errors = new BindException(anotherConcept, "concept");
		new ConceptValidator().validate(anotherConcept, errors);
		Assert.assertEquals(false, errors.hasErrors());
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if the concept map type property for a concept mapping is null", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheConceptMapTypePropertyForAConceptMappingIsNull() throws Exception {
		Concept concept = new Concept();
		concept.addName(new ConceptName("my name", Context.getLocale()));
		concept.addConceptMapping(new ConceptMap(Context.getConceptService().getConceptReferenceTerm(1), null));
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("conceptMappings[0].conceptMapType"));
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if the concept reference term property for a concept mapping is null", method = "validate(Object,Errors)")
	public void validate_shouldFailIfTheConceptReferenceTermPropertyForAConceptMappingIsNull() throws Exception {
		Concept concept = new Concept();
		concept.addName(new ConceptName("my name", Context.getLocale()));
		concept.addConceptMapping(new ConceptMap(null, Context.getConceptService().getConceptMapType(1)));
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("conceptMappings[0].conceptReferenceTerm"));
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass if the concept has a synonym that is also a short name", method = "validate(Object,Errors)")
	public void validate_shouldPassIfTheConceptHasASynonymThatIsAlsoAShortName() throws Exception {
		Concept concept = new Concept();
		concept.addName(new ConceptName("CD4", Context.getLocale()));
		// Add the short name. Because the short name is not counted as a Synonym. 
		// ConceptValidator will not record any errors.
		ConceptName name = new ConceptName("CD4", Context.getLocale());
		name.setConceptNameType(ConceptNameType.SHORT);
		concept.addName(name);
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		Assert.assertFalse(errors.hasErrors());
	}
	
	/**
	 * This test should pass if the map type id is set
	 * 
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if a concept map type created on the fly is used for a mapping", method = "validate(Object,Errors)")
	public void validate_shouldFailIfAConceptMapTypeCreatedOnTheFlyIsUsedForAMapping() throws Exception {
		Concept concept = new Concept();
		concept.addName(new ConceptName("my name", Context.getLocale()));
		ConceptMapType cmt = new ConceptMapType();
		cmt.setName("name");
		concept.addConceptMapping(new ConceptMap(Context.getConceptService().getConceptReferenceTerm(1), cmt));
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("conceptMappings[0].conceptMapType"));
	}
	
	/**
	 * This test should pass if the term id is set
	 * 
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if a term created on the fly is used for a mapping", method = "validate(Object,Errors)")
	public void validate_shouldFailIfATermCreatedOnTheFlyIsUsedForAMapping() throws Exception {
		Concept concept = new Concept();
		ConceptService cs = Context.getConceptService();
		concept.addName(new ConceptName("my name", Context.getLocale()));
		ConceptReferenceTerm term = new ConceptReferenceTerm();
		term.setCode("unique code");
		term.setConceptSource(cs.getConceptSource(1));
		
		concept.addConceptMapping(new ConceptMap(term, cs.getConceptMapType(1)));
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		Assert.assertEquals(true, errors.hasFieldErrors("conceptMappings[0].conceptReferenceTerm"));
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if a term is mapped multiple times to the same concept", method = "validate(Object,Errors)")
	public void validate_shouldFailIfATermIsMappedMultipleTimesToTheSameConcept() throws Exception {
		Concept concept = new Concept();
		ConceptService cs = Context.getConceptService();
		concept.addName(new ConceptName("my name", Context.getLocale()));
		ConceptMap map1 = new ConceptMap(cs.getConceptReferenceTerm(1), cs.getConceptMapType(1));
		concept.addConceptMapping(map1);
		ConceptMap map2 = new ConceptMap(cs.getConceptReferenceTerm(1), cs.getConceptMapType(1));
		concept.addConceptMapping(map2);
		
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		
		//the second mapping should be rejected
		Assert.assertEquals(true, errors.hasFieldErrors("conceptMappings[1]"));
	}
	
	/**
	 * @see {@link ConceptValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass if the duplicate name in the locale for the concept being validated is voided", method = "validate(Object,Errors)")
	public void validate_shouldPassIfTheDuplicateNameInTheLocaleForTheConceptBeingValidatedIsVoided() throws Exception {
		ConceptService cs = Context.getConceptService();
		ConceptName otherName = cs.getConceptName(1439);
		//sanity check since names should only be unique amongst preferred and fully specified names
		Assert.assertTrue(otherName.isFullySpecifiedName() || otherName.isPreferred());
		Assert.assertFalse(otherName.isVoided());
		Assert.assertFalse(otherName.getConcept().isRetired());
		
		//change to a duplicate name in the same locale
		ConceptName duplicateName = cs.getConceptName(2477);
		duplicateName.setName(otherName.getName());
		Concept concept = duplicateName.getConcept();
		concept.setPreferredName(duplicateName);
		//ensure that the name has been marked as preferred in its locale
		Assert.assertEquals(duplicateName, concept.getPreferredName(duplicateName.getLocale()));
		Assert.assertTrue(duplicateName.isPreferred());
		duplicateName.setVoided(true);
		
		Errors errors = new BindException(concept, "concept");
		new ConceptValidator().validate(concept, errors);
		Assert.assertFalse(errors.hasErrors());
	}
}
