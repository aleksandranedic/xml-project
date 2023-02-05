package ftn.xml.autor.service;

import ftn.xml.autor.dto.Metadata;
import ftn.xml.autor.dto.ZahtevData;
import ftn.xml.autor.dto.ZahtevDataMapper;
import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.repository.AutorRepository;
import ftn.xml.autor.utils.AuthenticationUtilitiesMetadata;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class SearchService {
    private static final String PRED = "http://www.ftn.uns.ac.rs/jaxb/autor/pred";
    private final List<String> METAS = List.of("Broj_prijave", "Datum_podnosenja"
            , "Naslov", "Vrsta", "Forma", "Naziv", "Status"
    );
    private static final String FILE_FOLDER = "./src/main/resources/data/files/";

    private final AuthenticationUtilitiesMetadata.ConnectionProperties conn;
    private final AutorRepository repository;

    private final ZahtevDataMapper zahtevDataMapper;
    private final QueryService queryService;

    @Autowired
    public SearchService(AutorRepository repository, ZahtevDataMapper zahtevDataMapper, QueryService queryService) throws IOException {
        this.repository = repository;
        this.zahtevDataMapper = zahtevDataMapper;
        this.queryService = queryService;
        conn = AuthenticationUtilitiesMetadata.loadProperties();
    }

    public List<ZahtevData> advancedSearch(List<Metadata> metadataList) {
        List<ZahtevZaIntelektualnuSvojinu> zahtevi = new ArrayList<>();
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, queryService.getSparqlQuery(metadataList));
        ResultSet results = query.execSelect();
        String varName;
        RDFNode varValue;
        while (results.hasNext()) {
            QuerySolution querySolution = results.next();
            Iterator<String> variableBindings = querySolution.varNames();

            while (variableBindings.hasNext()) {
                varName = variableBindings.next();
                varValue = querySolution.get(varName);
                if (Objects.equals(varName, "Broj_prijave")) {
                    try {
//                        zahtevi.addAll(repository.retrieveBasedOnBrojPrijave(varValue.toString()));
                        zahtevi.add(repository.retrieve(varValue.toString()+".xml"));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        query.close();
        return mapZahtevEntityToZahtevData(zahtevi);
    }


    public List<ZahtevData> basicSearch(String terms) throws Exception {
        Set<ZahtevZaIntelektualnuSvojinu> zahtevi = new HashSet<>(repository.retrieveBasedOnTermList(terms.split(";")));
        return mapZahtevEntityToZahtevData(zahtevi.stream().toList());

    }

    public List<ZahtevData> mapZahtevEntityToZahtevData(List<ZahtevZaIntelektualnuSvojinu> list) {
        return list.stream().map(zahtevDataMapper::convertToZahtevData).toList();
    }

}
