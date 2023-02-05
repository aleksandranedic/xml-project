<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:aut="http://www.ftn.uns.ac.rs/jaxb/autor"
                xmlns:pred="http://www.ftn.uns.ac.rs/jaxb/autor/pred/"
>
    <xsl:template match="/">
        <rdf:RDF>
            <rdf:Description rdf:about="http://www.ftn.uns.ac.rs/jaxb/autor/{//aut:Broj_prijave}">
                <pred:Broj_prijave>
                    <xsl:value-of select="//aut:Broj_prijave"/>
                </pred:Broj_prijave>
                <pred:Naslov>
                    <xsl:value-of select="//aut:Naslov"/>
                </pred:Naslov>
                <pred:Vrsta>
                    <xsl:value-of select="//aut:Vrsta"/>
                </pred:Vrsta>
                <pred:Forma>
                    <xsl:value-of select="//aut:Forma"/>
                </pred:Forma>
                <pred:Naziv>
                    <xsl:choose>
                        <xsl:when test="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Poslovno_ime">
                            <xsl:value-of select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Poslovno_ime"/>
                        </xsl:when>
                        <xsl:otherwise>
                                <xsl:value-of select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Ime"/> <xsl:text> </xsl:text> <xsl:value-of select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Prezime"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </pred:Naziv>
                <pred:Datum_podnosenja>
                    <xsl:value-of select="//aut:Datum_podnosenja"></xsl:value-of>
                </pred:Datum_podnosenja>
            </rdf:Description>
        </rdf:RDF>
    </xsl:template>
</xsl:stylesheet>