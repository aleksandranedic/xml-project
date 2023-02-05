package ftn.xml.zig.dto;

import ftn.xml.zig.model.ZahtevZaPriznanjeZiga;
import ftn.xml.zig.service.ZigService;
import ftn.xml.zig.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZahtevDataMapper {
    //TODO: Kopriaj zahtev data mapper
    private final ZigService zigService;

    @Autowired
    public ZahtevDataMapper( ZigService zigService) {
        this.zigService = zigService;
    }

    public ZahtevData convertToZahtevData(ZahtevZaPriznanjeZiga zahtev) {
        ZahtevData zahtevData = new ZahtevData();
        try{
            zahtevData.setStatus(zahtev.getResenje().getStatus());
        }catch (Exception e){
            zahtevData.setStatus("Prilozen");
        }
        zahtevData.setDatum(DateUtils.convertToString(zahtev.getDatumPodnosenja()));
        zahtevData.setBrojPrijave(zahtev.getBrojPrijaveZiga());

        zahtevData.setHtml(zigService.getHtmlString(zahtev.getBrojPrijaveZiga()));
        return zahtevData;
    }

}
