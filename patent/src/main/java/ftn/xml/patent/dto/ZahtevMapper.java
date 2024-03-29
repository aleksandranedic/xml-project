package ftn.xml.patent.dto;

import ftn.xml.patent.model.*;
import ftn.xml.patent.repository.PatentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.XMLDBException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

@Component
public class ZahtevMapper {

    private final PatentRepository patentRepository;


    @Autowired
    public ZahtevMapper(PatentRepository patentRepository) {
        this.patentRepository = patentRepository;
    }




    public ZahtevZaPriznanjePatenta.Resenje parseResenje(Resenje resenje) throws DatatypeConfigurationException {
        ZahtevZaPriznanjePatenta.Resenje novoResenje = new ZahtevZaPriznanjePatenta.Resenje();
        novoResenje.setStatus(resenje.getStatus());
        novoResenje.setDatum(parseToXMLGregorianCalendar(Timestamp.valueOf(LocalDateTime.now())));
        novoResenje.setIme(resenje.getIme());
        novoResenje.setPrezime(resenje.getPrezime());
        novoResenje.setObrazlozenje(resenje.getObrazlozenje());
        novoResenje.setBrojPrijave(resenje.getBrojPrijave());
        return novoResenje;
    }

    public ZahtevZaPriznanjePatenta parseZahtev(Zahtev zahtev) throws DatatypeConfigurationException, ParseException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ZahtevZaPriznanjePatenta zahtevZaPriznanjePatenta = new ZahtevZaPriznanjePatenta();
        zahtevZaPriznanjePatenta.setInformacijeOUstanovi(getInformacijeOUstanovi());
        zahtevZaPriznanjePatenta.setPopunjavaPodnosioc(getPopunjavaPodnosioc(zahtev));
        zahtevZaPriznanjePatenta.setPopunjavaZavod(getPopunjavaZavod());
        ZahtevZaPriznanjePatenta.PriloziUzZahtev priloziUzZahtev = new ZahtevZaPriznanjePatenta.PriloziUzZahtev();

        if (zahtev.getPrilozi() != null) {
            if (zahtev.getPrilozi().getApstrakt() != null) {
                priloziUzZahtev.setApstrakt(zahtev.getPrilozi().getApstrakt());
            }
            if (zahtev.getPrilozi().getPrvobitnaPrijava() != null) {
                priloziUzZahtev.setPrvaPrijava(zahtev.getPrilozi().getPrvobitnaPrijava());
            }
            if (zahtev.getPrilozi().getRanijePrijave() != null) {
                for (String prijava: zahtev.getPrilozi().getRanijePrijave()) {
                    priloziUzZahtev.getRanijaPrijava().add(prijava);
                }
            }
            if (zahtev.getPrilozi().getIzjavaOSticanjuPrava() != null) {
                priloziUzZahtev.setIzjavaOOsnovuSticanjaPravaNaPodnosenjePrijave(zahtev.getPrilozi().getIzjavaOSticanjuPrava());
            }
            if (zahtev.getPrilozi().getIzjavaPronalazaca() != null) {
                priloziUzZahtev.setIzjavaPronalazacaDaNeZeliDaBudeNaveden(zahtev.getPrilozi().getIzjavaPronalazaca());
            }
            if (zahtev.getPrilozi().getOpis() != null) {
                priloziUzZahtev.setOpisPronalaska(zahtev.getPrilozi().getOpis());
            }
            if (zahtev.getPrilozi().getNacrt() != null) {
                priloziUzZahtev.setNacrtNaKojiSePozivaOpis(zahtev.getPrilozi().getNacrt());
            }
        }

        zahtevZaPriznanjePatenta.setPriloziUzZahtev(priloziUzZahtev);
        return zahtevZaPriznanjePatenta;
    }

    private ZahtevZaPriznanjePatenta.PopunjavaZavod getPopunjavaZavod() throws DatatypeConfigurationException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ZahtevZaPriznanjePatenta.PopunjavaZavod popunjavaZavod = new ZahtevZaPriznanjePatenta.PopunjavaZavod();
        popunjavaZavod.setDatumPrijema(parseToXMLGregorianCalendar(Timestamp.valueOf(LocalDateTime.now())));
        popunjavaZavod.setBrojPrijave(patentRepository.getNextBrojPrijave().substring(2));
        return popunjavaZavod;
    }

    private ZahtevZaPriznanjePatenta.PopunjavaPodnosioc getPopunjavaPodnosioc(Zahtev zahtev) throws DatatypeConfigurationException, ParseException {
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc popunjavaPodnosioc = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc();

        popunjavaPodnosioc.setNazivPatenta(getNazivPatenta(zahtev));
        popunjavaPodnosioc.setPodaciOPunomocniku(getPodaciOPunomocniku(zahtev));
        popunjavaPodnosioc.setPodaciOPronalazacu(getPodaciOPronalazacu(zahtev));
        popunjavaPodnosioc.setPrvobitnaPrijava(getPrvobitnaPrijava(zahtev));
        popunjavaPodnosioc.setRanijePrijave(getRanijePrijave(zahtev));
        popunjavaPodnosioc.setDostavljanje( getDostavljanje(zahtev));
        popunjavaPodnosioc.setPodaciOPodnosiocu(getPodaciOPodnosiocu(zahtev));
        return popunjavaPodnosioc;
    }

    private static ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPodnosiocu getPodaciOPodnosiocu(Zahtev zahtev) {
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPodnosiocu podaciOPodnosiocu = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPodnosiocu();
        Zahtev.Lice podnosilac = zahtev.getPodnosilac();
        if (podnosilac.getInfo().getPrezime() == null) {
            podaciOPodnosiocu.setPodnosioc(getPoslovnoLice(podnosilac));
        } else {
            podaciOPodnosiocu.setPodnosioc(getFizickoLice(podnosilac));
        }
        if (zahtev.podnosilacJePronalazac)
            podaciOPodnosiocu.setPodnosiocJePronalazac(new TEmpty());
        return podaciOPodnosiocu;
    }

    private static ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.Dostavljanje getDostavljanje(Zahtev zahtev) {
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.Dostavljanje dostavljanje = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.Dostavljanje();
        if (zahtev.getAdresaZaDostavljanje() != null) {
            dostavljanje.setAdresa(getAdresa(zahtev.getAdresaZaDostavljanje()));
        }
        dostavljanje.setNacin(zahtev.getNacinDostavljanja());
        return dostavljanje;
    }

    private ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.RanijePrijave getRanijePrijave(Zahtev zahtev) throws DatatypeConfigurationException, ParseException {
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.RanijePrijave ranijePrijave = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.RanijePrijave();

        if (zahtev.getRanijePrijave() == null) {
            return null;
        }

        for (Zahtev.RanijaPrijava ranijaPrijava: zahtev.getRanijePrijave()) {
            ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.RanijePrijave.Prijava prijava = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.RanijePrijave.Prijava();
            prijava.setBrojPrijave(ranijaPrijava.getBrojPrijave());
            prijava.setDvoslovnaOznaka(ranijaPrijava.getDvoslovnaOznaka());
            prijava.setDatumPodnosenja(parseToXMLGregorianCalendar(ranijaPrijava.getDatumPodnosenja()));
            ranijePrijave.getPrijava().add(prijava);
        }
        return ranijePrijave;
    }

    private ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PrvobitnaPrijava getPrvobitnaPrijava(Zahtev zahtev) throws DatatypeConfigurationException, ParseException {
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PrvobitnaPrijava prvobitnaPrijava = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PrvobitnaPrijava();
        if (Objects.equals(zahtev.getPrvobitnaPrijava(), null)) {
            return null;
        }

        prvobitnaPrijava.setTipPrijave(zahtev.getPrvobitnaPrijava().getTipPrijave());
        prvobitnaPrijava.setBrojPrijave(zahtev.getPrvobitnaPrijava().getBrojPrijave());
        prvobitnaPrijava.setDatumPodnosenja(parseToXMLGregorianCalendar(zahtev.getPrvobitnaPrijava().getDatumPodnosenja()));
        return prvobitnaPrijava;
    }

    private XMLGregorianCalendar parseToXMLGregorianCalendar(String dateString) throws DatatypeConfigurationException, ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (dateString != null) {
            Date date = format.parse(dateString);

            return parseToXMLGregorianCalendar(date);
        }
        return null;

    }

    private static XMLGregorianCalendar parseToXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    }

    private static ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPronalazacu getPodaciOPronalazacu(Zahtev zahtev) {
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPronalazacu podaciOPronalazacu = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPronalazacu();

        if (zahtev.getPronalazaci() == null) {
            podaciOPronalazacu.setPronalazacNeZeliDaBudeNaveden(new TEmpty());
        } else {
            for (Zahtev.Lice pronalazac : zahtev.getPronalazaci()) {
                if (Objects.equals(pronalazac.getInfo().getIme(), "")) {
                    return null;
                }
                if (pronalazac.getInfo().getPrezime() == "") {
                    podaciOPronalazacu.getPronalazac().add(getPoslovnoLice(pronalazac));
                } else {
                    podaciOPronalazacu.getPronalazac().add(getFizickoLice(pronalazac));
                }
            }
        }
        return podaciOPronalazacu;
    }

    private static ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku getPodaciOPunomocniku(Zahtev zahtev) {
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku podaciOPunomocniku = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku();
        Zahtev.Punomocnik punomocnik = zahtev.getPunomocnik();
        if (punomocnik == null) {
            return null;
        }
        if (Objects.equals(punomocnik.getInfo().getIme(), "")) {
            return null;
        }

        if (punomocnik.getInfo().getPrezime() == "") {
            podaciOPunomocniku.setPunomocnik(getPoslovnoLice(punomocnik));
        } else {
            podaciOPunomocniku.setPunomocnik(getFizickoLice(punomocnik));
        }
        podaciOPunomocniku.setTipPunomocnika(getTipPunomocnika(punomocnik));
        return podaciOPunomocniku;
    }

    private static ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku.TipPunomocnika getTipPunomocnika(Zahtev.Punomocnik punomocnik) {
        ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku.TipPunomocnika tipPunomocnika = new ZahtevZaPriznanjePatenta.PopunjavaPodnosioc.PodaciOPunomocniku.TipPunomocnika();
        if (punomocnik.zajednickiPredstavnik) {
            tipPunomocnika.setPredstavnik(new TEmpty());
        }
        if (punomocnik.zaZastupanje) {
            tipPunomocnika.setZaZastupanje(new TEmpty());
        }
        if (punomocnik.zaPrijem) {
            tipPunomocnika.setZaPrijem(new TEmpty());
        }
        return tipPunomocnika;
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
        if (Objects.equals(a.getUlica(), "")) {
            return null;
        }

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
