<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:patent="http://www.ftn.uns.ac.rs/jaxb/patent"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Zahtev za priznanje patenta</title>
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
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    flex:1;
                    }
                    .border {
                    border:1px solid darkgrey;
                    }
                    .border-top {
                    border-top:1px solid darkgrey;
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
                    <h1>Zahtev za priznanje patenta</h1>
                    <div class="inline-container">
                        <div>
                            <xsl:value-of select="//patent:Informacije_o_ustanovi/patent:Naziv"/>,
                        </div>
                        <div>
                            <xsl:value-of select="//patent:Informacije_o_ustanovi/patent:Adresa/patent:Ulica"/>
                        </div>

                        <div>
                            <xsl:value-of select="//patent:Informacije_o_ustanovi/patent:Adresa/patent:Broj"/>,
                        </div>
                        <div>
                            <xsl:value-of select="//patent:Informacije_o_ustanovi/patent:Adresa/patent:Postanski_broj"/>
                        </div>

                        <div>
                            <xsl:value-of select="//patent:Informacije_o_ustanovi/patent:Adresa/patent:Grad"/>
                        </div>
                    </div>

                    <div class="container">

                        <table>
                            <thead>
                                <tr class="border">
                                    <th class="purple-cell">
                                        Broj prijave
                                    </th>
                                    <th class="purple-cell">
                                        Datum prijema
                                    </th>
                                    <th class="purple-cell">
                                        Priznati datum podnošenja
                                    </th>
                                </tr>
                            </thead>

                            <tbody>
                                <xsl:for-each select="//patent:Popunjava_zavod">
                                    <tr class="border">
                                        <td class="cell">
                                            <xsl:value-of select="patent:Broj_prijave"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Datum_prijema"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Priznati_datum_podnosenja"/>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </div>

                    <div class="block">
                        <div class="title">
                            Naziv patenta
                        </div>

                        <table>
                            <thead>
                                <tr class="border">
                                    <th class="purple-cell">
                                        Na srpskom jeziku
                                    </th>
                                    <th class="purple-cell">
                                        Na engleskom jeziku
                                    </th>

                                </tr>
                            </thead>

                            <tbody>
                                <xsl:for-each select="//patent:Naziv_patenta">
                                    <tr class="border">
                                        <td class="cell">
                                            <xsl:value-of select="patent:Naziv_na_srpskom"/>
                                        </td>
                                        <td>
                                            <xsl:value-of select="patent:Naziv_na_engleskom"/>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>

                    </div>

                    <div class="block">
                        <div class="title">
                            Podnosilac prijave
                        </div>

                        <table>
                            <thead>
                                <tr class="border">
                                    <th class="purple-cell">
                                        Ime
                                    </th>
                                    <th class="purple-cell">
                                        Adresa
                                    </th>
                                    <th class="purple-cell">
                                        Telefon
                                    </th>
                                    <th class="purple-cell">
                                        E_posta
                                    </th>
                                    <th class="purple-cell">
                                        Faks
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <xsl:for-each
                                        select="//patent:Popunjava_podnosioc/patent:Podaci_o_podnosiocu/patent:Podnosioc">
                                    <tr class="border">
                                        <td class="cell">
                                            <xsl:choose>
                                                <xsl:when test="patent:Poslovno_ime">
                                                    <xsl:value-of select="patent:Poslovno_ime"/>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <div class="inline">
                                                        <xsl:value-of select="patent:Ime"/>
                                                        <xsl:text> </xsl:text>
                                                        <xsl:value-of select="patent:Prezime"/>
                                                    </div>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </td>

                                        <td class="address-cell inline">
                                            <xsl:value-of select="patent:Adresa/patent:Ulica"/>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of select="patent:Adresa/patent:Broj"/>
                                            <xsl:value-of select="patent:Adresa/patent:Postanski_broj"/>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of select="patent:Adresa/patent:Grad"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Kontakt/patent:Telefon"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Kontakt/patent:E_posta"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Kontakt/patent:Faks"/>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </div>

                    <div class="block">
                        <div class="title">
                            Pronalazač
                        </div>
                        <xsl:for-each select="//patent:Popunjava_podnosioc/patent:Podaci_o_pronalazacu">
                            <xsl:choose>
                                <xsl:when test="patent:Pronalazac_ne_zeli_da_bude_naveden">
                                    <table>
                                        <thead>
                                            <tr class="border">
                                                <th>
                                                    Pronalazač ne želi da bude naveden
                                                </th>
                                            </tr>
                                        </thead>
                                    </table>
                                </xsl:when>
                                <xsl:otherwise>
                                    <table>
                                        <thead>
                                            <tr class="border">
                                                <th class="purple-cell">
                                                    Ime
                                                </th>
                                                <th class="purple-cell">
                                                    Adresa
                                                </th>
                                                <th class="purple-cell">
                                                    Telefon
                                                </th>
                                                <th class="purple-cell">
                                                    E_posta
                                                </th>
                                                <th class="purple-cell">
                                                    Faks
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr class="border">
                                                <td class="cell">
                                                    <xsl:for-each
                                                            select="//patent:Popunjava_podnosioc/patent:Podaci_o_pronalazacu/patent:Pronalazac">
                                                        <xsl:choose>
                                                            <xsl:when test="patent:Poslovno_ime">
                                                                <xsl:value-of select="patent:Poslovno_ime"/>
                                                            </xsl:when>
                                                            <xsl:otherwise>
                                                                <div class="inline">
                                                                    <xsl:value-of select="patent:Ime"/>
                                                                    <xsl:text> </xsl:text>
                                                                    <xsl:value-of select="patent:Prezime"/>
                                                                </div>
                                                            </xsl:otherwise>
                                                        </xsl:choose>
                                                    </xsl:for-each>
                                                </td>
                                                <td class="address-cell inline">
                                                    <xsl:value-of select="patent:Adresa/patent:Ulica"/>
                                                    <xsl:text> </xsl:text>
                                                    <xsl:value-of select="patent:Adresa/patent:Broj"/>
                                                    <xsl:text> </xsl:text>
                                                    <xsl:value-of select="patent:Adresa/patent:Postanski_broj"/>
                                                    <xsl:text> </xsl:text>
                                                    <xsl:value-of select="patent:Adresa/patent:Grad"/>
                                                </td>
                                                <td>
                                                    <xsl:value-of select="patent:Kontakt/patent:Telefon"/>
                                                </td>
                                                <td>
                                                    <xsl:value-of select="patent:Kontakt/patent:E_posta"/>
                                                </td>
                                                <td>
                                                    <xsl:value-of select="patent:Kontakt/patent:Faks"/>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:for-each>
                    </div>

                    <div class="block">
                        <div class="title">
                            Punomoćnik
                        </div>
                        <table>
                            <thead>
                                <tr class="border">
                                    <th class="purple-cell">
                                        Ime
                                    </th>
                                    <th class="purple-cell">
                                        Adresa
                                    </th>
                                    <th class="purple-cell">
                                        Telefon
                                    </th>
                                    <th class="purple-cell">
                                        E_posta
                                    </th>
                                    <th class="purple-cell">
                                        Faks
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <xsl:for-each
                                        select="//patent:Popunjava_podnosioc/patent:Podaci_o_punomocniku/patent:Punomocnik">
                                    <tr class="border">
                                        <td class="cell">
                                            <xsl:choose>
                                                <xsl:when test="patent:Poslovno_ime">
                                                    <xsl:value-of select="patent:Poslovno_ime"/>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <div class="inline">
                                                        <xsl:value-of select="patent:Ime"/>
                                                        <xsl:text> </xsl:text>
                                                        <xsl:value-of select="patent:Prezime"/>
                                                    </div>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </td>

                                        <td class="address-cell inline">
                                            <xsl:value-of select="patent:Adresa/patent:Ulica"/>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of select="patent:Adresa/patent:Broj"/>
                                            <xsl:value-of select="patent:Adresa/patent:Postanski_broj"/>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of select="patent:Adresa/patent:Grad"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Kontakt/patent:Telefon"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Kontakt/patent:E_posta"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Kontakt/patent:Faks"/>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                        <div>
                            <xsl:for-each select="//patent:Popunjava_podnosioc/patent:Podaci_o_punomocniku">
                                <xsl:choose>
                                    <xsl:when test="patent:Za_zastupanje">
                                        Punomoćnik za zastupanje
                                    </xsl:when>
                                    <xsl:when test="patent:Za_prijem">
                                        Punomoćnik za prijem podataka
                                    </xsl:when>
                                    <xsl:when test="patent:Predstavnik">
                                        Predstavnik
                                    </xsl:when>
                                </xsl:choose>
                            </xsl:for-each>

                        </div>
                    </div>

                    <xsl:for-each select="//patent:Popunjava_podnosioc/patent:Dostavljanje">
                        <div class="block">
                            <div class="title">
                                Adresa i način dostavljanja
                            </div>
                            <table>
                                <thead>
                                    <tr class="border">
                                        <th class="purple-cell">
                                            Adresa
                                        </th>
                                        <th class="purple-cell">
                                            Način dostavljanja
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <xsl:for-each select="//patent:Popunjava_podnosioc/patent:Dostavljanje">
                                        <tr class="border">

                                            <td class="address-cell inline">
                                                <xsl:value-of select="patent:Adresa/patent:Ulica"/>
                                                <xsl:text> </xsl:text>
                                                <xsl:value-of select="patent:Adresa/patent:Broj"/>
                                                <xsl:value-of select="patent:Adresa/patent:Postanski_broj"/>
                                                <xsl:text> </xsl:text>
                                                <xsl:value-of select="patent:Adresa/patent:Grad"/>
                                            </td>

                                            <td>
                                                <xsl:value-of select="patent:Nacin"/>
                                            </td>

                                        </tr>
                                    </xsl:for-each>
                                </tbody>
                            </table>
                        </div>
                    </xsl:for-each>

                    <div class="block">
                        <div class="title">
                            Prvobitna/Osnovna prijava
                        </div>

                        <table>
                            <thead>
                                <tr class="border">
                                    <th class="purple-cell">
                                        Broj prvobitne prijave/osnovne prijave
                                    </th>
                                    <th class="purple-cell">
                                        Datum podnošenja prvobitne prijave/osnovne prijave
                                    </th>
                                    <th class="purple-cell">
                                        Tip prijave
                                    </th>
                                </tr>
                            </thead>

                            <tbody>
                                <xsl:for-each select="//patent:Prvobitna_prijava">
                                    <tr class="border">
                                        <td class="cell">
                                            <xsl:value-of select="patent:Broj_prijave"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Datum_podnosenja"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Tip_prijave"/>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </div>

                    <div class="block">
                        <div class="title">
                            Zahtev za priznanje prvenstva iz ranijih prijava
                        </div>

                        <table>
                            <thead>
                                <tr class="border">
                                    <th class="purple-cell">
                                        Broj prijave
                                    </th>
                                    <th class="purple-cell">
                                        Datum podnošenja
                                    </th>
                                    <th class="purple-cell">
                                        Dvoslovna oznaka države, regionalne ili međunarodne organizacije
                                    </th>
                                </tr>
                            </thead>

                            <tbody>
                                <xsl:for-each select="//patent:Prijava">
                                    <tr class="border">
                                        <td class="cell">
                                            <xsl:value-of select="patent:Broj_prijave"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Datum_podnosenja"/>
                                        </td>

                                        <td>
                                            <xsl:value-of select="patent:Dvoslovna_oznaka"/>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </div>


                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
