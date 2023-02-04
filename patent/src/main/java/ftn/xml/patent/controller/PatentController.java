package ftn.xml.patent.controller;

import ftn.xml.patent.dto.Resenje;
import ftn.xml.patent.dto.Zahtev;
import ftn.xml.patent.dto.ZahtevData;
import ftn.xml.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;
import java.util.List;

@RestController
@RequestMapping(path = "/patent")
public class PatentController {

    private final PatentService service;
    public static final String FILES = "http://localhost:8002/files/";


    @Autowired
    public PatentController(PatentService service) {
        this.service = service;
    }

    @GetMapping
    public List<ZahtevData> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAll();
    }

    @GetMapping("/resolved")
    public List<ZahtevData> getAllResolved() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAllResolved();
    }

    @GetMapping("/unresolved")
    public List<ZahtevData> getAllUnresolved() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAllUnresolved();
    }

    @GetMapping("/{broj}")
    public ZahtevData getRequest(@PathVariable("broj") String brojPrijave) {
        return this.service.getZahtevData(brojPrijave);
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


    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE )
    public String createRequest(@RequestBody Zahtev zahtev) {
        try {
            service.save(zahtev);
            return "Zahtev je dodat.";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path= "/update", consumes = MediaType.APPLICATION_XML_VALUE)
    public String updateRequest(@RequestParam("broj") String brojPrijave, @RequestBody Resenje resenje) {
        try {
            service.updateRequest(brojPrijave, resenje);
            return "Uspešno ste ažurirali zahtev.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public String deleteRequest(@RequestParam("broj") String brojPrijave) {
        try {
            service.deleteRequest(brojPrijave);
            return "Uspešno ste obrisali zahtev.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/izvestaj")
    public String getIzvestaj(@RequestParam("start") String startDate, @RequestParam("end") String endDate) {
        try {
            return service.getIzvestajPdf(startDate, endDate);
        } catch (JAXBException | IllegalAccessException | XMLDBException | ClassNotFoundException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
    }


}
