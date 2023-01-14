package ftn.xml.autor.controller;

import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "autor")
public class AutorController {
    @Autowired
    public AutorService autorService;


    @GetMapping(value = "")
    public boolean findAll() {
        return true;
    }

    @GetMapping(value = "unmarshal")
    public void unmarshal() {
        autorService.unmarshalling();
    }

    @GetMapping(value = "save")
    public void marshal() {
        autorService.marshalling();
    }

    @GetMapping(value = "pdf")
    public void toPdf() {
        autorService.toPDF();
    }

    @GetMapping(value = "xhtml")
    public void toXHTML() {
        autorService.toXHTML();
    }
    @GetMapping(value = "rdf")
    public void createRdf() {
        autorService.createRdf();
    }

    @GetMapping(path = "{documentId}")
    public boolean findRequest(@PathVariable("documentId") String documentId) {
        this.autorService.getZahtev(documentId);
        return true;
    }

    @PostMapping(value = "")
    public boolean createRequest(@RequestBody() int requestDto) {
        return true;
    }

    @PutMapping(value = ":id")
    public boolean updateRequest(@RequestParam() int id) {
        return true;
    }

    @DeleteMapping(value = ":id")
    public boolean deleteRequest(@RequestParam() int id) {
        return true;
    }

}
