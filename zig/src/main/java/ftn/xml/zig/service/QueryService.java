package ftn.xml.zig.service;

import ftn.xml.zig.dto.Metadata;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class QueryService {


    private final List<String> METAS = List.of("Broj_prijave", "Datum_podnosenja", "Takse", "Vrsta_a", "Vrsta_b","Podnosilac", "Podnosilac_email");

    private static final String PRED = "http://www.ftn.uns.ac.rs/jaxb/zig/pred";

    public String getSparqlQuery(List<Metadata> metadata) {
        String FUSEKI = "http://localhost:8080/fuseki-zig/zigDataset/data/zig/metadata";
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
            builder.append(String.format("?zig <%s/%s> ?%s. ", PRED, meta, meta));
        }
        return builder.toString();
    }

    public String getFilterValue(List<Metadata> metadata) {
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
            value = String.format("NOT EXISTS {?zig <%s> \"%s\"}", m.getMeta(), m.getValue());
        } else {
            if (Objects.equals(m.getLogicalOperator(), "")) {
                throw new IllegalArgumentException("Only last parameter can be without operator.");
            }
            value = String.format(" %s NOT EXISTS {?zig <%s> \"%s\"}", m.getLogicalOperator(), m.getMeta(), m.getValue());
        }
        return value;
    }
}
