//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.02.03 at 05:48:04 PM CET 
//


package ftn.xml.patent.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TPoslovno_Lice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPoslovno_Lice">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ftn.uns.ac.rs/jaxb/patent}TLice">
 *       &lt;sequence>
 *         &lt;element name="Poslovno_ime">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPoslovno_Lice", propOrder = {
    "poslovnoIme"
})
public class TPoslovnoLice
    extends TLice
{

    @XmlElement(name = "Poslovno_ime", required = true)
    protected String poslovnoIme;

    /**
     * Gets the value of the poslovnoIme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoslovnoIme() {
        return poslovnoIme;
    }

    /**
     * Sets the value of the poslovnoIme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoslovnoIme(String value) {
        this.poslovnoIme = value;
    }

}
