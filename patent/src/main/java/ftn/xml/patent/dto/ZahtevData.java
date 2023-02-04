package ftn.xml.patent.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
public class ZahtevData {

    private String status;
    private String datum;
    private String brojPrijave;
    private String html;
    private List<String> prilozi;


}
