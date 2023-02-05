package ftn.xml.autor.dto;

import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.service.AutorService;
import ftn.xml.autor.utils.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class ZahtevDataMapper {
    private final AutorService autorService;

    public ZahtevDataMapper(AutorService autorService) {
        this.autorService = autorService;
    }

    public ZahtevData convertToZahtevData(ZahtevZaIntelektualnuSvojinu zahtev) {
        ZahtevData zahtevData = new ZahtevData();
        try{
            zahtevData.setStatus(zahtev.getResenje().getStatus());
        }catch (Exception e){
            zahtevData.setStatus("Prilozen");
        }
        zahtevData.setDatum(DateUtils.convertToString(zahtev.getPopunjavaZavod().getDatumPodnosenja()));
        zahtevData.setBrojPrijave(zahtev.getPopunjavaZavod().getBrojPrijave());

        zahtevData.setHtml(autorService.getHtmlString(zahtev.getPopunjavaZavod().getBrojPrijave()));
        return zahtevData;
    }

}
