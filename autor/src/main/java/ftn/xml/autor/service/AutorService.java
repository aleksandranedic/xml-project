package ftn.xml.autor.service;

import ftn.xml.autor.dto.Zahtev;
import ftn.xml.autor.dto.ZahtevMapper;
import ftn.xml.autor.model.ZahtevZaIntelektualnuSvojinu;
import ftn.xml.autor.repository.AutorRepository;
import ftn.xml.autor.repository.RdfRepository;
import ftn.xml.autor.utils.SchemaValidationEventHandler;
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
import java.util.List;

@Service
public class AutorService {
    public static final String CONTEXT_PATH = "ftn.xml.autor.model";
    private static final String SCHEMA_PATH = "./src/main/resources/data/xsd/autor.xsd";
    private final AutorRepository repository;
    private final RdfRepository rdfRepository;
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;
    private final ZahtevMapper mapper;

    @Autowired
    public AutorService(AutorRepository repository, RdfRepository rdfRepository, ZahtevMapper mapper) throws SAXException, JAXBException {
        this.repository = repository;
        this.rdfRepository = rdfRepository;
        this.mapper = mapper;

        JAXBContext context = JAXBContext.newInstance(CONTEXT_PATH);

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

    public ZahtevZaIntelektualnuSvojinu unmarshall(String path) throws JAXBException{
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

    private void addRdf(ZahtevZaIntelektualnuSvojinu zahtev) throws JAXBException, TransformerException {
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
    }
}
