package ftn.xml.patent.dto;

import java.util.*;

import ftn.xml.patent.model.ZahtevZaPriznanjePatenta;
import ftn.xml.patent.service.PatentService;
import ftn.xml.patent.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZahtevDataMapper {
    //TODO: Kopriaj zahtev data mapper
    private final PatentService patentService;

    @Autowired
    public ZahtevDataMapper(PatentService patentService) {
        this.patentService = patentService;
    }

    public ZahtevData convertToZahtevData(ZahtevZaPriznanjePatenta zahtev) {
        ZahtevData zahtevData = new ZahtevData();
        try {
            zahtevData.setStatus(zahtev.getResenje().getStatus());
        } catch (Exception e) {
            zahtevData.setStatus("Prilozen");
        }
        zahtevData.setDatum(DateUtils.convertToString(zahtev.getPopunjavaZavod().getDatumPrijema()));
        zahtevData.setBrojPrijave(zahtev.getPopunjavaZavod().getBrojPrijave());
        zahtevData.setHtml(patentService.getHtmlString(zahtev.getPopunjavaZavod().getBrojPrijave()));
        addPrilozi(zahtev, zahtevData);
        return zahtevData;
    }

    private static ZahtevData addPrilozi(ZahtevZaPriznanjePatenta zahtev, ZahtevData zahtevData) {
        ZahtevZaPriznanjePatenta.PriloziUzZahtev priloziUzZahtev = zahtev.getPriloziUzZahtev();

        if (priloziUzZahtev != null) {
        zahtevData.addPrilog(priloziUzZahtev.getApstrakt(), "Apstrakt");
        zahtevData.addPrilog(priloziUzZahtev.getPrvaPrijava(), "Prvobitna prijava");
        zahtevData.addPrilog(priloziUzZahtev.getOpisPronalaska(), "Opis pronalaska");
        zahtevData.addPrilog(priloziUzZahtev.getIzjavaOOsnovuSticanjaPravaNaPodnosenjePrijave(), "Izjava o sticanju prava na podnosenje prijave");
        zahtevData.addPrilog(priloziUzZahtev.getNacrtNaKojiSePozivaOpis(), "Nacrt na koji se poziva opis");
        zahtevData.addPrilog(priloziUzZahtev.getIzjavaPronalazacaDaNeZeliDaBudeNaveden(), "Izjava pronalazaca da ne zeli da bude naveden");

        if (priloziUzZahtev.getPatentniZahteviZaZastituPronalaska() != null) {
            for (String s : priloziUzZahtev.getPatentniZahteviZaZastituPronalaska()) {
                zahtevData.addPrilog(s, "Patentni zahtev za zastitu pronalaska");
            }
        }
        if (priloziUzZahtev.getRanijaPrijava() != null) {
            for (String s : priloziUzZahtev.getRanijaPrijava()) {
                zahtevData.addPrilog(s, "Ranija prijava");
            }
        }}
        return zahtevData;
    }


}
