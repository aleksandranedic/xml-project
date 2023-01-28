package ftn.xml.korisnik.dto;

import ftn.xml.korisnik.model.Korisnik;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private String role;

    public UserDTO(Korisnik korisnik) {
        this.name=korisnik.getIme();
        this.surname=korisnik.getPrezime();
        this.email=korisnik.getEmail();
        this.role=korisnik.getUloga();

    }
}
