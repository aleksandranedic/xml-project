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
public class PriloziDTO {
    public String Primerak_znaka;
    public String Spisak_robe_i_usluga;
    public String Punomocje;
    public String Opsti_akt;
    public String Dokaz_o_pravu_prvenstva;
    public String Dokaz_o_uplati_takse;
}
