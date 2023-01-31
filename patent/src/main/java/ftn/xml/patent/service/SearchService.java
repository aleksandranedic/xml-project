package ftn.xml.patent.service;

import ftn.xml.patent.dto.Metadata;
import ftn.xml.patent.dto.MetadataList;
import ftn.xml.patent.model.ZahtevZaPriznanjePatenta;
import ftn.xml.patent.repository.PatentRepository;
import ftn.xml.patent.utils.AuthenticationUtilitiesMetadata;
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
    private static final String PRED = "http://www.ftn.uns.ac.rs/jaxb/patent/pred";
    private final List<String> METAS = List.of("Broj_prijave", "Datum_podnosenja", "Naziv_na_srpskom", "Naziv_na_engleskom", "Datum_prijema", "Email", "Podnosioc");
    private final AuthenticationUtilitiesMetadata.ConnectionProperties conn;
    private final PatentRepository repository;

    @Autowired
    public SearchService( PatentRepository repository) throws IOException {
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
            value = String.format("NOT EXISTS {?patent <%s> \"%s\"}", m.getMeta(), m.getValue());
        } else {
            if (Objects.equals(m.getLogicalOperator(), "")) {
                throw new IllegalArgumentException("Only last parameter can be without operator.");
            }
            value = String.format(" %s NOT EXISTS {?patent <%s> \"%s\"}", m.getLogicalOperator(), m.getMeta(), m.getValue());
        }
        return value;
    }

    public List<ZahtevZaPriznanjePatenta> advancedSearch(MetadataList metadataList) {
        List<ZahtevZaPriznanjePatenta> zahtevi = new ArrayList<>();
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

    public List<ZahtevZaPriznanjePatenta> basicSearch(String terms) throws Exception {

        Set<ZahtevZaPriznanjePatenta> zahtevi = new HashSet<>();

        for (String term: terms.split(";")) {
            zahtevi.addAll(repository.retrieveBasedOnTerm(term));
        }

        return zahtevi.stream().toList();

    }


}
