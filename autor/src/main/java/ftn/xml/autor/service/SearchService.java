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

    private static String getPositiveValue(int i, Metadata m) {
        String value;
        if (i == 0) {
            value = String.format("?%s = \"%s\" ", m.getMeta(), m.getValue());
        } else {
            if (Objects.equals(m.getLogicalOperator(), "")) {
                throw new IllegalArgumentException("Only last parameter can be without operator.");
            }
            value = String.format("%s ?%s = \"%s\" ", m.getLogicalOperator(), m.getMeta(), m.getValue());
        }
        return value;
    }

    private static String getNegativeValue(int i, Metadata m) {
        String value;
        if (i == 0) {
            value = String.format("?%s != \"%s\" ", m.getMeta(), m.getValue());
        } else {
            if (Objects.equals(m.getLogicalOperator(), "")) {
                throw new IllegalArgumentException("Only last parameter can be without operator.");
            }
            value = String.format("%s ?%s != \"%s\" ", m.getLogicalOperator(), m.getMeta(), m.getValue());
        }
        return value;
    }

    private String getFilterValue(List<Metadata> metadata) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < metadata.size(); i++) {
            Metadata m = metadata.get(i);
            int lastIndex = metadata.size() - 1;

            if (Objects.equals(m.getOperator(), "!=")) {
                builder.append(getNegativeValue(i, m));
            } else {
                builder.append(getPositiveValue(i, m));
            }
        }
        return builder.toString();
    }


    private String getMetaString() {
        StringBuilder builder = new StringBuilder();
        for (String meta : METAS) {
            builder.append(String.format("?zig <%s/%s> ?%s . ", PRED, meta, meta));
        }
        return builder.toString();
    }

    public List<ZahtevZaIntelektualnuSvojinu> advancedSearch(List<Metadata> metadataList) {
        List<ZahtevZaIntelektualnuSvojinu> zahtevi = new ArrayList<>();
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, getSparqlQuery(metadataList));
        ResultSet results = query.execSelect();
        String varName;
        RDFNode varValue;
        while (results.hasNext()) {
            QuerySolution querySolution = results.next();
            Iterator<String> variableBindings = querySolution.varNames();

            while (variableBindings.hasNext()) {
                varName = variableBindings.next();
                varValue = querySolution.get(varName);

                try {
                    zahtevi.add(repository.retrieve(varValue.toString()+".xml"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        query.close();
        return zahtevi;
    }

    private String getSparqlQuery(List<Metadata> metadata) {
        String FUSEKI = "http://localhost:8080/fuseki-autor/autorDataset/data/autor/metadata";

        return "SELECT * FROM <" + FUSEKI + ">" +
                " WHERE {" +
                getMetaString() +
                " FILTER (" +
                getFilterValue(metadata) +
                ")" +
                "}" +
                " GROUP BY ?Broj_prijave";
    }


    public List<ZahtevZaIntelektualnuSvojinu> basicSearch(String terms) throws Exception {
        Set<ZahtevZaIntelektualnuSvojinu> zahtevi = new HashSet<>(repository.retrieveBasedOnTermList(terms.split(";")));
        return zahtevi.stream().toList();
    }

    public List<ZahtevData> mapZahtevEntityToZahtevData(List<ZahtevZaIntelektualnuSvojinu> list) {
        return list.stream().map(zahtevDataMapper::convertToZahtevData).toList();
    }

}
