package ftn.xml.zig.dto;

import org.springframework.stereotype.Component;

@Component
public class ZahtevMapper {
    public ZahtevZaPriznanjeZiga parseZahtev(Zahtev zahtev) {
        //TODO: Mapira se dto na entitet
        return new ZahtevZaPriznanjeZiga();
    }
}
