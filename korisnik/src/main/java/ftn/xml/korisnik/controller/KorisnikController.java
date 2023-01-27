package ftn.xml.korisnik.controller;

import ftn.xml.korisnik.dto.RegistrationDTO;
import ftn.xml.korisnik.service.KorisnikService;
import ftn.xml.korisnik.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/korisnik", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<String> registerKorisnik(@RequestBody RegistrationDTO data) {
        try {
            return new ResponseEntity<>(korisnikService.registerUser(data), HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginKorisnik(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(loginService.login(request));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping(path = "/logged",consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getLoggedUser(@RequestBody String email) {
        try {
            if (email.equals(""))
                throw new RuntimeException("Email is blank");
            return ResponseEntity.ok(korisnikService.getKorisnikByEmail(email));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
