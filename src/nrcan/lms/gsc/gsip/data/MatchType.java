//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.08.01 at 06:18:21 PM EDT 
//


package nrcan.lms.gsc.gsip.data;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for MatchType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MatchType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mime-type" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="source">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="useAnonFtp" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                 &lt;attribute name="alt-media-type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="proxy" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="pattern" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MatchType", namespace = "urn:x-gsip:1.0", propOrder = {
    "mimeType",
    "source"
})
public class MatchType {

    @XmlElement(name = "mime-type", namespace = "urn:x-gsip:1.0", required = true)
    protected List<MatchType.MimeType> mimeType;
    @XmlElement(namespace = "urn:x-gsip:1.0", required = true)
    protected MatchType.Source source;
    @XmlAttribute(name = "pattern")
    protected String pattern;

    /**
     * Gets the value of the mimeType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mimeType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMimeType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MatchType.MimeType }
     * 
     * 
     */
    public List<MatchType.MimeType> getMimeType() {
        if (mimeType == null) {
            mimeType = new ArrayList<MatchType.MimeType>();
        }
        return this.mimeType;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link MatchType.Source }
     *     
     */
    public MatchType.Source getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatchType.Source }
     *     
     */
    public void setSource(MatchType.Source value) {
        this.source = value;
    }

    /**
     * Gets the value of the pattern property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the value of the pattern property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPattern(String value) {
        this.pattern = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class MimeType {

        @XmlValue
        protected String value;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="useAnonFtp" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="alt-media-type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="proxy" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Source {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "useAnonFtp")
        protected Boolean useAnonFtp;
        @XmlAttribute(name = "alt-media-type")
        protected String altMediaType;
        @XmlAttribute(name = "proxy")
        protected Boolean proxy;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the useAnonFtp property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isUseAnonFtp() {
            if (useAnonFtp == null) {
                return false;
            } else {
                return useAnonFtp;
            }
        }

        /**
         * Sets the value of the useAnonFtp property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setUseAnonFtp(Boolean value) {
            this.useAnonFtp = value;
        }

        /**
         * Gets the value of the altMediaType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAltMediaType() {
            return altMediaType;
        }

        /**
         * Sets the value of the altMediaType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAltMediaType(String value) {
            this.altMediaType = value;
        }

        /**
         * Gets the value of the proxy property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isProxy() {
            if (proxy == null) {
                return false;
            } else {
                return proxy;
            }
        }

        /**
         * Sets the value of the proxy property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setProxy(Boolean value) {
            this.proxy = value;
        }

    }

}
