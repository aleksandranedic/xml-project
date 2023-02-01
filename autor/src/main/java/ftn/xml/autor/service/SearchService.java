package ftn.xml.autor.service;

import ftn.xml.autor.dto.Metadata;
import ftn.xml.autor.dto.MetadataList;
import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.repository.AutorRepository;
import ftn.xml.autor.utils.AuthenticationUtilitiesMetadata;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.*;

@Service
public class SearchService {
    private static final String PRED = "http://www.ftn.uns.ac.rs/jaxb/autor/pred";
    private final List<String> METAS = List.of("Broj_prijave", "Datum_podnosenja");
    private final AuthenticationUtilitiesMetadata.ConnectionProperties conn;
    private final AutorRepository repository;

    @Autowired
    public SearchService( AutorRepository repository) throws IOException {
        this.repository = repository;
        conn = AuthenticationUtilitiesMetadata.loadProperties();
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

    private static String getPositiveValue(int i, Metadata m) {
        String value;
        if (i == 0) {
            value = String.format("?%s = \"%s\"", m.getMeta(), m.getValue());
        } else {
            if (Objects.equals(m.getLogicalOperator(), "")) {
                throw new IllegalArgumentException("Only last parameter can be without operator.");
            }
            value = String.format(" %s ?%s = \"%s\"", m.getLogicalOperator(), m.getMeta(), m.getValue());
        }
        return value;
    }

    private static String getNegativeValue(int i, Metadata m) {
        String value;
        if (i == 0) {
            value = String.format("NOT EXISTS {?autor <%s> \"%s\"}", m.getMeta(), m.getValue());
        } else {
            if (Objects.equals(m.getLogicalOperator(), "")) {
                throw new IllegalArgumentException("Only last parameter can be without operator.");
            }
            value = String.format(" %s NOT EXISTS {?autor <%s> \"%s\"}", m.getLogicalOperator(), m.getMeta(), m.getValue());
        }
        return value;
    }

    public List<ZahtevZaIntelektualnuSvojinu> advancedSearch(MetadataList metadataList) {
        List<ZahtevZaIntelektualnuSvojinu> zahtevi = new ArrayList<>();
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, getSparqlQuery(metadataList.getMetadata()));
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
                        zahtevi.addAll(repository.retrieveBasedOnBrojPrijave(varValue.toString()));
                    } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        query.close();
        return zahtevi;
    }

    private String getSparqlQuery(List<Metadata> metadata) {
        String FUSEKI = "http://localhost:8080/fuseki/patentDataset/data/patent/metadata";

        return "SELECT * FROM <" + FUSEKI + ">" +
                "WHERE {" +
                getMetaString() +
                "FILTER (" +
                getFilterValue(metadata) +
                ")" +
                "}";
    }

    private String getMetaString() {
        StringBuilder builder = new StringBuilder();
        for (String meta : METAS) {
            builder.append(String.format("?patent <%s/%s> ?%s .", PRED, meta, meta));
        }
        return builder.toString();
    }

    public List<ZahtevZaIntelektualnuSvojinu> basicSearch(String terms) throws Exception {
        Set<ZahtevZaIntelektualnuSvojinu> zahtevi = new HashSet<>(repository.retrieveBasedOnTermList(terms.split(";")));
        return zahtevi.stream().toList();

    }


}