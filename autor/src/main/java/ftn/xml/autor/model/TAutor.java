//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.13 at 06:10:09 PM CET 
//


package ftn.xml.autor.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TAutor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TAutor">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ftn.uns.ac.rs/jaxb/autor}TFizicko_lice">
 *       &lt;sequence>
 *         &lt;element name="Godina_smrti" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Znak_autora" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAutor", propOrder = {
    "godinaSmrti",
    "znakAutora"
})
public class TAutor
    extends TFizickoLice
{

    @XmlElement(name = "Godina_smrti", required = true)
    protected XMLGregorianCalendar godinaSmrti;
    @XmlElement(name = "Znak_autora", required = true)
    protected String znakAutora;

    /**
     * Gets the value of the godinaSmrti property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGodinaSmrti() {
        return godinaSmrti;
    }

    /**
     * Sets the value of the godinaSmrti property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGodinaSmrti(XMLGregorianCalendar value) {
        this.godinaSmrti = value;
    }

    /**
     * Gets the value of the znakAutora property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZnakAutora() {
        return znakAutora;
    }

    /**
     * Sets the value of the znakAutora property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZnakAutora(String value) {
        this.znakAutora = value;
    }

}