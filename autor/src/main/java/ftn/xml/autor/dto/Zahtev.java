package ftn.xml.autor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Zahtev {

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

}