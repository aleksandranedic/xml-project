<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
        <!ELEMENT xsl:stylesheet (xsl:template)*>
        <!ATTLIST xsl:stylesheet
                xmlns:xsl CDATA #REQUIRED
                xmlns:aut CDATA #REQUIRED
                xmlns:fo CDATA #REQUIRED
                version CDATA #REQUIRED>
        <!ELEMENT xsl:template (fo:root)*>
        <!ATTLIST xsl:template
                match CDATA #REQUIRED>
        <!ELEMENT fo:root (fo:layout-master-set|fo:page-sequence)*>
        <!ELEMENT fo:layout-master-set (fo:simple-page-master)*>
        <!ELEMENT fo:simple-page-master (fo:region-body)*>
        <!ATTLIST fo:simple-page-master
                master-name CDATA #REQUIRED>
        <!ELEMENT fo:region-body (#PCDATA)>
        <!ATTLIST fo:region-body
                margin CDATA #REQUIRED>
        <!ELEMENT fo:page-sequence (fo:flow)*>
        <!ATTLIST fo:page-sequence
                master-reference CDATA #REQUIRED>
        <!ELEMENT fo:flow (fo:block|xsl:if)*>
        <!ATTLIST fo:flow
                flow-name CDATA #REQUIRED>
        <!ELEMENT fo:block (fo:inline|xsl:text|fo:block|fo:table|xsl:value-of|xsl:variable|xsl:for-each)*>
        <!ATTLIST fo:block
                font-family CDATA #IMPLIED
                font-size CDATA #IMPLIED
                font-weight CDATA #IMPLIED
                margin-top CDATA #IMPLIED
                text-align CDATA #IMPLIED>
        <!ELEMENT fo:inline (xsl:value-of|xsl:text)*>
        <!ELEMENT xsl:value-of (#PCDATA)>
        <!ATTLIST xsl:value-of
                select CDATA #REQUIRED>
        <!ELEMENT xsl:text (#PCDATA)>
        <!ELEMENT fo:table (fo:table-column|fo:table-body)*>
        <!ATTLIST fo:table
                border CDATA #REQUIRED
                font-family CDATA #REQUIRED
                font-size CDATA #IMPLIED
                margin-top CDATA #REQUIRED>
        <!ELEMENT fo:table-column (#PCDATA)>
        <!ATTLIST fo:table-column
                column-width CDATA #REQUIRED>
        <!ELEMENT fo:table-body (fo:table-row|xsl:if|xsl:for-each)*>
        <!ELEMENT fo:table-row (fo:table-cell)*>
        <!ATTLIST fo:table-row
                border CDATA #IMPLIED>
        <!ELEMENT fo:table-cell (fo:block|xsl:choose)*>
        <!ATTLIST fo:table-cell
                background-color CDATA #IMPLIED
                border CDATA #IMPLIED
                border-top CDATA #IMPLIED
                color CDATA #IMPLIED
                display-align CDATA #IMPLIED
                font-family CDATA #IMPLIED
                font-size CDATA #IMPLIED
                font-weight CDATA #IMPLIED
                number-columns-spanned CDATA #IMPLIED
                padding CDATA #IMPLIED
                padding-bottom CDATA #IMPLIED
                padding-left CDATA #IMPLIED
                padding-right CDATA #IMPLIED
                padding-top CDATA #IMPLIED>
        <!ELEMENT xsl:choose (xsl:when|xsl:otherwise)*>
        <!ELEMENT xsl:when (fo:block)*>
        <!ATTLIST xsl:when
                test CDATA #REQUIRED>
        <!ELEMENT xsl:otherwise (fo:block)*>
        <!ELEMENT xsl:if (fo:block|fo:table-row)*>
        <!ATTLIST xsl:if
                test CDATA #REQUIRED>
        <!ELEMENT xsl:variable (#PCDATA)>
        <!ATTLIST xsl:variable
                name CDATA #REQUIRED
                select CDATA #REQUIRED>
        <!ELEMENT xsl:for-each (fo:inline|fo:table-row)*>
        <!ATTLIST xsl:for-each
                select CDATA #REQUIRED>
        ]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:aut="http://www.ftn.uns.ac.rs/jaxb/autor"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="aut-page">
                    <fo:region-body margin="0.40in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="aut-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="center" font-family="sans-serif" font-size="24px" font-weight="bold">
                        Zahtev za unošenje u evidenciju i depovanovanje autorskih dela
                    </fo:block>
                    <fo:block text-align="center" margin-top="8px">
                        <fo:inline>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Naziv"/>
                        </fo:inline>
                        ,
                        <fo:inline>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Adresa/aut:Ulica"/>
                        </fo:inline>
                        <xsl:text> </xsl:text>
                        <fo:inline>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Adresa/aut:Broj"/>
                        </fo:inline>
                        ,
                        <fo:inline>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Adresa/aut:Postanski_broj"/>
                        </fo:inline>
                        <xsl:text> </xsl:text>
                        <fo:inline>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Adresa/aut:Grad"/>
                        </fo:inline>
                    </fo:block>

                    <fo:block margin-top="30px">
                        <fo:block text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                            Podnosilac prijave
                        </fo:block>
                        <fo:table font-family="serif" border="1px" margin-top="10px">
                            <fo:table-column column-width="15%"/>
                            <fo:table-column column-width="20%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-column column-width="20%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell background-color="#3e87bc" color="white" font-family="sans-serif"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Ime</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Adresa</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Telefon</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Email</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Faks</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Sediste</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell padding-left="5px" display-align="center" font-weight="bold">
                                        <xsl:choose>
                                            <xsl:when test="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Poslovno_ime">
                                                <fo:block>
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Poslovno_ime"/>
                                                </fo:block>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <fo:block>
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Ime"/>
                                                    <xsl:text> </xsl:text>
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Prezime"/>
                                                </fo:block>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </fo:table-cell>

                                    <fo:table-cell padding-right="8px" padding-bottom="4px" display-align="center">
                                        <fo:block font-weight="bold">
                                            <fo:inline>
                                                <xsl:value-of
                                                        select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Adresa/aut:Ulica"/>
                                            </fo:inline>
                                            <xsl:text> </xsl:text>
                                            <fo:inline>
                                                <xsl:value-of
                                                        select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Adresa/aut:Broj"/>
                                            </fo:inline>
                                            ,
                                            <fo:inline>
                                                <xsl:value-of
                                                        select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Adresa/aut:Postanski_broj"/>
                                            </fo:inline>
                                            <xsl:text> </xsl:text>
                                            <fo:inline>
                                                <xsl:value-of
                                                        select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Adresa/aut:Grad"/>
                                            </fo:inline>
                                            <xsl:text> </xsl:text>
                                            <fo:inline>
                                                <xsl:value-of
                                                        select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Adresa/aut:Drzava"/>
                                            </fo:inline>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell display-align="center">
                                        <fo:block font-weight="bold">
                                            <xsl:value-of
                                                    select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Kontakt/aut:Telefon"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell display-align="center">
                                        <fo:block font-weight="bold">
                                            <xsl:value-of
                                                    select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Kontakt/aut:E_posta"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell display-align="center">
                                        <fo:block font-weight="bold">
                                            <xsl:value-of
                                                    select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Kontakt/aut:Faks"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell display-align="center">
                                        <xsl:choose>
                                            <xsl:when test="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Sediste">
                                                <fo:block font-weight="bold">
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Sediste"/>
                                                </fo:block>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <fo:block font-weight="bold">
                                                    <fo:block>/</fo:block>
                                                </fo:block>
                                            </xsl:otherwise>
                                        </xsl:choose>

                                    </fo:table-cell>

                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <xsl:if test="//aut:Popunjava_podnosilac/aut:Punomocnik">
                        <fo:block margin-top="30px">
                            <fo:block text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                                Punomocnik
                            </fo:block>
                            <fo:table font-family="serif" border="1px" margin-top="10px">
                                <fo:table-column column-width="15%"/>
                                <fo:table-column column-width="20%"/>
                                <fo:table-column column-width="15%"/>
                                <fo:table-column column-width="20%"/>
                                <fo:table-column column-width="15%"/>
                                <fo:table-column column-width="15%"/>
                                <fo:table-body>
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell background-color="#3e87bc" color="white" font-family="sans-serif"
                                                       padding="5px" font-weight="bold">
                                            <fo:block>Ime</fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                       padding="5px" font-weight="bold">
                                            <fo:block>Adresa</fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                       padding="5px" font-weight="bold">
                                            <fo:block>Telefon</fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                       padding="5px" font-weight="bold">
                                            <fo:block>Email</fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                       padding="5px" font-weight="bold">
                                            <fo:block>Faks</fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                       padding="5px" font-weight="bold">
                                            <fo:block>Sediste</fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row border="1px solid darkgrey">
                                        <fo:table-cell padding-left="5px" display-align="center" font-weight="bold">
                                            <xsl:choose>
                                                <xsl:when
                                                        test="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Poslovno_ime">
                                                    <fo:block>
                                                        <xsl:value-of
                                                                select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Poslovno_ime"/>
                                                    </fo:block>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <fo:block>
                                                        <xsl:value-of
                                                                select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Ime"/>
                                                        <xsl:text> </xsl:text>
                                                        <xsl:value-of
                                                                select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Prezime"/>
                                                    </fo:block>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </fo:table-cell>

                                        <fo:table-cell padding-right="8px" padding-bottom="4px" display-align="center">
                                            <fo:block font-weight="bold">
                                                <fo:inline>
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Adresa/aut:Ulica"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Adresa/aut:Broj"/>
                                                </fo:inline>
                                                ,
                                                <fo:inline>
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Adresa/aut:Postanski_broj"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Adresa/aut:Grad"/>
                                                </fo:inline>
                                                <xsl:text> </xsl:text>
                                                <fo:inline>
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Adresa/aut:Drzava"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of
                                                        select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Kontakt/aut:Telefon"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of
                                                        select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Kontakt/aut:E_posta"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell display-align="center">
                                            <fo:block font-weight="bold">
                                                <xsl:value-of
                                                        select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Kontakt/aut:Faks"/>
                                            </fo:block>
                                        </fo:table-cell>

                                        <fo:table-cell display-align="center">
                                            <xsl:choose>
                                                <xsl:when test="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Sediste">
                                                    <fo:block font-weight="bold">
                                                        <xsl:value-of
                                                                select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Sediste"/>
                                                    </fo:block>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <fo:block font-weight="bold">
                                                        <fo:block>/</fo:block>
                                                    </fo:block>
                                                </xsl:otherwise>
                                            </xsl:choose>

                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-body>
                            </fo:table>
                        </fo:block>
                    </xsl:if>

                    <fo:block margin-top="30px">
                        <fo:block text-align="center" font-family="sans-serif" font-size="15px" font-weight="bold">
                            Autori
                        </fo:block>
                        <fo:table font-family="serif" border="1px" margin-top="10px">
                            <fo:table-column column-width="15%"/>
                            <fo:table-column column-width="18%"/>
                            <fo:table-column column-width="12%"/>
                            <fo:table-column column-width="13%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-column column-width="17%"/>
                            <fo:table-column column-width="10%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell background-color="#3e87bc" color="white" font-family="sans-serif"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Ime</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Adresa</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Drzavljanstvo</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Telefon</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Email</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Faks</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#3e87bc" font-family="sans-serif" color="white"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Godina smrti</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="//aut:Popunjava_podnosilac/aut:Autori/*">
                                    <xsl:choose>
                                        <xsl:when test="aut:Autor">
                                            <fo:table-row border="1px solid darkgrey">
                                                <fo:table-cell padding-left="5px" display-align="center"
                                                               font-weight="bold">
                                                    <fo:block>
                                                        <xsl:value-of
                                                                select="aut:Ime"/>
                                                        <xsl:text> </xsl:text>
                                                        <xsl:value-of
                                                                select="aut:Prezime"/>
                                                    </fo:block>
                                                </fo:table-cell>

                                                <fo:table-cell padding-right="8px" padding-bottom="4px"
                                                               display-align="center">
                                                    <fo:block font-weight="bold">
                                                        <fo:inline>
                                                            <xsl:value-of
                                                                    select="aut:Adresa/aut:Ulica"/>
                                                        </fo:inline>
                                                        <xsl:text> </xsl:text>
                                                        <fo:inline>
                                                            <xsl:value-of
                                                                    select="aut:Adresa/aut:Broj"/>
                                                        </fo:inline>
                                                        ,
                                                        <fo:inline>
                                                            <xsl:value-of
                                                                    select="aut:Adresa/aut:Postanski_broj"/>
                                                        </fo:inline>
                                                        <xsl:text> </xsl:text>
                                                        <fo:inline>
                                                            <xsl:value-of
                                                                    select="aut:Adresa/aut:Grad"/>
                                                        </fo:inline>
                                                        <xsl:text> </xsl:text>
                                                        <fo:inline>
                                                            <xsl:value-of
                                                                    select="aut:Adresa/aut:Drzava"/>
                                                        </fo:inline>
                                                    </fo:block>
                                                </fo:table-cell>

                                                <fo:table-cell display-align="center">
                                                    <fo:block font-weight="bold">
                                                        <xsl:value-of
                                                                select="aut:Drzavljanstvo"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell display-align="center">
                                                    <fo:block font-weight="bold">
                                                        <xsl:value-of
                                                                select="aut:Kontakt/aut:Telefon"/>
                                                    </fo:block>
                                                </fo:table-cell>

                                                <fo:table-cell display-align="center">
                                                    <fo:block font-weight="bold">
                                                        <xsl:value-of
                                                                select="aut:Kontakt/aut:E_posta"/>
                                                    </fo:block>
                                                </fo:table-cell>

                                                <fo:table-cell display-align="center">
                                                    <fo:block font-weight="bold">
                                                        <xsl:value-of
                                                                select="aut:Kontakt/aut:Faks"/>
                                                    </fo:block>
                                                </fo:table-cell>

                                                <fo:table-cell display-align="center">
                                                    <xsl:choose>
                                                        <xsl:when test="aut:Godina_smrti">
                                                            <fo:block font-weight="bold">
                                                                <xsl:value-of
                                                                        select="aut:Godina_smrti"/>
                                                            </fo:block>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <fo:block font-weight="bold">
                                                                <fo:block>/</fo:block>
                                                            </fo:block>
                                                        </xsl:otherwise>
                                                    </xsl:choose>

                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <fo:table-row border="1px solid darkgrey">
                                                <fo:table-cell padding-left="5px" display-align="center"
                                                               font-weight="bold">
                                                    <fo:block font-weight="bold">
                                                        <xsl:text> Anonimni autor</xsl:text>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell padding-left="5px" display-align="center"
                                                               font-weight="bold">
                                                    <fo:block font-weight="bold">
                                                        <xsl:text> / </xsl:text>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell padding-left="5px" display-align="center"
                                                               font-weight="bold">
                                                    <fo:block font-weight="bold">
                                                        <xsl:text> / </xsl:text>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell padding-left="5px" display-align="center"
                                                               font-weight="bold">
                                                    <fo:block font-weight="bold">
                                                        <xsl:text> / </xsl:text>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell padding-left="5px" display-align="center"
                                                               font-weight="bold">
                                                    <fo:block font-weight="bold">
                                                        <xsl:text> / </xsl:text>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell padding-left="5px" display-align="center"
                                                               font-weight="bold">
                                                    <fo:block font-weight="bold">
                                                        <xsl:text> / </xsl:text>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell padding-left="5px" display-align="center"
                                                               font-weight="bold">
                                                    <fo:block font-weight="bold">
                                                        <xsl:text> / </xsl:text>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>


                    <fo:block margin-top="30px" text-align="center" font-family="sans-serif" font-size="15px"
                              font-weight="bold">
                        Autorsko delo
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border-top="1px solid darkgrey" padding-top="10px"
                                                   font-family="sans-serif" font-weight="bold"
                                                   number-columns-spanned="2">
                                        <fo:block>
                                            Naslov
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal"
                                                   number-columns-spanned="2">
                                        <fo:block>
                                            <xsl:value-of select="//aut:AutorskoDelo/aut:Naslov"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell border-top="1px solid darkgrey" padding-top="10px"
                                                   font-family="sans-serif" font-weight="bold"
                                                   number-columns-spanned="2">
                                        <fo:block>
                                            Vrsta
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal"
                                                   number-columns-spanned="2">
                                        <fo:block>
                                            <xsl:value-of select="//aut:AutorskoDelo/aut:Vrsta"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>
                                    <fo:table-cell border-top="1px solid darkgrey" padding-top="10px"
                                                   font-family="sans-serif" font-weight="bold"
                                                   number-columns-spanned="2">
                                        <fo:block>
                                            Forma
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal"
                                                   number-columns-spanned="2">
                                        <fo:block>
                                            <xsl:value-of select="//aut:AutorskoDelo/aut:Forma"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>


                                <xsl:if test="//aut:AutorskoDelo/aut:Podaci_o_naslovu_autorskog_dela">
                                    <fo:table-row>
                                        <fo:table-cell border-top="1px solid darkgrey" padding-top="10px"
                                                       font-family="sans-serif" font-weight="bold"
                                                       number-columns-spanned="2">
                                            <fo:block>
                                                Podaci o zasnovanom autorskom delu
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal"
                                                       number-columns-spanned="2">
                                            <fo:block>
                                                <xsl:value-of select="//aut:AutorskoDelo/aut:Podaci_o_naslovu_autorskog_dela/aut:Naslov_autorskog_dela"/>
                                                <xsl:text> - </xsl:text>
                                                <xsl:value-of select="//aut:AutorskoDelo/aut:Podaci_o_naslovu_autorskog_dela/aut:Ime_autora"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:if>


                                <xsl:choose>
                                    <xsl:when test="//aut:AutorskoDelo/aut:Stvoreno_u_radnom_odnosu">
                                        <fo:table-row>
                                            <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal"
                                                           number-columns-spanned="2">
                                                <fo:block>
                                                    <xsl:text> Stvoreno u radnom odnosu</xsl:text>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <fo:table-row>
                                            <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal"
                                                           number-columns-spanned="2">
                                                <fo:block>
                                                    <xsl:text> Nije stvoreno u radnom odnosu</xsl:text>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:otherwise>
                                </xsl:choose>

                                <xsl:if test="//aut:AutorskoDelo/aut:Nacin_koriscenja">
                                    <fo:table-row>
                                        <fo:table-cell border-top="1px solid darkgrey" padding-top="10px"
                                                       font-family="sans-serif" font-weight="bold"
                                                       number-columns-spanned="2">
                                            <fo:block>
                                                Nacin koriscenja
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell font-family="sans-serif" padding="10px" font-weight="normal"
                                                       number-columns-spanned="2">
                                            <fo:block>
                                                <xsl:value-of select="//aut:AutorskoDelo/aut:Nacin_koriscenja"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:if>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="30px" text-align="center" font-family="sans-serif" font-size="15px"
                              font-weight="bold">
                        Popunjava zavod
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                            <fo:table-column column-width="100%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid darkgrey" color="white" background-color="#3e87bc"
                                                   padding-top="10px" font-family="sans-serif" font-weight="bold">
                                        <fo:block>
                                            Prilozi uz zahtev
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border="1px solid darkgrey" font-family="sans-serif"
                                                   padding="5px" font-weight="normal">
                                        <fo:block>
                                            <xsl:choose>
                                                <xsl:when test="//aut:Popunjava_zavod/aut:Prilozi_uz_zahtev/aut:Primer_autorskog_dela">
                                                    Primer autorskog dela
                                                </xsl:when>
                                            </xsl:choose>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:if test="//aut:Popunjava_zavod/aut:Prilozi_uz_zahtev/aut:Opis_autorskog_dela">

                                    <xsl:for-each
                                            select="//aut:Popunjava_zavod/aut:Prilozi_uz_zahtev/aut:Opis_autorskog_dela/*">
                                        <fo:table-row>
                                            <fo:table-cell border="1px solid darkgrey" font-family="sans-serif"
                                                           padding="5px" font-weight="normal">
                                                <fo:block>
                                                    <xsl:variable name="name" select="name(.)"/>
                                                    <xsl:value-of select="concat(translate($name, '_', ' '), ' ')"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:for-each>
                                </xsl:if>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>

                    <fo:block margin-top="80px" text-align="center" font-family="sans-serif" font-size="15px"
                              font-weight="bold">
                        <fo:table font-family="serif" border="1px" margin-top="10px" font-size="13px">
                            <fo:table-column column-width="50%"/>
                            <fo:table-column column-width="50%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell font-family="sans-serif" padding="5px" font-weight="bold">
                                        <fo:block>
                                            Broj prijave auta
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
                                            <xsl:value-of select="//aut:Broj_prijave"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell font-family="sans-serif">
                                        <fo:block>
                                            <xsl:value-of select="//aut:Datum_podnosenja"/>
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
