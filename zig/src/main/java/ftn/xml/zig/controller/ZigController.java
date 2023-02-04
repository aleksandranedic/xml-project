package ftn.xml.zig.controller;
import ftn.xml.zig.dto.Zahtev;
import ftn.xml.zig.model.ZahtevZaPriznanjeZiga;
import ftn.xml.zig.service.ZigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import java.util.List;

@RestController
@RequestMapping(path="zig")
public class ZigController {
    private final ZigService service;

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


    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_XML_VALUE)
    public String createRequest(@RequestBody Zahtev zahtev) {
        try {
            service.save(zahtev);
            return "Uspešno ste dodali zahtev.";

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
}
