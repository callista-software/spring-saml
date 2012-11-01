/**
 *    Copyright 2011,2012 Callista Enterprise AB
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.callistasoftware.netcare.commons.saml;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class Citizen extends User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Svenskt personnummer med 12-siffror.
	 * OSIF: Subject.SerialNumber
	 */
	private final String crn;
	
	/**
	 * För och efternamn
	 * OSIF: Subject.commonName (if included)
	 */
	private String name;
	
	/**
	 * Förnamn. Ett eller flera, beror på olika CA
	 * OSIF: Subject.GivenName
	 */
	private String givenName;
	
	/**
	 * Efternamn OSIF: Subject.Surname
	 */
	private String surname;
	
	/**
	 * Landskod, SE=Sverige
	 * OSIF: Subject.CountryName
	 */
	private String countryName;
	
	/**
	 * F (female)/M (male)
	 * Använd näst sista siffra personnummer – jämn siffra F, udda M.
	 */
	private String gender;
	
	/**
	 * YYYYMMDD (ISO 8601)
	 * 8 första siffrorna i Subject_SerialNumber
	 */
	private String dateOfBirth;
	
	/**
	 * Ålder i år
	 * Räknas fram från 8 första siffrorna i
	 * Subject_SerialNumber
	 */
	private int age;
	
	/**
	 * Namn på CA
	 * OSIF: Issuer.CommonName
	 */
	private String issuerName;
	
	/**
	 * Organisationsnamn på CA (Utgivaren av elegitimationen)
	 * OSIF: Issuer.OrganizationName
	 */
	private String issuerOrganizationName;
	
	/**
	 * Inloggningsmetodens läsbara namn,
	 * Text beskrivning av autentiseringsmetod.
	 * OSIF: securitylevel.description
	 */
	private String securityLevelDescription;
	
	/**
	 * QAA nivå. (3 för e-legitimation på fil och 4 för e-legitimation på kort)
	 * OSIF: security.level
	 */
	private String securityLevel;
	
	/**
	 * Signerat ”kvitto” från CA om spärrkontroll
	 * skett med OCSP. Tomt om CRL använts.
	 * OSIF:validation.ocsp.responce
	 */
	private String ocspResponse;
	
	/**
	 * Personnummer med 10 siffror istället för
	 * 12. (position 3-12 i OSIF:Subject.SerialNumber)
	 */
	private String serialNumber;
	
	/**
	 * e-legitimationen/klientcertifikatets unika serienummer. Kan användas främst i
	 * forenstic syfte. OSIF: SerialNumber
	 * Förväxla ej med personnummer i Subject_SerialNumber.
	 */
	private String certificateSerialNumber;
	
	private Collection<GrantedAuthority> roles;
	
	Citizen(final String crn, final Collection<GrantedAuthority> roleList) {
		super(crn, "", roleList);
		this.crn = crn;
	}

	public String getGivenName() {
		return givenName;
	}

	void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurname() {
		return surname;
	}

	void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCountryName() {
		return countryName;
	}

	void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getGender() {
		return gender;
	}

	void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getAge() {
		return age;
	}

	void setAge(int age) {
		this.age = age;
	}

	public String getIssuerName() {
		return issuerName;
	}

	void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getIssuerOrganizationName() {
		return issuerOrganizationName;
	}

	void setIssuerOrganizationName(String issuerOrganizationName) {
		this.issuerOrganizationName = issuerOrganizationName;
	}

	public String getSecurityLevelDescription() {
		return securityLevelDescription;
	}

	void setSecurityLevelDescription(String securityLevelDescription) {
		this.securityLevelDescription = securityLevelDescription;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getOcspResponse() {
		return ocspResponse;
	}

	void setOcspResponse(String ocspResponse) {
		this.ocspResponse = ocspResponse;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCertificateSerialNumber() {
		return certificateSerialNumber;
	}

	void setCertificateSerialNumber(String certificateSerialNumber) {
		this.certificateSerialNumber = certificateSerialNumber;
	}

	public Collection<GrantedAuthority> getRoles() {
		return roles;
	}

	void setRoles(Collection<GrantedAuthority> roles) {
		this.roles = roles;
	}

	void setName(String name) {
		this.name = name;
	}

	public String getCrn() {
		return crn;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		final StringBuffer buf = new StringBuffer();
		buf.append("==== CITIZEN ====\n");
		buf.append("Crn: ").append(this.crn).append("\n");
		buf.append("Name: ").append(this.name).append("\n");
		buf.append("Given name: ").append(this.givenName).append("\n");
		buf.append("Surname: ").append(this.surname).append("\n");
		buf.append("Country: ").append(this.countryName).append("\n");
		buf.append("Gender: ").append(this.gender).append("\n");
		buf.append("Date of birth: ").append(this.dateOfBirth).append("\n");
		buf.append("Age: ").append(this.age).append("\n");
		buf.append("Issuer name: ").append(this.issuerName).append("\n");
		buf.append("Issuer organization: ").append(this.issuerOrganizationName).append("\n");
		buf.append("Security level: ").append(this.securityLevel).append("\n");
		buf.append("Security level description: ").append(this.securityLevelDescription).append("\n");
		buf.append("Serial number: ").append(this.serialNumber).append("\n");
		buf.append("Certificate serial number: ").append(this.certificateSerialNumber).append("\n");
		buf.append("=================\n");
		
		return buf.toString();
	}
}
