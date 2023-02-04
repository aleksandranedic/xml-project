package ftn.xml.zig.utils;

import ftn.xml.zig.model.*;

public class PrettyPrint {
    public static void printZahtev(ZahtevZaPriznanjeZiga zahtev) {
        System.out.println("***** Zahtev za priznanje ziga *****");
        System.out.println("INFORMACIJE O USTANOVI: ");
        System.out.println("\t-Naziv: " + zahtev.getInformacijeOUstanovi().getNaziv());
        System.out.println("\t-Adresa: " + getAdresa(zahtev.getInformacijeOUstanovi().getAdresa()));

        System.out.println("Broj prijave ziga: " + zahtev.getBrojPrijaveZiga());
        System.out.println("Datum podnosenja ziga: " + zahtev.getDatumPodnosenja().toString());
        printPopunjavaPodnosilac(zahtev.getPopunjavaPodnosilac());
        printPriloziUzZahtev(zahtev.getPriloziUzZahtev());
    }

    private static void printPopunjavaPodnosilac(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac popunjavaPodnosioc) {
        System.out.println("POPUNJAVA PODNOSILAC: ");
        int i = 1;
        TLice podnosilac = popunjavaPodnosioc.getPodnosilac();
        System.out.println("Podnosilac " + i + ":");
        System.out.println(getLice(podnosilac));

        System.out.println("Punomocnik:");
        System.out.println(getLice(popunjavaPodnosioc.getPunomocnik()));

        System.out.println("Zig:");
        System.out.println(getZig(popunjavaPodnosioc.getZig()));

        System.out.println("Dodatne informacije:");
        System.out.println(getDodatneInformacije(popunjavaPodnosioc.getDodatneInformacije()));

        System.out.println("Placene takse:");
        System.out.println(getPlceneTakse(popunjavaPodnosioc.getPlaceneTakse()));
    }

    private static void printPriloziUzZahtev(ZahtevZaPriznanjeZiga.PriloziUzZahtev priloziUzZahtev) {
        System.out.println("PRILOZI UZ ZAHTEV: ");
        System.out.println(getPriloziUzZahtev(priloziUzZahtev));
    }

    private static String getAdresa(Adresa adresa) {
        String adr = adresa.getUlica() + " " + adresa.getBroj() + ", " + adresa.getPostanskiBroj() + " " + adresa.getGrad();
        if (adresa.getDrzava() != null) {
            adr += ", " + adresa.getDrzava();
        }
        return adr;
    }
    private static String getLice(TLice lice) {
        String info = "";
        if (lice instanceof TFizickoLice) {
            TFizickoLice fizickoLice = (TFizickoLice) lice;
            info += "\t-Ime: " + fizickoLice.getIme() + "\n\t-Prezime:" + fizickoLice.getPrezime();
        }
        if (lice instanceof TPoslovnoLice) {
            TPoslovnoLice poslovnoLice = (TPoslovnoLice) lice;
            info += "\t-Poslovno ime: " + poslovnoLice.getPoslovnoIme();
        }
        info += "\n\t-Adresa: " + getAdresa(lice.getAdresa());
        info += "\n\t-Telefon: " + lice.getKontakt().getTelefon();
        info += "\n\t-Email: " + lice.getKontakt().getEmail();
        info += "\n\t-Faks: " + lice.getKontakt().getFaks();
        return info;
    }

    private static String getZig(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig zig) {
        String info = "";
        info += "\t-Tip A: " + getTipA(zig.getVrsta().getTipA());
        info += "\n\t-Tip B: " + getTipB(zig.getVrsta().getTipB());
        info += "\n\t-Naznacenje boje: " + zig.getNaznacenjeBoje();
        info += "\n\t-Transliteracija znaka: " + zig.getTransliteracijaZnak();
        info += "\n\t-Prevod znaka: " + zig.getPrevodZnaka();
        return info;
    }

    private static String getTipA(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig.Vrsta.TipA tipA) {
        if (tipA.getIndividualniZig() != null)
            return "Individualni zig";
        else if (tipA.getKolektivniZig() != null)
            return "Kolektivni zig";
        return "Zig garancije";
    }

    private static String getTipB(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig.Vrsta.TipB tipB) {
        if (tipB.getGrafickiZnak() != null)
            return "Graficki znak";
        else if (tipB.getKombinovaniZnak() != null)
            return "Kombinovani znak";
        else if (tipB.getTrodimenzionalniZnak() != null)
            return "Trodimenzionalni znak";
        else if (tipB.getDrugaVrsta() != null)
            return tipB.getDrugaVrsta();
        return "Verbalni znak";
    }

    private static String getDodatneInformacije(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.DodatneInformacije info) {
        return "\t-Klasa robe i usluga: " + getKlasa(info.getKlasaRobeIUslaga()) + "\n\t-Zatrazeno pravo prvensta i osnov: " + info.getZatrazenoPravoPrvenstaIOsnov();
    }

    private static String getKlasa(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.DodatneInformacije.KlasaRobeIUslaga klasaRobeIUslaga) {
        String info = "";
        for (Integer klasa : klasaRobeIUslaga.getKlasa()) {
            info = info.concat(klasa + ", ");
        }
        return info.substring(0, info.length() - 2);
    }
    private static String getPlceneTakse(ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.PlaceneTakse placeneTakse) {
        String info = "";
        info += "\t-Osnovna taksa: " + placeneTakse.getOsnovnaTaksa();
        info += "\n\t-Za " + placeneTakse.getZaKlasa().getNazivKlase() + "klasa: " + placeneTakse.getZaKlasa().getSuma();
        info += "\n\t-Za graficko resenje: " + placeneTakse.getZaGrafickoResenje();
        info += "\n\t-UKUPNO: " + placeneTakse.getUkupno();
        return info;
    }

    private static String getPriloziUzZahtev(ZahtevZaPriznanjeZiga.PriloziUzZahtev prilozi) {
        System.out.println("Prilozi uz zahtev: ");
        String info = "";
        if (prilozi.getPrimerakZnaka() != null)
            info += "\t-Primerak znaka\n";
        if (prilozi.getPrimerakZnaka() != null)
            info += "\t-Primerak znaka\n";
        if (prilozi.getSpisakRobeIUsluga() != null)
            info += "\t-Spisak robe i usluga\n";
        if (prilozi.getPunomocje() != null )
            info += "\t-Punomocje\n";
        if (prilozi.getOpstiAkt() != null)
            info += "\t-Opsti akt\n";
        if (prilozi.getDokazOPravuPrvenstva() != null)
            info += "\t-Dokaz o pravu prvenstva\n";
        if (prilozi.getDokazOUplatiTakse() != null)
            info += "\t-Dokaz o uplati takse";
        return info;
    }
}
