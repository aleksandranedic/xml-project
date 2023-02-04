package ftn.xml.autor.controller;

import ftn.xml.autor.dto.Metadata;
import ftn.xml.autor.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) throws IOException {
        this.searchService = searchService;

    }

    //TODO proveri da li advanced search radi
    @PostMapping(path = "/advanced", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> advanced(@RequestBody List<Metadata> metadata) {
        try {
            return ResponseEntity.ok(this.searchService.advancedSearch(metadata));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/basic", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> basic(@RequestParam("terms") String terms) {
        try {
            return ResponseEntity.ok(this.searchService.basicSearch(terms));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
