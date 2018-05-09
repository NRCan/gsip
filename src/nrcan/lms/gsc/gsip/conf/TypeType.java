

package nrcan.lms.gsc.gsip.conf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour TypeType complex type.
 * 
 * 
 * <pre>
 * &lt;complexType name="TypeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="sameAs" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mime-type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="formats" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeType")
public class TypeType {

    @XmlAttribute(name = "sameAs")
    protected String sameAs;
    @XmlAttribute(name = "mime-type", required = true)
    protected String mimeType;
    @XmlAttribute(name = "formats")
    protected String formats;

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSameAs() {
        return sameAs;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSameAs(String value) {
        this.sameAs = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormats() {
        return formats;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormats(String value) {
        this.formats = value;
    }

}
