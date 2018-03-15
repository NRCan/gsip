//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2018.03.02 à 09:02:34 AM EST 
//


package nrcan.lms.gsc.gsip.conf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour ConfigurationType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="ConfigurationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="types" type="{urn:x-gsip:1.0}TypesType"/>
 *         &lt;element name="parameters" type="{urn:x-gsip:1.0}ParametersType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigurationType", propOrder = {
    "types",
    "parameters"
})
public class ConfigurationType {

    @XmlElement(required = true)
    protected TypesType types;
    @XmlElement(required = true)
    protected ParametersType parameters;

    /**
     * Obtient la valeur de la propriété types.
     * 
     * @return
     *     possible object is
     *     {@link TypesType }
     *     
     */
    public TypesType getTypes() {
        return types;
    }

    /**
     * Définit la valeur de la propriété types.
     * 
     * @param value
     *     allowed object is
     *     {@link TypesType }
     *     
     */
    public void setTypes(TypesType value) {
        this.types = value;
    }

    /**
     * Obtient la valeur de la propriété parameters.
     * 
     * @return
     *     possible object is
     *     {@link ParametersType }
     *     
     */
    public ParametersType getParameters() {
        return parameters;
    }

    /**
     * Définit la valeur de la propriété parameters.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametersType }
     *     
     */
    public void setParameters(ParametersType value) {
        this.parameters = value;
    }

}
