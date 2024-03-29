package ftn.xml.zig.controller;

import ftn.xml.zig.dto.*;
import ftn.xml.zig.model.ZahtevZaPriznanjeZiga;
import ftn.xml.zig.service.ZigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "zig")
public class ZigController {
    private final ZigService service;
    private final ZahtevDataMapper zahtevDataMapper;
    public static final String FILES = "http://localhost:8000/";

    @Autowired
    public ZigController(ZigService service, ZahtevDataMapper zahtevDataMapper) {
        this.service = service;
        this.zahtevDataMapper = zahtevDataMapper;
    }

    @GetMapping(value = "/{broj}", produces = MediaType.APPLICATION_XML_VALUE)
    public ZahtevData getRequest(@PathVariable("broj") String brojPrijave) {
        return zahtevDataMapper.convertToZahtevData(this.service.getZahtev(brojPrijave));
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

    @PostMapping(path = "/izvestaj", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getIzvestaj(@RequestBody DateRangeDto dateRange) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException {
        String filename = this.service.createIzvestaj(dateRange);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
        return new ResponseEntity<>("Uspešno kreiran izvestaj na putanji: http://localhost:8000/" + filename, responseHeaders, HttpStatus.OK);
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

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<ZahtevData> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAll().stream().map(zahtevDataMapper::convertToZahtevData).toList();
    }

    @GetMapping(value = "/resolved/{email}", produces = MediaType.APPLICATION_XML_VALUE)
    public List<ZahtevData> getAllResolved(@PathVariable String email) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAllResolved(email).stream().map(zahtevDataMapper::convertToZahtevData).toList();
    }

    @GetMapping(value = "/unresolved/{email}", produces = MediaType.APPLICATION_XML_VALUE)
    public List<ZahtevData> getAllUnresolved(@PathVariable String email) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return service.getAllUnresolved(email).stream().map(zahtevDataMapper::convertToZahtevData).toList();
    }

    @GetMapping(path = "/json", consumes = MediaType.APPLICATION_XML_VALUE)
    public void rdfToJSON(@RequestBody String brojPrijave) throws IOException {
        this.service.createJsonFromRdf(brojPrijave);
    }

    @GetMapping(path = "/create/rdf", consumes = MediaType.APPLICATION_XML_VALUE)
    public void saveRdfFile(@RequestBody String brojPrijave) throws IOException {
        this.service.createRdfFromRdf(brojPrijave);
    }
}
