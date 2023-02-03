package ftn.xml.autor.dto;

import ftn.xml.autor.model.AutorskoDelo;
import lombok.*;
import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Zahtev {
    private Lice podnosilac;
    private Lice punomocnik;
    private boolean jeAutor;
    private List<Autor> autori;
    private Prilozi prilozi;
    private AutorskoDeloDTO autorskoDelo;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class AutorskoDeloDTO {
        public boolean radniOdnos;
        public String naslov;
        public String vrstaDela;
        public String drugaVrsta;
        public String forma;
        public String naslovPrerade;
        public String imeAutoraPrerade;
        public String nacinKoriscenja;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static
    class Info {
        String ime;
        String prezime;
        String drzavljanstvo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
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
    @AllArgsConstructor
    static
    class Kontakt {
        String telefon;
        String eposta;
        String faks;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static
    public class Lice {
        Info info;
        Adresa adresa;
        Kontakt kontakt;
        String pseudonim;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static
    public class Autor {
        Info info;
        Adresa adresa;
        Kontakt kontakt;
        String pseudonim;
        String godinaSmrti;
        boolean anoniman;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class Prilozi {
        String punomocje;
        String zajednickiPredstavnik;
        String pravniOsnov;
        String uplataTakse;
        String primerDela;
        String opisDela;
    }

}