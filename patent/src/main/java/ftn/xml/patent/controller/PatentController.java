package ftn.xml.patent.controller;

import ftn.xml.patent.dto.*;
import ftn.xml.patent.model.ZahtevZaPriznanjePatenta;
import ftn.xml.patent.service.PatentService;
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
@RequestMapping(path = "/patent")
public class PatentController {

    private final PatentService service;
    private final ZahtevDataMapper zahtevDataMapper;
    public static final String FILES = "http://localhost:8002/files/";


    @Autowired
    public PatentController(PatentService service, ZahtevDataMapper zahtevDataMapper) {
        this.service = service;
        this.zahtevDataMapper = zahtevDataMapper;
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

    @GetMapping(path = "/{broj}", produces = MediaType.APPLICATION_XML_VALUE)
    public ZahtevZaPriznanjePatenta getRequest(@PathVariable("broj") String brojPrijave) {
        return this.service.getZahtev(brojPrijave);
    }

    @PostMapping(path = "/rich", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String addRequestWithRichInput(@RequestBody Zahtev zahtev) throws Exception {


        String brojPrijave = service.save(zahtev);
        return "Zahtev je dodat pod brojem prijave " + brojPrijave + ".";
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


    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String createRequest(@RequestBody Zahtev zahtev) {
        try {
            String brojPrijave = service.save(zahtev);
            return "Zahtev je dodat pod brojem prijave " + brojPrijave + ".";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> updateRequest(@RequestBody Resenje resenje) {
        try {
            service.updateRequest(resenje);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
            return new ResponseEntity<>("Uspešno ste ažurirali zahtev.", responseHeaders, HttpStatus.OK);
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

    @GetMapping(value = "/izvestaj", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public String getIzvestaj(@RequestParam("start") String startDate, @RequestParam("end") String endDate) {
        try {
            return service.getIzvestajPdf(startDate, endDate);
        } catch (JAXBException | IllegalAccessException | XMLDBException | ClassNotFoundException |
                 InstantiationException e) {
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
