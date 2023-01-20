package ftn.xml.korisnik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
    private String ime;
    private String prezime;
    private String uloga;
    private String email;
    private String sifra;
    private String ulica;
    private int broj;
    private int postanskiBroj;
    private String grad;
    private String drzava;
}
