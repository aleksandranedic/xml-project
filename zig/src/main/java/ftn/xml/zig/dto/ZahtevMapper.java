package ftn.xml.zig.dto;

import ftn.xml.zig.model.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class ZahtevMapper {
    public ZahtevZaPriznanjeZiga parseZahtev(Zahtev zahtev) {
        ZahtevZaPriznanjeZiga zahtevZaPriznanjeZiga = new ZahtevZaPriznanjeZiga();
        zahtevZaPriznanjeZiga.setInformacijeOUstanovi(getInformacijeOUstanovi());
        zahtevZaPriznanjeZiga.setPopunjavaPodnosilac(getPopunjavaPodnosioc(zahtev));
       /*

        zahtevZaPriznanjePatenta.setPopunjavaPodnosioc(getPopunjavaPodnosioc(zahtev));
        ZahtevZaPriznanjePatenta.PopunjavaZavod popunjavaZavod = new ZahtevZaPriznanjePatenta.PopunjavaZavod();
        popunjavaZavod.setDatumPrijema(parseToXMLGregorianCalendar(Timestamp.valueOf(LocalDateTime.now())));
        popunjavaZavod.setBrojPrijave(patentRepository.getNextBrojPrijave());
        zahtevZaPriznanjePatenta.setPopunjavaZavod(popunjavaZavod);

        return zahtevZaPriznanjePatenta;
       * */
        return new ZahtevZaPriznanjeZiga();
    }

    private static ZahtevZaPriznanjeZiga.PopunjavaPodnosilac getPopunjavaPodnosioc(Zahtev zahtev) {
        ZahtevZaPriznanjeZiga.PopunjavaPodnosilac popunjavaPodnosilac = new ZahtevZaPriznanjeZiga.PopunjavaPodnosilac();
        PodnosilacDTO podnosilacDTO = zahtev.getPodnosilac();
        popunjavaPodnosilac.setPodnosilac(getPodnosilac(podnosilacDTO));
        if (zahtev.getPunomocnik().getIme() != null) {
            PunomocnikDTO punomocnikDTO = zahtev.getPunomocnik();
            popunjavaPodnosilac.setPunomocnik(getPunomocnik(punomocnikDTO));
        }
        popunjavaPodnosilac.setZig(getZig(zahtev.getZig()));
        return popunjavaPodnosilac;
    }

    private static ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig getZig(ZigDTO zigDTO) {
        ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig zig = new ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig();
        zig.setNaznacenjeBoje(zigDTO.getNaznacenje_boje());
        zig.setOpisZnaka(zigDTO.getOpis_znaka());
        zig.setTransliteracijaZnak(zigDTO.getTransliteracija_znaka());
        zig.setPrevodZnaka(zigDTO.getPrevod_znaka());
        ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig.Vrsta vrsta = new ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig.Vrsta();
        vrsta.setTipA(new ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig.Vrsta.TipA(zigDTO.getVrsta().Tip_a));
        zig.setVrsta(vrsta);
        return zig;
    }
    private static TLice getPodnosilac(PodnosilacDTO podnosilacDTO) {
        if (podnosilacDTO.getPrezime() != null) {
            TFizickoLice fizickoLice = new TFizickoLice();
            fizickoLice.setIme(podnosilacDTO.getIme());
            fizickoLice.setPrezime(podnosilacDTO.getPrezime());
            fizickoLice.setAdresa(getAdresa(podnosilacDTO.getAdresa()));
            fizickoLice.setKontakt(getKontakt(podnosilacDTO.getKontakt()));
            return fizickoLice;
        }

        TPoslovnoLice poslovnoLice = new TPoslovnoLice();
        poslovnoLice.setPoslovnoIme(podnosilacDTO.getIme());
        poslovnoLice.setAdresa(getAdresa(podnosilacDTO.getAdresa()));
        poslovnoLice.setKontakt(getKontakt(podnosilacDTO.getKontakt()));
        return poslovnoLice;
    }

    private static TLice getPunomocnik(PunomocnikDTO punomocnikDTO) {
        if (punomocnikDTO.getPrezime() != null) {
            TFizickoLice fizickoLice = new TFizickoLice();
            fizickoLice.setIme(punomocnikDTO.getIme());
            fizickoLice.setPrezime(punomocnikDTO.getPrezime());
            fizickoLice.setAdresa(getAdresa(punomocnikDTO.getAdresa()));
            fizickoLice.setKontakt(getKontakt(punomocnikDTO.getKontakt()));
            return fizickoLice;
        }

        TPoslovnoLice poslovnoLice = new TPoslovnoLice();
        poslovnoLice.setPoslovnoIme(punomocnikDTO.getIme());
        poslovnoLice.setAdresa(getAdresa(punomocnikDTO.getAdresa()));
        poslovnoLice.setKontakt(getKontakt(punomocnikDTO.getKontakt()));
        return poslovnoLice;
    }

    private static Adresa getAdresa(AdresaDTO adresaDTO) {
        Adresa adresa = new Adresa();
        adresa.setBroj(adresaDTO.getBroj());
        adresa.setDrzava(adresaDTO.getDrzava());
        adresa.setGrad(adresaDTO.getGrad());
        adresa.setUlica(adresaDTO.getUlica());
        adresa.setPostanskiBroj(Integer.parseInt(adresaDTO.getPostanski_broj()));
        return adresa;
    }

    private static Kontakt getKontakt(KontaktDTO kontaktDTO) {
        Kontakt kontakt = new Kontakt();
        kontakt.setEmail(kontaktDTO.getEmail());
        kontakt.setFaks(kontaktDTO.getFaks());
        kontakt.setTelefon(kontaktDTO.getTelefon());
        return kontakt;
    }
    private static ZahtevZaPriznanjeZiga.InformacijeOUstanovi getInformacijeOUstanovi() {
        ZahtevZaPriznanjeZiga.InformacijeOUstanovi informacijeOUstanovi = new ZahtevZaPriznanjeZiga.InformacijeOUstanovi();
        informacijeOUstanovi.setAdresa(getAdresaUstanove());
        informacijeOUstanovi.setNaziv("Zavod za intelektualnu svojinu");
        return informacijeOUstanovi;
    }

    private static Adresa getAdresaUstanove() {
        Adresa adresa = new Adresa();
        adresa.setUlica("Knjeginje Milice");
        adresa.setBroj("5");
        adresa.setPostanskiBroj(11000);
        adresa.setGrad("Beograd");
        adresa.setDrzava("Srbija");
        return adresa;
    }
}
