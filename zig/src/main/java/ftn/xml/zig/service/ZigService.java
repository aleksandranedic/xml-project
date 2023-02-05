package ftn.xml.zig.service;

import ftn.xml.zig.model.izvestaj.Izvestaj;
import ftn.xml.zig.dto.*;
import ftn.xml.zig.model.ZahtevZaPriznanjeZiga;
import ftn.xml.zig.repository.RdfRepository;
import ftn.xml.zig.repository.ZigRepository;
import ftn.xml.zig.utils.AuthenticationUtilitiesMetadata;
import ftn.xml.zig.utils.SchemaValidationEventHandler;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
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
public class ZigService {

    public static final String CONTEXT_PATH = "ftn.xml.zig.model";
    private static final String SCHEMA_PATH = "./src/main/resources/data/xsd/zig_schema.xsd";
    private static final String FILE_FOLDER = "./src/main/resources/data/files/";
    private final ZigRepository repository;
    private final RdfRepository rdfRepository;
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;

    private final ZahtevMapper mapper;

    private final TransformationService trasformationService;

    private static final String TARGET_FOLDER = "./target/classes/data/files/";

    private final AuthenticationUtilitiesMetadata.ConnectionProperties conn;

    private final QueryService queryService;

    private final IzvestajService izvestajService;

    @Autowired
    public ZigService(ZigRepository repository, IzvestajService izvestajService, RdfRepository rdfRepository, ZahtevMapper mapper, TransformationService trasformationService, QueryService queryService) throws SAXException, JAXBException, IOException {
        this.repository = repository;
        this.rdfRepository = rdfRepository;
        this.izvestajService = izvestajService;
        this.mapper = mapper;
        this.trasformationService = trasformationService;
        this.queryService = queryService;
        this.conn = AuthenticationUtilitiesMetadata.loadProperties();


        JAXBContext context = JAXBContext.newInstance(CONTEXT_PATH);

        unmarshaller = context.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(SCHEMA_PATH));
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new SchemaValidationEventHandler());

        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    }

    public ZahtevZaPriznanjeZiga getZahtev(String brojPrijave) {
        try {
            return repository.retrieve(brojPrijave + ".xml");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void unmarshall() throws JAXBException {
        unmarshall("./src/main/resources/data/xml/p-1.xml");
    }

    public String getPdf(String brojPrijave) throws JAXBException {
        return trasformationService.toPDF(marshal(getZahtev(brojPrijave)), brojPrijave + ".pdf");
    }

    public String getHtml(String brojPrijave) throws JAXBException {
        return trasformationService.toXHTML(marshal(getZahtev(brojPrijave)), brojPrijave + ".html");
    }

    public ZahtevZaPriznanjeZiga unmarshall(String path) throws JAXBException {
        return (ZahtevZaPriznanjeZiga) unmarshaller.unmarshal(new File(path));
    }

    public OutputStream save(String path) throws Exception {
        ZahtevZaPriznanjeZiga zahtev = (ZahtevZaPriznanjeZiga) unmarshaller.unmarshal(new File("./src/main/resources/data/xml/" + path));
        return save(zahtev);
    }

    public OutputStream save(ZahtevZaPriznanjeZiga zahtev) throws Exception {

        OutputStream os = marshal(zahtev);
        repository.store(zahtev.getBrojPrijaveZiga() + ".xml", os);
        return os;
    }

    public OutputStream marshal(ZahtevZaPriznanjeZiga zahtev) throws JAXBException {
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(zahtev, os);
        return os;
    }

    public void createRdf(String file_path) {
        try {
            ZahtevZaPriznanjeZiga zahtev = getZahtev(file_path);
            ByteArrayOutputStream zahtev_xml_out = (ByteArrayOutputStream) marshal(zahtev);
            InputStream zahtev_input = new ByteArrayInputStream(zahtev_xml_out.toByteArray());
            ByteArrayOutputStream zahtev_output = new ByteArrayOutputStream();
            rdfRepository.extractMetadata(zahtev_input, zahtev_output);
            rdfRepository.writeRdf(zahtev_output.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ZahtevZaPriznanjeZiga> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return repository.retrieveAll();
    }

    public void deleteRequest(int brojPrijave) throws Exception {
        repository.remove(brojPrijave + ".xml");
    }

    public String save(Zahtev zahtev) throws Exception {
        ZahtevZaPriznanjeZiga zahtevZaPriznanjeZiga = mapper.parseZahtev(zahtev);
        save(zahtevZaPriznanjeZiga);
        addRdf(zahtevZaPriznanjeZiga);
        createJsonFromRdf(zahtevZaPriznanjeZiga.getBrojPrijaveZiga());
        createRdfFromRdf(zahtevZaPriznanjeZiga.getBrojPrijaveZiga());
        this.trasformationService.toXHTML(marshal(getZahtev(zahtevZaPriznanjeZiga.getBrojPrijaveZiga())), zahtevZaPriznanjeZiga.getBrojPrijaveZiga() + ".html");
        this.trasformationService.toPDF(marshal(getZahtev(zahtevZaPriznanjeZiga.getBrojPrijaveZiga())), zahtevZaPriznanjeZiga.getBrojPrijaveZiga() + ".pdf");
        return zahtevZaPriznanjeZiga.getBrojPrijaveZiga();
    }

    private void addRdf(ZahtevZaPriznanjeZiga zahtev) throws JAXBException, TransformerException {
        ByteArrayOutputStream zahtev_xml_out = (ByteArrayOutputStream) marshal(zahtev);

        InputStream zahtev_input = new ByteArrayInputStream(zahtev_xml_out.toByteArray());
        ByteArrayOutputStream zahtev_output = new ByteArrayOutputStream();

        rdfRepository.extractMetadata(zahtev_input, zahtev_output);
        rdfRepository.writeRdf(zahtev_output.toString());
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

    public List<ZahtevZaPriznanjeZiga> getAllResolved(String email) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaPriznanjeZiga> list = repository.retrieveAll();
        List<ZahtevZaPriznanjeZiga> list1 = new ArrayList<>();
        for (ZahtevZaPriznanjeZiga zahtev :
                list) {
            if (email.equals(zahtev.getPopunjavaPodnosilac().getPodnosilac().getKontakt().getEmail()) && zahtev.getResenje() != null) {
                list1.add(zahtev);
            }
        }
        return list1;
    }

    public void updateRequest(ResenjeDTO resenje) {
        ZahtevZaPriznanjeZiga zahtev = getZahtev(resenje.getBrojPrijave());
        try {
            zahtev.setResenje(mapper.parseResenje(resenje));
            save(zahtev);
            izvestajService.getResnjePdf(zahtev.getResenje(),"Resenje.pdf");
            createJsonFromRdf(zahtev.getBrojPrijaveZiga());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String createIzvestaj(DateRangeDto dateRange) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException {
        Izvestaj izvestaj = new Izvestaj();
        izvestaj.setBrojPodnetihZahteva(String.valueOf(retrieveAllInDateRange(dateRange).size()));
        izvestaj.setBrojOdobrenihZahteva(String.valueOf(getAllApprovedRequestInDateRange(dateRange, "Odobren").size()));
        izvestaj.setBrojOdbijenihZahteva(String.valueOf(getAllApprovedRequestInDateRange(dateRange, "Odbijen").size()));
        izvestaj.setNaslov(String.format("Izveštaj o broju zahteva za priznanje patenta u periodu od %s do %s", dateRange.getStartDate(), dateRange.getEndDate()));
        izvestajService.getIzvestajPdf(izvestaj, "Zig_Izvestaj_" + dateRange.getStartDate() + "_" + dateRange.getEndDate() + ".pdf");
        return "Zig_Izvestaj_" + dateRange.getStartDate() + "_" + dateRange.getEndDate() + ".pdf";
    }

    public List<ZahtevZaPriznanjeZiga> getAllApprovedRequestInDateRange(DateRangeDto dateRange, String status) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaPriznanjeZiga> list = retrieveAllInDateRange(dateRange);
        List<ZahtevZaPriznanjeZiga> list1 = new ArrayList<>();
        for (ZahtevZaPriznanjeZiga zahtev : list) {
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


    public List<ZahtevZaPriznanjeZiga> retrieveAllInDateRange(DateRangeDto dateRangeDto) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaPriznanjeZiga> list = repository.retrieveAll();
        List<ZahtevZaPriznanjeZiga> list1 = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(dateRangeDto.getStartDate(), dateTimeFormatter);
        LocalDate end = LocalDate.parse(dateRangeDto.getEndDate(), dateTimeFormatter);
        for (ZahtevZaPriznanjeZiga zig : list) {
            LocalDate datumPodnosenja = convertGregorianToLocalDateTime(zig.getDatumPodnosenja());
            if (start.isBefore(datumPodnosenja) && end.isAfter(datumPodnosenja)) {
                list1.add(zig);
            }
        }
        return list1;
    }

    public LocalDate convertGregorianToLocalDateTime(XMLGregorianCalendar xgc) {
        ZonedDateTime utcZoned = xgc.toGregorianCalendar().toZonedDateTime().withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned.toLocalDate();
    }

    public OutputStream marshalResenje(ZahtevZaPriznanjeZiga.Resenje resenje) throws JAXBException {
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(resenje, os);
        return os;
    }


    public List<ZahtevZaPriznanjeZiga> getAllUnresolved(String email) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaPriznanjeZiga> list = repository.retrieveAll();
        List<ZahtevZaPriznanjeZiga> list1 = new ArrayList<>();
        for (ZahtevZaPriznanjeZiga zahtev : list) {
            if (email.equals(zahtev.getPopunjavaPodnosilac().getPodnosilac().getKontakt().getEmail()) && zahtev.getResenje() == null) {
                list1.add(zahtev);
            }
        }
        return list1;
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
