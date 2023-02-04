package ftn.xml.patent.service;

import ftn.xml.patent.dto.Resenje;
import ftn.xml.patent.dto.Zahtev;
import ftn.xml.patent.dto.ZahtevData;
import ftn.xml.patent.dto.ZahtevMapper;
import ftn.xml.patent.model.ZahtevZaPriznanjePatenta;
import ftn.xml.patent.model.izvestaj.Izvestaj;
import ftn.xml.patent.repository.PatentRepository;
import ftn.xml.patent.repository.RdfRepository;
import ftn.xml.patent.utils.SchemaValidationEventHandler;
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
public class PatentService {

    public static final String CONTEXT_PATH = "ftn.xml.patent.model";
    private static final String SCHEMA_PATH = "./src/main/resources/data/xsd/p-1.xsd";
    private static final String FILE_FOLDER = "./src/main/resources/data/files/";
    private final PatentRepository repository;
    private final RdfRepository rdfRepository;
    private final TransformationService trasformationService;
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;
    private final ZahtevMapper mapper;
    private final IzvestajService izvestajService;

    @Autowired
    public PatentService(PatentRepository repository, RdfRepository rdfRepository, TransformationService trasformationService, ZahtevMapper mapper, IzvestajService izvestajService) throws SAXException, JAXBException {
        this.repository = repository;
        this.rdfRepository = rdfRepository;
        this.trasformationService = trasformationService;
        this.mapper = mapper;
        this.izvestajService = izvestajService;

        JAXBContext context = JAXBContext.newInstance(CONTEXT_PATH);

        unmarshaller = context.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(SCHEMA_PATH));
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new SchemaValidationEventHandler());

        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }

    public ZahtevData getZahtevData(String brojPrijave) {
        try {
            return mapper.parseZahtev(repository.retrieve(brojPrijave + ".xml"), getHtmlString(brojPrijave));
        } catch (Exception e) {
            throw new RuntimeException("Ne postoji zahtev za zadatim brojem prijave.");
        }
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

    private void addRdf(ZahtevZaPriznanjePatenta zahtev) throws JAXBException, TransformerException {
        ByteArrayOutputStream zahtev_xml_out = (ByteArrayOutputStream) marshal(zahtev);

        InputStream zahtev_input = new ByteArrayInputStream(zahtev_xml_out.toByteArray());
        ByteArrayOutputStream zahtev_output = new ByteArrayOutputStream();

        rdfRepository.extractMetadata(zahtev_input, zahtev_output);
        rdfRepository.writeRdf(zahtev_output.toString());
    }

    public List<ZahtevData> getAll() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return repository.retrieveAll().stream().map(zahtevZaPriznanjePatenta -> {
            return mapper.parseZahtev(zahtevZaPriznanjePatenta, getHtmlString(zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave()));
        }).toList();
    }

    public void deleteRequest(String brojPrijave) throws Exception {
        repository.remove(brojPrijave + ".xml");
    }

    public void save(Zahtev zahtev) throws Exception {
        ZahtevZaPriznanjePatenta zahtevZaPriznanjePatenta = mapper.parseZahtev(zahtev);

        System.out.println("Ovde");
        //save(zahtevZaPriznanjePatenta);
        //addRdf(zahtevZaPriznanjePatenta);
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

    public void updateRequest(String brojPrijave, Resenje resenje) {
        ZahtevZaPriznanjePatenta zahtevZaPriznanjePatenta = getZahtev(brojPrijave);
        try {
            zahtevZaPriznanjePatenta.setResenje(mapper.parseResenje(resenje));
            save(zahtevZaPriznanjePatenta);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ZahtevData> getAllResolved() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return repository.retrieveAllWithResenje().stream().map(zahtevZaPriznanjePatenta -> mapper.parseZahtev(zahtevZaPriznanjePatenta, getHtmlString(zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave()))).toList();
    }

    public List<ZahtevData> getAllUnresolved() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return repository.retrieveAllWithoutResenje().stream().map(zahtevZaPriznanjePatenta -> mapper.parseZahtev(zahtevZaPriznanjePatenta, getHtmlString(zahtevZaPriznanjePatenta.getPopunjavaZavod().getBrojPrijave()))).toList();
    }

    public String getIzvestajPdf(String startDate, String endDate) throws JAXBException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Izvestaj izvestaj = new Izvestaj();
        izvestaj.setBrojPodnetihZahteva(String.valueOf(repository.retrieveAllWithinDatePeriod(startDate, endDate).size()));
        izvestaj.setBrojOdobrenihZahteva(String.valueOf(repository.retrieveAllWithResenjeStatus(startDate, endDate, "Odobreno").size()));
        izvestaj.setBrojOdbijenihZahteva(String.valueOf(repository.retrieveAllWithResenjeStatus(startDate, endDate, "Odbijeno").size()));
        izvestaj.setNaslov(String.format("Izveštaj o broju zahteva za priznanje patenta u periodu od %s do %s", startDate, endDate));
        return izvestajService.getIzvestajPdf(izvestaj, "Patent_Izvestaj_" + startDate + "_" + endDate + ".pdf");
    }
}
