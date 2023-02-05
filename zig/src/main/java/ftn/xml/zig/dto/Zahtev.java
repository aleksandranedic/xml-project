package ftn.xml.zig.dto;

import ftn.xml.zig.model.ZahtevZaPriznanjeZiga;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zahtev {
    public PodnosilacDTO Podnosilac;
    public PunomocnikDTO Punomocnik;
    public ZigDTO Zig;
    public DodatneInformacijeDTO Dodatne_informacije;
    public PlaceneTakseDTO Placene_takse;
    public PriloziDTO Prilozi;

}
