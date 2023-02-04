package ftn.xml.autor.controller;

import ftn.xml.autor.dto.MetadataList;
import ftn.xml.autor.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) throws IOException {
        this.searchService = searchService;

    }

    //proveri
    @PostMapping(value = "advanced")
    public ResponseEntity<?> advanced(@RequestBody MetadataList metadata) {
        try {
            return ResponseEntity.ok(this.searchService.advancedSearch(metadata));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //basic
    @GetMapping("/basic")
    public ResponseEntity<?> basic(@RequestParam("terms") String terms) {
        try {
            return ResponseEntity.ok(this.searchService.basicSearch(terms));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
