<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:zig="http://www.ftn.uns.ac.rs/jaxb/zig"
                xmlns:pred="http://www.ftn.uns.ac.rs/jaxb/zig/pred/"
>
    <xsl:template match="/">
        <rdf:RDF>
            <rdf:Description rdf:about="http://www.ftn.uns.ac.rs/jaxb/zig/{//zig:Broj_prijave_ziga}">
                <pred:Broj_prijave>
                    <xsl:value-of select="//zig:Broj_prijave_ziga"/>
                </pred:Broj_prijave>
                <pred:Podnosilac>
                    <xsl:choose>
                        <xsl:when test="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Poslovno_ime">
                            <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Poslovno_ime"/>
                        </xsl:when>
                        <xsl:otherwise>
                                <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Ime"/> <xsl:text> </xsl:text> <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:prezime"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </pred:Podnosilac>
                <pred:Podnosilac_email>
                    <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Kontakt/zig:Email"/>
                </pred:Podnosilac_email>
                <pred:Datum_podnosenja>
                    <xsl:value-of select="//zig:Datum_podnosenja"/>
                </pred:Datum_podnosenja>
                <pred:Vrsta_a>
                    <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Zig/zig:Vrsta/zig:Tip_a"/>
                </pred:Vrsta_a>
                <pred:Vrsta_b>
                    <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Zig/zig:Vrsta/zig:Tip_b"/>
                </pred:Vrsta_b>
                <pred:Takse>
                    <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Placene_takse/zig:Ukupno"/>
                </pred:Takse>
            </rdf:Description>
        </rdf:RDF>
    </xsl:template>
</xsl:stylesheet>