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
    boolean podnosilacJePronalazac;
    List<Lice> pronalazaci;
    Punomocnik punomocnik;
    String nazivNaSrpskom;
    String nazivNaEngleskom;
    Adresa adresaZaDostavljanje;
    String nacinDostavljanja;
    PrvobitnaPrijava prvobitnaPrijava;
    List<RanijaPrijava> ranijePrijave;
    Prilozi prilozi;
    @Getter
    @Setter
    @NoArgsConstructor
    static
    class Prilozi{
        String prvobitnaPrijava;
        List<String> ranijePrijave;
        String izjavaOSticanjuPrava;
        String izjavaPronalazaca;
        String opis;
        String nacrt;
        String apstrakt;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    static
    class RanijaPrijava{
        String brojPrijave;
        String datumPodnosenja;
        String dvoslovnaOznaka;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    static
    class PrvobitnaPrijava{
        String brojPrijave;
        String datumPodnosenja;
        String tipPrijave;
    }

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
    public class Punomocnik extends Lice {
        boolean zaPrijem;
        boolean zaZastupanje;
        boolean zajednickiPredstavnik;
    }
}