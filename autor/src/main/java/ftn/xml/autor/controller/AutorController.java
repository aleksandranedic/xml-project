package ftn.xml.autor.controller;

import ftn.xml.autor.dto.*;
import ftn.xml.autor.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "autor")
public class AutorController {
    private final AutorService service;
    private final ZahtevDataMapper zahtevDataMapper;


    public static final String FILES = "http://localhost:8003/files/";


    @Autowired
    public AutorController(AutorService service, ZahtevDataMapper zahtevDataMapper) {
        this.service = service;
        this.zahtevDataMapper = zahtevDataMapper;
    }

    @GetMapping(path = "/{broj}", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ZahtevData getRequest(@PathVariable("broj") String brojPrijave) {
        return zahtevDataMapper.convertToZahtevData(this.service.getZahtev(brojPrijave));
    }


    @PostMapping(path = "/create", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public String createRequest(@RequestBody Zahtev zahtev) {
        try {
            service.save(zahtev);
            return "Uspešno ste dodali zahtev.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public String updateRequest(@RequestBody ResenjeDTO resenje) {
        try {
            service.updateRequest(resenje);
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

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<ZahtevData> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAll().stream().map(zahtevDataMapper::convertToZahtevData).toList();
    }

    @GetMapping(path = "/resolved/{email}", produces = MediaType.APPLICATION_XML_VALUE)
    public List<ZahtevData> getAllResolved(@PathVariable String email) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAllResolvedForUser(email).stream().map(zahtevDataMapper::convertToZahtevData).toList();

    }

    @GetMapping(path = "/unresolved/{email}", produces = MediaType.APPLICATION_XML_VALUE)
    public List<ZahtevData> getAllUnresolved(@PathVariable String email) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAllUnresolvedForUser(email).stream().map(zahtevDataMapper::convertToZahtevData).toList();
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

    @GetMapping(path = "/json", consumes = MediaType.APPLICATION_XML_VALUE)
    public void rdfToJSON(@RequestBody String brojPrijave) throws IOException {
        this.service.createJsonFromRdf(brojPrijave);
    }

    @GetMapping(path = "/create/rdf", consumes = MediaType.APPLICATION_XML_VALUE)
    public void saveRdfFile(@RequestBody String brojPrijave) throws IOException {
        this.service.createRdfFromRdf(brojPrijave);
    }

    @PostMapping(path = "/izvestaj")
    public void getIzvestaj(@RequestBody DateRangeDto dateRange) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException {
        this.service.createIzvestaj(dateRange);
    }
}
