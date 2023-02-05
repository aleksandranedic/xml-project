package ftn.xml.patent.utils;

import ftn.xml.patent.model.*;

import java.util.List;

public class PrettyPrint {

    public static void print(ZahtevZaPriznanjePatenta zahtev) {
        print(zahtev.getInformacijeOUstanovi());
        print(zahtev.getPopunjavaZavod());
        print(zahtev.getPopunjavaPodnosioc());
    }

    private static void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc popunjavaPodnosioc) {
        System.out.println("\n------------------------------");
        System.out.println("Popunjava podnosioc:");
        System.out.println("------------------------------");

        print(popunjavaPodnosioc.getNazivPatenta());
        //print(popunjavaPodnosioc.getPodaciOPodnosiocu());
        print(popunjavaPodnosioc.getPodaciOPronalazacu());
        print(popunjavaPodnosioc.getPodaciOPunomocniku());
        print(popunjavaPodnosioc.getDostavljanje());
        print(popunjavaPodnosioc.getPrvobitnaPrijava());
        print(popunjavaPodnosioc.getRanijePrijave());
    }

    private static void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.RanijePrijave ranijePrijave) {
        if (ranijePrijave.getPrijava().size() > 0) {
            System.out.println("Ranije prijave: ");
            for (ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.RanijePrijave.Prijava prijava : ranijePrijave.getPrijava()) {
                System.out.println("\tBroj prijave: " + prijava.getBrojPrijave());
                System.out.println("\tDvoslovna oznaka: " + prijava.getDvoslovnaOznaka());
                System.out.println("\tDatum podnosenja: " + prijava.getDatumPodnosenja());
            }
        }
    }

    private static void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PrvobitnaPrijava prvobitnaPrijava) {
        System.out.println("Prvobitna prijava: ");
        System.out.println("\tBroj prijave: " + prvobitnaPrijava.getBrojPrijave());
        System.out.println("\tTip prijave: " + prvobitnaPrijava.getTipPrijave());
        System.out.println("\tDatum podnosenja: " + prvobitnaPrijava.getDatumPodnosenja());

    }

    private static void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.Dostavljanje dostavljanje) {
        System.out.println("Dostavljanje: ");
        System.out.println("\tNacin: " + dostavljanje.getNacin());
        print(dostavljanje.getAdresa());
    }

    private static void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku podaciOPunomocniku) {
        System.out.println("\nPunomocnik: ");

        System.out.print("\tTip:");
        if (podaciOPunomocniku.getTipPunomocnika().getPredstavnik() != null) {
            System.out.print(" predstavnik");
        }
        if (podaciOPunomocniku.getTipPunomocnika().getZaPrijem() != null) {
            System.out.print(" za_prijem");
        }
        if (podaciOPunomocniku.getTipPunomocnika().getZaZastupanje() != null) {
            System.out.print(" za_zastupanje");
        }
        System.out.print("\n");

        print(podaciOPunomocniku.getPunomocnik());
    }

    private static void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPronalazacu podaciOPronalazacu) {
        System.out.println("\nPronalazac: ");
        //print(podaciOPronalazacu.getPronalazac());
    }

    private static void print(List<ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPodnosiocu> podaciOPodnosiocima) {
        System.out.println("\nPodnosioci: ");
        for (ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPodnosiocu podaci : podaciOPodnosiocima) {
            print(podaci.getPodnosioc());
        }
    }

    private static void print(TLice lice) {


        if (lice instanceof TFizickoLice) {
            TFizickoLice fizickoLice = (TFizickoLice) lice;
            System.out.println(fizickoLice.getIme() + " " + fizickoLice.getPrezime());
        }
        if (lice instanceof TPoslovnoLice) {
            TPoslovnoLice poslovnoLice = (TPoslovnoLice) lice;
            System.out.println(poslovnoLice.getPoslovnoIme());
        }

        print(lice.getAdresa());
        print(lice.getKontakt());
    }

    private static void print(ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.NazivPatenta nazivPatenta) {
        System.out.println("Naziv patenta:");
        System.out.println("\tSrpski: " + nazivPatenta.getNazivNaSrpskom());
        System.out.println("\tEngleski: " + nazivPatenta.getNazivNaEngleskom());
    }

    private static void print(ZahtevZaPriznanjePatenta.PopunjavaZavod popunjavaZavod) {
        System.out.println("\n------------------------------");
        System.out.println("Popunjava zavod:");
        System.out.println("------------------------------");
        System.out.println("\tBroj prijave: " + popunjavaZavod.getBrojPrijave());
        System.out.println("\tDatum prijema: " + popunjavaZavod.getDatumPrijema());
        System.out.println("\tPriznati datum podnosenja: " + popunjavaZavod.getPriznatiDatumPodnosenja());
    }

    private static void print(ZahtevZaPriznanjePatenta.InformacijeOUstanovi info) {
        System.out.println("\n------------------------------");
        System.out.println("Ustanova:");
        System.out.println("------------------------------");
        System.out.println("Naziv: " + info.getNaziv());
        print(info.getAdresa());

    }

    private static void print(Adresa adresa) {

        System.out.println("Adresa: ");
        System.out.println("\t" + adresa.getDrzava() + "\n\t"
                + adresa.getUlica() + " " + adresa.getBroj() + "\n\t" +
                adresa.getPostanskiBroj() + " " + adresa.getGrad());
    }

    private static void print(Kontakt kontakt) {
        System.out.println("Kontakt: ");
        System.out.println("\t" +
                kontakt.getTelefon() + "\n\t" +
                kontakt.getEPosta() + "\n\t" +
                kontakt.getFaks() + "\n\t"
        );
    }
}
