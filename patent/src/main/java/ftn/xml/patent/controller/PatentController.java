package ftn.xml.patent.controller;

import ftn.xml.patent.dto.Zahtev;
import ftn.xml.patent.model.ZahtevZaPriznanjePatenta;
import ftn.xml.patent.service.PatentService;
import org.apache.directory.api.util.exception.NotImplementedException;
import org.apache.jena.atlas.lib.NotImplemented;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

//    @GetMapping
//    public List<ZahtevZaPriznanjePatenta> getAllWithoutApproval() {
//        throw new NotImplementedException();
//    }
//
//    @GetMapping
//    public List<ZahtevZaPriznanjePatenta> getAllUserApproved() {
//        throw new NotImplementedException();
//    }

    @GetMapping("/{broj}")
    public boolean getRequest(@PathVariable("broj") String brojPrijave) {
        this.service.getZahtev(brojPrijave);
        return true;
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
