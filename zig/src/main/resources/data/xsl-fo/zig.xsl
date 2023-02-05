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
                            <xsl:value-of select="//zig:Informacije_o_ustanovi/zig:Naziv"/>
                        </fo:inline>,
                        <fo:inline>
                            <xsl:value-of select="//zig:Informacije_o_ustanovi/zig:Adresa/zig:Ulica"/>
                        </fo:inline>
                        <xsl:text> </xsl:text>
                        <fo:inline>
                            <xsl:value-of select="//zig:Informacije_o_ustanovi/zig:Adresa/zig:Broj"/>
                        </fo:inline>,
                        <fo:inline>
                            <xsl:value-of select="//zig:Informacije_o_ustanovi/zig:Adresa/zig:Postanski_broj"/>
                        </fo:inline>
                        <xsl:text> </xsl:text>
                        <fo:inline>
                            <xsl:value-of select="//zig:Informacije_o_ustanovi/zig:Adresa/zig:Grad"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block>
                        <fo:block text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                            Podnosilac prijave
                        </fo:block>
                        <fo:table font-family="serif" border="1px">
                            <fo:table-column column-width="20%"/>
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell border="1px" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>Ime</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px"  font-family="sans-serif"  padding="5px" font-weight="bold">
                                        <fo:block>Adresa</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px"  font-family="sans-serif"  padding="5px" font-weight="bold">
                                        <fo:block>Telefon</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px"  font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>Email</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px"  font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>Faks</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell border="1px" padding-left="5px" display-align="center" font-weight="bold">
                                            <xsl:choose>
                                                <xsl:when test="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Poslovno_ime">
                                                    <fo:block>
                                                        <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Poslovno_ime"/>
                                                    </fo:block>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <fo:block>
                                                        <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Ime"/> <xsl:text> </xsl:text> <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Prezime"/>
                                                    </fo:block>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px" padding-right="8px" padding-bottom="4px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <fo:inline>
                                                    <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Adresa/zig:Ulica"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Adresa/zig:Broj"/>
                                                </fo:inline>,
                                                <fo:inline>
                                                    <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Adresa/zig:Postanski_broj"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Adresa/zig:Grad"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Kontakt/zig:Telefon"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Kontakt/zig:Email"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:Kontakt/zig:faks"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block >
                        <fo:block text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                            Punomocnik
                        </fo:block>
                        <fo:table font-family="serif" border="1px" >
                            <fo:table-column column-width="20%"/>
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell border="1px" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>Ime</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px"  font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>Adresa</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px"  font-family="sans-serif"  padding="5px" font-weight="bold">
                                        <fo:block>Telefon</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px"  font-family="sans-serif"  padding="5px" font-weight="bold">
                                        <fo:block>Email</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px"  font-family="sans-serif"  padding="5px" font-weight="bold">
                                        <fo:block>Faks</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="//zig:Popunjava_podnosilac/zig:Punomocnik">
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell border="1px" padding-left="5px" display-align="center" font-weight="bold">
                                            <xsl:choose>
                                                <xsl:when test="zig:Poslovno_ime">
                                                    <fo:block>
                                                        <xsl:value-of select="zig:Poslovno_ime"/>
                                                    </fo:block>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <fo:block>
                                                        <xsl:value-of select="zig:Ime"/> <xsl:text> </xsl:text> <xsl:value-of select="zig:Prezime"/>
                                                    </fo:block>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px" padding-right="8px" padding-bottom="4px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <fo:inline>
                                                    <xsl:value-of select="zig:Adresa/zig:Ulica"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="zig:Adresa/zig:Broj"/>
                                                </fo:inline>,
                                                <fo:inline>
                                                    <xsl:value-of select="zig:Adresa/zig:Postanski_broj"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="zig:Adresa/zig:Grad"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="zig:Kontakt/zig:Telefon"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="zig:Kontakt/zig:Email"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of select="zig:Faks/zig:faks"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block  text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                        Informacije o zigu
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                        <fo:table-column column-width="50%"/>
                        <fo:table-column column-width="50%"/>
                        <fo:table-body>
                        <fo:table-row>
                            <fo:table-cell border="1px" border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                <fo:block>
                                   Vrsta
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row >
                            <fo:table-cell border="1px" font-family="sans-serif" padding="5px" font-weight="bold">
                                <fo:block>Tip A</fo:block>
                            </fo:table-cell>
                            <fo:table-cell border="1px" font-family="sans-serif" padding="5px" font-weight="bold">
                                <fo:block>Tip B</fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <fo:table-cell border="1px" font-family="sans-serif" padding="5px" font-weight="normal">
                                <fo:block>
                                    <xsl:value-of select="//zig:Zig/zig:Vrsta/zig:Tip_a"/>
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell border="1px" font-family="sans-serif" padding="5px" font-weight="normal">
                                <fo:block>
                                    <xsl:value-of select="//zig:Zig/zig:Vrsta/zig:Tip_b"/>
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <fo:table-cell border="1px" border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                <fo:block>
                                    Naznacenje boje, odnosno boja iz kojih se znak sastoji
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <fo:table-cell border="1px" font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                <fo:block>
                                    <xsl:value-of select="//zig:Zig/zig:Naznacenje_boje"/>
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                            <xsl:if test="//zig:Zig/zig:Transliteracija_znak">
                                <fo:table-row>
                                    <fo:table-cell border="1px" border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                        <fo:block>
                                            Transliteracija znaka
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px" font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                        <fo:block>
                                            <xsl:value-of select="//zig:Zig/zig:Transliteracija_znak"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:if>
                            <xsl:if test="//zig:Zig/zig:Prevod_znaka">
                                <fo:table-row>
                                    <fo:table-cell border="1px" border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                        <fo:block>
                                            Prevod znaka
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px" font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                        <fo:block>
                                            <xsl:value-of select="//zig:Zig/zig:Prevod_znaka"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </xsl:if>
                            <fo:table-row>
                                <fo:table-cell border="1px" border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                    <fo:block>
                                        Opis znaka
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell border="1px" font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                    <fo:block>
                                        <xsl:value-of select="//zig:Zig/zig:Opis_znaka"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell border="1px" border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                    <fo:block>
                                        Izgled znaka
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell border="1px" font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                    <fo:block>
                                        <xsl:element name="img">
                                            <xsl:attribute name="src">
                                                data\<xsl:value-of select="//zig:Primerak_znaka"/>
                                            </xsl:attribute>
                                        </xsl:element>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell border="1px" border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                    <fo:block>
                                        Brojevi klase roba i usluga prema Nicanskoj klasifikaciji
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell border="1px" font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                    <fo:block>
                                        <xsl:for-each select="//zig:Dodatne_informacije/zig:Klasa_robe_i_uslaga/zig:Klasa">
                                            <fo:inline>
                                                <xsl:value-of select="."/>
                                                <xsl:text>  </xsl:text>
                                            </fo:inline>
                                        </xsl:for-each>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell border="1px" border-top="1px solid darkgrey" padding-top="10px" font-family="sans-serif" font-weight="bold" number-columns-spanned="2">
                                    <fo:block>
                                        Zatrazeno pravo prvenstva i osnov
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell border="1px" font-family="sans-serif" padding="10px" font-weight="normal" number-columns-spanned="2">
                                    <fo:block>
                                        <xsl:value-of select="//zig:Zatrazeno_pravo_prvensta_i_osnov"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                        Informacije o placanju
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                            <fo:table-column column-width="65%"/>
                            <fo:table-column column-width="35%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell  border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            Placene takse
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell  border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            Dinara
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid darkgrey" font-family="sans-serif"  padding="5px" font-weight="bold">
                                        <fo:block>
                                            Osnovna taksa:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell  border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            <xsl:value-of select="//zig:Osnovna_taksa"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid darkgrey"  font-family="sans-serif"  padding="5px" font-weight="bold">
                                        <fo:block>
                                            Za
                                            <fo:inline>
                                                <xsl:value-of select="//zig:Za_klasa/zig:Zaziv_klase"/>
                                            </fo:inline>
                                            klasa:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell  border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            <xsl:value-of select="//zig:Za_klasa/zig:Suma"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell  border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            Za graficko resenje:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell  border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            <xsl:value-of select="//zig:Za_graficko_resenje"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell  border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            UKUPNO:
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell  border="1px solid darkgrey" font-family="sans-serif" padding="5px" font-weight="bold" font-size="15px">
                                        <fo:block>
                                            <xsl:value-of select="//zig:Ukupno"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px" text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                        <fo:table-column column-width="30%"/>
                        <fo:table-column column-width="30%"/>
                            <fo:table-column column-width="40%"/>
                        <fo:table-body>
                        <fo:table-row>
                            <fo:table-cell border="1px" font-family="sans-serif"  padding="5px" font-weight="bold">
                                <fo:block>
                                    Broj prijave ziga
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell border="1px" font-family="sans-serif" padding="5px" font-weight="bold">
                                <fo:block>
                                    Datum podnosenja
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <fo:table-cell border="1px" font-family="sans-serif">
                                <fo:block>
                                    <xsl:value-of select="//zig:Broj_prijave_ziga"/>
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell border="1px" font-family="sans-serif">
                                <fo:block>
                                    <xsl:value-of select="//zig:Datum_podnosenja"/>
                                </fo:block>
                            </fo:table-cell>
                            <fo:table-cell border="1px" font-family="sans-serif">
                                <fo:block>
                                    <xsl:element name="img">
                                        <xsl:attribute name="src">
                                            data\files\<xsl:value-of select="//zig:QR_kod"/>
                                        </xsl:attribute>
                                    </xsl:element>
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
