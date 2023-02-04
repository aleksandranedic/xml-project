package ftn.xml.zig.dto;

import ftn.xml.zig.model.*;
import ftn.xml.zig.repository.ZigRepository;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.XMLDBException;

import java.sql.Timestamp;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZahtevMapper {

    @Autowired
    ZigRepository zigRepository;

    public ZahtevZaPriznanjeZiga parseZahtev(Zahtev zahtev) throws DatatypeConfigurationException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ZahtevZaPriznanjeZiga zahtevZaPriznanjeZiga = new ZahtevZaPriznanjeZiga();
        zahtevZaPriznanjeZiga.setInformacijeOUstanovi(getInformacijeOUstanovi());
        zahtevZaPriznanjeZiga.setPopunjavaPodnosilac(getPopunjavaPodnosioc(zahtev));
        zahtevZaPriznanjeZiga.setPriloziUzZahtev(getPriloziUzZahtev(zahtev.getPrilozi()));
        zahtevZaPriznanjeZiga.setDatumPodnosenja(parseToXMLGregorianCalendar(Timestamp.valueOf(LocalDateTime.now())));
        String brojPrijave = zigRepository.getNextBrojPrijave();
        zahtevZaPriznanjeZiga.setBrojPrijaveZiga(brojPrijave);
        zahtevZaPriznanjeZiga.setQRKod(generateQR(brojPrijave));
        return zahtevZaPriznanjeZiga;
    }

    private final Path qrLocation = Paths.get("src/main/resources/data/qr/");
    private final String showZahtevHTMLEndPoint = "http://localhost:8080/zig/html/";
    public String generateQR(String brojPrijaveZiga){
        String httpBrojPrijaveZiga = brojPrijaveZiga.replace('/', '-');
        String fileNameBrojPrijaveZiga = brojPrijaveZiga.replace('/', '_').concat("_").concat("QR");
        String filename = null;
        try {
            filename = fileNameBrojPrijaveZiga + ".jpg";
            File file = new File(qrLocation + filename);
            ByteArrayOutputStream stream = QRCode
                    .from(showZahtevHTMLEndPoint + httpBrojPrijaveZiga)
                    .withSize(250, 250)
                    .stream();
            FileOutputStream fos = new FileOutputStream(file);
            stream.writeTo(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return filename;
        }
    }

    private XMLGregorianCalendar parseToXMLGregorianCalendar(String dateString) throws DatatypeConfigurationException, ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateString);

        return parseToXMLGregorianCalendar(date);
    }

    private static XMLGregorianCalendar parseToXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    }

    private static ZahtevZaPriznanjeZiga.PriloziUzZahtev getPriloziUzZahtev(PriloziDTO priloziDTO) {
        ZahtevZaPriznanjeZiga.PriloziUzZahtev priloziUzZahtev = new ZahtevZaPriznanjeZiga.PriloziUzZahtev();
        priloziUzZahtev.setDokazOPravuPrvenstva(priloziDTO.getDokaz_o_pravu_prvenstva());
        priloziUzZahtev.setOpstiAkt(priloziDTO.getOpsti_akt());
        priloziUzZahtev.setPunomocje(priloziDTO.getPunomocje());
        priloziUzZahtev.setPrimerakZnaka(priloziDTO.getPrimerak_znaka());
        priloziUzZahtev.setSpisakRobeIUsluga(priloziDTO.getSpisak_robe_i_usluga());
        priloziUzZahtev.setDokazOUplatiTakse(priloziDTO.getDokaz_o_uplati_takse());
        return priloziUzZahtev;
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
        popunjavaPodnosilac.setDodatneInformacije(getDodatneInformacije(zahtev.getDodatne_informacije()));
        popunjavaPodnosilac.setPlaceneTakse(getPlaceneTakse(zahtev.getPlacene_takse()));
        return popunjavaPodnosilac;
    }

    private static ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.PlaceneTakse getPlaceneTakse(PlaceneTakseDTO placeneTakseDTO) {
        ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.PlaceneTakse placeneTakse = new ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.PlaceneTakse();
        placeneTakse.setUkupno(BigInteger.valueOf(Long.parseLong(placeneTakseDTO.getUkupno())));
        placeneTakse.setOsnovnaTaksa(BigInteger.valueOf(Long.parseLong(placeneTakseDTO.getOsnovna_taksa())));
        placeneTakse.setZaGrafickoResenje(BigInteger.valueOf(Long.parseLong(placeneTakseDTO.getZa_graficko_resenje())));
        ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.PlaceneTakse.ZaKlasa zaKlasa = new ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.PlaceneTakse.ZaKlasa();
        zaKlasa.setSuma(BigInteger.valueOf(Long.parseLong(placeneTakseDTO.getZa_klasa().getSuma())));
        zaKlasa.setNazivKlase(placeneTakseDTO.getZa_klasa().getNaziv_klase());
        placeneTakse.setZaKlasa(zaKlasa);
        return placeneTakse;
    }
    private static ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.DodatneInformacije getDodatneInformacije(DodatneInformacijeDTO dodatneInformacijeDTO) {
        ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.DodatneInformacije dodatneInformacije = new ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.DodatneInformacije();
        dodatneInformacije.setZatrazenoPravoPrvenstaIOsnov(dodatneInformacijeDTO.getZatrazenoPravoPrvenstaIOsnov());

        ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.DodatneInformacije.KlasaRobeIUslaga klasaRobeIUslaga = new ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.DodatneInformacije.KlasaRobeIUslaga();
        List<Integer> klase = new ArrayList<>();
        for (klasaRobeIUslaga kl : dodatneInformacijeDTO.getKlasa()) {
            klase.add(Integer.parseInt(kl.getKlasaRobeIUslaga()));
        }
        klasaRobeIUslaga.setKlasa(klase);
        dodatneInformacije.setKlasaRobeIUslaga(klasaRobeIUslaga);
        return dodatneInformacije;
    }
    private static ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig getZig(ZigDTO zigDTO) {
        ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig zig = new ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig();
        zig.setNaznacenjeBoje(zigDTO.getNaznacenje_boje());
        zig.setOpisZnaka(zigDTO.getOpis_znaka());
        zig.setTransliteracijaZnak(zigDTO.getTransliteracija_znaka());
        zig.setPrevodZnaka(zigDTO.getPrevod_znaka());
        ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig.Vrsta vrsta = new ZahtevZaPriznanjeZiga.PopunjavaPodnosilac.Zig.Vrsta();
        vrsta.setTipA(zigDTO.getVrsta().getTip_a());
        vrsta.setTipB(zigDTO.getVrsta().getTip_b());
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
