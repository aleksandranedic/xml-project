package ftn.xml.korisnik.controller;

import ftn.xml.korisnik.dto.LoginDTO;
import ftn.xml.korisnik.dto.RegistrationDTO;
import ftn.xml.korisnik.service.AuthService;
import ftn.xml.korisnik.service.KorisnikService;
import ftn.xml.korisnik.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(path = "/korisnik",produces = MediaType.APPLICATION_XML_VALUE)
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private LoginService loginService;


    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> registerKorisnik(@RequestBody RegistrationDTO data) {
        try {
            return new ResponseEntity<>(korisnikService.registerUser(data), HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> loginKorisnik(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(loginService.login(request, loginDTO));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getLoggedUser(HttpServletRequest request) {
        try {
            String email = loginService.authService.getUsernameFromJWT(request.getHeader(AUTHORIZATION));
            return ResponseEntity.ok(korisnikService.getUserDTOByEmail(email));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
