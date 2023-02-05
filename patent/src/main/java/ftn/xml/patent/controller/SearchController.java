package ftn.xml.patent.controller;

import ftn.xml.patent.dto.Metadata;
import ftn.xml.patent.dto.MetadataList;
import ftn.xml.patent.dto.ZahtevDataMapper;
import ftn.xml.patent.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;
    private final ZahtevDataMapper zahtevDataMapper;

    @Autowired
    public SearchController(SearchService searchService, ZahtevDataMapper zahtevDataMapper) throws IOException {
        this.searchService = searchService;

        this.zahtevDataMapper = zahtevDataMapper;
    }

    @PostMapping( "/advanced")
    public ResponseEntity<?> advanced(@RequestBody List<Metadata> metadata) {
        try {
            return ResponseEntity.ok(this.searchService.advancedSearch(metadata).stream().map(zahtevDataMapper::convertToZahtevData).toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/basic")
    public ResponseEntity<?> basic(@RequestParam("terms") String terms) {
        try {
            return ResponseEntity.ok(this.searchService.basicSearch(terms).stream().map(zahtevDataMapper::convertToZahtevData).toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
