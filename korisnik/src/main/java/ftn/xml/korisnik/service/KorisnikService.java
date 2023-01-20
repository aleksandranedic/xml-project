package ftn.xml.korisnik.service;

import ftn.xml.korisnik.dto.RegistrationDTO;
import ftn.xml.korisnik.model.Korisnik;
import ftn.xml.korisnik.repository.KorisnikRepository;
import ftn.xml.korisnik.utils.PrettyPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

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

    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return authService.makeAccessToken(user, request);
    }
}
