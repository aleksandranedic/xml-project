package ftn.xml.patent.dto;

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
        try{
            zahtevData.setStatus(zahtev.getResenje().getStatus());
        }catch (Exception e){
            zahtevData.setStatus("Prilozen");
        }
        zahtevData.setDatum(DateUtils.convertToString(zahtev.getPopunjavaZavod().getDatumPrijema()));
        zahtevData.setBrojPrijave(zahtev.getPopunjavaZavod().getBrojPrijave());

        zahtevData.setHtml(patentService.getHtmlString(zahtev.getPopunjavaZavod().getBrojPrijave()));
        return zahtevData;
    }

}
