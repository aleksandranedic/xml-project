package ftn.xml.autor.utils;

import ftn.xml.autor.model.*;

import java.util.List;

public class PrettyPrint {
    public static void printZahtev(ZahtevZaIntelektualnuSvojinu zahtevZaIntelektualnuSvojinu) {
        System.out.println("**** Zahtev za unošenje u evidenciju i depovanovanje autorskih dela ****");
        System.out.println("-- Informacija o ustanovi --");
        System.out.println("\t - Naziv: " + zahtevZaIntelektualnuSvojinu.getInformacijaOUstanovi().getNaziv());
        System.out.println("\t - Adresa: " + printAdress(zahtevZaIntelektualnuSvojinu.getInformacijaOUstanovi().getAdresa()));
        printPopunjavaPodnosilac(zahtevZaIntelektualnuSvojinu.getPopunjavaPodnosilac());
        printPopunjavaZavod(zahtevZaIntelektualnuSvojinu.getPopunjavaZavod());
    }

    private static void printPopunjavaPodnosilac(ZahtevZaIntelektualnuSvojinu.PopunjavaPodnosilac popunjavaPodnosilac) {
        System.out.println("-- Popunjava podnosilac --");
        System.out.println("\t - Podnosilac ");
        System.out.println(printLice(popunjavaPodnosilac.getPodnosilac()));
        System.out.println("\t - Punomocnik ");
        System.out.println(printLice(popunjavaPodnosilac.getPunomocnik()));
        System.out.println("\t - Autori ");
        printAutori(popunjavaPodnosilac.getAutori().getAnonimanOrAutor());
        System.out.println("\t - Autorsko delo ");
        printAutorskoDelo(popunjavaPodnosilac.getAutorskoDelo());
    }

    private static void printAutorskoDelo(AutorskoDelo autorskoDelo) {
        System.out.println("\tNaslov: " + autorskoDelo.getNaslov());
        System.out.println("\tVrsta: " + autorskoDelo.getVrsta());
        System.out.println("\tForma: " + autorskoDelo.getForma());
        if (autorskoDelo.getPodaciONaslovuAutorskogDela() != null) {
            System.out.println("\t\tPodaci o zasnovanom delu");
            System.out.println("\t\tIme autora: " + autorskoDelo.getPodaciONaslovuAutorskogDela().getImeAutora());
            System.out.println("\t\tNaslov autorskog dela: " + autorskoDelo.getPodaciONaslovuAutorskogDela().getNaslovAutorskogDela());
        }
        if (autorskoDelo.getStvorenoURadnomOdnosu() != null) {
            String prom = autorskoDelo.getStvorenoURadnomOdnosu() != null ? "jeste" : "nije";
            System.out.println("\t\tStovreno u radnom odnosu: " + prom);
        }
        if (autorskoDelo.getNacinKoriscenja() != null) {
            System.out.println("\t\tNacin koriscenja: " + autorskoDelo.getNacinKoriscenja());
        }
    }

    private static void printAutori(List<Object> autorOrAnonimniAutor) {
        for (int i = 0; i < autorOrAnonimniAutor.size(); i++) {
            if (autorOrAnonimniAutor.get(i) instanceof TAutor autor) {
                System.out.println("\t" + i + ".Ime: " + autor.getIme());
                System.out.println("\tPrezime: " + autor.getPrezime());
                System.out.println("\tAdresa: " + printAdress(autor.getAdresa()));
                System.out.println(printKontakt(autor.getKontakt()));
            } else {
                System.out.println("\t" + i + ".Anonimni autor");
            }
        }

    }

    private static String printLice(TLice podnosilac) {
        String info = "";
        if (podnosilac instanceof TFizickoLice fizickoLice) {
            info += "\t-Ime: " + fizickoLice.getIme() + "\n\t-Prezime:" + fizickoLice.getPrezime();
            info += "\n\t-Adresa: " + printAdress(podnosilac.getAdresa());
            info += printKontakt(podnosilac.getKontakt());
            return info;
        } else if (podnosilac instanceof TPoslovnoLice poslovnoLice) {
            info += "\t-Poslovno ime: " + poslovnoLice.getPoslovnoIme();
            info += "\n\t-Adresa: " + printAdress(podnosilac.getAdresa());
            info += printKontakt(podnosilac.getKontakt());
            return info;
        } else {
            return "";
        }
    }

    private static void printPopunjavaZavod(ZahtevZaIntelektualnuSvojinu.PopunjavaZavod popunjavaZavod) {
        System.out.println("-- Popunjava zavod --");
        System.out.println("\t - Broj prijave: " + popunjavaZavod.getBrojPrijave());
        System.out.println("\t - Datum podnosenja: " + popunjavaZavod.getDatumPodnosenja().getDay() + "." + popunjavaZavod.getDatumPodnosenja().getMonth() + "." + popunjavaZavod.getDatumPodnosenja().getYear());
        System.out.println("\t - Prilozi uz zahtev - ");
        System.out.println("\t\tPrimer autorskog dela: " + popunjavaZavod.getPriloziUzZahtev().getPrimerAutorskogDela().toString());
        if (popunjavaZavod.getPriloziUzZahtev().getOpisAutorskogDela() != null) {
            System.out.println("\t\t - Opis autorskog dela - ");
            System.out.println("\t\tOpis sadrzaja autorskog dela: " + popunjavaZavod.getPriloziUzZahtev().getOpisAutorskogDela().getOpisSadrzajaAutorskogDela());
            System.out.println("\t\tOpis naslov autorskog dela: " + popunjavaZavod.getPriloziUzZahtev().getOpisAutorskogDela().getNazivAutorskogDela());
        }
    }

    private static String printAdress(Adresa adresa) {
        return adresa.getUlica() + " " + adresa.getBroj() + " " + adresa.getPostanskiBroj() + " " + adresa.getGrad() + " " + adresa.getDrzava();
    }

    private static String printKontakt(Kontakt kontakt) {
        String info = "";
        info += "\n\t-Telefon: " + kontakt.getTelefon();
        info += "\n\t-Email: " + kontakt.getEPosta();
        info += "\n\t-Faks: " + kontakt.getFaks();
        return info;
    }

}
