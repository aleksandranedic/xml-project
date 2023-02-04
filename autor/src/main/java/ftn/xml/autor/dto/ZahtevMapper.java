package ftn.xml.autor.dto;

import ftn.xml.autor.model.*;
import ftn.xml.autor.repository.AutorRepository;
import org.apache.commons.lang3.NotImplementedException;
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

@Component
public class ZahtevMapper {


    @Autowired
    private AutorRepository autorRepository;

    public ZahtevZaIntelektualnuSvojinu parseZahtev(Zahtev zahtev) throws DatatypeConfigurationException, ParseException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ZahtevZaIntelektualnuSvojinu zahtevZaIntelektualnuSvojinu = new ZahtevZaIntelektualnuSvojinu();
        zahtevZaIntelektualnuSvojinu.setPopunjavaPodnosilac(getPopunjavaPodnosioc(zahtev));
        zahtevZaIntelektualnuSvojinu.setPriloziUzZahtev(getPrilozi(zahtev));
        zahtevZaIntelektualnuSvojinu.setResenje(null);
        zahtevZaIntelektualnuSvojinu.setInformacijaOUstanovi(getInformacijeOUstanovi());
        zahtevZaIntelektualnuSvojinu.setPopunjavaZavod(getPopunjavaZavod(zahtev));
        return zahtevZaIntelektualnuSvojinu;
    }

    private ZahtevZaIntelektualnuSvojinu.PopunjavaZavod getPopunjavaZavod(Zahtev zahtev) throws DatatypeConfigurationException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ZahtevZaIntelektualnuSvojinu.PopunjavaZavod popunjavaZavod = new ZahtevZaIntelektualnuSvojinu.PopunjavaZavod();
        popunjavaZavod.setDatumPodnosenja(parseToXMLGregorianCalendar(Timestamp.valueOf(LocalDateTime.now())));
        popunjavaZavod.setBrojPrijave(autorRepository.getNextBrojPrijave());
        return popunjavaZavod;
    }

    private ZahtevZaIntelektualnuSvojinu.PriloziUzZahtev getPrilozi(Zahtev zahtev) {
        ZahtevZaIntelektualnuSvojinu.PriloziUzZahtev priloziUzZahtev = new ZahtevZaIntelektualnuSvojinu.PriloziUzZahtev();
        priloziUzZahtev.setPunomocje(zahtev.getPrilozi().getPunomocje());
        priloziUzZahtev.setDokazOUplatiTakse(zahtev.getPrilozi().getUplataTakse());
        priloziUzZahtev.setOpisAutorskogDela(zahtev.getPrilozi().getOpisDela());
        priloziUzZahtev.setPrimerAutorskogDela(zahtev.getPrilozi().getPrimerDela());
        priloziUzZahtev.setIzjavaOZajednickomPredstavniku(zahtev.getPrilozi().getZajednickiPredstavnik());
        priloziUzZahtev.setIzjavaOPravnoOsnovuZaPodnosenjePrijave(zahtev.getPrilozi().getPravniOsnov());
        return priloziUzZahtev;
    }


    private ZahtevZaIntelektualnuSvojinu.PopunjavaPodnosilac getPopunjavaPodnosioc(Zahtev zahtev) throws DatatypeConfigurationException, ParseException {
        ZahtevZaIntelektualnuSvojinu.PopunjavaPodnosilac popunjavaPodnosioc = new ZahtevZaIntelektualnuSvojinu.PopunjavaPodnosilac();
        popunjavaPodnosioc.setPodnosilac(getPodnosilac(zahtev));
        popunjavaPodnosioc.setAutori(getAutors(zahtev));
        popunjavaPodnosioc.setPunomocnik(getPunomocnik(zahtev));
        popunjavaPodnosioc.setAutorskoDelo(getAutorskoDelo(zahtev));
        return popunjavaPodnosioc;
    }


    private AutorskoDelo getAutorskoDelo(Zahtev zahtev) {
        AutorskoDelo autorskoDelo = new AutorskoDelo();
        autorskoDelo.setNaslov(zahtev.getAutorskoDelo().getNaslov());
        autorskoDelo.setForma(zahtev.getAutorskoDelo().getForma());
        autorskoDelo.setNacinKoriscenja(zahtev.getAutorskoDelo().getNacinKoriscenja());
        if (zahtev.getAutorskoDelo().isRadniOdnos()) {
            autorskoDelo.setStvorenoURadnomOdnosu(new TEmpty());
        } else {
            autorskoDelo.setStvorenoURadnomOdnosu(null);
        }
        if (zahtev.getAutorskoDelo().getVrstaDela().equals("")) {
            if (zahtev.getAutorskoDelo().getDrugaVrsta().equals("")) {
                autorskoDelo.setVrsta(null);
            } else {
                autorskoDelo.setVrsta(zahtev.getAutorskoDelo().getDrugaVrsta());
            }
        } else {
            autorskoDelo.setVrsta(zahtev.getAutorskoDelo().getVrstaDela());
        }
        autorskoDelo.setPodaciONaslovuAutorskogDela(getZasnovanotDelo(zahtev.getAutorskoDelo()));
        return autorskoDelo;

    }

    private TZasnovanoDelo getZasnovanotDelo(Zahtev.AutorskoDeloDTO autorskoDelo) {
        TZasnovanoDelo tZasnovanoDelo = new TZasnovanoDelo();
        tZasnovanoDelo.setImeAutora(autorskoDelo.getImeAutoraPrerade());
        tZasnovanoDelo.setNaslovAutorskogDela(autorskoDelo.getNaslovPrerade());
        return tZasnovanoDelo;
    }

    private TLice getPunomocnik(Zahtev zahtev) {
        Zahtev.Lice punomocnik = zahtev.getPunomocnik();
        if (punomocnik.getInfo() == null) {
            return null;
        }
        if (punomocnik.getInfo().getPrezime() == null) {
            return getPoslovnoLice(punomocnik);
        } else {
            return getFizickoLice(punomocnik);
        }
    }

    private ZahtevZaIntelektualnuSvojinu.PopunjavaPodnosilac.Autori getAutors(Zahtev zahtev) throws DatatypeConfigurationException, ParseException {
        ZahtevZaIntelektualnuSvojinu.PopunjavaPodnosilac.Autori autori = new ZahtevZaIntelektualnuSvojinu.PopunjavaPodnosilac.Autori();
        if (zahtev.isJeAutor()) {
            autori.getAnonimanOrAutor().add(getAutorKaoPodnosilac(zahtev.getPodnosilac()));
        }
        for (Zahtev.Autor autor : zahtev.getAutori()) {
            autori.getAnonimanOrAutor().add(getAutorZaZahtevZaIntelektualnuSvojinu(autor));
        }
        for (int i = 0; i < zahtev.getBrojAnonimnihAutora(); i++) {
            autori.getAnonimanOrAutor().add(new TEmpty());
        }
        return autori;
    }

    private Object getAutorZaZahtevZaIntelektualnuSvojinu(Zahtev.Autor autor) throws DatatypeConfigurationException, ParseException {
        if (autor.isAnoniman()) {
            return new TEmpty();
        } else {
            TAutor autorZaZahtev = new TAutor();
            autorZaZahtev.setIme(autor.getInfo().getIme());
            autorZaZahtev.setPrezime(autor.getInfo().getPrezime());
            autorZaZahtev.setDrzavljanstvo(autor.getInfo().getDrzavljanstvo());
            autorZaZahtev.setAdresa(getAdresa(autor.getAdresa()));
            autorZaZahtev.setKontakt(getKontakt(autor.getKontakt()));
            autorZaZahtev.setGodinaSmrti(parseToXMLGregorianCalendar(autor.getGodinaSmrti()));
            autorZaZahtev.setZnakAutora(autor.getPseudonim());
            return autorZaZahtev;
        }
    }

    private TAutor getAutorKaoPodnosilac(Zahtev.Lice podnosilac) {
        TAutor autor = new TAutor();
        Adresa adresa = getAdresa(podnosilac.getAdresa());
        autor.setAdresa(adresa);
        autor.setKontakt(getKontakt(podnosilac.getKontakt()));
        autor.setIme(podnosilac.getInfo().getIme());
        autor.setPrezime(podnosilac.getInfo().getPrezime());
        autor.setDrzavljanstvo(podnosilac.getInfo().getDrzavljanstvo());
        autor.setZnakAutora(podnosilac.getPseudonim());
        autor.setGodinaSmrti(null);
        return autor;
    }

    private TLice getPodnosilac(Zahtev zahtev) {
        Zahtev.Lice podnosilac = zahtev.getPodnosilac();
        if (podnosilac.getInfo().getPrezime() == null) {
            return getPoslovnoLice(podnosilac);
        } else {
            return getFizickoLice(podnosilac);
        }
    }

    private XMLGregorianCalendar parseToXMLGregorianCalendar(String dateString) throws DatatypeConfigurationException, ParseException {
        DateFormat format = new SimpleDateFormat("yyyy");
        Date date = format.parse(dateString);

        return parseToXMLGregorianCalendar(date);
    }

    private static XMLGregorianCalendar parseToXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
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
        if (a.getUlica().equals("")) {
            return null;
        }

        Adresa adresa = new Adresa();
        adresa.setDrzava(a.getDrzava());
        adresa.setGrad(a.getGrad());
        adresa.setBroj(BigInteger.valueOf(Integer.parseInt(a.getBroj())));
        adresa.setPostanskiBroj(BigInteger.valueOf(Long.parseLong(a.getPostanskiBroj())));
        adresa.setUlica(a.getUlica());
        return adresa;
    }

    private static ZahtevZaIntelektualnuSvojinu.InformacijaOUstanovi getInformacijeOUstanovi() {
        ZahtevZaIntelektualnuSvojinu.InformacijaOUstanovi informacijeOUstanovi = new ZahtevZaIntelektualnuSvojinu.InformacijaOUstanovi();
        informacijeOUstanovi.setAdresa(getAdresaUstanove());
        informacijeOUstanovi.setNaziv("Zavod za intelektualnu svojinu");
        return informacijeOUstanovi;
    }

    private static Adresa getAdresaUstanove() {
        Adresa adresa = new Adresa();
        adresa.setUlica("Knjeginje Ljubice");
        adresa.setBroj(BigInteger.valueOf(5));
        adresa.setPostanskiBroj(BigInteger.valueOf(11000));
        adresa.setGrad("Beograd");
        adresa.setDrzava("Srbija");
        return adresa;
    }

}
