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
                <pred:Naziv>
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
                </pred:Naziv>
                <pred:Datum_podnosenja>
                    <xsl:value-of select="//patent:Datum_podnosenja"/>
                </pred:Datum_podnosenja>
            </rdf:Description>
        </rdf:RDF>
    </xsl:template>
</xsl:stylesheet>