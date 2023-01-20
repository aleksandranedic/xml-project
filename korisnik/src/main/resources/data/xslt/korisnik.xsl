<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:korisnik="http://www.ftn.uns.ac.rs/jaxb/korisnik"
                xmlns:pred="http://www.ftn.uns.ac.rs/jaxb/korisnik/pred/"
>
    <xsl:template match="/">
        <rdf:RDF>
            <rdf:Description rdf:about="http://www.ftn.uns.ac.rs/jaxb/korisnik/{//korisnik:Email}">
                <pred:Email>
                    <xsl:value-of select="//korisnik:Email"/>
                </pred:Email>
                <pred:Ime>
                    <xsl:value-of select="//korisnik:Ime"/>
                </pred:Ime>
                <pred:Prezime>
                    <xsl:value-of select="//korisnik:Prezime"/>
                </pred:Prezime>
                <pred:Uloga>
                    <xsl:value-of select="//korisnik:Uloga"/>
                </pred:Uloga>
            </rdf:Description>
        </rdf:RDF>
    </xsl:template>
</xsl:stylesheet>