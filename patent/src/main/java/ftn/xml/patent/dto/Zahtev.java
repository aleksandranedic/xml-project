package ftn.xml.patent.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class Zahtev {
    Lice podnosilac;
    List<Pronalazac> pronalazaci;
    Punomocnik punomocnik;

    String nazivNaSrpskom;
    String nazivNaEngleskom;

    @Getter
    @Setter
    @NoArgsConstructor
    static
    class Info{
        String ime;
        String prezime;
        String drzavljanstvo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static
    class Adresa {
        String ulica;
        String broj;
        String drzava;
        String postanskiBroj;
        String grad;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static
    class Kontakt{
        String telefon;
        String eposta;
        String faks;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static
    public class Lice{
        Info info;
        Adresa adresa;
        Kontakt kontakt;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    static
    public class Pronalazac extends Lice {
        boolean pronalazacNaveden;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    static
    public class Punomocnik extends Lice {
        boolean zaPrijem;
        boolean zaZastupanje;
        boolean zajednickiPredstavnik;
    }
}