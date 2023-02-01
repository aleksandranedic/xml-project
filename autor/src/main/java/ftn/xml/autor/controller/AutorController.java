package ftn.xml.autor.controller;

import ftn.xml.autor.dto.Zahtev;
import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping
    public List<ZahtevZaIntelektualnuSvojinu> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAll();
    }

    @GetMapping("/{broj}")
    public ZahtevZaIntelektualnuSvojinu getRequest(@PathVariable("broj") String brojPrijave) {
        return this.service.getZahtev(brojPrijave);
    }


    @PostMapping(value = "/create")
    public String createRequest(@RequestBody Zahtev zahtev) {
        try {
            service.save(zahtev);
            return "Uspešno ste dodali zahtev.";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public String updateRequest(@RequestParam("broj") int brojPrijave, ZahtevZaIntelektualnuSvojinu zahtev) {
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

}
