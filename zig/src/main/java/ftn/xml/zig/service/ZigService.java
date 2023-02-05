package ftn.xml.zig.service;

import ftn.xml.zig.dto.Zahtev;
import ftn.xml.zig.dto.ZahtevData;
import ftn.xml.zig.dto.ZahtevMapper;
import ftn.xml.zig.model.ZahtevZaPriznanjeZiga;
import ftn.xml.zig.repository.RdfRepository;
import ftn.xml.zig.repository.ZigRepository;
import ftn.xml.zig.utils.SchemaValidationEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.xmldb.api.base.XMLDBException;

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

    @Autowired
    public ZigService(ZigRepository repository, RdfRepository rdfRepository, ZahtevMapper mapper, TransformationService trasformationService) throws SAXException, JAXBException {
        this.repository = repository;
        this.rdfRepository = rdfRepository;
        this.mapper = mapper;
        this.trasformationService = trasformationService;

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

    public ZahtevZaPriznanjeZiga unmarshall(String path) throws JAXBException{
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
    public List<ZahtevZaPriznanjeZiga> getAllResolved() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaPriznanjeZiga> list = repository.retrieveAll();
        list.stream().filter(zahtevZaIntelektualnuSvojinu -> {
            try {
                zahtevZaIntelektualnuSvojinu.getResenje();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        //TODO:Filtriraj da li ima resenje
        return list;
    }

    public List<ZahtevZaPriznanjeZiga> getAllUnresolved() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<ZahtevZaPriznanjeZiga> list = repository.retrieveAll();
        list.stream().filter(zahtevZaIntelektualnuSvojinu -> {
            try {
                zahtevZaIntelektualnuSvojinu.getResenje();
                return false;
            } catch (Exception e) {
                return true;
            }
        });
        //TODO:Filtriraj da nema resenje
        return list;
    }
}
