<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:aut="http://www.ftn.uns.ac.rs/jaxb/autor"
                version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Zahtev za unošenje u evidenciju i depovanovanje autorskih dela</title>
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
                    background-color:#3e87bc;
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
                    padding-left:5px;
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
                    <h1>Zahtev za unošenje u evidenciju i depovanovanje autorskih dela</h1>
                    <div class="inline-container">
                        <div>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Naziv"/>,
                        </div>
                        <div>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Adresa/aut:Ulica"/>
                        </div>

                        <div>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Adresa/aut:Broj"/>,
                        </div>
                        <div>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Adresa/aut:Postanski_broj"/>
                        </div>

                        <div>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Adresa/aut:Grad"/>,
                        </div>
                        <div>
                            <xsl:value-of select="//aut:Informacija_o_ustanovi/aut:Adresa/aut:Drzava"/>
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
                                    <th class="green-cell">
                                        Sediste
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="border">
                                    <td class="cell">
                                        <xsl:choose>
                                            <xsl:when test="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Poslovno_ime">
                                                <xsl:value-of
                                                        select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Poslovno_ime"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <div class="inline">
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Ime"/>
                                                    <xsl:text> </xsl:text>
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Prezime"/>
                                                </div>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>

                                    <td class="address-cell inline">
                                        <xsl:value-of
                                                select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Adresa/aut:Ulica"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of
                                                select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Adresa/aut:Broj"/>
                                        <xsl:value-of
                                                select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Adresa/aut:Postanski_broj"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of
                                                select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Adresa/aut:Grad"/>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of
                                                select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Adresa/aut:Drzava"/>
                                    </td>

                                    <td>
                                        <xsl:value-of
                                                select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Kontakt/aut:Telefon"/>
                                    </td>

                                    <td>
                                        <xsl:value-of
                                                select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Kontakt/aut:E_posta"/>
                                    </td>

                                    <td>
                                        <xsl:value-of
                                                select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Kontakt/aut:Faks"/>
                                    </td>
                                    <td class="cell">
                                        <xsl:choose>
                                            <xsl:when test="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:Sediste">
                                                <xsl:value-of
                                                        select="//aut:Popunjava_podnosilac/aut:Podnosilac/aut:sediste"/>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <div class="inline">
                                                    /
                                                </div>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <xsl:if test="//aut:Popunjava_podnosilac/aut:Punomocnik">
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
                                        <th class="green-cell">
                                            Sediste
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="border">
                                        <td class="cell">
                                            <xsl:choose>
                                                <xsl:when
                                                        test="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Poslovno_ime">
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Poslovno_ime"/>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <div class="inline">
                                                        <xsl:value-of
                                                                select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Ime"/>
                                                        <xsl:text> </xsl:text>
                                                        <xsl:value-of
                                                                select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Prezime"/>
                                                    </div>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </td>

                                        <td class="address-cell inline">
                                            <xsl:value-of
                                                    select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Adresa/aut:Ulica"/>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of
                                                    select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Adresa/aut:Broj"/>
                                            <xsl:value-of
                                                    select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Adresa/aut:Postanski_broj"/>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of
                                                    select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Adresa/aut:Grad"/>
                                        </td>

                                        <td>
                                            <xsl:value-of
                                                    select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Kontakt/aut:Telefon"/>
                                        </td>

                                        <td>
                                            <xsl:value-of
                                                    select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Kontakt/aut:E_posta"/>
                                        </td>

                                        <td>
                                            <xsl:value-of
                                                    select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Kontakt/aut:Faks"/>
                                        </td>
                                        <td class="cell">
                                            <xsl:choose>
                                                <xsl:when test="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Sediste">
                                                    <xsl:value-of
                                                            select="//aut:Popunjava_podnosilac/aut:Punomocnik/aut:Sediste"/>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <div class="inline">
                                                        /
                                                    </div>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </xsl:if>
                    <div class="block">
                        <div class="title">
                            Autori
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
                                        Drzavljanstvo
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
                                    <th class="green-cell">
                                        Godina smrti
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <xsl:for-each select="//aut:Popunjava_podnosilac/aut:Autori/*">
                                    <xsl:choose>
                                        <xsl:when test="aut:Autor">
                                            <tr class="border">
                                                <td class="cell">
                                                    <div class="inline">
                                                        <xsl:value-of select="aut:Ime"/>
                                                        <xsl:text> </xsl:text>
                                                        <xsl:value-of select="aut:Prezime"/>
                                                    </div>
                                                </td>

                                                <td class="address-cell inline">
                                                    <xsl:value-of select="aut:Adresa/aut:Ulica"/>
                                                    <xsl:text> </xsl:text>
                                                    <xsl:value-of select="aut:Adresa/aut:Broj"/>
                                                    <xsl:value-of select="aut:Adresa/aut:Postanski_broj"/>
                                                    <xsl:text> </xsl:text>
                                                    <xsl:value-of select="aut:Adresa/aut:Grad"/>
                                                    <xsl:text> </xsl:text>
                                                    <xsl:value-of select="aut:Adresa/aut:Drzava"/>
                                                </td>

                                                <td>
                                                    <xsl:value-of select="aut:Drzavljanstvo"/>
                                                </td>
                                                <td>
                                                    <xsl:value-of select="aut:Kontakt/aut:Telefon"/>
                                                </td>

                                                <td>
                                                    <xsl:value-of select="aut:Kontakt/aut:E_posta"/>
                                                </td>

                                                <td>
                                                    <xsl:value-of select="aut:Kontakt/aut:Faks"/>
                                                </td>

                                                <td>
                                                    <xsl:choose>
                                                        <xsl:when test="aut:Godina_smrti">
                                                            <xsl:value-of select="aut:Godina_smrti"/>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            /
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </td>
                                            </tr>
                                        </xsl:when>
                                        <xsl:otherwise>

                                            <tr class="border">
                                                <td class="cell">
                                                    <div class="inline">
                                                        Anonimni autor
                                                    </div>
                                                </td>
                                                <td>/</td>
                                                <td>/</td>
                                                <td>/</td>
                                                <td>/</td>
                                                <td>/</td>
                                            </tr>

                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </div>


                    <div class="block">
                        <div class="title">
                            Autorsko delo
                        </div>
                        <table class="table-2">
                            <thead>
                                <tr>
                                    <th class="border-top two-cols">
                                        Naslov
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="cell-2 weight-normal">
                                        <xsl:value-of select="//aut:AutorskoDelo/aut:Naslov"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="border-top two-cols">
                                        Vrsta
                                    </td>
                                </tr>
                                <tr>
                                    <td class="cell-2 weight-normal">
                                        <xsl:value-of select="//aut:AutorskoDelo/aut:Vrsta"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="border-top two-cols">
                                        Forma
                                    </td>
                                </tr>
                                <tr>
                                    <td class="cell-2 weight-normal">
                                        <xsl:value-of select="//aut:AutorskoDelo/aut:Forma"/>
                                    </td>
                                </tr>
                                <xsl:if test="//aut:AutorskoDelo/aut:Podaci_o_naslovu_autorskog_dela">
                                    <tr>
                                        <td class="border-top two-cols">
                                            Podaci o zasnovanom autorskom delu
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="cell-2 weight-normal">
                                            <xsl:value-of select="aut:Naslov_autorskog_dela"/>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of select="aut:Ime_autora"/>
                                        </td>
                                    </tr>
                                </xsl:if>
                                <xsl:choose>
                                    <xsl:when test="//aut:AutorskoDelo/aut:Stvoreno_u_radnom_odnosu">
                                        <tr>
                                            <td class="border-top two-cols">
                                                Stvoreno u radnom odnosu
                                            </td>
                                        </tr>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <tr>
                                            <td class="border-top two-cols">
                                                Nije stvoreno u radnom odnosu
                                            </td>
                                        </tr>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <tr>
                                    <td class="border-top two-cols">
                                        Nacin koriscenja
                                    </td>
                                </tr>
                                <tr>
                                    <td class="cell-2 weight-normal">
                                        <xsl:value-of select="//aut:AutorskoDelo/aut:Nacin_koriscenja"/>
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
                                <tr>
                                    <td class="border cell-2">
                                        <xsl:value-of
                                                select="//aut:Popunjava_zavod/aut:Prilozi_uz_zahtev/aut:Primer_autorskog_dela"/>
                                    </td>
                                </tr>
                                <xsl:if test="//aut:Popunjava_zavod/aut:Prilozi_uz_zahtev/aut:Opis_autorskog_dela">
                                    <xsl:for-each
                                            select="//aut:Popunjava_zavod/aut:Prilozi_uz_zahtev/aut:Opis_autorskog_dela/*">
                                        <tr>
                                            <td class="border cell-2">
                                                <xsl:variable name="name" select="name(.)"/>
                                                <xsl:value-of select="concat(translate($name, '_', ' '), ' ')"/>
                                            </td>
                                        </tr>
                                    </xsl:for-each>
                                </xsl:if>
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
                                        <xsl:value-of select="//aut:Popunjava_zavod/aut:Broj_prijave"/>
                                    </td>
                                    <td class="cell-2">
                                        <xsl:value-of select="//aut:Popunjava_zavod/aut:Datum_podnosenja"/>
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
