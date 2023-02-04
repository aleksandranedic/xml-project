package ftn.xml.zig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceneTakseDTO {
    public String Osnovna_taksa;
    public ZaKlasaDTO Za_klasa;
    public String Za_graficko_resenje;
    public String Ukupno;
}
