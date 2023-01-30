package ftn.xml.patent.dto;

import ftn.xml.patent.model.*;

import java.math.BigInteger;

public class ZahtevMapper {


    public ZahtevZaPriznanjePatenta parseZahtev(Zahtev zahtev) {
        ZahtevZaPriznanjePatenta zahtevZaPriznanjePatenta = new ZahtevZaPriznanjePatenta();
        zahtevZaPriznanjePatenta.setInformacijeOUstanovi(getInformacijeOUstanovi());
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc popunjavaPodnosioc = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc();

        popunjavaPodnosioc.setNazivPatenta(getNazivPatenta(zahtev));
        popunjavaPodnosioc.setPodaciOPunomocniku(getPodaciOPunomocniku(zahtev));
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPronalazacu podaciOPronalazacu = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPronalazacu();
        //TODO: Menjaj pronalazaca moze biti vise, pronalazac ne zeli da bude naveden i na beku i na frontu


        for (Zahtev.Pronalazac pronalazac : zahtev.getPronalazaci()) {
            if (pronalazac.getInfo().getPrezime() == null) {
                podaciOPronalazacu.setPronalazac(getPoslovnoLice(pronalazac));
            } else {
                podaciOPronalazacu.setPronalazac(getFizickoLice(pronalazac));
            }
        }
        popunjavaPodnosioc.setPodaciOPronalazacu(podaciOPronalazacu);

        return zahtevZaPriznanjePatenta;
    }

    private static ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku getPodaciOPunomocniku(Zahtev zahtev) {
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku podaciOPunomocniku = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku();

        Zahtev.Punomocnik punomocnik = zahtev.getPunomocnik();
        if (punomocnik.getInfo().getPrezime() == null) {
            podaciOPunomocniku.setPunomocnik(getPoslovnoLice(punomocnik));
        } else {
            podaciOPunomocniku.setPunomocnik(getFizickoLice(punomocnik));
        }



        return podaciOPunomocniku;
    }

    private static TFizickoLice getFizickoLice(Zahtev.Lice l) {
        TFizickoLice lice = new TFizickoLice();
        lice.setIme(l.getInfo().getIme());
        lice.setPrezime(l.getInfo().getPrezime());
        lice.setDrzavljanstvo(l.getInfo().getDrzavljanstvo());
        lice.setKontakt(getKontakt(l.getKontakt()));
        lice.setAdresa(getAdresa(l.getAdresa()));
        return lice;
    }

    private static TPoslovnoLice getPoslovnoLice(Zahtev.Lice l) {
        TPoslovnoLice lice = new TPoslovnoLice();
        lice.setPoslovnoIme(l.getInfo().getIme());
        lice.setAdresa(getAdresa(l.getAdresa()));
        lice.setKontakt(getKontakt(l.getKontakt()));
        return lice;
    }

    private static Kontakt getKontakt(Zahtev.Kontakt k) {
        Kontakt kontakt = new Kontakt();
        kontakt.setFaks(k.getFaks());
        kontakt.setTelefon(k.getTelefon());
        kontakt.setEPosta(k.getEposta());
        return kontakt;
    }

    private static Adresa getAdresa(Zahtev.Adresa a) {
        Adresa adresa = new Adresa();
        adresa.setDrzava(a.getDrzava());
        adresa.setGrad(a.getGrad());
        adresa.setBroj(a.getBroj());
        adresa.setPostanskiBroj(BigInteger.valueOf(Long.parseLong(a.getPostanskiBroj())));
        adresa.setUlica(a.getUlica());
        return adresa;
    }

    private static ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.NazivPatenta getNazivPatenta(Zahtev zahtev) {
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.NazivPatenta nazivPatenta = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.NazivPatenta();
        nazivPatenta.setNazivNaSrpskom(zahtev.getNazivNaSrpskom());
        nazivPatenta.setNazivNaEngleskom(zahtev.getNazivNaEngleskom());
        return nazivPatenta;
    }

    private static ZahtevZaPriznanjePatenta.InformacijeOUstanovi getInformacijeOUstanovi() {
        ZahtevZaPriznanjePatenta.InformacijeOUstanovi informacijeOUstanovi = new ZahtevZaPriznanjePatenta.InformacijeOUstanovi();
        informacijeOUstanovi.setAdresa(getAdresaUstanove());
        informacijeOUstanovi.setNaziv("Zavod za intelektualnu svojinu");
        return informacijeOUstanovi;
    }

    private static Adresa getAdresaUstanove() {
        Adresa adresa = new Adresa();
        adresa.setUlica("Knjeginje Milice");
        adresa.setBroj("5");
        adresa.setPostanskiBroj(BigInteger.valueOf(11000));
        adresa.setGrad("Beograd");
        adresa.setDrzava("Srbija");
        return adresa;
    }
}
