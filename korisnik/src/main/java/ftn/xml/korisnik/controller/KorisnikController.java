package ftn.xml.korisnik.controller;

import ftn.xml.korisnik.dto.RegistrationDTO;
import ftn.xml.korisnik.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/korisnik", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;


    @PostMapping
    public ResponseEntity<String> registerKorisnik(@RequestBody RegistrationDTO data) {
        //sacuvaj korisnika u exist bazi
        try {
            return ResponseEntity.ok(korisnikService.registerUser(data));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> loginKorisnik(HttpServletRequest request) {
        //sacuvaj korisnika u exist bazi
        try {
            return ResponseEntity.ok(korisnikService.login(request));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
