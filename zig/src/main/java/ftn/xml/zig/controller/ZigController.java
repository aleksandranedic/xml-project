package ftn.xml.zig.controller;
import ftn.xml.zig.service.ZigService;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="zig")
public class ZigController {
    private final ZigService service;

    @Autowired
    public ZigController(ZigService service) {
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
    @GetMapping(value = "marshal")
    public void marshal() {
        service.marshalling();
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

    @PutMapping(value = ":id")
    public boolean updateRequest(@RequestParam() int id) {
        return true;
    }

    @DeleteMapping(value = ":id")
    public boolean deleteRequest(@RequestParam() int id) {
        return true;
    }
}
