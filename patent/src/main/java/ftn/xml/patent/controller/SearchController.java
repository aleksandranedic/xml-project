package ftn.xml.patent.controller;

import ftn.xml.patent.dto.MetadataList;
import ftn.xml.patent.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) throws IOException {
        this.searchService = searchService;

    }

    @GetMapping(value = "advanced")
    public ResponseEntity<?> advanced(@RequestBody MetadataList metadata) {
        try {
            return ResponseEntity.ok(this.searchService.advancedSearch(metadata));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "basic")
    public ResponseEntity<?> basic(@RequestParam("term") String term) {
        try {
            return ResponseEntity.ok(this.searchService.basicSearch(term));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}