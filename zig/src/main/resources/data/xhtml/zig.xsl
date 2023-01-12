<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:zig="http://www.ftn.uns.ac.rs/jaxb/zig"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <html>
        <head>
            <title>Zahtev za priznanje ziga</title>
            <style>
                h1 {
                    font-weight: bold;
                }
                .container {
                    font-size: 20px;
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
                    margin-top:30px;
                    width: 100%;
                }
                .title {
                    font-size:25px;
                    font-weight:bold;
                }
                table {
                    width: 100%;
                    border:1px;
                    margin-top:10px;
                    margin-bottom:5px;
                    font-weight: bold;
                }
                .table-2 {
                    font-family:serif;
                    border:1px;
                    margin-top:10px;
                    font-size: 20px
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
                .border {
                    border:1px solid darkgrey;
                }
                .border-top {
                    border-top:1px solid darkgrey;
                }
                .green-cell {
                    background-color:#4caf50;
                    color:white;
                    font-family:sans-serif;
                    padding:5px;
                    font-weight:bold;
                }
                .light-green-cell {
                    background-color:#80C883;
                    color:white;
                    font-family:sans-serif;
                    font-weight:bold;
                }
                .cell {
                    padding-left:5px
                    font-weight:bold
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
            </style>
        </head>
        <body>
            <div class="container">
                <h1> Zahtev za priznanje žiga </h1>
                <div class="inline-container">
                    <div>
                        <xsl:value-of select="//zig:informacije_o_ustanovi/zig:naziv"/>,
                    </div>
                    <div>
                        <xsl:value-of select="//zig:informacije_o_ustanovi/zig:adresa/zig:ulica"/>
                    </div>

                    <div>
                        <xsl:value-of select="//zig:informacije_o_ustanovi/zig:adresa/zig:broj"/>,
                    </div>
                    <div>
                        <xsl:value-of select="//zig:informacije_o_ustanovi/zig:adresa/zig:postanski_broj"/>
                    </div>

                    <div>
                        <xsl:value-of select="//zig:informacije_o_ustanovi/zig:adresa/zig:grad"/>
                    </div>
                </div>

                <div class="block">
                    <div class="title">
                        Podnosilac prijave
                    </div>
                    <table>
                        <thead>
                            <tr class="border">
                                <th class="green-cell">
                                    Ime
                                </th>
                                <th class="green-cell">
                                    Adresa
                                </th>
                                <th class="green-cell">
                                    Telefon
                                </th>
                                <th class="green-cell">
                                    Email
                                </th>
                                <th class="green-cell">
                                    Faks
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                                <tr class="border">
                                    <td class="cell">
                                        <xsl:choose>
                                            <xsl:when test="zig:poslovno_ime">
                                                <xsl:value-of select="zig:poslovno_ime"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <div class="inline">
                                                    <xsl:value-of select="zig:ime"/> <xsl:text> </xsl:text> <xsl:value-of select="zig:prezime"/>
                                                </div>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>

                                    <td class="address-cell inline">
                                        <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:adresa/zig:ulica"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:adresa/zig:broj"/>
                                        <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:adresa/zig:postanski_broj"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:adresa/zig:grad"/>
                                    </td>

                                    <td>
                                        <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:telefon"/>
                                    </td>

                                    <td>
                                        <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:email"/>
                                    </td>

                                    <td>
                                        <xsl:value-of select="//zig:popunjava_podnosilac/zig:podnosilac/zig:faks"/>
                                    </td>
                                </tr>
                        </tbody>
                    </table>
                </div>

                <div class="block">
                    <div class="title">
                        Punomocnik
                    </div>
                    <table>
                        <thead>
                            <tr class="border">
                                <th class="green-cell">
                                    Ime
                                </th>
                                <th class="green-cell">
                                    Adresa
                                </th>
                                <th class="green-cell">
                                    Telefon
                                </th>
                                <th class="green-cell">
                                    Email
                                </th>
                                <th class="green-cell">
                                    Faks
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <xsl:for-each select="//zig:popunjava_podnosilac/zig:punomocnik">
                                <tr class="border">
                                    <td class="cell">
                                        <xsl:choose>
                                            <xsl:when test="zig:poslovno_ime">
                                                <xsl:value-of select="zig:poslovno_ime"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <div class="inline">
                                                    <xsl:value-of select="zig:ime"/> <xsl:text> </xsl:text> <xsl:value-of select="zig:prezime"/>
                                                </div>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>

                                    <td class="address-cell inline">
                                        <xsl:value-of select="zig:adresa/zig:ulica"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="zig:adresa/zig:broj"/>
                                        <xsl:value-of select="zig:adresa/zig:postanski_broj"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="zig:adresa/zig:grad"/>
                                    </td>

                                    <td>
                                        <xsl:value-of select="zig:telefon"/>
                                    </td>

                                    <td>
                                        <xsl:value-of select="zig:email"/>
                                    </td>

                                    <td>
                                        <xsl:value-of select="zig:faks"/>
                                    </td>
                                </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </div>

                <div class="block">
                    <div class="title">
                        Informacije o zigu
                    </div>
                    <table class="table-2">
                       <thead>
                        <tr>
                            <th class="border-top two-cols">
                                Vrsta
                            </th>
                        </tr>
                        <tr>
                            <th class="cell-2">
                                Tip A
                            </th>
                            <th class="cell-2">
                               Tip B
                            </th>
                        </tr>
                       </thead>
                        <tbody>
                        <tr>
                            <td class="cell-2 weight-normal">
                                <xsl:variable name="name_a" select="name(//zig:zig/zig:vrsta/zig:tip_a/*[1])"/>
                                <xsl:value-of select="concat(translate($name_a, '_', ' '), ' ')"/>
                            </td>
                            <td class="cell-2 weight-normal">
                                <xsl:variable name="name_b" select="name(//zig:zig/zig:vrsta/zig:tip_b/*[1])"/>
                                <xsl:value-of select="concat(translate($name_b, '_', ' '), ' ')"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="border-top two-cols">
                                Naznacenje boje, odnosno boja iz kojih se znak sastoji
                            </td>
                        </tr>
                        <tr>
                            <td class="cell-2 weight-normal">
                                <xsl:value-of select="//zig:zig/zig:naznacenje_boje"/>
                            </td>
                        </tr>
                        <xsl:if test="//zig:zig/zig:transliteracija_znak">
                            <tr>
                                <td class="border-top two-cols">
                                    Transliteracija znaka
                                </td>
                            </tr>
                            <tr>
                                <td class="cell-2 weight-normal">
                                    <xsl:value-of select="//zig:zig/zig:transliteracija_znak"/>
                                </td>
                            </tr>
                        </xsl:if>
                        <xsl:if test="//zig:zig/zig:prevod_znaka">
                            <tr>
                                <td class="border-top two-cols">
                                    Prevod znaka
                                </td>
                            </tr>
                            <tr>
                                <td class="cell-2 weight-normal">
                                    <xsl:value-of select="//zig:zig/zig:prevod_znaka"/>
                                </td>
                            </tr>
                        </xsl:if>
                        <tr>
                            <td class="border-top two-cols">
                                Opis znaka
                            </td>
                        </tr>
                        <tr>
                            <td class="cell-2 weight-normal">
                                <xsl:value-of select="//zig:zig/zig:opis_znaka"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="border-top two-cols">
                                Brojevi klase roba i usluga prema Nicanskoj klasifikaciji
                            </td>
                        </tr>
                        <tr>
                            <td class="cell-2 weight-normal inline">
                                <xsl:for-each select="//zig:dodatne_informacije/zig:klasa_robe_i_uslaga/zig:klasa">
                                    <xsl:value-of select="."/>
                                    <xsl:text>  </xsl:text>
                                </xsl:for-each>
                            </td>
                        </tr>
                        <tr>
                            <td class="border-top two-cols">
                                Zatrazeno pravo prvenstva i osnov
                            </td>
                        </tr>
                        <tr>
                            <td class="cell-2 weight-normal">
                                <xsl:value-of select="//zig:zatrazeno_pravo_prvensta_i_osnov"/>
                            </td>
                        </tr>
                    </tbody>
                    </table>
                </div>

                <div class="block">
                    <div class="title">
                        Informacije o placanju
                    </div>
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
                                    <xsl:value-of select="//zig:osnovna_taksa"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="border light-green-cell" style="width: 65%;">
                                    <div class="inline">
                                        Za
                                        <xsl:value-of select="//zig:za_klasa/zig:naziv_klase"/>
                                        klasa:
                                    </div>
                                </td>
                                <td class="border" style="width: 35%;">
                                    <xsl:value-of select="//zig:za_klasa/zig:suma"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="border light-green-cell" style="width: 65%;">
                                    Za graficko resenje:
                                </td>
                                <td class="border" style="width: 35%;">
                                    <xsl:value-of select="//zig:za_graficko_resenje"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="border light-green-cell" style="width: 65%;">
                                    UKUPNO:
                                </td>
                                <td class="border" style="width: 35%;font-size: 20px;">
                                    <xsl:value-of select="//zig:ukupno"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="block">
                    <div class="title">
                        Popunjava zavod
                    </div>
                    <table>
                    <thead>
                    <tr>
                        <th class="border green-cell cell-2">
                            Prilozi uz zahtev
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <xsl:for-each select="//zig:popunjava_zavod/zig:prilozi_uz_zahtev/*">
                        <tr>
                            <td class="border cell-2">
                                <xsl:variable name="name" select="name(.)"/>
                                <xsl:value-of select="concat(translate($name, '_', ' '), ' ')"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                    </tbody>
                    </table>
                </div>

                <div class="block" style="margin-top: 80px">
                    <table>
                    <thead>
                    <tr>
                        <th class="cell-2">
                            Broj prijave ziga
                        </th>
                        <th class="cell-2">
                            Datum podnosenja
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="cell-2">
                            <xsl:value-of select="//zig:broj_prijave_ziga"/>
                        </td>
                        <td class="cell-2">
                            <xsl:value-of select="//zig:datum_podnosenja"/>
                        </td>
                    </tr>
                    </tbody>
                    </table>
                </div>
            </div>
        </body>
    </html>
    </xsl:template>
</xsl:stylesheet>
