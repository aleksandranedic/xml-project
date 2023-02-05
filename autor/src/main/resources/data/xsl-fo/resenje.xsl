<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:aut="http://www.ftn.uns.ac.rs/jaxb/autor"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zig-page">
                    <fo:region-body margin="0.40in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="resenje-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="center" font-family="sans-serif" font-size="24px" font-weight="bold">
                        Rešenje
                    </fo:block>

                    <fo:block text-align="center" margin-top="8px">
                        Šifra rešenja
                        <fo:inline>
                            <xsl:value-of select="//aut:Resenje/aut:Broj_prijave"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block text-align="center" margin-top="8px">
                        Šifra rešenja
                        <fo:inline>
                            <xsl:value-of select="//aut:Resenje/aut:Broj_prijave"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block text-align="center" margin-top="8px">
                        Status
                        <fo:inline>
                            <xsl:value-of select="//aut:Resenje/aut:Status"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block text-align="center" margin-top="8px">
                        Ime službenika
                        <fo:inline>
                            <xsl:value-of select="//aut:Resenje/aut:Ime"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block text-align="center" margin-top="8px">
                        Prezime službenika
                        <fo:inline>
                            <xsl:value-of select="//aut:Resenje/aut:Prezime"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block text-align="center" margin-top="8px">
                        Datum
                        <fo:inline>
                            <xsl:value-of select="//aut:Resenje/aut:Datum"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block text-align="center" margin-top="8px">
                        Obrazloženje
                        <fo:inline>
                            <xsl:value-of select="//aut:Resenje/aut:Obrazlozenje"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block text-align="center" margin-top="8px">
                        Putanja do PDF-a zahteva
                        <fo:inline>
                            http://localhost:8000/<xsl:value-of select="//aut:Broj_prijave"/>.pdf
                        </fo:inline>
                    </fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>