package ftn.xml.autor.service;

import ftn.xml.autor.dto.Metadata;
import ftn.xml.autor.dto.ResenjeDTO;
import ftn.xml.autor.dto.Zahtev;
import ftn.xml.autor.dto.ZahtevMapper;
import ftn.xml.autor.model.EmailDataDTO;
import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.repository.AutorRepository;
import ftn.xml.autor.repository.RdfRepository;
import ftn.xml.autor.utils.AuthenticationUtilitiesMetadata;
import ftn.xml.autor.utils.SchemaValidationEventHandler;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

@Service
public class AutorService {
    public static final String CONTEXT_PATH = "ftn.xml.autor.model";
    private static final String SCHEMA_PATH = "./src/main/resources/data/xsd/autor.xsd";
    private static final String FILE_FOLDER = "./src/main/resources/data/files/";
    private static final String TARGET_FOLDER = "./target/classes/data/files/";


    private static final String FUSEKI_DATASET_PATH = "/autorDataset";
    private final AutorRepository repository;
    private final RdfRepository rdfRepository;
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;
    private final ZahtevMapper mapper;
    private final EmailService emailService;
    private final TransformationService transformationService;
    private final QueryService queryService;

    private final AuthenticationUtilitiesMetadata.ConnectionProperties conn;

    @Autowired
    public AutorService(AutorRepository repository, RdfRepository rdfRepository, ZahtevMapper mapper, EmailService emailService, TransformationService transformationService, QueryService queryService) throws SAXException, JAXBException, IOException {
        this.repository = repository;
        this.rdfRepository = rdfRepository;
        this.mapper = mapper;
        this.emailService = emailService;
        this.transformationService = transformationService;
        this.queryService = queryService;
        JAXBContext context = JAXBContext.newInstance(CONTEXT_PATH);
        conn = AuthenticationUtilitiesMetadata.loadProperties();
        unmarshaller = context.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(SCHEMA_PATH));
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new SchemaValidationEventHandler());

        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }

    public ZahtevZaIntelektualnuSvojinu getZahtev(String brojPrijave) {
        try {
            return repository.retrieve(brojPrijave + ".xml");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void unmarshall() throws JAXBException {
        unmarshall("./src/main/resources/data/xml/autor.xml");
    }

    public ZahtevZaIntelektualnuSvojinu unmarshall(String path) throws JAXBException {
        return (ZahtevZaIntelektualnuSvojinu) unmarshaller.unmarshal(new File(path));
    }

    public OutputStream save(String path) throws Exception {
        ZahtevZaIntelektualnuSvojinu zahtev = (ZahtevZaIntelektualnuSvojinu) unmarshaller.unmarshal(new File("./src/main/resources/data/xml/" + path));
        return save(zahtev);
    }

    public OutputStream save(ZahtevZaIntelektualnuSvojinu zahtev) throws Exception {
        OutputStream os = marshal(zahtev);
        repository.store(zahtev.getPopunjavaZavod().getBrojPrijave() + ".xml", os);
        return os;
    }

    public OutputStream marshal(ZahtevZaIntelektualnuSvojinu zahtev) throws JAXBException {
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(zahtev, os);
        return os;
    }

    public void addRdf(String file_path) {
        try {
            ZahtevZaIntelektualnuSvojinu zahtev = getZahtev(file_path);
            addRdf(zahtev);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPdf(String brojPrijave) throws JAXBException {
        return this.transformationService.toPDF(marshal(getZahtev(brojPrijave)), brojPrijave + ".pdf");
    }


    private void addRdf(ZahtevZaIntelektualnuSvojinu zahtev) throws JAXBException, TransformerException, IOException {
        ByteArrayOutputStream zahtev_xml_out = (ByteArrayOutputStream) marshal(zahtev);
        InputStream zahtev_input = new ByteArrayInputStream(zahtev_xml_out.toByteArray());
        ByteArrayOutputStream zahtev_output = new ByteArrayOutputStream();
        rdfRepository.extractMetadata(zahtev_input, zahtev_output);
        rdfRepository.writeRdf(zahtev_output.toString());
    }

    public List<ZahtevZaIntelektualnuSvojinu> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return repository.retrieveAll();
    }

    public void deleteRequest(int brojPrijave) throws Exception {
        repository.remove(brojPrijave + ".xml");
    }

    public void save(Zahtev zahtev) throws Exception {
        ZahtevZaIntelektualnuSvojinu zahtevZaIntelektualnuSvojinu = mapper.parseZahtev(zahtev);
        save(zahtevZaIntelektualnuSvojinu);
        addRdf(zahtevZaIntelektualnuSvojinu);
        createJsonFromRdf(zahtevZaIntelektualnuSvojinu.getPopunjavaZavod().getBrojPrijave());
        createRdfFromRdf(zahtevZaIntelektualnuSvojinu.getPopunjavaZavod().getBrojPrijave());
        this.transformationService.toXHTML(marshal(getZahtev(zahtevZaIntelektualnuSvojinu.getPopunjavaZavod().getBrojPrijave())), zahtevZaIntelektualnuSvojinu.getPopunjavaZavod().getBrojPrijave() + ".html");
        this.transformationService.toPDF(marshal(getZahtev(zahtevZaIntelektualnuSvojinu.getPopunjavaZavod().getBrojPrijave())), zahtevZaIntelektualnuSvojinu.getPopunjavaZavod().getBrojPrijave() + ".pdf");
    }

    public void updateRequest(ResenjeDTO resenje) {
        ZahtevZaIntelektualnuSvojinu zahtev = getZahtev(resenje.getBrojPrijave());
        try {
            zahtev.setResenje(mapper.parseResenje(resenje));
            save(zahtev);
            String email = zahtev.getPopunjavaPodnosilac().getPodnosilac().getKontakt().getEPosta();
            String documentPath = "./src/main/resources/data/files/" + zahtev.getPopunjavaZavod().getBrojPrijave() + ".pdf";
            EmailDataDTO emailDataDTO = EmailService.buildEmailDTO(email, documentPath);
            emailService.sendEmail(emailDataDTO);
            createJsonFromRdf(zahtev.getPopunjavaZavod().getBrojPrijave());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ZahtevZaIntelektualnuSvojinu> getAllResolved() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaIntelektualnuSvojinu> list = repository.retrieveAll();
        list.stream().filter(zahtevZaIntelektualnuSvojinu -> {
            try {
                zahtevZaIntelektualnuSvojinu.getResenje();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return list;
    }

    public List<ZahtevZaIntelektualnuSvojinu> getAllUnresolved() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaIntelektualnuSvojinu> list = repository.retrieveAll();
        list.stream().filter(zahtevZaIntelektualnuSvojinu -> {
            try {
                zahtevZaIntelektualnuSvojinu.getResenje();
                return false;
            } catch (Exception e) {
                return true;
            }
        });
        return list;
    }

    public String getHtml(String brojPrijave) throws JAXBException {
        return this.transformationService.toXHTML(marshal(getZahtev(brojPrijave)), brojPrijave + ".html");
    }

    public String getHtmlString(String brojPrijave) {

        String filePath = FILE_FOLDER + brojPrijave + ".html";

        String content;
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try {
                content = Files.readString(file.toPath(), Charset.defaultCharset());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            try {
                getHtml(brojPrijave);

            } catch (JAXBException e) {
                throw new RuntimeException(e.getMessage());
            }
            return getHtmlString(brojPrijave);
        }
        return content;
    }


    public void createJsonFromRdf(String brojPrijave) throws IOException {
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, queryService.getSparqlQuery(List.of(new Metadata("Broj_prijave", brojPrijave, "&&", "="))));
        ResultSet results = query.execSelect();
        OutputStream outputStream = new FileOutputStream(FILE_FOLDER + brojPrijave + ".json");
        ResultSetFormatter.outputAsJSON(outputStream, results);
        OutputStream outputStream1 = new FileOutputStream(TARGET_FOLDER + brojPrijave + ".json");
        results = query.execSelect();
        ResultSetFormatter.outputAsJSON(outputStream1, results);
        outputStream.flush();
        outputStream.close();
        query.close();
    }

    public void createRdfFromRdf(String brojPrijave) throws IOException {
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, queryService.getSparqlQuery(List.of(new Metadata("Broj_prijave", brojPrijave, "&&", "="))));
        ResultSet results = query.execSelect();
        OutputStream outputStream = new FileOutputStream(FILE_FOLDER + brojPrijave + ".rdf");
        ResultSetFormatter.out(outputStream, results);
        OutputStream outputStream1 = new FileOutputStream(TARGET_FOLDER + brojPrijave + ".rdf");
        results = query.execSelect();
        ResultSetFormatter.out(outputStream1, results);
        outputStream.flush();
        outputStream.close();
        query.close();
    }
}
