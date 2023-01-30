package ftn.xml.patent.controller;

import ftn.xml.patent.model.ZahtevZaPriznanjePatenta;
import ftn.xml.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;
import java.util.List;
@RestController
@RequestMapping(path = "patent")
public class PatentController {

    private final PatentService service;

    @Autowired
    public PatentController(PatentService service) {
        this.service = service;
    }

    @GetMapping
    public List<ZahtevZaPriznanjePatenta> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAll();
    }
    @GetMapping(value = "unmarshal")
    public void unmarshal() throws JAXBException {
        service.unmarshall();
    }

    @GetMapping()
    public boolean findRequest(@RequestParam("broj") String brojPrijave) {
        this.service.getZahtev(brojPrijave);
        return true;
    }

    @PostMapping()
    public String createRequest(ZahtevZaPriznanjePatenta zahtevZaPriznanjePatenta) {
        try {
            service.save(zahtevZaPriznanjePatenta);
            return "Uspešno ste predali zahtev.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public String updateRequest(@RequestParam("broj") int brojPrijave, ZahtevZaPriznanjePatenta zahtev) {
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
