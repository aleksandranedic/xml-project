package ftn.xml.patent.controller;

import ftn.xml.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "patent")
public class PatentController {

    private final PatentService service;

    @Autowired
    public PatentController(PatentService service) {
        this.service = service;
    }

    @GetMapping(value = "")
    public boolean findAll() {
        return true;
    }
    @GetMapping(value = "unmarshal")
    public void unmarshal() {
        service.unmarshalling();
    }
    @GetMapping(value = "save")
    public void save() {
        service.save();
    }
    @GetMapping(value = "rdf")
    public void createRdf() {
        service.createRdf();
    }
    @GetMapping(value = "pdf")
    public void toPdf() {
        service.toPDF();
    }
    @GetMapping(value = "xhtml")
    public void toXHTML() {
        service.toXHTML();
    }
    @GetMapping(path = "{documentId}")
    public boolean findRequest(@PathVariable("documentId") String documentId) {
        this.service.getZahtev(documentId);
        return true;
    }

    @PostMapping(value = "")
    public boolean createRequest(@RequestBody() int requestDto) {
        return true;
    }

    @PutMapping(value = "{id}")
    public boolean updateRequest(@RequestParam() int id) {
        return true;
    }

    @DeleteMapping(value = "{id}")
    public boolean deleteRequest(@RequestParam() int id) {
        return true;
    }

}
