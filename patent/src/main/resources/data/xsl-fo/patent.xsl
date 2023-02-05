<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:patent="http://www.ftn.uns.ac.rs/jaxb/patent"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="patent-page">
                    <fo:region-body margin="0.40in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="patent-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="center" font-family="serif" font-size="24px">
                        Zahtev za priznanje patenta
                    </fo:block>
                    <fo:block text-align="center" margin-top="8px" font-family="serif">
                        <fo:inline>
                            <xsl:value-of select="//patent:Informacije_o_ustanovi/patent:Naziv"/>,
                        </fo:inline>
                        <fo:inline>
                            <xsl:value-of select="//patent:Informacije_o_ustanovi/patent:Adresa/patent:Ulica"/>
                        </fo:inline >
                        <xsl:text> </xsl:text>
                        <fo:inline>
                            <xsl:value-of select="//patent:Informacije_o_ustanovi/patent:Adresa/patent:Broj"/>,
                        </fo:inline >
                        <fo:inline>
                            <xsl:value-of select="//patent:Informacije_o_ustanovi/patent:Adresa/patent:Postanski_broj"/>
                        </fo:inline >
                        <xsl:text> </xsl:text>
                        <fo:inline>
                            <xsl:value-of select="//patent:Informacije_o_ustanovi/patent:Adresa/patent:Grad"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block margin-top="30px">
                        <fo:table font-family="serif" border="1px" margin-top="10px">
                            <fo:table-column column-width="30%"/>
                            <fo:table-column column-width="35%"/>
                            <fo:table-column column-width="35%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Broj prijave</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Datum prijema</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Priznati datum podnošenja</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <xsl:for-each select="//patent:Popunjava_zavod">
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell border="1px solid darkgrey" padding-left="5px" display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Broj_prijave"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Datum_prijema"/>
                                            </fo:block>
                                        </fo:table-cell>

                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px">
                        <fo:block text-align="center" font-family="serif" font-size="15px">
                            Naziv patenta
                        </fo:block>
                        <fo:table font-family="serif" border="1px" margin-top="10px">
                            <fo:table-column column-width="50%"/>
                            <fo:table-column column-width="50%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Na srpskom jeziku</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Na engleskom jeziku</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <xsl:for-each select="//patent:Naziv_patenta">
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell border="1px solid darkgrey" padding-left="5px" display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Naziv_na_srpskom"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Naziv_na_engleskom"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>


                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px">
                        <fo:block text-align="center" font-family="serif" font-size="15px">
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
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Ime</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Adresa</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Telefon</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Email</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Faks</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each
                                        select="//patent:Popunjava_podnosioc/patent:Podaci_o_podnosiocu/patent:Podnosioc">
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell border="1px solid darkgrey" padding-left="5px" display-align="center">
                                            <xsl:choose>
                                                <xsl:when test="patent:Poslovno_ime">
                                                    <fo:block>
                                                        <xsl:value-of select="patent:Poslovno_ime"/>
                                                    </fo:block>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <fo:block>
                                                        <xsl:value-of select="patent:Ime"/>
                                                        <xsl:text> </xsl:text>
                                                        <xsl:value-of select="patent:Prezime"/>
                                                    </fo:block>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px" display-align="center">
                                            <fo:block>
                                                <fo:inline>
                                                    <xsl:value-of select="patent:Adresa/patent:Ulica"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="patent:Adresa/patent:Broj"/>
                                                </fo:inline>
                                                <fo:inline>
                                                    <xsl:value-of select="patent:Adresa/patent:Postanski_broj"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="patent:Adresa/patent:Grad"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px solid darkgrey" display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Kontakt/patent:Telefon"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px solid darkgrey" display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Kontakt/patent:E_posta"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell border="1px solid darkgrey" display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Kontakt/patent:Faks"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px">
                        <fo:block text-align="center" font-family="serif" font-size="15px">
                            Pronalazac
                        </fo:block>
                        <xsl:for-each select="//patent:Popunjava_podnosioc/patent:Podaci_o_pronalazacu">
                            <xsl:choose>
                                <xsl:when test="patent:Pronalazac_ne_zeli_da_bude_naveden">
                                    <fo:block font-family="serif">
                                        Pronalazac ne želi da bude naveden
                                    </fo:block>
                                </xsl:when>
                                <xsl:otherwise>
                                    <fo:table font-family="serif" border="1px" margin-top="10px">
                                        <fo:table-column column-width="20%"/>
                                        <fo:table-column column-width="25%"/>
                                        <fo:table-column column-width="15%"/>
                                        <fo:table-column column-width="25%"/>
                                        <fo:table-column column-width="15%"/>
                                        <fo:table-body>
                                            <fo:table-row border="1px solid darkgrey">
                                                <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                               padding="5px">
                                                    <fo:block>Ime</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                               padding="5px">
                                                    <fo:block>Adresa</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                               padding="5px">
                                                    <fo:block>Telefon</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                               padding="5px">
                                                    <fo:block>Email</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                               padding="5px">
                                                    <fo:block>Faks</fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <xsl:for-each
                                                    select="//patent:Popunjava_podnosioc/patent:Podaci_o_pronalazacu/patent:Pronalazac">
                                                <fo:table-row border="1px solid darkgrey">
                                                    <fo:table-cell border="1px solid darkgrey" padding-left="5px" display-align="center"
                                                    >
                                                        <xsl:choose>
                                                            <xsl:when test="patent:Poslovno_ime">
                                                                <fo:block>
                                                                    <xsl:value-of select="patent:Poslovno_ime"/>
                                                                </fo:block>
                                                            </xsl:when>
                                                            <xsl:otherwise>
                                                                <fo:block>
                                                                    <xsl:value-of select="patent:Ime"/>
                                                                    <xsl:text> </xsl:text>
                                                                    <xsl:value-of select="patent:Prezime"/>
                                                                </fo:block>
                                                            </xsl:otherwise>
                                                        </xsl:choose>
                                                    </fo:table-cell>
                                                    <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px"
                                                                   display-align="center">
                                                        <fo:block>
                                                            <fo:inline>
                                                                <xsl:value-of select="patent:Adresa/patent:Ulica"/>
                                                            </fo:inline>
                                                            <xsl:text> </xsl:text>
                                                            <fo:inline>
                                                                <xsl:value-of select="patent:Adresa/patent:Broj"/>
                                                            </fo:inline>
                                                            <fo:inline>
                                                                <xsl:value-of
                                                                        select="patent:Adresa/patent:Postanski_broj"/>
                                                            </fo:inline>
                                                            <xsl:text> </xsl:text>
                                                            <fo:inline>
                                                                <xsl:value-of select="patent:Adresa/patent:Grad"/>
                                                            </fo:inline>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border="1px solid darkgrey" display-align="center">
                                                        <fo:block>
                                                            <xsl:value-of select="patent:Kontakt/patent:Telefon"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border="1px solid darkgrey" display-align="center">
                                                        <fo:block>
                                                            <xsl:value-of select="patent:Kontakt/patent:E_posta"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border="1px solid darkgrey" display-align="center">
                                                        <fo:block>
                                                            <xsl:value-of select="patent:Kontakt/patent:Faks"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </fo:table-body>
                                    </fo:table>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:for-each>
                    </fo:block>

                    <fo:block margin-top="30px">
                        <fo:block text-align="center" font-family="serif" font-size="15px">
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
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Ime</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Adresa</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Telefon</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Email</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Faks</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each
                                        select="//patent:Popunjava_podnosioc/patent:Podaci_o_punomocniku/patent:Punomocnik">
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell border="1px solid darkgrey" padding-left="5px" display-align="center">
                                            <xsl:choose>
                                                <xsl:when test="patent:Poslovno_ime">
                                                    <fo:block>
                                                        <xsl:value-of select="patent:Poslovno_ime"/>
                                                    </fo:block>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <fo:block>
                                                        <xsl:value-of select="patent:Ime"/>
                                                        <xsl:text> </xsl:text>
                                                        <xsl:value-of select="patent:Prezime"/>
                                                    </fo:block>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px"
                                                       display-align="center">
                                            <fo:block>
                                                <fo:inline>
                                                    <xsl:value-of select="patent:Adresa/patent:Ulica"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="patent:Adresa/patent:Broj"/>
                                                </fo:inline>
                                                ,
                                                <fo:inline>
                                                    <xsl:value-of
                                                            select="patent:Adresa/patent:Postanski_broj"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="patent:Adresa/patent:Grad"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Kontakt/patent:Telefon"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Kontakt/patent:E_posta"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Kontakt/patent:Faks"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>

                        <fo:block>
                            <xsl:for-each select="//patent:Popunjava_podnosioc/patent:Podaci_o_punomocniku">
                                <xsl:choose>
                                    <xsl:when test="patent:Za_zastupanje">
                                        <fo:block>
                                            Punomocnik za zastupanje
                                        </fo:block>
                                    </xsl:when>
                                    <xsl:when test="patent:Za_prijem">
                                        <fo:block>
                                            Punomocnik za prijem podataka
                                        </fo:block>
                                    </xsl:when>
                                    <xsl:when test="patent:Predstavnik">
                                        <fo:block>
                                            Predstavnik
                                            //patent:Popunjava_podnosioc/patent:Podaci_o_podnosiocu/patent:Podnosioc/patent:Ime              </fo:block>
                                    </xsl:when>
                                </xsl:choose>
                            </xsl:for-each>
                        </fo:block>
                    </fo:block>

                    <fo:block margin-top="30px" text-align="center" font-family="serif" font-size="15px"
                    >
                        Adresa i nacin dostavljanja
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                            <fo:table-column column-width="50%"/>
                            <fo:table-column column-width="50%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Adresa</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Nacin dostavljanje</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="//patent:Popunjava_podnosioc/patent:Dostavljanje">
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px"
                                                       display-align="center">
                                            <fo:block>
                                                <fo:inline>
                                                    <xsl:value-of select="patent:Adresa/patent:Ulica"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="patent:Adresa/patent:Broj"/>
                                                </fo:inline>
                                                <fo:inline>
                                                    <xsl:value-of
                                                            select="patent:Adresa/patent:Postanski_broj"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of select="patent:Adresa/patent:Grad"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px"
                                                       display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Nacin"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px" text-align="center" font-family="serif" font-size="15px"
                    >
                        Prvobitna/Osnovna prijava
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                            <fo:table-column column-width="35%"/>
                            <fo:table-column column-width="35%"/>
                            <fo:table-column column-width="30%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Broj prvobitne prijave/osnovne prijave</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Datum podnošenja prvobitne prijave/osnovne prijave</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Tip prijave</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <xsl:for-each select="//patent:Prvobitna_prijava">
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px"
                                                       display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Broj_prijave"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px"
                                                       display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Datum_podnosenja"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px"
                                                       display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Tip_prijave"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px" text-align="center" font-family="serif" font-size="15px"
                    >
                        Zahtev za priznanje prvenstva iz ranijih prijava
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="50%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Broj prijave</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Datum podnošenja</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border="1px solid darkgrey" font-family="serif"
                                                   padding="5px">
                                        <fo:block>Dvoslovna oznaka države, regionalne ili medjunarodne organizacije</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <xsl:for-each select="//patent:Prijava">
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px"
                                                       display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Broj_prijave"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px"
                                                       display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Datum_podnosenja"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border="1px solid darkgrey" padding-right="8px" padding-bottom="4px"
                                                       display-align="center">
                                            <fo:block>
                                                <xsl:value-of select="patent:Dvoslovna_oznaka"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
