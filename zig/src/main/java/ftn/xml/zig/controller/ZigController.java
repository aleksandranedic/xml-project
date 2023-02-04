package ftn.xml.zig.controller;
import ftn.xml.zig.dto.Zahtev;
import ftn.xml.zig.model.ZahtevZaPriznanjeZiga;
import ftn.xml.zig.service.ZigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import java.util.List;

@RestController
@RequestMapping(path="zig")
public class ZigController {
    private final ZigService service;
    public static final String FILES = "http://localhost:8002/files/";

    @Autowired
    public ZigController(ZigService service) {
        this.service = service;
    }

    @GetMapping
    public List<ZahtevZaPriznanjeZiga> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAll();
    }

    @GetMapping("/{broj}")
    public ZahtevZaPriznanjeZiga getRequest(@PathVariable("broj") String brojPrijave) {
        return this.service.getZahtev(brojPrijave);
    }


    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> createRequest(@RequestBody Zahtev zahtev) {
        try {
            service.save(zahtev);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
            return new ResponseEntity<>("Uspešno ste dodali zahtev.", responseHeaders, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public String updateRequest(@RequestParam("broj") int brojPrijave, Zahtev zahtev) {
        try {
            //service.updateRequest(brojPrijave, zahtev);
            return "Uspešno ste ažurirali zahtev.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public String deleteRequest(@RequestParam("broj") int brojPrijave) {
        try {
            service.deleteRequest(brojPrijave);
            return "Uspešno ste obrisali zahtev.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/pdf/{broj}")
    public String getPdf(@PathVariable("broj") String brojPrijave) {
        try {
            return FILES + this.service.getPdf(brojPrijave);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/html/{broj}")
    public String getHtml(@PathVariable("broj") String brojPrijave) {
        try {
            return FILES + this.service.getHtml(brojPrijave);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
