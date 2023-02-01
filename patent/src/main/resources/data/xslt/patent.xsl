<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:patent="http://www.ftn.uns.ac.rs/jaxb/patent"
                xmlns:pred="http://www.ftn.uns.ac.rs/jaxb/patent/pred/"
>
    <xsl:template match="/">
        <rdf:RDF>
            <rdf:Description rdf:about="http://www.ftn.uns.ac.rs/jaxb/patent/{//patent:Broj_prijave}">
                <pred:Broj_prijave>
                    <xsl:value-of select="//patent:Broj_prijave"/>
                </pred:Broj_prijave>
                <pred:Podnosioc>
                    <xsl:choose>
                        <xsl:when test="//patent:Popunjava_podnosioc/patent:Podaci_o_podnosiocu/patent:Podnosioc/patent:Poslovno_ime">
                            <xsl:value-of select="//patent:Popunjava_podnosioc/patent:Podaci_o_podnosiocu/patent:Podnosioc/patent:Poslovno_ime"/>
                        </xsl:when>
                        <xsl:otherwise>
                                <xsl:value-of select="//patent:Popunjava_podnosioc/patent:Podaci_o_podnosiocu/patent:Podnosioc/patent:Ime"/>
                            <xsl:text> </xsl:text>
                            <xsl:value-of select="//patent:Popunjava_podnosioc/patent:Podaci_o_podnosiocu/patent:Podnosioc/patent:Prezime"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </pred:Podnosioc>
                <pred:Email>
                    <xsl:value-of select="//patent:Podnosioc//patent:Kontakt//patent:E_posta"/>
                </pred:Email>
                <pred:Naziv_na_srpskom>
                    <xsl:value-of select="//patent:Naziv_na_srpskom"/>
                </pred:Naziv_na_srpskom>
                <pred:Naziv_na_engleskom>
                    <xsl:value-of select="//patent:Naziv_na_engleskom"/>
                </pred:Naziv_na_engleskom>
                <pred:Datum_podnosenja>
                    <xsl:value-of select="substring(//patent:Datum_podnosenja, 1, 10)" />
                </pred:Datum_podnosenja>
                <pred:Datum_prijema>
                    <xsl:value-of select="substring(//patent:Datum_prijema, 1, 10)" />
                </pred:Datum_prijema>
            </rdf:Description>
        </rdf:RDF>
    </xsl:template>
</xsl:stylesheet>