<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:zig="http://www.ftn.uns.ac.rs/jaxb/zig"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="zig-page">
                    <fo:region-body margin="0.40in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="zig-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="center" font-family="sans-serif" font-size="24px" font-weight="bold">
                        Zahtev za priznanje žiga
                    </fo:block>
                    <fo:block text-align="center" margin-top="8px">
                        <fo:inline>
                            <xsl:value-of select="//zig:informacije_o_ustanovi/zig:naziv"/>
                        </fo:inline>,
                        <fo:inline>
                            <xsl:value-of select="//zig:informacije_o_ustanovi/zig:adresa/zig:ulica"/>
                        </fo:inline>
                        <xsl:text> </xsl:text>
                        <fo:inline>
                            <xsl:value-of select="//zig:informacije_o_ustanovi/zig:adresa/zig:broj"/>
                        </fo:inline>,
                        <fo:inline>
                            <xsl:value-of select="//zig:informacije_o_ustanovi/zig:adresa/zig:postanski_broj"/>
                        </fo:inline>
                        <xsl:text> </xsl:text>
                        <fo:inline>
                            <xsl:value-of select="//zig:informacije_o_ustanovi/zig:adresa/zig:grad"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block margin-top="30px">
                        <fo:block text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                            Podnosilac prijave
                        </fo:block>
                        <fo:table font-family="serif" border="1px" margin-top="10px">
                            <fo:table-column column-width="20%"/>
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell background-color="#4caf50" color="white" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>Ime</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>Adresa</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>Telefon</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>Email</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>Faks</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell padding-left="5px" display-align="center" font-weight="bold">
                                            <xsl:choose>
                                                <xsl:when test="//zig:popunjava_podnosilac/zig:podnosilac/zig:poslovno_ime">
                                                    <fo:block>
                                                        <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:poslovno_ime"/>
                                                    </fo:block>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <fo:block>
                                                        <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:ime"/> <xsl:text> </xsl:text> <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:prezime"/>
                                                    </fo:block>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </fo:table-cell>

                                        <fo:table-cell padding-right="8px" padding-bottom="4px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <fo:inline>
                                                    <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:adresa/zig:ulica"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:adresa/zig:broj"/>
                                                </fo:inline>,
                                                <fo:inline>
                                                    <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:adresa/zig:postanski_broj"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:adresa/zig:grad"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:telefon"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:email"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:faks"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px">
                        <fo:block text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                            Punomocnik
                        </fo:block>
                        <fo:table font-family="serif" border="1px" margin-top="10px">
                            <fo:table-column column-width="20%"/>
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>Ime</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>Adresa</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>Telefon</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>Email</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>Faks</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="//zig:popunjava_podnosilac/zig:punomocnik">
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell padding-left="5px" display-align="center" font-weight="bold">
                                            <xsl:choose>
                                                <xsl:when test="zig:poslovno_ime">
                                                    <fo:block>
                                                        <xsl:value-of select="zig:poslovno_ime"/>
                                                    </fo:block>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <fo:block>
                                                        <xsl:value-of select="zig:ime"/> <xsl:text> </xsl:text> <xsl:value-of select="zig:prezime"/>
                                                    </fo:block>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </fo:table-cell>

                                        <fo:table-cell padding-right="8px" padding-bottom="4px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <fo:inline>
                                                    <xsl:value-of select="zig:adresa/zig:ulica"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="zig:adresa/zig:broj"/>
                                                </fo:inline>,
                                                <fo:inline>
                                                    <xsl:value-of select="zig:adresa/zig:postanski_broj"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="zig:adresa/zig:grad"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="zig:telefon"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="zig:email"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="zig:faks"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px" text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                        Informacije o zigu
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="50%"/>
                        <fo:table-body>
                        <fo:table-row>
                            <fo:table-cell border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                <fo:block>
                                   Vrsta
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row >
                            <fo:table-cell font-family="sans-serif" padding="5px" font-weight="bold">
                                <fo:block>Tip A</fo:block>
                            </fo:table-cell>
                            <fo:table-cell font-family="sans-serif" padding="5px" font-weight="bold">
                                <fo:block>Tip B</fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <fo:table-cell font-family="sans-serif" padding="5px" font-weight="normal">
                                <fo:block>
                                    <xsl:variable name="name_a" select="name(//zig:zig/zig:vrsta/zig:tip_a/*[1])"/>
                                    <xsl:value-of select="concat(translate($name_a, '_', ' '), ' ')"/>
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell font-family="sans-serif" padding="5px" font-weight="normal">
                                <fo:block>
                                    <xsl:variable name="name_b" select="name(//zig:zig/zig:vrsta/zig:tip_b/*[1])"/>
                                    <xsl:value-of select="concat(translate($name_b, '_', ' '), ' ')"/>
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <fo:table-cell border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                <fo:block>
                                    Naznacenje boje, odnosno boja iz kojih se znak sastoji
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                <fo:block>
                                    <xsl:value-of select="//zig:zig/zig:naznacenje_boje"/>
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                            <xsl:if test="//zig:zig/zig:transliteracija_znak">
                                <fo:table-row>
                                    <fo:table-cell border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                        <fo:block>
                                            Transliteracija znaka
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                        <fo:block>
                                            <xsl:value-of select="//zig:zig/zig:transliteracija_znak"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:if>
                            <xsl:if test="//zig:zig/zig:prevod_znaka">
                                <fo:table-row>
                                    <fo:table-cell border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                        <fo:block>
                                            Prevod znaka
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                        <fo:block>
                                            <xsl:value-of select="//zig:zig/zig:prevod_znaka"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:if>
                            <fo:table-row>
                                <fo:table-cell border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                    <fo:block>
                                        Opis znaka
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                    <fo:block>
                                        <xsl:value-of select="//zig:zig/zig:opis_znaka"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                    <fo:block>
                                        Brojevi klase roba i usluga prema Nicanskoj klasifikaciji
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                    <fo:block>
                                        <xsl:for-each select="//zig:dodatne_informacije/zig:klasa_robe_i_uslaga/zig:klasa">
                                            <fo:inline>
                                                <xsl:value-of select="."/>
                                                <xsl:text>  </xsl:text>
                                            </fo:inline>
                                        </xsl:for-each>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                    <fo:block>
                                        Zatrazeno pravo prvenstva i osnov
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                    <fo:block>
                                        <xsl:value-of select="//zig:zatrazeno_pravo_prvensta_i_osnov"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px" text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                        Informacije o placanju
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                            <fo:table-column column-width="65%"/>
                            <fo:table-column column-width="35%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid darkgrey" background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>
                                            Placene takse
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" background-color="#4caf50" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>
                                            Dinara
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid darkgrey" background-color="#80C883" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>
                                            Osnovna taksa:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            <xsl:value-of select="//zig:osnovna_taksa"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid darkgrey" background-color="#80C883" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>
                                            Za
                                            <fo:inline>
                                                <xsl:value-of select="//zig:za_klasa/zig:naziv_klase"/>
                                            </fo:inline>
                                            klasa:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            <xsl:value-of select="//zig:za_klasa/zig:suma"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid darkgrey" background-color="#80C883" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>
                                            Za graficko resenje:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            <xsl:value-of select="//zig:za_graficko_resenje"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid darkgrey" background-color="#80C883" font-family="sans-serif" color="white" padding="5px" font-weight="bold">
                                        <fo:block>
                                            UKUPNO:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold" font-size="15px">
                                        <fo:block>
                                            <xsl:value-of select="//zig:ukupno"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px" text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                        Popunjava zavod
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                        <fo:table-column column-width="100%"/>
                        <fo:table-body>
                        <fo:table-row>
                            <fo:table-cell border="1px solid darkgrey" color="white" background-color="#4caf50" padding-top="10px" font-family="sans-serif" font-weight="bold">
                                <fo:block>
                                    Prilozi uz zahtev
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <xsl:for-each select="//zig:popunjava_zavod/zig:prilozi_uz_zahtev/*">
                            <fo:table-row>
                                <fo:table-cell border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="normal">
                                    <fo:block>
                                        <xsl:variable name="name" select="name(.)"/>
                                        <xsl:value-of select="concat(translate($name, '_', ' '), ' ')"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </xsl:for-each>
                        </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="80px" text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="50%"/>
                        <fo:table-body>
                        <fo:table-row>
                            <fo:table-cell font-family="sans-serif"  padding="5px" font-weight="bold">
                                <fo:block>
                                    Broj prijave ziga
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell font-family="sans-serif" padding="5px" font-weight="bold">
                                <fo:block>
                                    Datum podnosenja
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <fo:table-cell font-family="sans-serif">
                                <fo:block>
                                    <xsl:value-of select="//zig:broj_prijave_ziga"/>
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell font-family="sans-serif">
                                <fo:block>
                                    <xsl:value-of select="//zig:datum_podnosenja"/>
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
