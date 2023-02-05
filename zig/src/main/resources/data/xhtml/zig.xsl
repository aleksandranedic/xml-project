<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:zig="http://www.ftn.uns.ac.rs/jaxb/zig"
                version="2.0">

    <xsl:template match="/">
        <html>
        <head>
            <title>Zahtev za priznanje ziga</title>
            <style>
                h1 {
                    font-weight: bold;
                }
                .container {
                    font-size: 15px;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    height: 100%;
                    font-family: sans-serif;
                    padding: 0px 4%;
                }
                .inline-container {
                    display: flex;
                    gap: 10px;
                }
                .inline {
                    display: flex;
                    gap: 3px;
                }
                .block {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    width: 100%;
                }
                .title {
                    font-size:15px;
                    width:100%;
                    margin-top: 10px;
                }
                .half {
                    width: 50%
                }
                .third {
                    width: 33%
                }
                table {
                    width: 100%;
                    border:1px;
                    margin-top:10px;
                    margin-bottom:5px;
                }
                .table-2 {
                    font-family:serif;
                    border:1px;
                    margin-top:10px;
                    font-size: 15px
                }
                tr {
                    display: inline-flex;
                    width: 100%;
                }
                th, td {
                    width: 20%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }
                .margin-right {
                    font-weight: bold;
                    margin-right: 5px;
                }
                .border {
                    border:1px solid darkgrey;
                }
                .border-top {
                    border-top:1px solid darkgrey;
                }
                .green-cell {
                    font-family:sans-serif;
                    padding:5px;
                }
                .light-green-cell {
                    font-family:sans-serif;
                }
                .cell {
                    padding-left:5px
                }
                .address-cell {
                    padding-right:8px;
                    padding-bottom:4px;
                }
                .two-cols {
                    width: 100%;
                    justify-content: center;
                    display: flex;
                }
                .cell-2 {
                    width: 100%;
                    display: flex;
                    justify-content: center;
                }
                .weight-normal {
                    font-weight: normal
                }
                .table-like {
                    display: flex;
                    width: 100%;
                }
                .flex-col {
                    display: flex;
                    flex-direction: column;
                }
                .flex {
                    display: flex;
                align-items: center;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1> Zahtev za priznanje žiga </h1>
                <div class="inline-container">
                    <div>
                        <xsl:value-of select="//zig:Informacije_o_ustanovi/zig:Naziv"/>,
                    </div>
                    <div>
                        <xsl:value-of select="//zig:Informacije_o_ustanovi/zig:Adresa/zig:Ulica"/>
                    </div>

                    <div>
                        <xsl:value-of select="//zig:Informacije_o_ustanovi/zig:Adresa/zig:Broj"/>,
                    </div>
                    <div>
                        <xsl:value-of select="//zig:Informacije_o_ustanovi/zig:Adresa/zig:Postanski_broj"/>
                    </div>

                    <div>
                        <xsl:value-of select="//zig:Informacije_o_ustanovi/zig:Adresa/zig:Grad"/>
                    </div>
                </div>

                <div class="border">

                    <div class="block">
                        <div class="title">
                            <b>Podnosilac prijave</b>: ime i prezime/poslovno ime, ulica i broj, poštanski broj, mesto, država prebivališta/sedišta
                        </div>
                        <table>
                            <tbody>
                                <tr class="border">
                                    <td class="cell half">
                                        <xsl:choose>
                                            <xsl:when test="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Poslovno_ime">
                                                <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Poslovno_ime"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <div class="inline">
                                                    <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Ime"/> <xsl:text> </xsl:text> <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Prezime"/>
                                                </div>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>

                                    <td class="address-cell inline half">
                                        <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Adresa/zig:Ulica"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Adresa/zig:Broj"/>,
                                        <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Adresa/zig:Postanski_broj"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Adresa/zig:Grad"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="third border">
                                       <p class="margin-right">Telefon:</p> <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Kontakt/zig:Telefon"/>
                                    </td>

                                    <td class="third border">
                                        <p class="margin-right">Email:</p>  <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Kontakt/zig:Email"/>
                                    </td>

                                    <td class="third border">
                                        <p class="margin-right">Faks:</p> <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Podnosilac/zig:Kontakt//zig:Faks"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <xsl:if test="//zig:Popunjava_podnosilac/zig:Punomocnik">
                    <div class="block">
                        <div class="title">
                            <b>Punomoćnik</b>: ime i prezime/poslovno ime, ulica i broj, poštanski broj, mesto, država prebivališta/sedišta
                        </div>
                        <table>
                            <tbody>
                                <tr class="border">
                                    <td class="cell half">
                                        <xsl:choose>
                                            <xsl:when test="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Poslovno_ime">
                                                <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Poslovno_ime"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <div class="inline">
                                                    <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Ime"/> <xsl:text> </xsl:text> <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Prezime"/>
                                                </div>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>

                                    <td class="address-cell inline half">
                                        <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Adresa/zig:Ulica"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Adresa/zig:Broj"/>,
                                        <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Adresa/zig:Postanski_broj"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Adresa/zig:Grad"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="third border">
                                        <p class="margin-right">Telefon:</p> <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Kontakt/zig:Telefon"/>
                                    </td>

                                    <td class="third border">
                                        <p class="margin-right">Email:</p>  <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Kontakt/zig:Email"/>
                                    </td>

                                    <td class="third border">
                                        <p class="margin-right">Faks:</p> <xsl:value-of select="//zig:Popunjava_podnosilac/zig:Punomocnik/zig:Kontakt//zig:Faks"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    </xsl:if>

                    <div class="block">
                        <div class="table-like">
                            <div class="half flex-col">
                                <div class="flex border">
                                    <p class="margin-right">a)</p>
                                    <xsl:value-of select="//zig:Zig//zig:Tip_a"/>
                                </div>
                                <div class="flex border">
                                    <p class="margin-right">b)</p>
                                    <xsl:value-of select="//zig:Zig//zig:Tip_b"/>
                                </div>
                                <div class="flex-col border">
                                    <p class="margin-right">Naznacenje boje, odnosno boja iz kojih se znak sastoji</p>
                                    <xsl:value-of select="//zig:Zig//zig:Naznacenje_boje"/>
                                </div>
                                <xsl:if test="//zig:Zig//zig:Transliteracija_znak">
                                    <div class="flex-col border">
                                        <p class="margin-right">Transliteracija znaka</p>
                                        <xsl:value-of select="//zig:Zig//zig:Transliteracija_znak"/>
                                    </div>
                                </xsl:if>
                                <xsl:if test="//zig:Zig//zig:Prevod_znaka">
                                    <div class="flex-col border">
                                        <p class="margin-right">Prevod znaka</p>
                                        <xsl:value-of select="//zig:Zig//zig:Prevod_znaka"/>
                                    </div>
                                </xsl:if>
                                <xsl:if test="//zig:Zig//zig:Opis_znaka">
                                    <div class="flex-col border">
                                        <p class="margin-right">Opis znaka</p>
                                        <xsl:value-of select="//zig:Zig//zig:Opis_znaka"/>
                                    </div>
                                </xsl:if>
                            </div>
                            <div class="half border">
                                <xsl:element name="img">
                                    <xsl:attribute name="src">
                                        <xsl:value-of select="//zig:Primerak_znaka"/>
                                    </xsl:attribute>
                                    <xsl:attribute name="style">object-fit: contain;"</xsl:attribute>
                                    <xsl:attribute name="width">330px</xsl:attribute>
                                    <xsl:attribute name="height">360px</xsl:attribute>
                                    <xsl:attribute name="maxWidth">330px</xsl:attribute>
                                    <xsl:attribute name="maxHeight">360px</xsl:attribute>
                                </xsl:element>
                            </div>
                        </div>
                    </div>

                    <div class="flex-col block border">
                        <p class="margin-right">
                            Brojevi klase roba i usluga prema Nicanskoj klasifikaciji
                        </p>
                        <p class="title">
                            <xsl:for-each select="//zig:Dodatne_informacije/zig:Klasa_robe_i_uslaga/zig:Klasa">
                                <xsl:value-of select="."/>
                                <xsl:text>  </xsl:text>
                            </xsl:for-each>
                        </p>
                    </div>

                    <div class="block border">
                        <td class="title">
                            Zatrazeno pravo prvenstva i osnov
                        </td>
                        <p class="cell-2 weight-normal">
                            <xsl:value-of select="//zig:Zatrazeno_pravo_prvensta_i_osnov"/>
                        </p>
                    </div>

                    <div class="block">
                        <table>
                            <thead>
                                <tr>
                                    <th class="border green-cell" style="width: 65%;">
                                        Placene takse
                                    </th>
                                    <th class="border green-cell" style="width: 35%;">
                                        Dinara
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="border light-green-cell" style="width: 65%;">
                                        Osnovna taksa:
                                    </td>
                                    <td class="border" style="width: 35%;">
                                        <xsl:value-of select="//zig:Osnovna_taksa"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="border light-green-cell" style="width: 65%;">
                                        <div class="inline">
                                            Za
                                            <xsl:value-of select="//zig:Za_klasa/zig:Naziv_klase"/>
                                            klasa:
                                        </div>
                                    </td>
                                    <td class="border" style="width: 35%;">
                                        <xsl:value-of select="//zig:Za_klasa/zig:Suma"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="border light-green-cell" style="width: 65%;">
                                        Za graficko resenje:
                                    </td>
                                    <td class="border" style="width: 35%;">
                                        <xsl:value-of select="//zig:Za_graficko_resenje"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="border light-green-cell" style="width: 65%;">
                                        UKUPNO:
                                    </td>
                                    <td class="border" style="width: 35%;font-size: 15px;">
                                        <xsl:value-of select="//zig:Ukupno"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="block" style="margin-top: 40px">

                        <div class="flex">
                            <div class="flex-col third">
                                <p class="margin-right">Broj prijave ziga</p>
                                <p>
                                    <xsl:value-of select="//zig:Broj_prijave_ziga"/>
                                </p>
                            </div>
                            <div class="flex-col third">
                                <p class="margin-right">Datum podnosenja</p>
                                <p>
                                    <xsl:value-of select="//zig:Datum_podnosenja"/>
                                </p>
                            </div>
                            <div class="third">
                                <xsl:element name="img">
                                    <xsl:attribute name="src">
                                        <xsl:value-of select="//zig:QR_kod"/>
                                    </xsl:attribute>
                                    <xsl:attribute name="width">200px</xsl:attribute>
                                    <xsl:attribute name="height">200px</xsl:attribute>
                                    <xsl:attribute name="maxWidth">200px</xsl:attribute>
                                    <xsl:attribute name="maxHeight">200px</xsl:attribute>
                                </xsl:element>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>
    </html>
    </xsl:template>
</xsl:stylesheet>
