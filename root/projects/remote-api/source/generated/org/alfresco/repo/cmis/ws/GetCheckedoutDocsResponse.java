
package org.alfresco.repo.cmis.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCheckedoutDocsResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="getCheckedoutDocsResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="documents" type="{http://www.cmis.org/ns/1.0}objectCollectionType"/>
 *           &lt;element ref="{http://www.cmis.org/ns/1.0}hasMoreItems"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "documents",
    "hasMoreItems"
})
@XmlRootElement(name = "getCheckedoutDocsResponse")
public class GetCheckedoutDocsResponse {

    @XmlElement(namespace = "http://www.cmis.org/ns/1.0", required = true)
    protected ObjectCollectionType documents;
    @XmlElement(namespace = "http://www.cmis.org/ns/1.0")
    protected boolean hasMoreItems;

    /**
     * Gets the value of the documents property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectCollectionType }
     *     
     */
    public ObjectCollectionType getDocuments() {
        return documents;
    }

    /**
     * Sets the value of the documents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectCollectionType }
     *     
     */
    public void setDocuments(ObjectCollectionType value) {
        this.documents = value;
    }

    /**
     * Gets the value of the hasMoreItems property.
     * 
     */
    public boolean isHasMoreItems() {
        return hasMoreItems;
    }

    /**
     * Sets the value of the hasMoreItems property.
     * 
     */
    public void setHasMoreItems(boolean value) {
        this.hasMoreItems = value;
    }

}
