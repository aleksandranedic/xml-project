package ftn.xml.autor.controller;

import ftn.xml.autor.dto.Zahtev;
import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import java.util.List;

@RestController
@RequestMapping(path = "autor")
public class AutorController {
    private final AutorService service;

    @Autowired
    public AutorController(AutorService service) {
        this.service = service;
    }


    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<ZahtevZaIntelektualnuSvojinu> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAll();
    }

    @GetMapping(path = "/{broj}", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ZahtevZaIntelektualnuSvojinu getRequest(@PathVariable("broj") String brojPrijave) {
        return this.service.getZahtev(brojPrijave);
    }


    @PostMapping(path = "/create", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public String createRequest(@RequestBody Zahtev zahtev) {
        try {
//            System.out.println(zahtev);
            service.save(zahtev);
            return "Uspešno ste dodali zahtev.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public String updateRequest(@RequestParam("broj") int brojPrijave, ZahtevZaIntelektualnuSvojinu zahtev) {
        try {
            //service.updateRequest(brojPrijave, zahtev);
            return "Uspešno ste ažurirali zahtev.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public String deleteRequest(@RequestParam("broj") int brojPrijave) {
        try {
            service.deleteRequest(brojPrijave);
            return "Uspešno ste obrisali zahtev.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
