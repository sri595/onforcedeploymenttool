
package org.sforce.soap.local.sobject;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OFSClient__MetadataLogInformation__c complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OFSClient__MetadataLogInformation__c">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:sobject.enterprise.soap.sforce.com}sObject">
 *       &lt;sequence>
 *         &lt;element name="OFSClient__Action__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__BaseOrgId__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__BaseOrgRefreshToken__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__BaseOrgToken__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__BaseOrgUrl__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__ID__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__OrganizationId__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__RecordId__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__SourceOrgRefreshToken__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__SourceOrgToken__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__SourceOrganizationURL__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__Status__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__TargetOrgRefreshToken__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__TargetOrgTokenNonEncrypted__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OFSClient__TargetOrgToken__c" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OFSClient__MetadataLogInformation__c", propOrder = {
    "ofsClientClientActionC",
    "ofsClientClientBaseOrgIdC",
    "ofsClientClientBaseOrgRefreshTokenC",
    "ofsClientClientBaseOrgTokenC",
    "ofsClientClientBaseOrgUrlC",
    "ofsClientClientIDC",
    "ofsClientClientOrganizationIdC",
    "ofsClientClientRecordIdC",
    "ofsClientClientSourceOrgRefreshTokenC",
    "ofsClientClientSourceOrgTokenC",
    "ofsClientClientSourceOrganizationURLC",
    "ofsClientClientStatusC",
    "ofsClientClientTargetOrgRefreshTokenC",
    "ofsClientClientTargetOrgTokenNonEncryptedC",
    "ofsClientClientTargetOrgTokenC",
    "ofsClientOverrideC",
    "ofsClientBitBucketAccessTokenC",
    "ofsClientBitBucketRefreshTOkenC",
    "ofsClientBitBucketURLC",
    "ofsClientBitBucketUserNameC",
    "ofsClientGITAccessTokenC",
    "ofsClientGITURLC",
    "ofsClientGITUserNameC",
    "ofsClientRepositoryIdC"
    
})
public class OFSClientMetadataLogInformationC
    extends SObject
{

    @XmlElementRef(name = "OFSClient__Action__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientActionC;
    @XmlElementRef(name = "OFSClient__BaseOrgId__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientBaseOrgIdC;
    @XmlElementRef(name = "OFSClient__BaseOrgRefreshToken__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientBaseOrgRefreshTokenC;
    @XmlElementRef(name = "OFSClient__BaseOrgToken__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientBaseOrgTokenC;
    @XmlElementRef(name = "OFSClient__BaseOrgUrl__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientBaseOrgUrlC;
    @XmlElementRef(name = "OFSClient__ID__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientIDC;
    @XmlElementRef(name = "OFSClient__OrganizationId__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientOrganizationIdC;
    @XmlElementRef(name = "OFSClient__RecordId__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientRecordIdC;
    @XmlElementRef(name = "OFSClient__SourceOrgRefreshToken__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientSourceOrgRefreshTokenC;
    @XmlElementRef(name = "OFSClient__SourceOrgToken__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientSourceOrgTokenC;
    @XmlElementRef(name = "OFSClient__SourceOrganizationURL__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientSourceOrganizationURLC;
    @XmlElementRef(name = "OFSClient__Status__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientStatusC;
    @XmlElementRef(name = "OFSClient__TargetOrgRefreshToken__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientTargetOrgRefreshTokenC;
    @XmlElementRef(name = "OFSClient__TargetOrgTokenNonEncrypted__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientTargetOrgTokenNonEncryptedC;
    @XmlElementRef(name = "OFSClient__TargetOrgToken__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class)
    protected JAXBElement<String> ofsClientClientTargetOrgTokenC;
    
    @XmlElementRef(name = "OFSClient__Override__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class, required = false)
    protected JAXBElement<Boolean> ofsClientOverrideC;
    @XmlElementRef(name = "OFSClient__BitBucket_AccessToken__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ofsClientBitBucketAccessTokenC;
    @XmlElementRef(name = "OFSClient__BitBucket_RefreshTOken__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ofsClientBitBucketRefreshTOkenC;
    @XmlElementRef(name = "OFSClient__BitBucket_URL__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ofsClientBitBucketURLC;
    @XmlElementRef(name = "OFSClient__BitBucket_User_Name__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ofsClientBitBucketUserNameC;
    @XmlElementRef(name = "OFSClient__GIT_Access_Token__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ofsClientGITAccessTokenC;
    @XmlElementRef(name = "OFSClient__GIT_URL__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ofsClientGITURLC;
    @XmlElementRef(name = "OFSClient__GIT_User_Name__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ofsClientGITUserNameC;
    @XmlElementRef(name = "OFSClient__RepositoryId__c", namespace = "urn:sobject.enterprise.soap.sforce.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ofsClientRepositoryIdC;
    /**
     * Gets the value of the ofsClientClientActionC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientActionC() {
        return ofsClientClientActionC;
    }

    /**
     * Sets the value of the ofsClientClientActionC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientActionC(JAXBElement<String> value) {
        this.ofsClientClientActionC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientBaseOrgIdC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientBaseOrgIdC() {
        return ofsClientClientBaseOrgIdC;
    }

    /**
     * Sets the value of the ofsClientClientBaseOrgIdC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientBaseOrgIdC(JAXBElement<String> value) {
        this.ofsClientClientBaseOrgIdC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientBaseOrgRefreshTokenC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientBaseOrgRefreshTokenC() {
        return ofsClientClientBaseOrgRefreshTokenC;
    }

    /**
     * Sets the value of the ofsClientClientBaseOrgRefreshTokenC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientBaseOrgRefreshTokenC(JAXBElement<String> value) {
        this.ofsClientClientBaseOrgRefreshTokenC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientBaseOrgTokenC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientBaseOrgTokenC() {
        return ofsClientClientBaseOrgTokenC;
    }

    /**
     * Sets the value of the ofsClientClientBaseOrgTokenC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientBaseOrgTokenC(JAXBElement<String> value) {
        this.ofsClientClientBaseOrgTokenC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientBaseOrgUrlC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientBaseOrgUrlC() {
        return ofsClientClientBaseOrgUrlC;
    }

    /**
     * Sets the value of the ofsClientClientBaseOrgUrlC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientBaseOrgUrlC(JAXBElement<String> value) {
        this.ofsClientClientBaseOrgUrlC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientIDC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientIDC() {
        return ofsClientClientIDC;
    }

    /**
     * Sets the value of the ofsClientClientIDC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientIDC(JAXBElement<String> value) {
        this.ofsClientClientIDC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientOrganizationIdC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientOrganizationIdC() {
        return ofsClientClientOrganizationIdC;
    }

    /**
     * Sets the value of the ofsClientClientOrganizationIdC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientOrganizationIdC(JAXBElement<String> value) {
        this.ofsClientClientOrganizationIdC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientRecordIdC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientRecordIdC() {
        return ofsClientClientRecordIdC;
    }

    /**
     * Sets the value of the ofsClientClientRecordIdC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientRecordIdC(JAXBElement<String> value) {
        this.ofsClientClientRecordIdC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientSourceOrgRefreshTokenC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientSourceOrgRefreshTokenC() {
        return ofsClientClientSourceOrgRefreshTokenC;
    }

    /**
     * Sets the value of the ofsClientClientSourceOrgRefreshTokenC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientSourceOrgRefreshTokenC(JAXBElement<String> value) {
        this.ofsClientClientSourceOrgRefreshTokenC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientSourceOrgTokenC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientSourceOrgTokenC() {
        return ofsClientClientSourceOrgTokenC;
    }

    /**
     * Sets the value of the ofsClientClientSourceOrgTokenC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientSourceOrgTokenC(JAXBElement<String> value) {
        this.ofsClientClientSourceOrgTokenC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientSourceOrganizationURLC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientSourceOrganizationURLC() {
        return ofsClientClientSourceOrganizationURLC;
    }

    /**
     * Sets the value of the ofsClientClientSourceOrganizationURLC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientSourceOrganizationURLC(JAXBElement<String> value) {
        this.ofsClientClientSourceOrganizationURLC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientStatusC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientStatusC() {
        return ofsClientClientStatusC;
    }

    /**
     * Sets the value of the ofsClientClientStatusC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientStatusC(JAXBElement<String> value) {
        this.ofsClientClientStatusC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientTargetOrgRefreshTokenC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientTargetOrgRefreshTokenC() {
        return ofsClientClientTargetOrgRefreshTokenC;
    }

    /**
     * Sets the value of the ofsClientClientTargetOrgRefreshTokenC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientTargetOrgRefreshTokenC(JAXBElement<String> value) {
        this.ofsClientClientTargetOrgRefreshTokenC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientTargetOrgTokenNonEncryptedC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientTargetOrgTokenNonEncryptedC() {
        return ofsClientClientTargetOrgTokenNonEncryptedC;
    }

    /**
     * Sets the value of the ofsClientClientTargetOrgTokenNonEncryptedC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientTargetOrgTokenNonEncryptedC(JAXBElement<String> value) {
        this.ofsClientClientTargetOrgTokenNonEncryptedC = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ofsClientClientTargetOrgTokenC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientTargetOrgTokenC() {
        return ofsClientClientTargetOrgTokenC;
    }

    /**
     * Sets the value of the ofsClientClientTargetOrgTokenC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientTargetOrgTokenC(JAXBElement<String> value) {
        this.ofsClientClientTargetOrgTokenC = ((JAXBElement<String> ) value);
    }
    /**
     * Gets the value of the ofsClientOverrideC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getOFSClientOverrideC() {
        return ofsClientOverrideC;
    }

    /**
     * Sets the value of the ofsClientOverrideC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setOFSClientOverrideC(JAXBElement<Boolean> value) {
        this.ofsClientOverrideC = value;
    }
    
    /**
     * Gets the value of the ofsClientBitBucketAccessTokenC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientBitBucketAccessTokenC() {
        return ofsClientBitBucketAccessTokenC;
    }

    /**
     * Sets the value of the ofsClientBitBucketAccessTokenC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientBitBucketAccessTokenC(JAXBElement<String> value) {
        this.ofsClientBitBucketAccessTokenC = value;
    }

    /**
     * Gets the value of the ofsClientBitBucketRefreshTOkenC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientBitBucketRefreshTOkenC() {
        return ofsClientBitBucketRefreshTOkenC;
    }

    /**
     * Sets the value of the ofsClientBitBucketRefreshTOkenC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientBitBucketRefreshTOkenC(JAXBElement<String> value) {
        this.ofsClientBitBucketRefreshTOkenC = value;
    }

    /**
     * Gets the value of the ofsClientBitBucketURLC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientBitBucketURLC() {
        return ofsClientBitBucketURLC;
    }

    /**
     * Sets the value of the ofsClientBitBucketURLC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientBitBucketURLC(JAXBElement<String> value) {
        this.ofsClientBitBucketURLC = value;
    }

    /**
     * Gets the value of the ofsClientBitBucketUserNameC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientBitBucketUserNameC() {
        return ofsClientBitBucketUserNameC;
    }

    /**
     * Sets the value of the ofsClientBitBucketUserNameC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientBitBucketUserNameC(JAXBElement<String> value) {
        this.ofsClientBitBucketUserNameC = value;
    }

    /**
     * Gets the value of the ofsClientGITAccessTokenC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientGITAccessTokenC() {
        return ofsClientGITAccessTokenC;
    }

    /**
     * Sets the value of the ofsClientGITAccessTokenC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientGITAccessTokenC(JAXBElement<String> value) {
        this.ofsClientGITAccessTokenC = value;
    }

    /**
     * Gets the value of the ofsClientGITURLC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientGITURLC() {
        return ofsClientGITURLC;
    }

    /**
     * Sets the value of the ofsClientGITURLC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientGITURLC(JAXBElement<String> value) {
        this.ofsClientGITURLC = value;
    }

    /**
     * Gets the value of the ofsClientGITUserNameC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientGITUserNameC() {
        return ofsClientGITUserNameC;
    }

    /**
     * Sets the value of the ofsClientGITUserNameC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientGITUserNameC(JAXBElement<String> value) {
        this.ofsClientGITUserNameC = value;
    }
    /**
     * Gets the value of the ofsClientRepositoryIdC property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOFSClientRepositoryIdC() {
        return ofsClientRepositoryIdC;
    }

    /**
     * Sets the value of the ofsClientRepositoryIdC property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOFSClientRepositoryIdC(JAXBElement<String> value) {
        this.ofsClientRepositoryIdC = value;
    }

    
}
