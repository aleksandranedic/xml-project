//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.20 at 11:54:02 AM CET 
//


package ftn.xml.korisnik.model;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Ulica">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Broj" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="Postanski_broj">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *               &lt;totalDigits value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Grad">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Drzava">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ulica",
    "broj",
    "postanskiBroj",
    "grad",
    "drzava"
})
@XmlRootElement(name = "Adresa")
public class Adresa {

    @XmlElement(name = "Ulica", required = true)
    protected String ulica;
    @XmlElement(name = "Broj", required = true)
    protected BigInteger broj;
    @XmlElement(name = "Postanski_broj", required = true)
    protected BigInteger postanskiBroj;
    @XmlElement(name = "Grad", required = true)
    protected String grad;
    @XmlElement(name = "Drzava", required = true)
    protected String drzava;

    /**
     * Gets the value of the ulica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUlica() {
        return ulica;
    }

    /**
     * Sets the value of the ulica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUlica(String value) {
        this.ulica = value;
    }

    /**
     * Gets the value of the broj property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBroj() {
        return broj;
    }

    /**
     * Sets the value of the broj property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBroj(BigInteger value) {
        this.broj = value;
    }

    /**
     * Gets the value of the postanskiBroj property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPostanskiBroj() {
        return postanskiBroj;
    }

    /**
     * Sets the value of the postanskiBroj property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPostanskiBroj(BigInteger value) {
        this.postanskiBroj = value;
    }

    /**
     * Gets the value of the grad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrad() {
        return grad;
    }

    /**
     * Sets the value of the grad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrad(String value) {
        this.grad = value;
    }

    /**
     * Gets the value of the drzava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrzava() {
        return drzava;
    }

    /**
     * Sets the value of the drzava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrzava(String value) {
        this.drzava = value;
    }

}
