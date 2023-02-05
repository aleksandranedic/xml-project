package ftn.xml.korisnik.service;

import ftn.xml.korisnik.dto.RegistrationDTO;
import ftn.xml.korisnik.dto.UserDTO;
import ftn.xml.korisnik.model.Korisnik;
import ftn.xml.korisnik.repository.KorisnikRepository;
import ftn.xml.korisnik.utils.PrettyPrint;
import org.apache.jena.fuseki.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class KorisnikService {
    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthService authService;

    public String registerUser(RegistrationDTO data) {
        try {
            System.out.println("[INFO] Korisnik: JAXB marshalling.Cuvanje korisnika u bazi\n");
            Korisnik korisnik;
            try {
                korisnik = getKorisnikByEmail(data.getEmail());
            } catch (Exception e) {
                korisnik = null;
            }
            if (korisnik != null)
                throw new RuntimeException("Korisnik vec postoji");
            JAXBContext context = JAXBContext.newInstance("ftn.xml.korisnik.model");
            korisnik = new Korisnik(data);
            korisnik.setSifra(bCryptPasswordEncoder.encode(korisnik.getSifra()));
            PrettyPrint.printKorisnika(korisnik);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            OutputStream os = new ByteArrayOutputStream();
            marshaller.marshal(korisnik, os);
            korisnikRepository.store(korisnik.getEmail(), os);
            return "Uspesno ste se registrovali";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Neuspensa registracija");
        }
    }

    public Korisnik getKorisnikByEmail(String email) {
        try {
            return korisnikRepository.retrieve(email);
        } catch (Exception e) {
            throw new RuntimeException("There is no user with this email");
        }
    }

    public UserDTO getUserDTOByEmail(String email) {
        try {
            return new UserDTO(korisnikRepository.retrieve(email));
        } catch (Exception e) {
            throw new RuntimeException("There is no user with this email");
        }
    }
}
