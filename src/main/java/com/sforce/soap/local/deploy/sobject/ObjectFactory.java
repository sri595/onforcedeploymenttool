
package com.sforce.soap.local.deploy.sobject;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;



/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sforce.soap.enterprise.sobject package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _OFSServerMetadataLogCOFSServerTargetOrgTokenC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__TargetOrgToken__c");
    private final static QName _OFSServerMetadataLogCOFSServerSourceOrganizationURLC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__SourceOrganizationURL__c");
    private final static QName _OFSServerMetadataLogCOFSServerBaseOrgUrlC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__BaseOrgUrl__c");
    private final static QName _OFSServerMetadataLogCOFSServerTargetOrgTokenNonEncryptedC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__TargetOrgTokenNonEncrypted__c");
    private final static QName _OFSServerMetadataLogCOFSServerOrganizationIdC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__OrganizationId__c");
    private final static QName _OFSServerMetadataLogCOFSServerRecordIdC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__RecordId__c");
    private final static QName _OFSServerMetadataLogCOFSServerSourceOrgTokenC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__SourceOrgToken__c");
    private final static QName _OFSServerMetadataLogCOFSServerBaseOrgRefreshTokenC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__BaseOrgRefreshToken__c");
    private final static QName _OFSServerMetadataLogCOFSServerSourceOrgRefreshTokenC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__SourceOrgRefreshToken__c");
    private final static QName _OFSServerMetadataLogCOFSServerBaseOrgIdC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__BaseOrgId__c");
    private final static QName _OFSServerMetadataLogCOFSServerIDC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__ID__c");
    private final static QName _OFSServerMetadataLogCOFSServerBaseOrgTokenC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__BaseOrgToken__c");
    private final static QName _OFSServerMetadataLogCOFSServerNameC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__Name__c");
    private final static QName _OFSServerMetadataLogCOFSServerStatusC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__Status__c");
    
    private final static QName _OFSServerMetadataLogCOFSServerActionC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__Action__c");
    private final static QName _OFSServerMetadataLogCOFSServerTargetOrgRefreshTokenC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__TargetOrgRefreshToken__c");
    private final static QName _OFSServerMetadataLogCOFSServerEnableVersionControlC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__Enable_Version_Control__c");
    private final static QName _OFSServerMetadataLogCOFSServerGITServerURLC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__GIT_Server_URL__c");

    
    
    private final static QName _OFSServerMetadataLogCOFSServerGITAccessTokenC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__GIT_Access_Token__c");
    private final static QName _OFSServerMetadataLogCOFSServerBitBucketRefreshTOkenC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__BitBucket_RefreshTOken__c");
    private final static QName _OFSServerMetadataLogCOFSServerBitBucketURLC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__BitBucket_URL__c");
    private final static QName _OFSServerMetadataLogCOFSServerBitBucketAccessTokenC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__BitBucket_AccessToken__c");
    private final static QName _OFSServerMetadataLogCOFSServerBitBucketUserNameC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__BitBucket_User_Name__c");
    private final static QName _OFSServerMetadataLogCOFSServerGITUserNameC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__GIT_User_Name__c");
    private final static QName _OFSServerMetadataLogCOFSServerRepositoryIdC_QNAME = new QName("urn:sobject.enterprise.soap.sforce.com", "OFSServer__RepositoryId__c");
    
    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sforce.soap.enterprise.sobject
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OFSServerMetadataLogC }
     * 
     */
    public OFSServerMetadataLogC createOFSServerMetadataLogC() {
        return new OFSServerMetadataLogC();
    }

    /**
     * Create an instance of {@link SObject }
     * 
     */
    public com.sforce.soap.enterprise.sobject.SObject createSObject() {
        return new com.sforce.soap.enterprise.sobject.SObject();
    }

    /**
     * Create an instance of {@link AggregateResult }
     * 
     */
    public AggregateResult createAggregateResult() {
        return new AggregateResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__TargetOrgToken__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerTargetOrgTokenC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerTargetOrgTokenC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__BaseOrgUrl__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerBaseOrgUrlC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerBaseOrgUrlC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__OrganizationId__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerOrganizationIdC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerOrganizationIdC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__TargetOrgTokenNonEncrypted__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerTargetOrgTokenNonEncryptedC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerTargetOrgTokenNonEncryptedC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__BaseOrgId__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerBaseOrgIdC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerBaseOrgIdC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__BaseOrgToken__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerBaseOrgTokenC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerBaseOrgTokenC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__Action__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerActionC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerActionC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__RecordId__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerRecordIdC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerRecordIdC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__TargetOrgRefreshToken__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerTargetOrgRefreshTokenC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerTargetOrgRefreshTokenC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__SourceOrgToken__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerSourceOrgTokenC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerSourceOrgTokenC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__BaseOrgRefreshToken__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerBaseOrgRefreshTokenC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerBaseOrgRefreshTokenC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__SourceOrgRefreshToken__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerSourceOrgRefreshTokenC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerSourceOrgRefreshTokenC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__ID__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerIDC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerIDC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__Name__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerNameC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerNameC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__Status__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerStatusC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerStatusC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__SourceOrganizationURL__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerSourceOrganizationURLC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerSourceOrganizationURLC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__Enable_Version_Control__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerEnableVersionControlC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerEnableVersionControlC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__GIT_Server_URL__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerGITServerURLC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerGITServerURLC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__BitBucket_URL__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerBitBucketURLC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerBitBucketURLC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__BitBucket_AccessToken__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerBitBucketAccessTokenC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerBitBucketAccessTokenC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__BitBucket_User_Name__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerBitBucketUserNameC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerBitBucketUserNameC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__GIT_User_Name__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerGITUserNameC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerGITUserNameC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__GIT_Access_Token__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerGITAccessTokenC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerGITAccessTokenC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__BitBucket_RefreshTOken__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerBitBucketRefreshTOkenC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerBitBucketRefreshTOkenC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:sobject.enterprise.soap.sforce.com", name = "OFSServer__RepositoryId__c", scope = OFSServerMetadataLogC.class)
    public JAXBElement<String> createOFSServerMetadataLogCOFSServerRepositoryIdC(String value) {
        return new JAXBElement<String>(_OFSServerMetadataLogCOFSServerRepositoryIdC_QNAME, String.class, OFSServerMetadataLogC.class, value);
    }
}