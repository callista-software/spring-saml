package org.callistasoftware.netcare.commons.saml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for populating a citizen object and set
 * it as the principal of the {@link SecurityContextHolder} if the
 * login was successful.
 *
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 */
@Service
public class CitizenAuthenticationService implements SAMLUserDetailsService {

	private static Logger log = LoggerFactory.getLogger(CitizenAuthenticationService.class);
	
	@Value("${saml.citizen.roles}")
	private String citizenRoles;
	
	@Override
	public Object loadUserBySAML(SAMLCredential credential)
			throws UsernameNotFoundException {
		// The Subject_SerialNumber field holds a Swedish personal id number with 12 digits.
        final String subjectSerialNumber = getValue(credential.getAttributeByName("Subject_SerialNumber"));
        log.debug("subjectSerialNumber: {}", subjectSerialNumber);
        
        if (subjectSerialNumber != null && subjectSerialNumber.length() == 12) {
        	return populateCitizen(credential);
        } else {
        	throw new UsernameNotFoundException("Civic registration number is invalid. Parsed value is " + subjectSerialNumber);
        }
	}
	
	private Citizen populateCitizen(final SAMLCredential credential) {
    	final String subjectSerialNumber = getValue(credential.getAttributeByName("Subject_SerialNumber"));
		final String subjectCommonName = getValue(credential.getAttributeByName("Subject_CommonName"));
		
		/*
		 * Resolve roles
		 */
		final Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		final String[] split = citizenRoles.split(",");
		for (final String r : split) {
			roles.add(new UserRole(r.trim()));
		}
		
		final Citizen citizen = new Citizen(subjectSerialNumber, roles);
		citizen.setName(subjectCommonName);
		citizen.setGivenName(getValue(credential.getAttributeByName("Subject_GivenName")));
		citizen.setSurname(getValue(credential.getAttributeByName("Subject_Surname")));
		
		final String age = getValue(credential.getAttributeByName("age"));
		citizen.setAge(age != null ? Integer.valueOf(age): null);
		
		citizen.setCertificateSerialNumber(getValue(credential.getAttributeByName("CertificateSerialNumber")));
		citizen.setCountryName(getValue(credential.getAttributeByName("Subject_CountryName")));
		citizen.setDateOfBirth(getValue(credential.getAttributeByName("dateOfBirth")));
		citizen.setGender(getValue(credential.getAttributeByName("Gender")));
		citizen.setIssuerName(getValue(credential.getAttributeByName("Issuer_CommonName")));
		citizen.setIssuerOrganizationName(getValue(credential.getAttributeByName("Issuer_OrganizationName")));
		citizen.setOcspResponse(getValue(credential.getAttributeByName("ValidationOcspResponce")));
		citizen.setSecurityLevel(getValue(credential.getAttributeByName("SecurityLevel")));
		citizen.setSecurityLevelDescription(getValue(credential.getAttributeByName("SecurityLevelDescription")));
		citizen.setSerialNumber(getValue(credential.getAttributeByName("sn_id")));
		
		return citizen;
	}
	
	private String getValue(final Attribute attr) {
		if (attr == null) {
			return null;
		}
		
		if (attr.getAttributeValues() != null) {
			return getAttrValue(attr.getAttributeValues());
		}
		
		return null;
	}

	private String getAttrValue(List<XMLObject> values) {
        if (values != null){
        	for(XMLObject obj: values) {
        		return obj.getDOM().getTextContent();
        	}                               
        }
        
        return null;
	}
}
