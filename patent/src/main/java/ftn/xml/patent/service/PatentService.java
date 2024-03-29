package ftn.xml.patent.service;

import ftn.xml.patent.dto.*;
import ftn.xml.patent.model.ZahtevZaPriznanjePatenta;
import ftn.xml.patent.model.izvestaj.Izvestaj;
import ftn.xml.patent.repository.PatentRepository;
import ftn.xml.patent.repository.RdfRepository;
import ftn.xml.patent.utils.AuthenticationUtilitiesMetadata;
import ftn.xml.patent.utils.SchemaValidationEventHandler;
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
public class PatentService {

    public static final String CONTEXT_PATH = "ftn.xml.patent.model";
    private static final String SCHEMA_PATH = "./src/main/resources/data/xsd/p-1.xsd";
    private static final String FILE_FOLDER = "./src/main/resources/data/files/";

    private static final String TARGET_FOLDER = "./target/classes/data/files/";

    private final PatentRepository repository;
    private final RdfRepository rdfRepository;
    private final TransformationService trasformationService;
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;
    private final ZahtevMapper mapper;
    private final IzvestajService izvestajService;
    private final AuthenticationUtilitiesMetadata.ConnectionProperties conn;

    private final QueryService queryService;

    @Autowired
    public PatentService(PatentRepository repository, RdfRepository rdfRepository, TransformationService trasformationService, ZahtevMapper mapper, IzvestajService izvestajService, QueryService queryService) throws SAXException, JAXBException, IOException {
        this.repository = repository;
        this.rdfRepository = rdfRepository;
        this.trasformationService = trasformationService;
        this.mapper = mapper;
        this.izvestajService = izvestajService;
        this.conn = AuthenticationUtilitiesMetadata.loadProperties();
        this.queryService = queryService;

        JAXBContext context = JAXBContext.newInstance(CONTEXT_PATH);

        unmarshaller = context.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(SCHEMA_PATH));
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new SchemaValidationEventHandler());

        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }


    public ZahtevZaPriznanjePatenta getZahtev(String brojPrijave) {
        try {
            return repository.retrieve(brojPrijave + ".xml");
        } catch (Exception e) {
            throw new RuntimeException("Ne postoji zahtev za zadatim brojem prijave.");
        }
    }


    public void unmarshall() throws JAXBException {
        unmarshall("./src/main/resources/data/xml/p-1.xml");
    }

    public ZahtevZaPriznanjePatenta unmarshall(String path) throws JAXBException {
        return (ZahtevZaPriznanjePatenta) unmarshaller.unmarshal(new File(path));
    }

    public OutputStream save(String path) throws Exception {
        ZahtevZaPriznanjePatenta zahtev = (ZahtevZaPriznanjePatenta) unmarshaller.unmarshal(new File("./src/main/resources/data/xml/" + path));
        return save(zahtev);
    }

    public OutputStream save(ZahtevZaPriznanjePatenta zahtev) throws Exception {
        OutputStream os = marshal(zahtev);
        repository.store(zahtev.getPopunjavaZavod().getBrojPrijave() + ".xml", os);
        return os;
    }

    public OutputStream marshal(ZahtevZaPriznanjePatenta zahtev) throws JAXBException {
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(zahtev, os);
        return os;
    }

    public void addRdf(String file_path) {
        try {
            ZahtevZaPriznanjePatenta zahtev = getZahtev(file_path);
            addRdf(zahtev);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addRdf(ZahtevZaPriznanjePatenta zahtev) throws JAXBException, TransformerException, IOException {
        ByteArrayOutputStream zahtev_xml_out = (ByteArrayOutputStream) marshal(zahtev);

        InputStream zahtev_input = new ByteArrayInputStream(zahtev_xml_out.toByteArray());
        ByteArrayOutputStream zahtev_output = new ByteArrayOutputStream();

        rdfRepository.extractMetadata(zahtev_input, zahtev_output);
        rdfRepository.writeRdf(zahtev_output.toString());
    }

    public List<ZahtevZaPriznanjePatenta> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return repository.retrieveAll();
    }

    public void deleteRequest(String brojPrijave) throws Exception {
        repository.remove(brojPrijave + ".xml");
    }

    public String save(Zahtev zahtev) throws Exception {
        ZahtevZaPriznanjePatenta zahtevZaPriznanjePatenta = mapper.parseZahtev(zahtev);
        save(zahtevZaPriznanjePatenta);
        addRdf(zahtevZaPriznanjePatenta);
        createJsonFromRdf(zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave());
        createRdfFromRdf(zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave());
        trasformationService.toXHTML(marshal(getZahtev(zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave())), zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave() + ".html");
        trasformationService.toPDF(marshal(getZahtev(zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave())), zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave() + ".pdf");
        return zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave();
    }

    public String getPdf(String brojPrijave) throws JAXBException {
        return trasformationService.toPDF(marshal(getZahtev(brojPrijave)), brojPrijave + ".pdf");
    }

    public String getHtml(String brojPrijave) throws JAXBException {
        return trasformationService.toXHTML(marshal(getZahtev(brojPrijave)), brojPrijave + ".html");
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

    public void updateRequest(Resenje resenje) {
        ZahtevZaPriznanjePatenta zahtevZaPriznanjePatenta = getZahtev(resenje.getBrojPrijave());
        try {
            zahtevZaPriznanjePatenta.setResenje(mapper.parseResenje(resenje));
            zahtevZaPriznanjePatenta.getPopunjavaZavod().setBrojPrijave("P-" + zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave());
            save(zahtevZaPriznanjePatenta);
            izvestajService.getResnjePdf(zahtevZaPriznanjePatenta.getResenje(),"Resenje.pdf");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ZahtevZaPriznanjePatenta> getAllResolved(String email) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaPriznanjePatenta> list = repository.retrieveAll();
        List<ZahtevZaPriznanjePatenta> list1 = new ArrayList<>();
        for (ZahtevZaPriznanjePatenta zahtev :
                list) {
            if (email.equals(zahtev.getPopunjavaPodnosioc().getPodaciOPodnosiocu().getPodnosioc().getKontakt().getEPosta()) && zahtev.getResenje() != null) {
                list1.add(zahtev);
            }
        }
        return list1;
    }

    public List<ZahtevZaPriznanjePatenta> getAllUnresolved(String email) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaPriznanjePatenta> list = repository.retrieveAll();
        List<ZahtevZaPriznanjePatenta> list1 = new ArrayList<>();
        for (ZahtevZaPriznanjePatenta zahtev :
                list) {
            if (email.equals(zahtev.getPopunjavaPodnosioc().getPodaciOPodnosiocu().getPodnosioc().getKontakt().getEPosta()) && zahtev.getResenje() == null) {
                list1.add(zahtev);
            }
        }
        return list1;
    }

    public String getIzvestajPdf(String startDate, String endDate) throws JAXBException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Izvestaj izvestaj = new Izvestaj();
        izvestaj.setBrojPodnetihZahteva(String.valueOf(repository.retrieveAllWithinDatePeriod(startDate, endDate).size()));
        izvestaj.setBrojOdobrenihZahteva(String.valueOf(repository.retrieveAllWithResenjeStatus(startDate, endDate, "Odobreno").size()));
        izvestaj.setBrojOdbijenihZahteva(String.valueOf(repository.retrieveAllWithResenjeStatus(startDate, endDate, "Odbijeno").size()));
        izvestaj.setNaslov(String.format("Izveštaj o broju zahteva za priznanje patenta u periodu od %s do %s", startDate, endDate));
        return "http://localhost:8002/"+izvestajService.getIzvestajPdf(izvestaj, "Patent_Izvestaj_" + startDate + "_" + endDate + ".pdf");
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
        outputStream1.flush();
        outputStream1.close();
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

    public String createIzvestaj(DateRangeDto dateRange) throws JAXBException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Izvestaj izvestaj = new Izvestaj();
        izvestaj.setBrojPodnetihZahteva(String.valueOf(retrieveAllInDateRange(dateRange).size()));
        izvestaj.setBrojOdobrenihZahteva(String.valueOf(getAllApprovedRequestInDateRange(dateRange, "Odobren").size()));
        izvestaj.setBrojOdbijenihZahteva(String.valueOf(getAllApprovedRequestInDateRange(dateRange, "Odbijen").size()));
        izvestaj.setNaslov(String.format("Izveštaj o broju zahteva za priznanje patenta u periodu od %s do %s", dateRange.getStartDate(), dateRange.getEndDate()));
        return izvestajService.getIzvestajPdf(izvestaj, "Patent_Izvestaj_" + dateRange.getStartDate() + "_" + dateRange.getEndDate() + ".pdf");

    }

    public List<ZahtevZaPriznanjePatenta> getAllApprovedRequestInDateRange(DateRangeDto dateRange, String status) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaPriznanjePatenta> list = retrieveAllInDateRange(dateRange);
        List<ZahtevZaPriznanjePatenta> list1 = new ArrayList<>();
        for (ZahtevZaPriznanjePatenta zahtev : list) {
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


    public List<ZahtevZaPriznanjePatenta> retrieveAllInDateRange(DateRangeDto dateRangeDto) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaPriznanjePatenta> list = repository.retrieveAll();
        List<ZahtevZaPriznanjePatenta> list1 = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(dateRangeDto.getStartDate(), dateTimeFormatter);
        LocalDate end = LocalDate.parse(dateRangeDto.getEndDate(), dateTimeFormatter);
        for (ZahtevZaPriznanjePatenta intelektualnuSvojinu : list) {
            LocalDate datumPodnosenja = convertGregorianToLocalDateTime(intelektualnuSvojinu.getPopunjavaZavod().getDatumPrijema());
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
