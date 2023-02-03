package ftn.xml.patent.repository;

import ftn.xml.patent.utils.AuthenticationUtilitiesMetadata;
import ftn.xml.patent.utils.SparqlUtil;
import net.sf.saxon.TransformerFactoryImpl;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Repository;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Repository
public class RdfRepository {

    private TransformerFactory transformerFactory;
    private static final String XSLT_FILE = "./src/main/resources/data/xslt/patent.xsl";
    private static final String SPARQL_NAMED_GRAPH_URI = "/patent/metadata";
    private static final String FILE_FOLDER = "./src/main/resources/data/files/";
    private static final String TARGET_FOLDER = "./target/classes/data/files/";

    AuthenticationUtilitiesMetadata.ConnectionProperties conn;

    public RdfRepository() {
        transformerFactory = new TransformerFactoryImpl();
        try {
            conn = AuthenticationUtilitiesMetadata.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void extractMetadata(InputStream in, OutputStream out) throws TransformerException {
        StreamSource transformSource = new StreamSource(new File(XSLT_FILE));
        Transformer grddlTransformer = transformerFactory.newTransformer(transformSource);
        grddlTransformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
        grddlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamSource source = new StreamSource(in);
        StreamResult result = new StreamResult(out);
        grddlTransformer.transform(source, result);
    }

    public void writeRdf(String rdf) {
        ResultSet results = getRdfResultSet(rdf);
        ResultSetFormatter.out(System.out, results);
    }

    public String writeRdfToFile(String rdf, String brojPrijave) {
        ResultSet results = getRdfResultSet(rdf);
        String filePath = brojPrijave + ".rdf";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_FOLDER + filePath);
            FileOutputStream targetOutputStream = new FileOutputStream(TARGET_FOLDER + filePath);
            ResultSetFormatter.out(fileOutputStream, results);
            ResultSetFormatter.out(targetOutputStream, results);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }

    private ResultSet getRdfResultSet(String rdf) {
        Model model = ModelFactory.createDefaultModel();
        ByteArrayInputStream is = new ByteArrayInputStream(rdf.getBytes(StandardCharsets.UTF_8));
        model.read(is, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out, SparqlUtil.NTRIPLES);
        String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI, new String(out.toByteArray()));
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
        processor.execute();
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + SPARQL_NAMED_GRAPH_URI, "?s ?p ?o");
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();
        query.close();
        return results;
    }


}
