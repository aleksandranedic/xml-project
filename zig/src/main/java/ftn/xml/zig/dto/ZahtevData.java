package ftn.xml.zig.dto;

import ftn.xml.zig.model.ZahtevZaPriznanjeZiga;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ZahtevData {
    private String status;
    private String datum;
    private String brojPrijave;
    private String html;

    public ZahtevData(ZahtevZaPriznanjeZiga zahtevZaPriznanjeZiga) {
        try{
            this.status=zahtevZaPriznanjeZiga.getResenje().getStatus();
        }catch (Exception e){
            this.status="";
        }
//        this.datum=zahtevZaIntelektualnuSvojinu.getPopunjavaZavod().getDatumPodnosenja();
        this.datum="12.3.2022.";
        this.brojPrijave=zahtevZaPriznanjeZiga.getBrojPrijaveZiga();
        this.html="";
    }
}