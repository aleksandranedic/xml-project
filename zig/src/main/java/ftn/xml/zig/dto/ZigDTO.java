package ftn.xml.zig.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZigDTO {
    public VrstaDTO Vrsta;
    public String Naznacenje_boje;
    public String Transliteracija_znaka;
    public String Prevod_znaka;
    public String Opis_znaka;
}
