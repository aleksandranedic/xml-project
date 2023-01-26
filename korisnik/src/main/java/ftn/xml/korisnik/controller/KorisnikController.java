package ftn.xml.korisnik.controller;

import ftn.xml.korisnik.dto.RegistrationDTO;
import ftn.xml.korisnik.service.KorisnikService;
import ftn.xml.korisnik.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/korisnik")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<String> registerKorisnik(@RequestBody RegistrationDTO data) {
        try {
            return ResponseEntity.ok(korisnikService.registerUser(data));
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

    @GetMapping("{email}")
    public ResponseEntity<?> getLoggedUser(@PathVariable String email) {
        try {
            return ResponseEntity.ok(korisnikService.getKorisnikByEmail(email));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
