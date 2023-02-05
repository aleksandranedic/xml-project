package ftn.xml.autor.dto;

import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
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

    public ZahtevData(ZahtevZaIntelektualnuSvojinu zahtevZaIntelektualnuSvojinu) {
        try{
            this.status=zahtevZaIntelektualnuSvojinu.getResenje().getStatus();
        }catch (Exception e){
            this.status="";
        }
//        this.datum=zahtevZaIntelektualnuSvojinu.getPopunjavaZavod().getDatumPodnosenja();
        this.datum="12.3.2022.";
        this.brojPrijave=zahtevZaIntelektualnuSvojinu.getPopunjavaZavod().getBrojPrijave();
        this.html="";
    }
}
