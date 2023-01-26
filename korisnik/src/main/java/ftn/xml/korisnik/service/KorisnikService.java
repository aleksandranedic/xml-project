package ftn.xml.korisnik.service;

import ftn.xml.korisnik.dto.RegistrationDTO;
import ftn.xml.korisnik.model.Korisnik;
import ftn.xml.korisnik.repository.KorisnikRepository;
import ftn.xml.korisnik.utils.PrettyPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Service
public class KorisnikService {
    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public String registerUser(RegistrationDTO data) {
        try {
            System.out.println("[INFO] Korisnik: JAXB marshalling.Cuvanje korisnika u bazi\n");
            JAXBContext context = JAXBContext.newInstance("ftn.xml.korisnik.model");
            Korisnik korisnik = new Korisnik(data);
            korisnik.setSifra(bCryptPasswordEncoder.encode(korisnik.getSifra()));
            PrettyPrint.printKorisnika(korisnik);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            OutputStream os = new ByteArrayOutputStream();
            marshaller.marshal(korisnik, os);
            korisnikRepository.store(korisnik.getEmail(), os);
            return "Uspesno ste se registrovali";
        } catch (Exception e) {
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

}
