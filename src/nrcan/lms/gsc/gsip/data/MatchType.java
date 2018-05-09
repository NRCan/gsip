

package nrcan.lms.gsc.gsip.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java pour MatchType complex type.
 * 
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
 *                 &lt;attribute name="header" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "MatchType", propOrder = {
    "mimeType",
    "source"
})
public class MatchType {

    @XmlElement(name = "mime-type", required = true)
    protected List<MatchType.MimeType> mimeType;
    @XmlElement(required = true)
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
     * <p>Classe Java pour anonymous complex type.
     * 
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
     * <p>Classe Java pour anonymous complex type.
     * 
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="useAnonFtp" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *       &lt;attribute name="header" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        @XmlAttribute(name = "header")
        protected String header;
        @XmlAttribute(name = "proxy")
        protected Boolean proxy;

        /**
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
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHeader() {
            return header;
        }

        /**
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHeader(String value) {
            this.header = value;
        }

        /**
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
