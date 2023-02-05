package ftn.xml.patent.dto;


import lombok.AllArgsConstructor;
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
    private List<Prilog> prilozi = new ArrayList<>();

    public void addPrilog(String putanja, String naziv) {
        if (putanja != null) {
            prilozi.add( new Prilog(putanja, naziv));
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Prilog {
        private String putanja;
        private String naslov;
    }

}
