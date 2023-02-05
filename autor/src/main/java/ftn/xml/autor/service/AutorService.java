package ftn.xml.autor.service;

import ftn.xml.autor.dto.*;
import ftn.xml.autor.model.EmailDataDTO;
import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.model.izvestaj.Izvestaj;
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
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AutorService {
    public static final String CONTEXT_PATH = "ftn.xml.autor.model";
    private static final String SCHEMA_PATH = "./src/main/resources/data/xsd/autor.xsd";
    private static final String FILE_FOLDER = "./src/main/resources/data/files/";
    private static final String TARGET_FOLDER = "./target/classes/data/files/";


    private final String FUSEKI_DATABASE = "<http://localhost:8080/fuseki-autor/autorDataset/data/autor/metadata>";

    private final String DATE_PREDICT = "<http://www.ftn.uns.ac.rs/jaxb/autor/pred/Datum_podnosenja>";
    private final AutorRepository repository;
    private final RdfRepository rdfRepository;
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;
    private final ZahtevMapper mapper;
    private final EmailService emailService;
    private final TransformationService transformationService;
    private final QueryService queryService;

    private final IzvestajService izvestajService;
    private final AuthenticationUtilitiesMetadata.ConnectionProperties conn;

    @Autowired
    public AutorService(AutorRepository repository, RdfRepository rdfRepository, ZahtevMapper mapper, EmailService emailService, TransformationService transformationService, QueryService queryService, IzvestajService izvestajService) throws SAXException, JAXBException, IOException {
        this.repository = repository;
        this.rdfRepository = rdfRepository;
        this.mapper = mapper;
        this.emailService = emailService;
        this.transformationService = transformationService;
        this.queryService = queryService;
        this.izvestajService = izvestajService;
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
            zahtev.getPopunjavaZavod().setBrojPrijave("A-" + zahtev.getPopunjavaZavod().getBrojPrijave());
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

    public List<ZahtevZaIntelektualnuSvojinu> getAllResolvedForUser(String email) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaIntelektualnuSvojinu> list = repository.retrieveAll();
        List<ZahtevZaIntelektualnuSvojinu> list1 = new ArrayList<>();
        for (ZahtevZaIntelektualnuSvojinu zahtev :
                list) {
            if (email.equals(zahtev.getPopunjavaPodnosilac().getPodnosilac().getKontakt().getEPosta()) && zahtev.getResenje() != null) {
                list1.add(zahtev);
            }
        }
        return list1;
    }

    public List<ZahtevZaIntelektualnuSvojinu> getAllUnresolvedForUser(String email) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaIntelektualnuSvojinu> list = repository.retrieveAll();
        List<ZahtevZaIntelektualnuSvojinu> list1 = new ArrayList<>();
        for (ZahtevZaIntelektualnuSvojinu zahtev : list) {
            if (email.equals(zahtev.getPopunjavaPodnosilac().getPodnosilac().getKontakt().getEPosta()) && zahtev.getResenje() == null) {
                list1.add(zahtev);
            }
        }
        return list1;
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


    public void createIzvestaj(DateRangeDto dateRange) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException {
        Izvestaj izvestaj = new Izvestaj();
        izvestaj.setBrojPodnetihZahteva(String.valueOf(retrieveAllInDateRange(dateRange).size()));
        izvestaj.setBrojOdobrenihZahteva(String.valueOf(getAllApprovedRequestInDateRange(dateRange, "Odobren").size()));
        izvestaj.setBrojOdbijenihZahteva(String.valueOf(getAllApprovedRequestInDateRange(dateRange, "Odbijen").size()));
        izvestaj.setNaslov(String.format("Izveštaj o broju zahteva za priznanje patenta u periodu od %s do %s", dateRange.getStartDate(), dateRange.getEndDate()));
        izvestajService.getIzvestajPdf(izvestaj, "Patent_Izvestaj_" + dateRange.getStartDate() + "_" + dateRange.getEndDate() + ".pdf");
    }

    public List<ZahtevZaIntelektualnuSvojinu> getAllApprovedRequestInDateRange(DateRangeDto dateRange, String status) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaIntelektualnuSvojinu> list = retrieveAllInDateRange(dateRange);
        List<ZahtevZaIntelektualnuSvojinu> list1 = new ArrayList<>();
        for (ZahtevZaIntelektualnuSvojinu zahtev : list) {
            if (zahtev.getResenje() == null) {
                continue;
            } else {
                if (zahtev.getResenje().getStatus().equals(status)) {
                    list1.add(zahtev);
                }
            }
        }
        return list1;
    }


    public List<ZahtevZaIntelektualnuSvojinu> retrieveAllInDateRange(DateRangeDto dateRangeDto) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaIntelektualnuSvojinu> list = repository.retrieveAll();
        List<ZahtevZaIntelektualnuSvojinu> list1 = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(dateRangeDto.getStartDate(), dateTimeFormatter);
        LocalDate end = LocalDate.parse(dateRangeDto.getEndDate(), dateTimeFormatter);
        for (ZahtevZaIntelektualnuSvojinu intelektualnuSvojinu : list) {
            LocalDate datumPodnosenja = convertGregorianToLocalDateTime(intelektualnuSvojinu.getPopunjavaZavod().getDatumPodnosenja());
            if (start.isBefore(datumPodnosenja) && end.isAfter(datumPodnosenja)) {
                list1.add(intelektualnuSvojinu);
            }
        }
        return list1;
    }

    public LocalDate convertGregorianToLocalDateTime(XMLGregorianCalendar xgc) {
        ZonedDateTime utcZoned = xgc.toGregorianCalendar().toZonedDateTime().withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned.toLocalDate();
    }
}
