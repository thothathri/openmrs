package org.openmrs.web.dwr;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.Provider;

public class ProviderListItemTest {
	
	private Provider provider;
	
	@SuppressWarnings("serial")
	@Before
	public void setup() {
		provider = new Provider() {
			
			{
				setPerson(new Person() {
					
					{
						setNames(new HashSet<PersonName>() {
							
							{
								add(new PersonName() {
									
									{
										setGivenName("givenName");
										setFamilyName("familyName");
										setMiddleName("middleName");
										
									}
								});
							}
						});
					}
				});
			}
		};
	}
	
	/**
	 * @see ProviderListItem#getDisplayName()
	 * @verifies return a display name based on whether provider has a person associated
	 */
	@Test
	public void getDisplayName_shouldReturnADisplayNameBasedOnWhetherProviderHasAPersonAssociated() throws Exception {
		
		ProviderListItem providerListItem = new ProviderListItem(provider);
		Assert.assertEquals("givenName middleName familyName", providerListItem.getDisplayName());
	}
	
	/**
	 * @see ProviderListItem#getDisplayName()
	 * @verifies return a display name based on provider name when person is not associated
	 */
	@Test
	public void getDisplayName_shouldReturnADisplayNameBasedOnProviderNameWhenPersonIsNotAssociated() throws Exception {
		Provider provider = new Provider();
		provider.setName("providerName");
		
		ProviderListItem providerListItem = new ProviderListItem(provider);
		Assert.assertEquals("providerName", providerListItem.getDisplayName());
	}
	
	/**
	 * @see ProviderListItem#getIdentifier()
	 * @verifies return the identifier that is mentioned for the provider when a person is not
	 *           specified
	 */
	@Test
	public void getIdentifier_shouldReturnTheIdentifierThatIsMentionedForTheProviderWhenAPersonIsNotSpecified()
	        throws Exception {
		Provider provider = new Provider();
		provider.setName("providerName");
		provider.setIdentifier("identifier");
		ProviderListItem providerListItem = new ProviderListItem(provider);
		Assert.assertEquals("identifier", providerListItem.getIdentifier());
	}
	
	/**
	 * @see ProviderListItem#getProviderId()
	 * @verifies return the provider id
	 */
	@Test
	public void getProviderId_shouldReturnTheProviderId() throws Exception {
		provider.setProviderId(2);
		
		ProviderListItem providerListItem = new ProviderListItem(provider);
		Assert.assertEquals(new Integer(2), providerListItem.getProviderId());
	}
	
}
