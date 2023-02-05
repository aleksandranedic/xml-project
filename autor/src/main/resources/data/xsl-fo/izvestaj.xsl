<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:izvestaj="http://www.ftn.uns.ac.rs/jaxb/izvestaj"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="izvestaj-page">
                    <fo:region-body margin="0.40in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="izvestaj-page">
                <fo:flow flow-name="xsl-region-body">

                    <fo:block text-align="center" font-family="serif" font-size="24px" font-weight="bold">
                        <xsl:value-of select="//izvestaj:Izvestaj/izvestaj:Naslov"/>
                    </fo:block>

                    <fo:block margin-top="30px">

                        <fo:table font-family="serif" border="1px" margin-top="10px">
                            <fo:table-column column-width="33%"/>
                            <fo:table-column column-width="33%"/>
                            <fo:table-column column-width="33%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell color="black" font-family="serif"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Broj podnetih zahteva</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell font-family="serif" color="black"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Broj odobrenih zahteva</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell font-family="serif" color="black"
                                                   padding="5px" font-weight="bold">
                                        <fo:block>Broj odbijenih zahteva</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <fo:table-row>

                                    <fo:table-cell display-align="center">
                                        <fo:block font-weight="bold">
                                            <xsl:value-of select="//izvestaj:Izvestaj/izvestaj:Broj_podnetih_zahteva"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell display-align="center">
                                        <fo:block font-weight="bold">
                                            <xsl:value-of select="//izvestaj:Izvestaj/izvestaj:Broj_odobrenih_zahteva"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <fo:table-cell display-align="center">
                                        <fo:block font-weight="bold">
                                            <xsl:value-of select="//izvestaj:Izvestaj/izvestaj:Broj_odbijenih_zahteva"/>
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
