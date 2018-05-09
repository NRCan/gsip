

package nrcan.lms.gsc.gsip.template;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the nrcan.lms.gsc.gsip.template package. 
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

    private final static QName _Templates_QNAME = new QName("urn:x-gsip:1.0", "Templates");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: nrcan.lms.gsc.gsip.template
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TemplatesType }
     * 
     */
    public TemplatesType createTemplatesType() {
        return new TemplatesType();
    }

    /**
     * Create an instance of {@link TemplateType }
     * 
     */
    public TemplateType createTemplateType() {
        return new TemplateType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TemplatesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:x-gsip:1.0", name = "Templates")
    public JAXBElement<TemplatesType> createTemplates(TemplatesType value) {
        return new JAXBElement<TemplatesType>(_Templates_QNAME, TemplatesType.class, null, value);
    }

}
