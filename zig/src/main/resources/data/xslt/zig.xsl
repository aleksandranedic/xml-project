<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:zig="http://www.ftn.uns.ac.rs/jaxb/zig"
                xmlns:pred="http://www.ftn.uns.ac.rs/jaxb/zig/pred/"
>
    <xsl:template match="/">
        <rdf:RDF>
            <rdf:Description rdf:about="http://www.ftn.uns.ac.rs/jaxb/zig/{//zig:broj_prijave_ziga}">
                <pred:Broj_prijave>
                    <xsl:value-of select="//zig:broj_prijave_ziga"/>
                </pred:Broj_prijave>
                <pred:Naziv>
                    <xsl:choose>
                        <xsl:when test="//zig:popunjava_podnosilac/zig:podnosilac/zig:poslovno_ime">
                            <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:poslovno_ime"/>
                        </xsl:when>
                        <xsl:otherwise>
                                <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:ime"/> <xsl:text> </xsl:text> <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:prezime"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </pred:Naziv>
                <pred:Datum_podnosenja>
                    <xsl:value-of select="//zig:datum_podnosenja"></xsl:value-of>
                </pred:Datum_podnosenja>
            </rdf:Description>
        </rdf:RDF>
    </xsl:template>
</xsl:stylesheet>